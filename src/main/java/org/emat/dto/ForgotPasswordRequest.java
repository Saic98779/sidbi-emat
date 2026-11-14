package org.emat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Request DTO for {@code POST /auth/forgot-password}. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request a password-reset OTP for a registered email")
public class ForgotPasswordRequest {

    @Schema(description = "Registered email (plain text)", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
}
