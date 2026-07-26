package org.emat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApprovalRequest;
import org.emat.dto.CreateIndustryAssociationAppraisalRequest;
import org.emat.dto.IndustryAssociationAppraisalResponse;
import org.emat.dto.UpdateIndustryAssociationAppraisalRequest;
import org.emat.service.IndustryAssociationAppraisalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Industry Association Appraisal endpoints.
 * Provides CRUD operations for managing appraisals with 1:1 relationship to registrations.
 */
@RestController
@RequestMapping("/api/v1/industry-association-appraisals")
@RequiredArgsConstructor
@Slf4j
public class IndustryAssociationAppraisalController {

    private final IndustryAssociationAppraisalService service;

    /**
     * Create a new Industry Association Appraisal.
     * POST /api/v1/industry-association-appraisals
     *
     * @param request the creation request
     * @return ResponseEntity with created appraisal and HTTP 201
     */
    @PostMapping
    public ResponseEntity<IndustryAssociationAppraisalResponse> createAppraisal(
            @RequestBody CreateIndustryAssociationAppraisalRequest request) {
        log.info("Received request to create new Industry Association Appraisal");
        IndustryAssociationAppraisalResponse response = service.createAppraisal(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieve an appraisal by UUID.
     * GET /api/v1/industry-association-appraisals/{uuid}
     *
     * @param uuid the unique identifier
     * @return ResponseEntity with appraisal and HTTP 200
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<IndustryAssociationAppraisalResponse> getAppraisalById(
            @PathVariable String uuid) {
        log.info("Received request to fetch appraisal with UUID: {}", uuid);
        IndustryAssociationAppraisalResponse response = service.getAppraisalById(uuid);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve appraisal by registration UUID.
     * GET /api/v1/industry-association-appraisals/registration/{registrationUuid}
     *
     * @param registrationUuid the registration unique identifier
     * @return ResponseEntity with appraisal and HTTP 200
     */
    @GetMapping("/registration/{registrationUuid}")
    public ResponseEntity<IndustryAssociationAppraisalResponse> getAppraisalByRegistrationUuid(
            @PathVariable String registrationUuid) {
        log.info("Received request to fetch appraisal for registration UUID: {}", registrationUuid);
        IndustryAssociationAppraisalResponse response = service.getAppraisalByRegistrationUuid(registrationUuid);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve all active appraisals.
     * GET /api/v1/industry-association-appraisals
     *
     * @return ResponseEntity with list of appraisals and HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<IndustryAssociationAppraisalResponse>> getAllAppraisals() {
        log.info("Received request to fetch all appraisals");
        List<IndustryAssociationAppraisalResponse> responses = service.getAllAppraisals();
        return ResponseEntity.ok(responses);
    }

    /**
     * Update an existing appraisal.
     * PUT /api/v1/industry-association-appraisals/{uuid}
     *
     * @param uuid the unique identifier
     * @param request the update request
     * @return ResponseEntity with updated appraisal and HTTP 200
     */
    @PutMapping("/{uuid}")
    public ResponseEntity<IndustryAssociationAppraisalResponse> updateAppraisal(
            @PathVariable String uuid,
            @RequestBody UpdateIndustryAssociationAppraisalRequest request) {
        log.info("Received request to update appraisal with UUID: {}", uuid);
        IndustryAssociationAppraisalResponse response = service.updateAppraisal(uuid, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Approve or reject an appraisal by SIDBE.
     * PATCH /api/v1/industry-association-appraisals/{uuid}/approve
     *
     * @param uuid the unique identifier
     * @param approvalRequest the approval request
     * @param authentication the current authenticated user
     * @return ResponseEntity with updated appraisal and HTTP 200
     */
    @PatchMapping("/{uuid}/approve")
    public ResponseEntity<IndustryAssociationAppraisalResponse> approveBySidbe(
            @PathVariable String uuid,
            @RequestBody ApprovalRequest approvalRequest,
            Authentication authentication) {
        log.info("Received SIDBE approval request for appraisal with UUID: {}", uuid);
        String username = authentication.getName();
        IndustryAssociationAppraisalResponse response = service.approveBySidbe(uuid, approvalRequest, username);
        return ResponseEntity.ok(response);
    }

    /**
     * Permanently delete an appraisal (hard delete).
     * DELETE /api/v1/industry-association-appraisals/{uuid}/permanent
     *
     * @param uuid the unique identifier
     * @return ResponseEntity with HTTP 204 (No Content)
     */
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> DeleteAppraisal(@PathVariable String uuid) {
        log.info("Received request to permanently delete appraisal with UUID: {}", uuid);
        service.permanentlyDeleteAppraisal(uuid);
        return ResponseEntity.noContent().build();
    }
}

