package org.emat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiStringDecryptDeserializer;
import tools.jackson.databind.annotation.JsonDeserialize;

/**
 * Request DTO for {@code POST /auth/reset-password}.
 *
 * <p>Either {@code resetToken} (from {@code POST /auth/verify-otp}) or {@code otp} (directly from
 * the email) must be supplied. If both are present the reset token is used.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Reset the password using a verified reset token or the emailed OTP")
public class ResetPasswordRequest {

    @Schema(description = "Registered email (PII-encrypted ENC:... value)", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String email;

    @Schema(description = "Reset token returned by POST /auth/verify-otp")
    private String resetToken;

    @Schema(description = "6-digit OTP (alternative to resetToken)", example = "482913")
    private String otp;

    @Schema(description = "New password (PII-encrypted ENC:... value)", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String newPassword;

    @Schema(description = "Must match newPassword (PII-encrypted ENC:... value)", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String confirmPassword;
}
