package org.emat.service;

import org.emat.dto.ForgotPasswordRequest;
import org.emat.dto.ResetPasswordRequest;
import org.emat.dto.VerifyOtpRequest;
import org.emat.dto.VerifyOtpResponse;

/**
 * Forgot / reset password flow:
 *
 * <ol>
 *   <li>{@link #forgotPassword} emails a 6-digit OTP to a registered user.
 *   <li>{@link #verifyOtp} validates the OTP and issues a short-lived single-use reset token.
 *   <li>{@link #resetPassword} sets the new password using the reset token (or the OTP directly).
 * </ol>
 *
 * <p>OTPs and reset tokens are only ever stored as hashes.
 */
public interface PasswordResetService {

    /**
     * Generates and emails an OTP if the email belongs to an active user. Completes silently for
     * unknown emails so that the endpoint cannot be used to discover registered accounts.
     */
    void forgotPassword(ForgotPasswordRequest request);

    /** Validates the OTP (consuming it on success) and returns a single-use reset token. */
    VerifyOtpResponse verifyOtp(VerifyOtpRequest request);

    /** Validates the reset token / OTP, encodes and stores the new password, then invalidates it. */
    void resetPassword(ResetPasswordRequest request);
}
