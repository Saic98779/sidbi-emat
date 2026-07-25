package org.emat.dto;

import org.emat.enums.Role;

import java.time.Instant;

/**
 * Response DTO for login API.
 */
public class LoginResponse {

    private String token;
    private Instant expiresAt;
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String district;
    private String state;
    private Role role;
    private boolean active;

    public LoginResponse() {
    }

    public LoginResponse(String token,
                         Instant expiresAt,
                         Long userId,
                         String username,
                         String email,
                         String firstName,
                         String lastName,
                         String district,
                         String state,
                         Role role,
                         boolean active) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.district = district;
        this.state = state;
        this.role = role;
        this.active = active;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
