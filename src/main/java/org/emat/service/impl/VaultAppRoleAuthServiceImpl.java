package org.emat.service.impl;

import java.util.Map;
import org.emat.exception.VaultAppRoleAuthenticationException;
import org.emat.service.VaultAppRoleAuthService;
import org.emat.util.PiiEncryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * Validates Vault AppRole credentials by logging in to Vault's AppRole auth endpoint and, on
 * success, reads {@code pii.encryption.secret-key} from the KV v2 secrets engine using the client
 * token issued to the caller. Falls back to the locally configured property (itself sourced from
 * Vault at startup) if the key is not present at the expected path.
 */
@Service
public class VaultAppRoleAuthServiceImpl implements VaultAppRoleAuthService {

    private static final Logger log = LoggerFactory.getLogger(VaultAppRoleAuthServiceImpl.class);

    private static final String PII_SECRET_KEY = "pii.encryption.secret-key";

    private final PiiEncryptionService encryptionService;
    private final ObjectMapper objectMapper;
    private final RestClient restClient;
    private final String appRoleLoginUrl;
    private final String kvSecretUrl;

    public VaultAppRoleAuthServiceImpl(
            ObjectMapper objectMapper,
            PiiEncryptionService encryptionService,
            @Value("${spring.cloud.vault.scheme:https}") String scheme,
            @Value("${spring.cloud.vault.host:vault-emat.metaversedu.in}") String host,
            @Value("${spring.cloud.vault.port:443}") int port,
            @Value("${spring.cloud.vault.kv.backend:secret}") String kvBackend,
            @Value("${spring.cloud.vault.application-name:emat}") String applicationName,
            @Value("${spring.profiles.active:local}") String profile,
            @Value("${pii.key-endpoint.vault-approle-path:auth/approle}") String appRolePath) {
        this.encryptionService = encryptionService;
        this.objectMapper = objectMapper;
        this.restClient = RestClient.create();
        String baseUrl = scheme + "://" + host + ":" + port;
        String normalizedAppRolePath =
                appRolePath.startsWith("/") ? appRolePath.substring(1) : appRolePath;
        this.appRoleLoginUrl = baseUrl + "/v1/" + normalizedAppRolePath + "/login";
        this.kvSecretUrl =
                baseUrl + "/v1/" + kvBackend + "/data/" + applicationName + "/" + profile;
    }

    @Override
    public String resolveSecretKey(String roleId, String secretId) {
        String clientToken = authenticate(roleId, secretId);
        String key = readSecretKey(clientToken);
        if (key == null || key.isBlank()) {
            log.warn(
                    "PII encryption key not found at Vault path {}, falling back to configured"
                            + " property",
                    kvSecretUrl);
            return encryptionService.getSecretKeyValue();
        }
        return key;
    }

    private String authenticate(String roleId, String secretId) {
        try {
            String body =
                    objectMapper.writeValueAsString(
                            Map.of("role_id", roleId, "secret_id", secretId));
            ResponseEntity<String> response =
                    restClient
                            .post()
                            .uri(appRoleLoginUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(body)
                            .retrieve()
                            .toEntity(String.class);
            JsonNode node = objectMapper.readTree(response.getBody());
            String clientToken = node.path("auth").path("client_token").asString(null);
            if (clientToken == null || clientToken.isBlank()) {
                throw new VaultAppRoleAuthenticationException(
                        HttpStatus.UNAUTHORIZED,
                        "Vault AppRole login succeeded but no client token was returned");
            }
            log.info("Vault AppRole authentication succeeded");
            return clientToken;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.warn("Vault AppRole login rejected with HTTP {}", e.getStatusCode().value());
            throw new VaultAppRoleAuthenticationException(
                    HttpStatus.UNAUTHORIZED, "Invalid Vault AppRole role-id or secret-id", e);
        } catch (ResourceAccessException e) {
            throw new VaultAppRoleAuthenticationException(
                    HttpStatus.BAD_GATEWAY, "Cannot reach Vault at " + appRoleLoginUrl, e);
        } catch (JacksonException e) {
            throw new VaultAppRoleAuthenticationException(
                    HttpStatus.BAD_GATEWAY, "Malformed response from Vault AppRole login", e);
        }
    }

    private String readSecretKey(String clientToken) {
        try {
            ResponseEntity<String> response =
                    restClient
                            .get()
                            .uri(kvSecretUrl)
                            .header("X-Vault-Token", clientToken)
                            .retrieve()
                            .toEntity(String.class);
            JsonNode node = objectMapper.readTree(response.getBody());
            return node.path("data").path("data").path(PII_SECRET_KEY).asString(null);
        } catch (HttpClientErrorException e) {
            HttpStatusCode status = e.getStatusCode();
            if (status.value() == 403) {
                throw new VaultAppRoleAuthenticationException(
                        HttpStatus.FORBIDDEN,
                        "Vault AppRole authenticated but not authorized to read the PII encryption"
                                + " key",
                        e);
            }
            log.warn("Vault secret read failed with HTTP {}", status.value());
            throw new VaultAppRoleAuthenticationException(
                    HttpStatus.BAD_GATEWAY,
                    "Vault returned HTTP "
                            + status.value()
                            + " while reading the PII encryption key",
                    e);
        } catch (HttpServerErrorException e) {
            log.warn("Vault secret read failed with HTTP {}", e.getStatusCode().value());
            throw new VaultAppRoleAuthenticationException(
                    HttpStatus.BAD_GATEWAY,
                    "Vault returned HTTP "
                            + e.getStatusCode().value()
                            + " while reading the PII encryption key",
                    e);
        } catch (ResourceAccessException e) {
            throw new VaultAppRoleAuthenticationException(
                    HttpStatus.BAD_GATEWAY, "Cannot reach Vault at " + kvSecretUrl, e);
        } catch (JacksonException e) {
            throw new VaultAppRoleAuthenticationException(
                    HttpStatus.BAD_GATEWAY,
                    "Malformed response from Vault when reading the key",
                    e);
        }
    }
}
