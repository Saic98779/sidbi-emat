package org.emat.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.ApprovalRequest;
import org.emat.dto.CreateIndustryAssociationRegistrationRequest;
import org.emat.dto.IndustryAssociationRegistrationResponse;
import org.emat.dto.UpdateIndustryAssociationRegistrationRequest;
import org.emat.service.EndpointRolePolicyService;
import org.emat.service.IndustryAssociationRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
                .body(ApiResponse.created("Registration created successfully", service.createRegistration(request)));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationRead'))")
    public ResponseEntity<ApiResponse<IndustryAssociationRegistrationResponse>> getRegistrationById(
            @PathVariable Long id) {
        log.info("Received request to fetch registration with ID: {}", id);
        return ResponseEntity.ok(ApiResponse.success("Registration fetched successfully", service.getRegistrationById(id)));
    }


    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationRead'))")
    public ResponseEntity<ApiResponse<List<IndustryAssociationRegistrationResponse>>> getAllRegistrations() {
        log.info("Received request to fetch all registrations");
        return ResponseEntity.ok(ApiResponse.success("Registrations fetched successfully", service.getAllRegistrations()));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationWrite'))")
    public ResponseEntity<ApiResponse<IndustryAssociationRegistrationResponse>> updateRegistration(
            @PathVariable Long id,
            @RequestBody UpdateIndustryAssociationRegistrationRequest request) {
        log.info("Received request to update registration with ID: {}", id);
        return ResponseEntity.ok(ApiResponse.success("Registration updated successfully", service.updateRegistration(id, request)));
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
        return ResponseEntity.ok(ApiResponse.success("Registration approved successfully", service.approveBySidbe(id, approvalRequest, authentication.getName())));
    }


    @Operation(
            summary = "Search Industry Association Registrations",
            description = "Retrieves Industry Association Registrations filtered by state, district, and SIDBI approval status."
    )
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationRead'))")
    public ResponseEntity<ApiResponse<List<IndustryAssociationRegistrationResponse>>> getRegistrations(
            @RequestParam String state,
            @RequestParam String district,
            @RequestParam Boolean isSidbeApproved) {
        return ResponseEntity.ok(ApiResponse.success("Registrations fetched successfully", service.getRegistrations(state, district, isSidbeApproved)));
    }
}
