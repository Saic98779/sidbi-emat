package org.emat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiStringDecryptDeserializer;
import tools.jackson.databind.annotation.JsonDeserialize;

/** Request DTO for {@code POST /auth/forgot-password}. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request a password-reset OTP for a registered email")
public class ForgotPasswordRequest {

    @Schema(description = "Registered email (PII-encrypted ENC:... value)", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String email;
}
