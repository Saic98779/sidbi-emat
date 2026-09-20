package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateDisbursementNoteCapacityBuildingIaRequest;
import org.emat.dto.DisbursementNoteCapacityBuildingIaResponse;
import org.emat.dto.UpdateDisbursementNoteCapacityBuildingIaRequest;
import org.emat.dto.UpdateDisbursementNoteCapacityBuildingIaStatusRequest;
import org.emat.service.DisbursementNoteCapacityBuildingIaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disbursement-note-capacity-building-ia")
@RequiredArgsConstructor
@Slf4j
public class DisbursementNoteCapacityBuildingIaController {

    private final DisbursementNoteCapacityBuildingIaService service;

    @PostMapping
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('disbursementNoteCapacityBuildingIaCreate'))")
    public ResponseEntity<ApiResponse<DisbursementNoteCapacityBuildingIaResponse>> create(
            @RequestBody CreateDisbursementNoteCapacityBuildingIaRequest request) {
        log.info("Received request to create Disbursement Note Capacity Building IA");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "Disbursement Note created successfully",
                                service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('disbursementNoteCapacityBuildingIaRead'))")
    public ResponseEntity<ApiResponse<DisbursementNoteCapacityBuildingIaResponse>> getById(
            @PathVariable("id") Long id) {
        log.info("Received request to fetch Disbursement Note with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Disbursement Note fetched successfully", service.getById(id)));
    }

    @GetMapping("/registration/{registrationId}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('disbursementNoteCapacityBuildingIaRead'))")
    public ResponseEntity<ApiResponse<DisbursementNoteCapacityBuildingIaResponse>>
            getByRegistrationId(@PathVariable("registrationId") Long registrationId) {
        log.info(
                "Received request to fetch Disbursement Note for registration ID: {}",
                registrationId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Disbursement Note fetched successfully",
                        service.getByRegistrationId(registrationId)));
    }

    @GetMapping
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('disbursementNoteCapacityBuildingIaRead'))")
    public ResponseEntity<ApiResponse<List<DisbursementNoteCapacityBuildingIaResponse>>> getAll() {
        log.info("Received request to fetch all Disbursement Notes");
        return ResponseEntity.ok(
                ApiResponse.success("Disbursement Notes fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('disbursementNoteCapacityBuildingIaUpdate'))")
    public ResponseEntity<ApiResponse<DisbursementNoteCapacityBuildingIaResponse>> update(
            @PathVariable("id") Long id,
            @RequestBody UpdateDisbursementNoteCapacityBuildingIaRequest request) {
        log.info("Received request to update Disbursement Note with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Disbursement Note updated successfully", service.update(id, request)));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('disbursementNoteCapacityBuildingIaUpdate'))")
    public ResponseEntity<ApiResponse<DisbursementNoteCapacityBuildingIaResponse>> updateStatus(
            @PathVariable("id") Long id,
            @RequestBody UpdateDisbursementNoteCapacityBuildingIaStatusRequest request) {
        log.info("Received request to update Disbursement Note status with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Disbursement Note status updated successfully",
                        service.updateStatus(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('disbursementNoteCapacityBuildingIaDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete Disbursement Note with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("Disbursement Note deleted successfully", null));
    }
}