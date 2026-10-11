package org.emat.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.List;
import org.emat.config.AuthCookieProperties;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateUserRequest;
import org.emat.dto.LoginRequest;
import org.emat.dto.LoginResponse;
import org.emat.dto.UserResponse;
import org.emat.enums.Role;
import org.emat.exception.CaptchaValidationException;
import org.emat.service.CaptchaService;
import org.emat.service.JwtService;
import org.emat.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

/** REST Controller for User endpoints. */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CaptchaService captchaService;

    public UserController(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            CaptchaService captchaService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.captchaService = captchaService;
    }

    /** Create a new user. POST /api/users */
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "User created successfully", userService.createUser(request)));
    }

    /** Get all users. GET /api/users */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(
                ApiResponse.success("Users fetched successfully", userService.getAllUsers()));
    }

    /**
     * Get users by district and/or state and/or role. GET
     * /api/users/search?district={district}&state={state}&role={role} All parameters are optional.
     * You can search by any combination.
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsersByLocation(
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) Role role) {

        List<UserResponse> users;

        if (district != null && state != null && role != null) {
            // Search by district, state, and role
            users = userService.getUsersByDistrictAndStateAndRole(district, state, role);
        } else if (district != null && state != null) {
            // Search by both district and state
            users = userService.getUsersByDistrictAndState(district, state);
        } else if (district != null && role != null) {
            // Search by district and role
            users = userService.getUsersByDistrictAndRole(district, role);
        } else if (state != null && role != null) {
            // Search by state and role
            users = userService.getUsersByStateAndRole(state, role);
        } else if (district != null) {
            // Search by district only
            users = userService.getUsersByDistrict(district);
        } else if (state != null) {
            // Search by state only
            users = userService.getUsersByState(state);
        } else if (role != null) {
            // Search by role only
            users = userService.getUsersByRole(role);
        } else {
            // No parameters provided, return all users
            users = userService.getAllUsers();
        }

        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", users));
    }

    /** Login endpoint. POST /users/login */
    @PostMapping("/login")
    @SecurityRequirement(name = "") // This endpoint doesn't require authentication
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {
        if (request.getUsername() == null || request.getPassword() == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (captchaService.isEnabled()
                && !captchaService.consumeAndValidate(
                        request.getCaptchaId(), request.getCaptchaAnswer())) {
            throw new CaptchaValidationException(
                    "Invalid or expired captcha. Please fetch a new captcha and try again.");
        }

        Authentication authentication =
                authenticationManager.authenticate(
                        UsernamePasswordAuthenticationToken.unauthenticated(
                                request.getUsername(), request.getPassword()));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        httpRequest.getSession(true)
                .setAttribute(
                        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

        String token = jwtService.generateToken(request.getUsername());
        Instant expiresAt = jwtService.getExpiryFromNow();
        long maxAgeSeconds =
                Math.max(0L, expiresAt.getEpochSecond() - Instant.now().getEpochSecond());
        ResponseCookie authCookie =
                ResponseCookie.from(AuthCookieProperties.AUTH_COOKIE_NAME, token)
                        .httpOnly(true)
                        .secure(httpRequest.isSecure())
                        .path("/")
                        .sameSite("Strict")
                        .maxAge(maxAgeSeconds)
                        .build();
        httpResponse.addHeader("Set-Cookie", authCookie.toString());

        UserResponse user = userService.getUserByUsername(request.getUsername());
        LoginResponse response =
                new LoginResponse(
                        token,
                        expiresAt,
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getDistrict(),
                        user.getState(),
                        user.getRole(),
                        user.isActive());
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @GetMapping("/by-role")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsersByRole(@RequestParam Role role) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Users fetched successfully", userService.getUsersByRole(role)));
    }

    @PostMapping("/logout")
    @SecurityRequirement(name = "")
    public ResponseEntity<ApiResponse<Void>> logout(
            HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }
        SecurityContextHolder.clearContext();

        ResponseCookie expiredCookie =
                ResponseCookie.from(AuthCookieProperties.AUTH_COOKIE_NAME, "")
                        .httpOnly(true)
                        .secure(request.isSecure())
                        .path("/")
                        .sameSite("Strict")
                        .maxAge(0)
                        .build();
        response.addHeader("Set-Cookie", expiredCookie.toString());

        return ResponseEntity.ok(ApiResponse.success("Logout successful", null));
    }
}
