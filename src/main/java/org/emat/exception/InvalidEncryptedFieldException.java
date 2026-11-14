package org.emat.exception;

/**
 * Thrown while reading a request body when a field that must be RSA-encrypted (e.g. a password)
 * cannot be decrypted. {@link GlobalExceptionHandler} unwraps it from Jackson's wrapper exception
 * so the client gets a clear 400 message instead of a raw parser error.
 */
public class InvalidEncryptedFieldException extends IllegalArgumentException {

    public InvalidEncryptedFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
