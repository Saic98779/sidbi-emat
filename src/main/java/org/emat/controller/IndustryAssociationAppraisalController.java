package org.emat.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.ApprovalRequest;
import org.emat.dto.CreateIndustryAssociationAppraisalRequest;
import org.emat.dto.IndustryAssociationAppraisalResponse;
import org.emat.dto.UpdateIndustryAssociationAppraisalRequest;
import org.emat.service.IndustryAssociationAppraisalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/industry-association-appraisals")
@RequiredArgsConstructor
@Slf4j
public class IndustryAssociationAppraisalController {

    private final IndustryAssociationAppraisalService service;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalCreate'))")
    public ResponseEntity<ApiResponse<IndustryAssociationAppraisalResponse>> createAppraisal(
            @RequestBody CreateIndustryAssociationAppraisalRequest request) {
        log.info("Received request to create new Industry Association Appraisal");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Appraisal created successfully", service.createAppraisal(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalRead'))")
    public ResponseEntity<ApiResponse<IndustryAssociationAppraisalResponse>> getAppraisalById(
            @PathVariable("id") Long id) {
        log.info("Received request to fetch appraisal with ID: {}", id);
        return ResponseEntity.ok(ApiResponse.success("Appraisal fetched successfully", service.getAppraisalById(id)));
    }

    @GetMapping("/registration/{registrationId}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalRead'))")
    public ResponseEntity<ApiResponse<IndustryAssociationAppraisalResponse>> getAppraisalByRegistrationUId(
            @PathVariable("registrationId") Long registrationId) {
        log.info("Received request to fetch appraisal for registration ID: {}", registrationId);
        return ResponseEntity.ok(ApiResponse.success("Appraisal fetched successfully", service.getAppraisalByRegistrationId(registrationId)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalRead'))")
    public ResponseEntity<ApiResponse<List<IndustryAssociationAppraisalResponse>>> getAllAppraisals() {
        log.info("Received request to fetch all appraisals");
        return ResponseEntity.ok(ApiResponse.success("Appraisals fetched successfully", service.getAllAppraisals()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalUpdate'))")
    public ResponseEntity<ApiResponse<IndustryAssociationAppraisalResponse>> updateAppraisal(
            @PathVariable("id") Long id,
            @RequestBody UpdateIndustryAssociationAppraisalRequest request) {
        log.info("Received request to update appraisal with ID: {}", id);
        return ResponseEntity.ok(ApiResponse.success("Appraisal updated successfully", service.updateAppraisal(id, request)));
    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalApprove'))")
    public ResponseEntity<ApiResponse<IndustryAssociationAppraisalResponse>> approveBySidbe(
            @PathVariable("id") Long id,
            @RequestBody ApprovalRequest approvalRequest,
            Authentication authentication) {
        log.info("Received SIDBE approval request for appraisal with ID: {}", id);
        return ResponseEntity.ok(ApiResponse.success("Appraisal approved successfully", service.approveBySidbe(id, approvalRequest, authentication.getName())));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalDelete'))")
    public ResponseEntity<ApiResponse<Void>> deleteAppraisal(@PathVariable("id") Long id) {
        log.info("Received request to permanently delete appraisal with ID: {}", id);
        service.permanentlyDeleteAppraisal(id);
        return ResponseEntity.ok(ApiResponse.success("Appraisal deleted successfully", null));
    }


    @Operation(
            summary = "Search Industry Association Appraisals",
            description = "Retrieves Industry Association Appraisals filtered by state, district, and SIDBI approval status."
    )
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalSearch'))")
    public ResponseEntity<ApiResponse<List<IndustryAssociationAppraisalResponse>>> getAppraisals(
            @RequestParam String state,
            @RequestParam String district,
            @RequestParam Boolean isSidbeApproved) {
        return ResponseEntity.ok(ApiResponse.success("Appraisals fetched successfully", service.getAppraisals(state, district, isSidbeApproved)));
    }
}
