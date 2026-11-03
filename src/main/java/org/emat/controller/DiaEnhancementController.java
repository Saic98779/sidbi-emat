package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateDiaEnhancementRequest;
import org.emat.dto.DiaEnhancementResponse;
import org.emat.dto.UpdateDiaEnhancementRequest;
import org.emat.service.DiaEnhancementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dia-enhancement")
@RequiredArgsConstructor
@Slf4j
public class DiaEnhancementController {

    private final DiaEnhancementService service;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaEnhancementCreate'))")
    public ResponseEntity<ApiResponse<DiaEnhancementResponse>> create(
            @RequestBody CreateDiaEnhancementRequest request) {
        log.info("Received request to create DIA Enhancement");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "DIA Enhancement created successfully", service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaEnhancementRead'))")
    public ResponseEntity<ApiResponse<DiaEnhancementResponse>> getById(
            @PathVariable("id") Long id) {
        log.info("Received request to fetch DIA Enhancement with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success("DIA Enhancement fetched successfully", service.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaEnhancementRead'))")
    public ResponseEntity<ApiResponse<List<DiaEnhancementResponse>>> getAll() {
        log.info("Received request to fetch all DIA Enhancements");
        return ResponseEntity.ok(
                ApiResponse.success("DIA Enhancements fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaEnhancementUpdate'))")
    public ResponseEntity<ApiResponse<DiaEnhancementResponse>> update(
            @PathVariable("id") Long id, @RequestBody UpdateDiaEnhancementRequest request) {
        log.info("Received request to update DIA Enhancement with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA Enhancement updated successfully", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaEnhancementDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete DIA Enhancement with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("DIA Enhancement deleted successfully", null));
    }
}
