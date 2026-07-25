package org.emat.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.emat.dto.CreateUserRequest;
import org.emat.dto.LoginRequest;
import org.emat.dto.LoginResponse;
import org.emat.dto.UserResponse;
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
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        UserResponse user = userService.createUser(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Get all users.
     * GET /api/users
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Get users by district and/or state.
     * GET /api/users/search?district={district}&state={state}
     * Both parameters are optional. You can search by district only, state only, or both.
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> getUsersByLocation(
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String state) {

        List<UserResponse> users;

        if (district != null && state != null) {
            // Search by both district and state
            users = userService.getUsersByDistrictAndState(district, state);
        } else if (district != null) {
            // Search by district only
            users = userService.getUsersByDistrict(district);
        } else if (state != null) {
            // Search by state only
            users = userService.getUsersByState(state);
        } else {
            // No parameters provided, return all users
            users = userService.getAllUsers();
        }

        return ResponseEntity.ok(users);
    }

    /**
     * Login endpoint.
     * POST /api/users/login
     */
    @PostMapping("/login")
    @SecurityRequirement(name = "")  // This endpoint doesn't require authentication
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
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
        return ResponseEntity.ok(response);
    }
}
