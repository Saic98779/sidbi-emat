package org.emat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** Response body for the PII encryption key endpoint. */
@Schema(description = "PII encryption key details")
@Getter
@Setter
@AllArgsConstructor
public class PiiEncryptionKeyResponse {

    @Schema(
            description = "Base64-encoded AES-256 key used for field-level PII encryption",
            example = "aB3x...base64..==")
    private String secretKey;

    @Schema(
            description = "Whether field-level PII encryption is currently enabled",
            example = "true")
    private boolean enabled;
}
