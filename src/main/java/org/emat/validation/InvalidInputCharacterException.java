package org.emat.validation;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.exc.InvalidFormatException;

/**
 * Signals that a user-supplied {@link String} field contains one or more prohibited characters
 * ({@code <}, {@code >}, {@code /}, {@code \}).
 *
 * <p>Extends {@link InvalidFormatException} so the Spring HTTP message converter surfaces it as an
 * {@code HttpMessageNotReadableException} (HTTP 400) while keeping the original message intact for
 * {@link org.emat.exception.GlobalExceptionHandler}.
 */
public class InvalidInputCharacterException extends InvalidFormatException {

    private static final long serialVersionUID = 1L;

    public InvalidInputCharacterException(JsonParser parser, String message) {
        super(parser, message, null, String.class);
    }
}