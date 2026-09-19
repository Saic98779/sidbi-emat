package org.emat.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import tools.jackson.core.JsonParser;
import tools.jackson.core.TokenStreamContext;

/**
 * Central, reusable validation for user-supplied {@link String} values to reduce the risk of SQL
 * injection, XSS and other input-based attacks.
 *
 * <p>A {@link String} value is rejected when it contains any of the prohibited characters:
 * {@code <}, {@code >}, {@code /}, {@code \}. The input is never modified or sanitized; invalid
 * input is rejected as-is.
 */
public final class SafeInputValidator {

    private static final char[] PROHIBITED_CHARACTERS = {'<', '>'};

    private SafeInputValidator() {}

    /**
     * Returns the first prohibited character found in {@code value}, or {@code '\u0000'} when the
     * value is {@code null} or contains no prohibited characters.
     */
    public static char findProhibitedCharacter(String value) {
        if (value != null) {
            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);
                if (c == '<' || c == '>') {
                    return c;
                }
            }
        }
        return 0;
    }

    /**
     * Validates {@code value} and throws {@link InvalidInputCharacterException} if it contains a
     * prohibited character. {@code null} values are ignored.
     *
     * @param value the user-supplied value to validate
     * @param parser the active parser, used to build a meaningful field path for the error message
     */
    public static void validate(String value, JsonParser parser) {
        if (value == null) {
            return;
        }
        char prohibited = findProhibitedCharacter(value);
        if (prohibited != 0) {
            throw new InvalidInputCharacterException(
                    parser,
                    "Field '"
                            + fieldPath(parser)
                            + "' contains invalid character '"
                            + prohibited
                            + "'. Input must not contain the prohibited characters '<', '>',");
        }
    }

    /** Builds a human-readable JSON path (e.g. {@code vendorName} or {@code details.salaryMonth}) for the field being read. */
    private static String fieldPath(JsonParser parser) {
        List<String> path = new ArrayList<>();
        TokenStreamContext context = parser.streamReadContext();
        while (context != null) {
            String name = context.currentName();
            if (name != null && !name.isBlank()) {
                if (context.hasCurrentIndex() && context.getCurrentIndex() >= 0) {
                    path.add(name + "[" + context.getCurrentIndex() + "]");
                } else {
                    path.add(name);
                }
            } else if (context.hasCurrentIndex() && context.getCurrentIndex() >= 0) {
                path.add("[" + context.getCurrentIndex() + "]");
            }
            context = context.getParent();
        }
        String currentName = parser.currentName();
        if (currentName != null && !currentName.isBlank()) {
            path.add(0, currentName);
        }
        if (path.isEmpty()) {
            return "request";
        }
        Collections.reverse(path);
        return String.join(".", path);
    }
}