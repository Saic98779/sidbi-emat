package org.emat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.RsaStringDecryptDeserializer;
import tools.jackson.databind.annotation.JsonDeserialize;

/** Request DTO for {@code POST /users/change-password} (logged-in user). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Change the logged-in user's password using the current password")
public class ChangePasswordRequest {

    @Schema(description = "Registered email of the logged-in user (plain text)", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "Current password (RSA-OAEP/SHA-256 encrypted with the key from GET /auth/public-key, base64)", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = RsaStringDecryptDeserializer.class)
    private String currentPassword;

    @Schema(description = "New password (RSA-OAEP/SHA-256 encrypted with the key from GET /auth/public-key, base64)", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = RsaStringDecryptDeserializer.class)
    private String newPassword;

    @Schema(description = "Must match newPassword (RSA-OAEP/SHA-256 encrypted with the key from GET /auth/public-key, base64)", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = RsaStringDecryptDeserializer.class)
    private String confirmPassword;
}
