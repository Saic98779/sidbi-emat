package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateDiaCapacityBuildingProgramRequest;
import org.emat.dto.DiaCapacityBuildingProgramResponse;
import org.emat.dto.UpdateDiaCapacityBuildingProgramRequest;
import org.emat.service.DiaCapacityBuildingProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dia-capacity-building-program")
@RequiredArgsConstructor
@Slf4j
public class DiaCapacityBuildingProgramController {

    private final DiaCapacityBuildingProgramService service;

    @PostMapping
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('diaCapacityBuildingProgramCreate'))")
    public ResponseEntity<ApiResponse<DiaCapacityBuildingProgramResponse>> create(
            @RequestBody CreateDiaCapacityBuildingProgramRequest request) {
        log.info("Received request to create DIA Capacity Building Program");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "DIA Capacity Building Program created successfully",
                                service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('diaCapacityBuildingProgramRead'))")
    public ResponseEntity<ApiResponse<DiaCapacityBuildingProgramResponse>> getById(
            @PathVariable("id") Long id) {
        log.info("Received request to fetch DIA Capacity Building Program with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA Capacity Building Program fetched successfully", service.getById(id)));
    }

    @GetMapping
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('diaCapacityBuildingProgramRead'))")
    public ResponseEntity<ApiResponse<List<DiaCapacityBuildingProgramResponse>>> getAll() {
        log.info("Received request to fetch all DIA Capacity Building Programs");
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA Capacity Building Programs fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('diaCapacityBuildingProgramUpdate'))")
    public ResponseEntity<ApiResponse<DiaCapacityBuildingProgramResponse>> update(
            @PathVariable("id") Long id,
            @RequestBody UpdateDiaCapacityBuildingProgramRequest request) {
        log.info("Received request to update DIA Capacity Building Program with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA Capacity Building Program updated successfully",
                        service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('diaCapacityBuildingProgramDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete DIA Capacity Building Program with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("DIA Capacity Building Program deleted successfully", null));
    }
}
