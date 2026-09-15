package org.emat.service;

/**
 * Authenticates to Vault using the application's configured AppRole credentials and resolves the
 * PII field-encryption key. If Vault authentication succeeds, the key is read from Vault using the
 * resulting client token; if the key is not present there, the locally configured {@code
 * pii.encryption.secret-key} property (itself sourced from Vault at startup) is used as a
 * fallback.
 */
public interface VaultAppRoleAuthService {

    /**
     * Authenticates to Vault using the application's configured AppRole credentials (sourced from
     * {@code spring.cloud.vault.app-role.role-id / secret-id}) and returns the PII encryption key.
     *
     * @return the Base64-encoded AES-256 PII encryption key
     * @throws org.emat.exception.VaultAppRoleAuthenticationException if the credentials are invalid
     *     or not authorized to read the key
     */
    String resolveSecretKey();
}
