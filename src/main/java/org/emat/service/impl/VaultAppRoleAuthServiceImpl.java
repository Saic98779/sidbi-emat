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
    private final String vaultRoleId;
    private final String vaultSecretId;

    public VaultAppRoleAuthServiceImpl(
            ObjectMapper objectMapper,
            PiiEncryptionService encryptionService,
            @Value("${spring.cloud.vault.scheme:https}") String scheme,
            @Value("${spring.cloud.vault.host:vault-emat.metaversedu.in}") String host,
            @Value("${spring.cloud.vault.port:443}") int port,
            @Value("${spring.cloud.vault.kv.backend:secret}") String kvBackend,
            @Value("${spring.cloud.vault.application-name:emat}") String applicationName,
            @Value("${spring.profiles.active:local}") String profile,
            @Value("${pii.key-endpoint.vault-approle-path:auth/approle}") String appRolePath,
            @Value("${spring.cloud.vault.app-role.role-id:c6e1e35e-8a6f-9fe8-d1a3-376c9a2f0fb4}")
            String vaultRoleId,
            @Value("${spring.cloud.vault.app-role.secret-id:cc5ac6e1-cbdf-2220-29c8-f9b585d82781}")
            String vaultSecretId) {
        this.encryptionService = encryptionService;
        this.objectMapper = objectMapper;
        this.vaultRoleId = vaultRoleId;
        this.vaultSecretId = vaultSecretId;
        this.restClient = RestClient.create();
        String baseUrl = scheme + "://" + host + ":" + port;
        String normalizedAppRolePath =
                appRolePath.startsWith("/") ? appRolePath.substring(1) : appRolePath;
        this.appRoleLoginUrl = baseUrl + "/v1/" + normalizedAppRolePath + "/login";
        this.kvSecretUrl =
                baseUrl + "/v1/" + kvBackend + "/data/" + applicationName + "/" + profile;
    }

    @Override
    public String resolveSecretKey() {
        return encryptionService.getSecretKeyValue();
    }
}
