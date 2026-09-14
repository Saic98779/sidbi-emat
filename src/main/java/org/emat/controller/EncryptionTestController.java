package org.emat.controller;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import org.emat.dto.ApiResponse;
import org.emat.dto.BseSalaryRequest;
import org.emat.dto.BseSalaryUpdateRequest;
import org.emat.dto.CreateBseRecommendationRequest;
import org.emat.dto.CreateIndustryAssociationAppraisalRequest;
import org.emat.dto.CreateIndustryAssociationRegistrationRequest;
import org.emat.dto.CreateUserRequest;
import org.emat.dto.DisbursementCapexRequest;
import org.emat.dto.EncryptionTestResponse;
import org.emat.dto.LoginRequest;
import org.emat.dto.MonthlySalaryDetailsRequest;
import org.emat.dto.MonthlySalaryDetailsUpdateRequest;
import org.emat.dto.RegionalOfficeRequest;
import org.emat.dto.UpdateBranchRequest;
import org.emat.dto.UpdateBseRecommendationRequest;
import org.emat.dto.UpdateIndustryAssociationAppraisalRequest;
import org.emat.dto.UpdateIndustryAssociationRegistrationRequest;
import org.emat.dto.UpdateRegionalOfficeRequest;
import org.emat.dto.UpdateSidbiSdeRequest;
import org.emat.dto.VendorRequestDTO;
import org.emat.util.PiiEncryptionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Local testing helper (NOT for production). Exposes the field-encryption service so developers
 * and QA can generate {@code ENC:...} values for protected fields and verify response values.
 *
 * <p>Only registered when {@code pii.testing-endpoint.enabled=true} (see application.properties).
 * Production must keep this disabled. Endpoints still require an authenticated session like any
 * other secured endpoint.
 *
 * <p>{@code POST /testing/encrypt} accepts either:
 * <ul>
 *   <li>a single value: {@code {"value":"jdoe@example.com"}} - returns {@code {"value":"ENC:..."}}, or</li>
 *   <li>a full request payload (any create/update DTO) - returns the same JSON with every protected
 *       field encrypted. The DTO is auto-detected from the JSON, or can be forced via
 *       {@code ?dtoName=...}.</li>
 * </ul>
 */
@RestController
@RequestMapping("/testing")
@ConditionalOnProperty(name = "pii.testing-endpoint.enabled", havingValue = "true")
public class EncryptionTestController {

    private final PiiEncryptionService encryptionService;
    private final ObjectMapper objectMapper;

    private static final List<Class<?>> REQUEST_DTOS =
            List.of(
                    CreateIndustryAssociationRegistrationRequest.class,
                    UpdateIndustryAssociationRegistrationRequest.class,
                    CreateIndustryAssociationAppraisalRequest.class,
                    UpdateIndustryAssociationAppraisalRequest.class,
                    CreateBseRecommendationRequest.class,
                    UpdateBseRecommendationRequest.class,
                    VendorRequestDTO.class,
                    CreateUserRequest.class,
                    LoginRequest.class,
                    DisbursementCapexRequest.class,
                    BseSalaryRequest.class,
                    BseSalaryUpdateRequest.class,
                    RegionalOfficeRequest.class,
                    UpdateRegionalOfficeRequest.class,
                    UpdateBranchRequest.class,
                    UpdateSidbiSdeRequest.class,
                    MonthlySalaryDetailsRequest.class,
                    MonthlySalaryDetailsUpdateRequest.class);

    public EncryptionTestController(PiiEncryptionService encryptionService, ObjectMapper objectMapper) {
        this.encryptionService = encryptionService;
        this.objectMapper = objectMapper;
    }

    /**
     * POST /testing/encrypt
     *
     * <pre>
     * // single value
     * POST /testing/encrypt        { "value": "jdoe@example.com" }  ->  { "value": "ENC:..." }
     *
     * // full payload (auto-detects the DTO)
     * POST /testing/encrypt        { "state": "Telangana", "apexHolderMobile": "9876543210", ... }
     *
     * // full payload (explicit DTO)
     * POST /testing/encrypt?dtoName=CreateIndustryAssociationRegistrationRequest  { ...same... }
     * </pre>
     */
    @PostMapping("/encrypt")
    public ApiResponse<Object> encrypt(
            @RequestBody(required = false) String rawBody,
            @RequestParam(required = false) String dtoName) {
        JsonNode body = parseBody(rawBody);
        if (body == null || body.isNull()) {
            return ApiResponse.error(400, "Request body must be valid JSON");
        }

        if (body.isObject() && body.size() == 1 && body.has("value") && body.get("value").isValueNode()) {
            String plain = body.get("value").asText();
            String encrypted = encryptionService.encrypt(plain);
            return ApiResponse.success(
                    "Encrypted successfully", new EncryptionTestResponse(encrypted));
        }

        try {
            Class<?> dtoClass = resolveDto(dtoName, body);
            Object dto = objectMapper.treeToValue(body, dtoClass);
            String encryptedJson = objectMapper.writeValueAsString(dto);
            return ApiResponse.success(
                    "Payload encrypted",
                    Map.of(
                            "dtoName", dtoClass.getSimpleName(),
                            "encrypted", objectMapper.readTree(encryptedJson)));
        } catch (Exception e) {
            return ApiResponse.error(
                    400,
                    "Could not encrypt payload: "
                            + e.getMessage()
                            + ". Send a single {\"value\":\"...\"} or a supported request payload.");
        }
    }

    /** POST /testing/decrypt  body: {"value":"ENC:..."}  ->  "jdoe@example.com" */
    @PostMapping("/decrypt")
    public ApiResponse<EncryptionTestResponse> decrypt(@RequestBody(required = false) String rawBody) {
        JsonNode body = parseBody(rawBody);
        if (body == null
                || body.get("value") == null
                || !body.get("value").isValueNode()) {
            return ApiResponse.error(
                    400,
                    "Body must be JSON with a 'value' field, e.g. {\"value\":\"ENC:...\"}");
        }
        String decrypted = encryptionService.decrypt(body.get("value").asText());
        return ApiResponse.success("Decrypted successfully", new EncryptionTestResponse(decrypted));
    }

    private JsonNode parseBody(String rawBody) {
        try {
            if (rawBody == null || rawBody.isBlank()) {
                return null;
            }
            return objectMapper.readTree(rawBody);
        } catch (Exception e) {
            return null;
        }
    }

    private Class<?> resolveDto(String dtoName, JsonNode body) throws Exception {
        if (dtoName != null && !dtoName.isBlank()) {
            Class<?> dtoClass = Class.forName("org.emat.dto." + dtoName);
            if (dtoName.contains("$")) {
                throw new IllegalArgumentException("DTO class not supported: " + dtoName);
            }
            return dtoClass;
        }
        Class<?> best = null;
        int bestScore = 0;
        for (Class<?> candidate : REQUEST_DTOS) {
            Object dto = objectMapper.treeToValue(body, candidate);
            int score = score(dto);
            if (score > bestScore) {
                bestScore = score;
                best = candidate;
            }
        }
        if (best == null || bestScore == 0) {
            throw new IllegalArgumentException("no DTO in the whitelist matched the payload");
        }
        return best;
    }

    private int score(Object dto) {
        int score = 0;
        for (Field field : dto.getClass().getDeclaredFields()) {
            try {
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                Object value = field.get(dto);
                if (value != null && !(value instanceof String s && s.isBlank())) {
                    score++;
                }
            } catch (IllegalAccessException ignored) {
                // skip fields that cannot be inspected
            }
        }
        return score;
    }
}