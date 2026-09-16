package org.emat.util;

import jakarta.annotation.PostConstruct;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Central service responsible for encrypting/decrypting Personally Identifiable Information (PII)
 * such as email addresses, mobile numbers, OTPs, credentials and backend identifiers before they
 * leave/enter the application boundary (i.e. in HTTP request/response bodies).
 *
 * <p>Transport security (HTTPS/TLS) remains the primary control for protecting data in transit (see
 * {@code SecurityConfig}), this service adds an additional, defense-in-depth, application-level
 * encryption layer for sensitive fields so that PII is never exchanged with the frontend in plain
 * text, even if TLS is terminated upstream (e.g. at a load balancer/proxy).
 *
 * <p>Algorithm: AES-256-GCM (authenticated encryption). Output format: {@code
 * ENC:<url-safe-base64(iv + ciphertext) without padding>}. The URL-safe encoding keeps encrypted
 * values safe to embed in URL path segments and query strings (e.g. encrypted identifiers passed
 * back as {@code @PathVariable}/{@code @RequestParam}), and also round-trips losslessly in JSON
 * bodies. A fixed prefix allows the deserializer to detect whether an inbound value is already
 * encrypted. String PII fields accept plain values for backward compatibility, but backend
 * identifiers ({@code Long} IDs) are enforced strictly: only encrypted forms are accepted.
 */
@Component
public class PiiEncryptionService {

    private static final Logger log = LoggerFactory.getLogger(PiiEncryptionService.class);

    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH_BYTES = 12;
    private static final int GCM_TAG_LENGTH_BITS = 128;
    private static final String ENCRYPTED_PREFIX = "ENC:";

    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${pii.encryption.secret-key}")
    private String secretKeyValue;

    @Value("${pii.field-encryption.enabled:true}")
    private boolean enabled;

    private SecretKeySpec secretKey;

    @PostConstruct
    void init() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretKeyValue);

            if (keyBytes.length != 32) {
                throw new IllegalStateException(
                        "pii.encryption.secret-key must decode to exactly 32 bytes for AES-256");
            }

            this.secretKey = new SecretKeySpec(keyBytes, "AES");

            if (!enabled) {
                log.warn(
                        "PII field-level encryption is DISABLED "
                                + "(pii.field-encryption.enabled=false). "
                                + "This should never be the case in production.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(
                    "pii.encryption.secret-key must be a valid Base64-encoded AES-256 key", e);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getSecretKeyValue() {
        return secretKeyValue;
    }

    /** Returns true if the given value carries the marker of a value encrypted by this service. */
    public boolean isEncrypted(String value) {
        return value != null && value.startsWith(ENCRYPTED_PREFIX);
    }

    /** Encrypts a plain text PII value for safe transmission to the frontend. */
    public String encrypt(String plainText) {
        if (plainText == null || !enabled) {
            return plainText;
        }
        try {
            byte[] iv = new byte[GCM_IV_LENGTH_BYTES];
            secureRandom.nextBytes(iv);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(
                    Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv));
            byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            ByteBuffer buffer = ByteBuffer.allocate(iv.length + cipherText.length);
            buffer.put(iv).put(cipherText);
            return ENCRYPTED_PREFIX
                    + Base64.getUrlEncoder().withoutPadding().encodeToString(buffer.array());
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Failed to encrypt PII value", e);
        }
    }

    /** Encrypts a Long backend identifier for safe transmission to the frontend. */
    public String encryptId(Long id) {
        return encrypt(String.valueOf(id));
    }

    /**
     * Decrypts a Long backend identifier received from the frontend.
     *
     * <p>Strict mode: whenever field encryption is enabled, the value must carry the {@code ENC:}
     * marker produced by this service. Plain numeric identifiers are rejected with an {@link
     * IllegalArgumentException} so that IDs can only ever travel in encrypted form. If encryption
     * is disabled (local debug only), plain numbers pass through.
     *
     * @throws IllegalArgumentException if field encryption is enabled and the value is not in the
     *     recognized encrypted format
     */
    public Long decryptId(String encryptedId) {
        if (encryptedId != null) {
            encryptedId = encryptedId.replaceAll("^\"|\"$", "");
        }
        if (enabled && !isEncrypted(encryptedId)) {
            throw new IllegalArgumentException(
                    "Plain IDs are not accepted. Expected an encrypted identifier in the form "
                            + "ENC:<base64url(iv+ciphertext+tag)>; got: "
                            + masked(encryptedId));
        }
        try {
            return Long.parseLong(decrypt(encryptedId));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Encrypted identifier does not resolve to a valid numeric ID", e);
        }
    }

    /**
     * Decrypts a string PII value received from the frontend. If the incoming value is not in the
     * recognized encrypted format, it is returned unchanged (treated as plain text). This
     * backward-compatible behavior applies to string PII fields (email, mobile, password) only -
     * {@link #decryptId(String)} is strict and rejects non-encrypted identifiers.
     */
    public String decrypt(String value) {
        if (value == null || !enabled || !isEncrypted(value)) {
            return value;
        }
        try {
            byte[] decoded =
                    Base64.getUrlDecoder().decode(value.substring(ENCRYPTED_PREFIX.length()));
            ByteBuffer buffer = ByteBuffer.wrap(decoded);
            byte[] iv = new byte[GCM_IV_LENGTH_BYTES];
            buffer.get(iv);
            byte[] cipherText = new byte[buffer.remaining()];
            buffer.get(cipherText);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(
                    Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv));
            byte[] plain = cipher.doFinal(cipherText);
            return new String(plain, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to decrypt PII value: malformed payload", e);
        }
    }

    private String masked(String value) {
        if (value == null || value.isBlank()) {
            return "<empty>";
        }
        if (value.length() <= 6) {
            return "<redacted>";
        }
        return "<redacted length=" + value.length() + ">";
    }
}
