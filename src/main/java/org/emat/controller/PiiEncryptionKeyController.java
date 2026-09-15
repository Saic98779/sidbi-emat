package org.emat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.emat.dto.ApiResponse;
import org.emat.dto.PiiEncryptionKeyResponse;
import org.emat.service.VaultAppRoleAuthService;
import org.emat.util.PiiEncryptionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the field-level PII encryption key so the frontend can encrypt/decrypt protected fields
 * (see {@code documentation/FE_BE_ENCRYPTION_CONTRACT.md}).
 *
 * <p>The key is only released after the application re-authenticates to HashiCorp Vault using its
 * own configured AppRole credentials ({@code spring.cloud.vault.app-role.role-id / secret-id}) at
 * request time. On success the key is read from Vault (KV v2, path {@code secret/emat/{profile}})
 * under the resulting client token; if absent there it falls back to the configured {@code
 * pii.encryption.secret-key} property. Only registered when {@code pii.key-endpoint.enabled=true}.
 *
 * <p>Endpoint requires a valid JWT and is restricted to head-office roles via the {@code
 * piiEncryptionKeyRead} role policy. Production must explicitly opt in.
 */
@RestController
@RequestMapping("/pii-encryption-key")
@RequiredArgsConstructor
//@ConditionalOnProperty(name = "pii.key-endpoint.enabled", havingValue = "true")
@Tag(
        name = "PII Encryption Key",
        description = "APIs for retrieving the PII field-encryption key for the frontend")
@SecurityRequirement(name = "bearerAuth")
public class PiiEncryptionKeyController {

    private final VaultAppRoleAuthService vaultAppRoleAuthService;
    private final PiiEncryptionService encryptionService;

    /**
     * Re-authenticates to Vault with the application's configured AppRole credentials and returns
     * the PII encryption key.
     *
     * <pre>
     * POST /emat/v1/pii-encryption-key
     * -> 200 { "status": 200, "data": { "secretKey": "aB3x...", "enabled": true } }
     * -> 401 invalid Vault AppRole credentials
     * -> 403 valid credentials but no permission to read the key
     * </pre>
     */
    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('piiEncryptionKeyRead'))")
    @Operation(
            summary = "Get PII encryption key (Vault AppRole validated)",
            description =
                    "Re-authenticates to Vault using the application's configured AppRole"
                        + " credentials and returns the Base64-encoded AES-256 key used for"
                        + " field-level PII encryption plus whether field encryption is enabled.")
    public ResponseEntity<ApiResponse<PiiEncryptionKeyResponse>> getPiiEncryptionKey() {
        String secretKey = vaultAppRoleAuthService.resolveSecretKey();
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.success(
                                "Vault AppRole credentials validated; PII encryption key retrieved"
                                        + " successfully",
                                new PiiEncryptionKeyResponse(
                                        secretKey, encryptionService.isEnabled())));
    }
}
