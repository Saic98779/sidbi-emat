package org.emat.validation;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.jdk.StringDeserializer;

/**
 * Default {@link String} deserializer used for every inbound request body. It preserves the original
 * {@link StringDeserializer} behavior and rejects values that contain a prohibited character
 * ({@code <}, {@code >}, {@code /}, {@code \}) via {@link SafeInputValidator}.
 *
 * <p>Registered globally in {@code org.emat.config.InputValidationConfig}, so it applies to every
 * {@code String} field of every request DTO, including nested DTOs and {@code String} fields inside
 * collections. Fields with an explicit {@code @JsonDeserialize(using = ...)} override are handled
 * by their own deserializer.
 */
public class SafeInputStringDeserializer extends StringDeserializer {

    public static final SafeInputStringDeserializer INSTANCE = new SafeInputStringDeserializer();

    private SafeInputStringDeserializer() {}

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) {
        String value = super.deserialize(p, ctxt);
        SafeInputValidator.validate(value, p);
        return value;
    }
}