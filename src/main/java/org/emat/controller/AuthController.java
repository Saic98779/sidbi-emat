package org.emat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.emat.dto.ApiResponse;
import org.emat.dto.ForgotPasswordRequest;
import org.emat.dto.LoginPublicKeyResponse;
import org.emat.dto.ResetPasswordRequest;
import org.emat.dto.VerifyOtpRequest;
import org.emat.dto.VerifyOtpResponse;
import org.emat.service.PasswordResetService;
import org.emat.util.LoginRsaKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Pre-authentication endpoints ({@code /auth/...}): login public key and forgot / reset password.
 *
 * <p>The {@code /auth/public-key} endpoint returns the RSA public key the frontend uses to encrypt
 * the {@code password} field of {@code POST /users/login} <strong>before any JWT is available</strong>.
 * The corresponding RSA private key stays on the server (in the {@link LoginRsaKeyService}).
 */
@RestController
@RequestMapping("/auth")
@Tag(
        name = "Pre-authentication",
        description = "Public endpoints available before login (no JWT required)")
@SecurityRequirement(name = "")
public class AuthController {

    private final LoginRsaKeyService rsaKeyService;
    private final PasswordResetService passwordResetService;

    public AuthController(
            LoginRsaKeyService rsaKeyService, PasswordResetService passwordResetService) {
        this.rsaKeyService = rsaKeyService;
        this.passwordResetService = passwordResetService;
    }

    /**
     * Step 1 of forgot password: emails a 6-digit OTP to the registered email.
     *
     * <p>Always answers with the same message, whether or not the email is registered, so the
     * endpoint can't be used to find out which emails have accounts.
     */
    @PostMapping("/forgot-password")
    @Operation(
            summary = "Request a password-reset OTP",
            description =
                    "Emails a 6-digit OTP (valid 5 minutes) to the registered email. Returns the"
                        + " same response for unknown emails. A new OTP can be requested every 60"
                        + " seconds.")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {
        passwordResetService.forgotPassword(request);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "If the email is registered, an OTP has been sent to it.", null));
    }

    /** Step 2 of forgot password: checks the OTP and returns a single-use reset token. */
    @PostMapping("/verify-otp")
    @Operation(
            summary = "Verify the password-reset OTP",
            description =
                    "Checks the OTP (5 wrong attempts cancel it) and returns a single-use reset"
                            + " token valid for 10 minutes, to pass to POST /auth/reset-password.")
    public ResponseEntity<ApiResponse<VerifyOtpResponse>> verifyOtp(
            @RequestBody VerifyOtpRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "OTP verified successfully", passwordResetService.verifyOtp(request)));
    }

    /** Step 3 of forgot password: sets the new password using the reset token (or the OTP). */
    @PostMapping("/reset-password")
    @Operation(
            summary = "Reset the password",
            description =
                    "Sets a new password using the reset token from /auth/verify-otp, or the"
                        + " emailed OTP directly. The password needs 8-64 characters with an"
                        + " uppercase letter, a lowercase letter, a digit and a special"
                        + " character.")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @RequestBody ResetPasswordRequest request) {
        passwordResetService.resetPassword(request);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Password reset successfully. Please log in with your new password.",
                        null));
    }

    /**
     * Returns the RSA public key the frontend must use to encrypt the login password.
     *
     * <p>No authentication is required; the corresponding RSA private key never leaves the server.
     *
     * <pre>
     * GET /auth/public-key
     * 200 {
     *   "status": 200,
     *   "message": "Public key for login password encryption",
     *   "data": {
     *     "publicKey": "&lt;base64 SPKI&gt;",
     *     "algorithm": "RSA-OAEP",
     *     "hash": "SHA-256",
     *     "keySize": 2048
     *   }
     * }
     * </pre>
     */
    @GetMapping("/public-key")
    @Operation(
            summary = "Get RSA public key for login password encryption",
            description =
                    "Returns the base64-encoded SPKI RSA public key the frontend must use with"
                        + " Web Crypto crypto.subtle.importKey('spki', ...) and RSA-OAEP to"
                        + " encrypt the login password before calling POST /users/login."
                        + " No authentication is required.")
    @ApiResponses(
            value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Public key returned successfully")
            })
    public ResponseEntity<ApiResponse<LoginPublicKeyResponse>> getLoginPublicKey() {
        LoginPublicKeyResponse body =
                new LoginPublicKeyResponse(
                        rsaKeyService.getPublicKeyBase64(),
                        rsaKeyService.getAlgorithm(),
                        rsaKeyService.getHash(),
                        rsaKeyService.getRsaKeySize());
        return ResponseEntity.ok(
                ApiResponse.success("Public key for login password encryption", body));
    }
}