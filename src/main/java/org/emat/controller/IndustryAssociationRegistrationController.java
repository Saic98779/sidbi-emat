package org.emat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApprovalRequest;
import org.emat.dto.CreateIndustryAssociationRegistrationRequest;
import org.emat.dto.IndustryAssociationRegistrationResponse;
import org.emat.dto.UpdateIndustryAssociationRegistrationRequest;
import org.emat.service.IndustryAssociationRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Industry Association Registration endpoints.
 * Provides CRUD operations and query endpoints for managing registrations.
 */
@RestController
@RequestMapping("/industry-association-registrations")
@RequiredArgsConstructor
@Slf4j
public class IndustryAssociationRegistrationController {

    private final IndustryAssociationRegistrationService service;

    /**
     * Create a new Industry Association Registration.
     * POST /industry-association-registrations
     *
     * @param request the creation request
     * @return ResponseEntity with created registration and HTTP 201
     */
    @PostMapping
    public ResponseEntity<IndustryAssociationRegistrationResponse> createRegistration(
            @RequestBody CreateIndustryAssociationRegistrationRequest request) {
        log.info("Received request to create new Industry Association Registration");
        IndustryAssociationRegistrationResponse response = service.createRegistration(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieve a registration by UUID.
     * GET /industry-association-registrations/{uuid}
     *
     * @param uuid the unique identifier
     * @return ResponseEntity with registration and HTTP 200
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<IndustryAssociationRegistrationResponse> getRegistrationById(
            @PathVariable String uuid) {
        log.info("Received request to fetch registration with UUID: {}", uuid);
        IndustryAssociationRegistrationResponse response = service.getRegistrationById(uuid);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve all active registrations.
     * GET /industry-association-registrations
     *
     * @return ResponseEntity with list of registrations and HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<IndustryAssociationRegistrationResponse>> getAllRegistrations() {
        log.info("Received request to fetch all registrations");
        List<IndustryAssociationRegistrationResponse> responses = service.getAllRegistrations();
        return ResponseEntity.ok(responses);
    }

    /**
     * Update an existing registration.
     * PUT /industry-association-registrations/{uuid}
     *
     * @param uuid the unique identifier
     * @param request the update request
     * @return ResponseEntity with updated registration and HTTP 200
     */
    @PutMapping("/{uuid}")
    public ResponseEntity<IndustryAssociationRegistrationResponse> updateRegistration(
            @PathVariable String uuid,
            @RequestBody UpdateIndustryAssociationRegistrationRequest request) {
        log.info("Received request to update registration with UUID: {}", uuid);
        IndustryAssociationRegistrationResponse response = service.updateRegistration(uuid, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft delete a registration (mark as inactive).
     * DELETE /industry-association-registrations/{uuid}
     *
     * @param uuid the unique identifier
     * @return ResponseEntity with HTTP 204 (No Content)
     */
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable String uuid) {
        log.info("Received request to delete registration with UUID: {}", uuid);
        service.deleteRegistration(uuid);
        return ResponseEntity.noContent().build();
    }

    /**
     * Approve or reject a registration by SIDBE.
     * PATCH /industry-association-registrations/{uuid}/approve
     *
     * @param uuid the unique identifier
     * @param approvalRequest the approval request
     * @param authentication the current authenticated user
     * @return ResponseEntity with updated registration and HTTP 200
     */
    @PatchMapping("/{uuid}/approve")
    public ResponseEntity<IndustryAssociationRegistrationResponse> approveBySidbe(
            @PathVariable String uuid,
            @RequestBody ApprovalRequest approvalRequest,
            Authentication authentication) {
        log.info("Received SIDBE approval request for registration with UUID: {}", uuid);
        String username = authentication.getName();
        IndustryAssociationRegistrationResponse response = service.approveBySidbe(uuid, approvalRequest, username);
        return ResponseEntity.ok(response);
    }

    /**
     * Permanently delete a registration (hard delete).
     * DELETE /industry-association-registrations/{uuid}/permanent
     *
     * @param uuid the unique identifier
     * @return ResponseEntity with HTTP 204 (No Content)
     */
    @DeleteMapping("/{uuid}/permanent")
    public ResponseEntity<Void> permanentlyDeleteRegistration(@PathVariable String uuid) {
        log.info("Received request to permanently delete registration with UUID: {}", uuid);
        service.permanentlyDeleteRegistration(uuid);
        return ResponseEntity.noContent().build();
    }
}
