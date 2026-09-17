package org.emat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.emat.dto.ApiResponse;
import org.emat.dto.LoginPublicKeyResponse;
import org.emat.util.LoginRsaKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Pre-authentication endpoints ({@code GET /auth/...}).
 *
 * <p>The {@code /auth/public-key} endpoint returns the RSA public key the frontend uses to encrypt
 * the {@code password} field of {@code POST /users/login} <strong>before any JWT is available</strong>.
 * The corresponding RSA private key stays on the server (in the {@link LoginRsaKeyService}).
 */
@RestController
@RequestMapping("/auth")
@Tag(
        name = "Pre-authentication",
        description = "Public endpoints available before login (no JWT required)")
@SecurityRequirement(name = "")
public class AuthController {

    private final LoginRsaKeyService rsaKeyService;

    public AuthController(LoginRsaKeyService rsaKeyService) {
        this.rsaKeyService = rsaKeyService;
    }

    /**
     * Returns the RSA public key the frontend must use to encrypt the login password.
     *
     * <p>No authentication is required; the corresponding RSA private key never leaves the server.
     *
     * <pre>
     * GET /auth/public-key
     * 200 {
     *   "status": 200,
     *   "message": "Public key for login password encryption",
     *   "data": {
     *     "publicKey": "&lt;base64 SPKI&gt;",
     *     "algorithm": "RSA-OAEP",
     *     "hash": "SHA-256",
     *     "keySize": 2048
     *   }
     * }
     * </pre>
     */
    @GetMapping("/public-key")
    @Operation(
            summary = "Get RSA public key for login password encryption",
            description =
                    "Returns the base64-encoded SPKI RSA public key the frontend must use with"
                        + " Web Crypto crypto.subtle.importKey('spki', ...) and RSA-OAEP to"
                        + " encrypt the login password before calling POST /users/login."
                        + " No authentication is required.")
    @ApiResponses(
            value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Public key returned successfully")
            })
    public ResponseEntity<ApiResponse<LoginPublicKeyResponse>> getLoginPublicKey() {
        LoginPublicKeyResponse body =
                new LoginPublicKeyResponse(
                        rsaKeyService.getPublicKeyBase64(),
                        rsaKeyService.getAlgorithm(),
                        rsaKeyService.getHash(),
                        rsaKeyService.getRsaKeySize());
        return ResponseEntity.ok(
                ApiResponse.success("Public key for login password encryption", body));
    }
}