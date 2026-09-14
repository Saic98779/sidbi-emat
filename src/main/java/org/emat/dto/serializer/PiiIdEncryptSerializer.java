package org.emat.dto.serializer;

import org.emat.util.PiiEncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * Serializes a {@code Long} backend identifier by encrypting it via {@link PiiEncryptionService},
 * so identifiers exchanged with the frontend are never transmitted in plain text.
 *
 * <p>Jackson 3 (Spring Boot 4) instantiates custom serializers directly, so the service is held
 * in a static field injected by Spring when this {@code @Component} is created.
 */
@Component
public class PiiIdEncryptSerializer extends ValueSerializer<Long> {

    private static PiiEncryptionService encryptionService;

    @Autowired
    public void setEncryptionService(PiiEncryptionService encryptionService) {
        PiiIdEncryptSerializer.encryptionService = encryptionService;
    }

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializationContext serializers) {
        gen.writeString(value == null ? null : encryptionService.encryptId(value));
    }
}