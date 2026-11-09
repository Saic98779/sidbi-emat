package org.emat.exception;

/**
 * Thrown when a forgot/reset-password step fails validation: invalid or expired OTP or reset token,
 * too many attempts, a resend requested too soon, or a password that violates the policy.
 */
public class PasswordResetException extends RuntimeException {

    public PasswordResetException(String message) {
        super(message);
    }
}
