package org.emat.service;

/**
 * Validates Vault AppRole credentials against HashiCorp Vault and resolves the PII field-encryption
 * key. If Vault authentication of the supplied role-id/secret-id succeeds, the key is read from
 * Vault using the resulting client token; if the key is not present there, the locally configured
 * {@code pii.encryption.secret-key} property (itself sourced from Vault at startup) is used as a
 * fallback.
 */
public interface VaultAppRoleAuthService {

    /**
     * Authenticates to Vault using the supplied AppRole credentials and returns the PII encryption
     * key.
     *
     * @param roleId the Vault AppRole role-id
     * @param secretId the Vault AppRole secret-id
     * @return the Base64-encoded AES-256 PII encryption key
     * @throws org.emat.exception.VaultAppRoleAuthenticationException if the credentials are invalid
     *     or not authorized to read the key
     */
    String resolveSecretKey(String roleId, String secretId);
}
