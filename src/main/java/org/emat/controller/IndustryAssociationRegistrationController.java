package org.emat.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.ApprovalRequest;
import org.emat.dto.CreateIndustryAssociationRegistrationRequest;
import org.emat.dto.IdRequest;
import org.emat.dto.IndustryAssociationRegistrationResponse;
import org.emat.dto.RegistrationDropdownDto;
import org.emat.dto.RegistrationDropdownRequest;
import org.emat.dto.StageHistoryResponse;
import org.emat.dto.StageResponse;
import org.emat.dto.UpdateIndustryAssociationRegistrationRequest;
import org.emat.service.EndpointRolePolicyService;
import org.emat.service.IndustryAssociationRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/industry-association-registrations")
@RequiredArgsConstructor
@Slf4j
public class IndustryAssociationRegistrationController {

    private final IndustryAssociationRegistrationService service;
    private final EndpointRolePolicyService endpointRolePolicyService;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationWrite'))")
    public ResponseEntity<ApiResponse<IndustryAssociationRegistrationResponse>> createRegistration(
            @RequestBody CreateIndustryAssociationRegistrationRequest request) {
        log.info("Received request to create new Industry Association Registration");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "Registration created successfully",
                                service.createRegistration(request)));
    }

    @PostMapping("/get")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationRead'))")
    public ResponseEntity<ApiResponse<IndustryAssociationRegistrationResponse>> getRegistrationById(
            @Valid @RequestBody IdRequest request) {
        log.info("Received request to fetch registration with ID: {}", request.getId());
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Registration fetched successfully",
                        service.getRegistrationById(request.getId())));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationRead'))")
    public ResponseEntity<ApiResponse<List<IndustryAssociationRegistrationResponse>>>
            getAllRegistrations() {
        log.info("Received request to fetch all registrations");
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Registrations fetched successfully", service.getAllRegistrations()));
    }

    @PostMapping("/dropdown")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationDropDown'))")
    public ResponseEntity<ApiResponse<List<RegistrationDropdownDto>>> getRegistrationDropdown(
            @RequestBody(required = false) RegistrationDropdownRequest request) {
        log.info("Received request to fetch registration dropdown");
        if (request == null) {
            request = new RegistrationDropdownRequest();
        }
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Registration dropdown fetched successfully",
                        service.getRegistrationDropdown(
                                request.getStageId(),
                                request.getState(),
                                request.getCreatedBy(),
                                request.getDistrict())));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationWrite'))")
    public ResponseEntity<ApiResponse<IndustryAssociationRegistrationResponse>> updateRegistration(
            @PathVariable Long id,
            @RequestBody UpdateIndustryAssociationRegistrationRequest request) {
        log.info("Received request to update registration with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Registration updated successfully",
                        service.updateRegistration(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('sidbiSde'))")
    public ResponseEntity<ApiResponse<Void>> deleteRegistration(@PathVariable Long id) {
        log.info("Received request to delete registration with ID: {}", id);
        service.deleteRegistration(id);
        return ResponseEntity.ok(ApiResponse.success("Registration deleted successfully", null));
    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('sidbiSde'))")
    public ResponseEntity<ApiResponse<IndustryAssociationRegistrationResponse>> approveBySidbe(
            @PathVariable Long id,
            @RequestBody ApprovalRequest approvalRequest,
            Authentication authentication) {
        log.info("Received SIDBE approval request for registration with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Registration approved successfully",
                        service.approveBySidbe(id, approvalRequest, authentication.getName())));
    }

    @PostMapping("/stage-history")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationRead'))")
    public ResponseEntity<ApiResponse<List<StageHistoryResponse>>> getStageHistoryByRegistrationId(
            @Valid @RequestBody IdRequest request) {
        log.info("Received request to fetch stage history for registration ID: {}",
                request.getId());
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Stage history fetched successfully",
                        service.getStageHistoryByRegistrationId(request.getId())));
    }

    @Operation(
            summary = "Get all stages",
            description = "Retrieves all stages with their ID, stage and sub-stage.")
    @GetMapping("/stages")
    public ResponseEntity<ApiResponse<List<StageResponse>>> getAllStages() {
        log.info("Received request to fetch all stages");
        return ResponseEntity.ok(
                ApiResponse.success("Stages fetched successfully", service.getAllStages()));
    }

    @Operation(
            summary = "Get registrations by stage ID",
            description =
                    "Retrieves all active Industry Association Registrations that are currently at"
                            + " the given stage.")
    @PostMapping("/stage")
    public ResponseEntity<ApiResponse<List<IndustryAssociationRegistrationResponse>>>
            getRegistrationsByStageId(@Valid @RequestBody IdRequest request) {
        log.info("Received request to fetch registrations for stage ID: {}", request.getId());
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Registrations fetched successfully",
                        service.getRegistrationsByStageId(request.getId())));
    }
}
