package org.emat.service.impl;

import org.emat.exception.PasswordResetException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Rules a new password must meet (used by both reset and change password):
 *
 * <ul>
 *   <li>{@code min-length} (default 8) to 64 characters, no spaces at the start or end;
 *   <li>at least one uppercase letter, one lowercase letter, one digit and one special character;
 *   <li>matches the confirmation;
 *   <li>is not the same as the current password.
 * </ul>
 */
@Component
public class PasswordPolicy {

    private static final int MAX_LENGTH = 64;

    private final PasswordEncoder passwordEncoder;

    @Value("${security.password-reset.min-password-length:8}")
    private int minLength;

    public PasswordPolicy(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Checks length, character mix and confirmation. Does not look at the current password, so it
     * is safe to run before the caller has been verified.
     *
     * @throws PasswordResetException with a user-facing message if a rule is broken
     */
    public void validateFormat(String newPassword, String confirmPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            throw new PasswordResetException("New password is required");
        }
        if (confirmPassword == null || !newPassword.equals(confirmPassword)) {
            throw new PasswordResetException("New password and confirm password do not match");
        }
        if (!newPassword.equals(newPassword.strip())) {
            throw new PasswordResetException("Password must not start or end with a space");
        }
        int min = Math.max(minLength, 8);
        if (newPassword.length() < min || newPassword.length() > MAX_LENGTH) {
            throw new PasswordResetException(
                    "Password must be between " + min + " and " + MAX_LENGTH + " characters");
        }
        boolean upper = false;
        boolean lower = false;
        boolean digit = false;
        boolean special = false;
        for (char c : newPassword.toCharArray()) {
            if (Character.isUpperCase(c)) {
                upper = true;
            } else if (Character.isLowerCase(c)) {
                lower = true;
            } else if (Character.isDigit(c)) {
                digit = true;
            } else if (!Character.isWhitespace(c)) {
                special = true;
            }
        }
        if (!(upper && lower && digit && special)) {
            throw new PasswordResetException(
                    "Password must contain at least one uppercase letter, one lowercase letter,"
                            + " one digit and one special character");
        }
    }

    /**
     * Rejects reusing the current password. Only call this <strong>after</strong> the caller has
     * proved who they are (OTP / reset token / current password); otherwise the error message
     * would let anyone check guesses of the current password.
     *
     * @param currentPasswordHash the user's stored (BCrypt) password
     */
    public boolean isSameAsCurrent(String newPassword, String currentPasswordHash) {
        return currentPasswordHash != null && passwordEncoder.matches(newPassword, currentPasswordHash);
    }

    /** Throws if {@link #isSameAsCurrent} is true. */
    public void ensureDifferent(String newPassword, String currentPasswordHash) {
        if (isSameAsCurrent(newPassword, currentPasswordHash)) {
            throw new PasswordResetException(
                    "New password must be different from the current password");
        }
    }
}
