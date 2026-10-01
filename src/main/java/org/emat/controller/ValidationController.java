package org.emat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.PanValidationResponse;
import org.emat.service.EndpointRolePolicyService;
import org.emat.validator.IndustryAssociationRegistrationValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validations")
@RequiredArgsConstructor
@Slf4j
public class ValidationController {

    private final IndustryAssociationRegistrationValidator registrationValidator;
    private final EndpointRolePolicyService endpointRolePolicyService;

    @GetMapping("/pan")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationRead'))")
    public ResponseEntity<ApiResponse<PanValidationResponse>> validatePan(@RequestParam String panNo) {
        log.info("Received PAN validation request");

        String normalizedPan = panNo.trim();
        boolean duplicate = registrationValidator.isPanDuplicate(normalizedPan);
        PanValidationResponse response = new PanValidationResponse(normalizedPan, duplicate);
        String message = duplicate ? "PAN number already exists" : "PAN number is available";

        return ResponseEntity.ok(ApiResponse.success(message, response));
    }
}
