package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateGrantDisbursementRequest;
import org.emat.dto.GrantDisbursementResponse;
import org.emat.dto.UpdateGrantDisbursementRequest;
import org.emat.service.GrantDisbursementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grant-disbursement")
@RequiredArgsConstructor
@Slf4j
public class GrantDisbursementController {

    private final GrantDisbursementService service;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('grantDisbursementCreate'))")
    public ResponseEntity<ApiResponse<GrantDisbursementResponse>> create(
            @RequestBody CreateGrantDisbursementRequest request) {
        log.info("Received request to create Grant Disbursement");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "Grant Disbursement created successfully",
                                service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('grantDisbursementRead'))")
    public ResponseEntity<ApiResponse<GrantDisbursementResponse>> getById(
            @PathVariable("id") Long id) {
        log.info("Received request to fetch Grant Disbursement with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Grant Disbursement fetched successfully", service.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('grantDisbursementRead'))")
    public ResponseEntity<ApiResponse<List<GrantDisbursementResponse>>> getAll() {
        log.info("Received request to fetch all Grant Disbursements");
        return ResponseEntity.ok(
                ApiResponse.success("Grant Disbursements fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('grantDisbursementUpdate'))")
    public ResponseEntity<ApiResponse<GrantDisbursementResponse>> update(
            @PathVariable("id") Long id, @RequestBody UpdateGrantDisbursementRequest request) {
        log.info("Received request to update Grant Disbursement with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Grant Disbursement updated successfully", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('grantDisbursementDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete Grant Disbursement with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("Grant Disbursement deleted successfully", null));
    }
}
