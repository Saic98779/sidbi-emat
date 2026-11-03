package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateDiaExposureVisitRequest;
import org.emat.dto.DiaExposureVisitResponse;
import org.emat.dto.UpdateDiaExposureVisitRequest;
import org.emat.service.DiaExposureVisitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dia-exposure-visit")
@RequiredArgsConstructor
@Slf4j
public class DiaExposureVisitController {

    private final DiaExposureVisitService service;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaExposureVisitCreate'))")
    public ResponseEntity<ApiResponse<DiaExposureVisitResponse>> create(
            @RequestBody CreateDiaExposureVisitRequest request) {
        log.info("Received request to create DIA Exposure Visit");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "DIA Exposure Visit created successfully",
                                service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaExposureVisitRead'))")
    public ResponseEntity<ApiResponse<DiaExposureVisitResponse>> getById(
            @PathVariable("id") Long id) {
        log.info("Received request to fetch DIA Exposure Visit with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA Exposure Visit fetched successfully", service.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaExposureVisitRead'))")
    public ResponseEntity<ApiResponse<List<DiaExposureVisitResponse>>> getAll() {
        log.info("Received request to fetch all DIA Exposure Visits");
        return ResponseEntity.ok(
                ApiResponse.success("DIA Exposure Visits fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaExposureVisitUpdate'))")
    public ResponseEntity<ApiResponse<DiaExposureVisitResponse>> update(
            @PathVariable("id") Long id, @RequestBody UpdateDiaExposureVisitRequest request) {
        log.info("Received request to update DIA Exposure Visit with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA Exposure Visit updated successfully", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaExposureVisitDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete DIA Exposure Visit with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("DIA Exposure Visit deleted successfully", null));
    }
}
