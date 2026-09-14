package org.emat.dto.serializer;

import org.emat.util.PiiEncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * Deserializes a {@code Long} backend identifier that arrived from the frontend in encrypted form,
 * by decrypting it via {@link PiiEncryptionService}. For backward compatibility, plain-text values
 * are accepted as-is (decrypt returns them unchanged).
 *
 * <p>Jackson 3 (Spring Boot 4) instantiates custom deserializers directly, so the service is held
 * in a static field injected by Spring when this {@code @Component} is created.
 */
@Component
public class PiiIdDecryptDeserializer extends ValueDeserializer<Long> {

    private static PiiEncryptionService encryptionService;

    @Autowired
    public void setEncryptionService(PiiEncryptionService encryptionService) {
        PiiIdDecryptDeserializer.encryptionService = encryptionService;
    }

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) {
        String value = p.getValueAsString();
        if (value == null) {
            return null;
        }
        if (encryptionService.isEncrypted(value)) {
            return encryptionService.decryptId(value);
        }
        return Long.parseLong(value);
    }
}