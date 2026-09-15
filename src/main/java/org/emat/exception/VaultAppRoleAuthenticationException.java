package org.emat.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when Vault AppRole credentials supplied to the PII encryption key endpoint cannot be
 * validated (invalid role-id/secret-id) or are not authorized to read the PII key.
 */
public class VaultAppRoleAuthenticationException extends RuntimeException {

    private final HttpStatus status;

    public VaultAppRoleAuthenticationException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public VaultAppRoleAuthenticationException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
