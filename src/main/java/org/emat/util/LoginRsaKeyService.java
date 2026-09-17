package org.emat.util;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Holds the RSA key pair used to protect the {@code password} field of the unauthenticated login
 * request (the AES-PII key cannot be handed out before login because {@code GET /pii-encryption-key}
 * itself requires a verified JWT).
 *
 * <p>The frontend fetches the {@linkplain #getPublicKeyBase64() public key} (base64-encoded SPKI)
 * from {@code GET /auth/public-key} <em>before</em> login and encrypts the password with Web
 * Crypto's {@code RSA-OAEP / SHA-256}. The private key stays on the server, so the AES field-
 * encryption key never needs to be exposed pre-authentication.
 *
 * <p>The private key is loaded from {@code pii.login-rsa.private-key} (base64-encoded PKCS#8 DER,
 * normally provisioned via Vault so every application instance shares the same key). When no key is
 * configured and {@code pii.login-rsa.key-file} is set (recommended for local development), the key
 * pair is generated once, persisted to that file and reloaded on every startup so ciphertexts stay
 * valid across restarts. When neither is configured, an ephemeral pair is generated at startup -
 * encrypted logins become un-decryptable after a restart.
 *
 * <p>RSA limit note: with RSA-2048 + OAEP-SHA-256 up to 190 bytes of plaintext can be encrypted,
 * which is more than enough for a password.
 */
@Component
public class LoginRsaKeyService {

    private static final Logger log = LoggerFactory.getLogger(LoginRsaKeyService.class);

    /**
     * OAEP digest + MGF1 digest both SHA-256. This MUST be set explicitly: the shorthand
     * transformation {@code "RSA/ECB/OAEPWithSHA-256AndMGF1Padding"} defaults MGF1 to SHA-1, which
     * is incompatible with Web Crypto's {@code RSA-OAEP + SHA-256} (MGF1-SHA-256).
     */
    private static final OAEPParameterSpec OAEP_SPEC =
            new OAEPParameterSpec(
                    "SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
    private static final int DEFAULT_KEY_SIZE = 2048;
    private static final int MAX_OAEP_SHA256_PLAINTEXT = 190;

    @Value("${pii.login-rsa.private-key:}")
    private String configuredPrivateKeyBase64;

    @Value("${pii.login-rsa.key-size:" + DEFAULT_KEY_SIZE + "}")
    private int rsaKeySize;

    @Value("${pii.login-rsa.key-file:}")
    private String localKeyFile;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    void init() {
        try {
            if (isConfiguredKey()) {
                this.privateKey =
                        loadPrivateKey(Base64.getDecoder().decode(configuredPrivateKeyBase64.trim()));
                this.publicKey = derivePublicKey((RSAPrivateCrtKey) privateKey);
                log.info("PII login RSA key: loaded from pii.login-rsa.private-key");
                return;
            }

            Path keyPath = (localKeyFile == null || localKeyFile.isBlank())
                    ? null
                    : Paths.get(localKeyFile).toAbsolutePath();

            if (keyPath != null && Files.exists(keyPath)) {
                String stored = Files.readString(keyPath).trim();
                if (stored.isEmpty()) {
                    throw new IllegalStateException(
                            "pii.login-rsa.key-file exists but is empty: " + keyPath);
                }
                this.privateKey = loadPrivateKey(Base64.getDecoder().decode(stored));
                this.publicKey = derivePublicKey((RSAPrivateCrtKey) privateKey);
                log.info("PII login RSA key: loaded from {}", keyPath);
                return;
            }

            KeyPair pair = generateKeyPair();
            this.privateKey = pair.getPrivate();
            this.publicKey = pair.getPublic();

            if (keyPath != null) {
                persistLocalKey(keyPath);
                log.warn(
                        "PII login RSA key: generated a {} bit key pair and persisted it to {}"
                            + " (pii.login-rsa.key-file). The public key stays stable across"
                            + " restarts. Delete the file to force regeneration.",
                        rsaKeySize,
                        keyPath);
            } else {
                log.warn(
                        "PII login RSA key: pii.login-rsa.private-key / key-file not configured,"
                            + " generated an ephemeral {} bit key pair. Encrypted logins will fail"
                            + " after this instance restarts - provision a shared key via Vault, or"
                            + " set pii.login-rsa.key-file for local development.",
                        rsaKeySize);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(
                    "pii.login-rsa.private-key must be a valid Base64-encoded PKCS#8 RSA key", e);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to read/write pii.login-rsa.key-file: " + localKeyFile, e);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialise the RSA login key pair", e);
        }
    }

    private KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(rsaKeySize);
        return generator.generateKeyPair();
    }

    private PrivateKey loadPrivateKey(byte[] pkcs8Der) throws Exception {
        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(pkcs8Der));
    }

    private void persistLocalKey(Path keyPath) throws IOException {
        byte[] pkcs8Der = privateKey.getEncoded();
        Path parent = keyPath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.writeString(keyPath, Base64.getEncoder().encodeToString(pkcs8Der));
    }

    private PublicKey derivePublicKey(RSAPrivateCrtKey privateKey) throws Exception {
        RSAPublicKeySpec spec =
                new RSAPublicKeySpec(privateKey.getModulus(), privateKey.getPublicExponent());
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    /** Base64-encoded SPKI (DER) public key, directly usable with {@code crypto.subtle.importKey("spki", ...)}. */
    public String getPublicKeyBase64() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /** Decrypts a base64-encoded {@code RSA-OAEP/SHA-256} ciphertext with the server-side private key. */
    public String decrypt(String base64Ciphertext) {
        if (base64Ciphertext == null || base64Ciphertext.isBlank()) {
            throw new IllegalArgumentException(
                    "Encrypted login credential must not be blank; encrypt the password with the"
                            + " public key from GET /auth/public-key first");
        }
        try {
            byte[] cipherBytes = Base64.getDecoder().decode(base64Ciphertext);
            if (cipherBytes.length > rsaKeySize / 8) {
                throw new IllegalArgumentException("RSA ciphertext longer than the key size");
            }
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey, OAEP_SPEC);
            byte[] plain = cipher.doFinal(cipherBytes);
            return new String(plain, StandardCharsets.UTF_8);
        } catch (BadPaddingException e) {
            throw new IllegalArgumentException(
                    "Failed to decrypt the login password: it was encrypted with a mismatched"
                        + " public key. Re-fetch GET /auth/public-key and encrypt the password"
                        + " again before submitting login.",
                    e);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Failed to decrypt login credential with the RSA private key", e);
        }
    }

    public boolean isEnabled() {
        return privateKey != null;
    }

    public int getRsaKeySize() {
        return rsaKeySize;
    }

    public int getMaxPlaintextLength() {
        return MAX_OAEP_SHA256_PLAINTEXT;
    }

    /** Algorithm identifier matching the frontend Web Crypto contract. */
    public String getAlgorithm() {
        return "RSA-OAEP";
    }

    /** Hash identifier matching the frontend Web Crypto contract. */
    public String getHash() {
        return "SHA-256";
    }

    /** Private key exposed only for Vault-backed provisioning checks. */
    public boolean isConfiguredKey() {
        return configuredPrivateKeyBase64 != null && !configuredPrivateKeyBase64.isBlank();
    }
}