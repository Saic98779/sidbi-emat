package org.emat.dto.serializer;

import org.emat.util.LoginRsaKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * Decrypts the incoming {@code password} field of the unauthenticated login request using the
 * server-side RSA private key.
 *
 * <p>The frontend must encrypt the password with the public key obtained from {@code GET
 * /auth/public-key} ({@code RSA-OAEP / SHA-256}, base64-encoded ciphertext). This keeps the AES
 * field-encryption key private before any JWT can be issued.
 *
 * <p>Usage: {@code @JsonDeserialize(using = RsaStringDecryptDeserializer.class)} on the field.
 *
 * <p>Jackson 3 (Spring Boot 4) instantiates custom deserializers directly, so the service is held
 * in a static field injected by Spring when this {@code @Component} is created.
 */
@Component
public class RsaStringDecryptDeserializer extends ValueDeserializer<String> {

    private static LoginRsaKeyService loginRsaKeyService;

    @Autowired
    public void setLoginRsaKeyService(LoginRsaKeyService loginRsaKeyService) {
        RsaStringDecryptDeserializer.loginRsaKeyService = loginRsaKeyService;
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) {
        String value = p.getValueAsString();
        return loginRsaKeyService.decrypt(value);
    }
}