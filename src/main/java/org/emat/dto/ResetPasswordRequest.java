package org.emat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.RsaStringDecryptDeserializer;
import tools.jackson.databind.annotation.JsonDeserialize;

/**
 * Request DTO for {@code POST /auth/reset-password}. Requires the {@code resetToken} returned by
 * {@code POST /auth/verify-otp}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Reset the password using the reset token from POST /auth/verify-otp")
public class ResetPasswordRequest {

    @Schema(description = "Registered email (plain text)", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "Reset token returned by POST /auth/verify-otp", requiredMode = Schema.RequiredMode.REQUIRED)
    private String resetToken;

    @Schema(description = "New password (RSA-OAEP/SHA-256 encrypted with the key from GET /auth/public-key, base64)", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = RsaStringDecryptDeserializer.class)
    private String newPassword;

    @Schema(description = "Must match newPassword (RSA-OAEP/SHA-256 encrypted with the key from GET /auth/public-key, base64)", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = RsaStringDecryptDeserializer.class)
    private String confirmPassword;
}
