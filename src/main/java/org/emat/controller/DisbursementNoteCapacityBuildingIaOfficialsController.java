package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateDisbursementNoteCapacityBuildingIaOfficialsRequest;
import org.emat.dto.DisbursementNoteCapacityBuildingIaOfficialsResponse;
import org.emat.dto.UpdateDisbursementNoteCapacityBuildingIaOfficialsRequest;
import org.emat.service.DisbursementNoteCapacityBuildingIaOfficialsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disbursement-note-capacity-building-ia-officials")
@RequiredArgsConstructor
@Slf4j
public class DisbursementNoteCapacityBuildingIaOfficialsController {

    private final DisbursementNoteCapacityBuildingIaOfficialsService service;

    @PostMapping
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('disbursementNoteCapacityBuildingIaOfficialsCreate'))")
    public ResponseEntity<ApiResponse<DisbursementNoteCapacityBuildingIaOfficialsResponse>> create(
            @RequestBody CreateDisbursementNoteCapacityBuildingIaOfficialsRequest request) {
        log.info("Received request to create Disbursement Note Capacity Building IA Officials");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "Disbursement Note created successfully",
                                service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('disbursementNoteCapacityBuildingIaOfficialsRead'))")
    public ResponseEntity<ApiResponse<DisbursementNoteCapacityBuildingIaOfficialsResponse>>
            getById(@PathVariable("id") Long id) {
        log.info("Received request to fetch Disbursement Note with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Disbursement Note fetched successfully", service.getById(id)));
    }

    @GetMapping("/registration/{registrationId}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('disbursementNoteCapacityBuildingIaOfficialsRead'))")
    public ResponseEntity<ApiResponse<DisbursementNoteCapacityBuildingIaOfficialsResponse>>
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
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('disbursementNoteCapacityBuildingIaOfficialsRead'))")
    public ResponseEntity<ApiResponse<List<DisbursementNoteCapacityBuildingIaOfficialsResponse>>>
            getAll() {
        log.info("Received request to fetch all Disbursement Notes");
        return ResponseEntity.ok(
                ApiResponse.success("Disbursement Notes fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('disbursementNoteCapacityBuildingIaOfficialsUpdate'))")
    public ResponseEntity<ApiResponse<DisbursementNoteCapacityBuildingIaOfficialsResponse>> update(
            @PathVariable("id") Long id,
            @RequestBody UpdateDisbursementNoteCapacityBuildingIaOfficialsRequest request) {
        log.info("Received request to update Disbursement Note with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Disbursement Note updated successfully", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('disbursementNoteCapacityBuildingIaOfficialsDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete Disbursement Note with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("Disbursement Note deleted successfully", null));
    }
}