package org.emat.dto.serializer;

import org.emat.util.PiiEncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * Decrypts an incoming {@link String} PII field (email, mobile number, credential, etc.) received
 * from the frontend. Rejects plain-text values in strict mode (default); passed through unchanged
 * for backward compatibility only when strict mode is disabled.
 *
 * <p>Usage: {@code @JsonDeserialize(using = PiiStringDecryptDeserializer.class)} on the field.
 *
 * <p>Jackson 3 (Spring Boot 4) instantiates custom deserializers directly, so the service is held
 * in a static field injected by Spring when this {@code @Component} is created.
 */
@Component
public class PiiStringDecryptDeserializer extends ValueDeserializer<String> {

    private static PiiEncryptionService encryptionService;

    @Autowired
    public void setEncryptionService(PiiEncryptionService encryptionService) {
        PiiStringDecryptDeserializer.encryptionService = encryptionService;
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) {
        String value = p.getValueAsString();
        return encryptionService.decrypt(value);
    }
}