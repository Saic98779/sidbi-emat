package org.emat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** Response body for the unauthenticated login public-key endpoint ({@code GET /auth/public-key}). */
@Schema(description = "RSA public key used to encrypt the login password before authentication")
@Getter
@Setter
@AllArgsConstructor
public class LoginPublicKeyResponse {

    @Schema(
            description = "Base64-encoded SPKI public key for Web Crypto importKey('spki', ...)",
            example = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAL...")
    private String publicKey;

    @Schema(description = "Encryption algorithm", example = "RSA-OAEP")
    private String algorithm;

    @Schema(description = "Hash algorithm", example = "SHA-256")
    private String hash;

    @Schema(description = "RSA key size in bits", example = "2048")
    private int keySize;
}