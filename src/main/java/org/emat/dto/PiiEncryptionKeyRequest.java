package org.emat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/** Request body for retrieving the PII encryption key via Vault AppRole authentication. */
@Schema(description = "Vault AppRole credentials required to fetch the PII encryption key")
@Getter
@Setter
public class PiiEncryptionKeyRequest {

    @NotBlank(message = "roleId must not be blank")
    @Schema(
            description = "Vault AppRole role-id",
            example = "f3d4..."
            /* placeholder only, real value never logged */ )
    private String roleId;

    @NotBlank(message = "secretId must not be blank")
    @Schema(
            description = "Vault AppRole secret-id",
            example = "9a2c..."
            /* placeholder only, real value never logged */ )
    private String secretId;
}
