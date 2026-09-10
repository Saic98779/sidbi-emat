package org.emat.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateUserRequest;
import org.emat.dto.LoginRequest;
import org.emat.dto.LoginResponse;
import org.emat.dto.UserResponse;
import org.emat.enums.Role;
import org.emat.service.JwtService;
import org.emat.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

/**
 * REST Controller for User endpoints.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Create a new user.
     * POST /api/users
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("User created successfully", userService.createUser(request)));
    }

    /**
     * Get all users.
     * GET /api/users
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", userService.getAllUsers()));
    }

    /**
     * Get users by district and/or state and/or role.
     * GET /api/users/search?district={district}&state={state}&role={role}
     * All parameters are optional. You can search by any combination.
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

    /**
     * Login endpoint.
     * POST /users/login
     */
    @PostMapping("/login")
    @SecurityRequirement(name = "")  // This endpoint doesn't require authentication
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        if (request.getUsername() == null || request.getPassword() == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(request.getUsername(), request.getPassword())
        );

        String token = jwtService.generateToken(request.getUsername());
        Instant expiresAt = jwtService.getExpiryFromNow();
        UserResponse user = userService.getUserByUsername(request.getUsername());
        LoginResponse response = new LoginResponse(
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
                user.isActive()
        );
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @GetMapping("/by-role")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsersByRole(@RequestParam Role role) {
        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", userService.getUsersByRole(role)));
    }
}
