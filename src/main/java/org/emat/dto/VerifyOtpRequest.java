package org.emat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiStringDecryptDeserializer;
import tools.jackson.databind.annotation.JsonDeserialize;

/** Request DTO for {@code POST /auth/verify-otp}. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Verify the password-reset OTP sent to the user's email")
public class VerifyOtpRequest {

    @Schema(description = "Registered email (PII-encrypted ENC:... value)", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String email;

    @Schema(description = "6-digit OTP received by email", example = "482913", requiredMode = Schema.RequiredMode.REQUIRED)
    private String otp;
}
