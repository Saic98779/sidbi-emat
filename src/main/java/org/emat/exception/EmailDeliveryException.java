package org.emat.exception;

/** Thrown when an outbound email (for example a password-reset OTP) cannot be sent. */
public class EmailDeliveryException extends RuntimeException {

    public EmailDeliveryException(String message, Throwable cause) {
        super(message, cause);
    }
}
