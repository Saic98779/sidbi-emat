package org.emat.exception;

/** Thrown when a submitted CAPTCHA is missing, invalid, expired or already used. */
public class CaptchaValidationException extends RuntimeException {

    public CaptchaValidationException(String message) {
        super(message);
    }
}
