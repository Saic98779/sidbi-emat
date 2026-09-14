package org.emat.dto.serializer;

import org.emat.util.PiiEncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * Encrypts a {@link String} PII field (email, mobile number, credential, etc.) when it is
 * serialized into an outgoing JSON response, so it is never exposed to the frontend in plain text.
 * Pair with {@link PiiStringDecryptDeserializer} to decrypt on the way back in.
 *
 * <p>Usage: {@code @JsonSerialize(using = PiiStringEncryptSerializer.class)} on the field.
 *
 * <p>Jackson 3 (Spring Boot 4) instantiates custom serializers directly, so the service is held
 * in a static field injected by Spring when this {@code @Component} is created.
 */
@Component
public class PiiStringEncryptSerializer extends ValueSerializer<String> {

    private static PiiEncryptionService encryptionService;

    @Autowired
    public void setEncryptionService(PiiEncryptionService encryptionService) {
        PiiStringEncryptSerializer.encryptionService = encryptionService;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializationContext serializers) {
        gen.writeString(value == null ? null : encryptionService.encrypt(value));
    }
}