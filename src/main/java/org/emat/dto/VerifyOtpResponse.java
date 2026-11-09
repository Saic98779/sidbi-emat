package org.emat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Response DTO for {@code POST /auth/verify-otp}. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Single-use reset token issued after a successful OTP verification")
public class VerifyOtpResponse {

    @Schema(description = "Opaque single-use token to pass to POST /auth/reset-password")
    private String resetToken;

    @Schema(description = "Seconds until the reset token expires", example = "600")
    private long expiresInSeconds;
}
