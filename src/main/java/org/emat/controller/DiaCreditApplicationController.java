package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateDiaCreditApplicationRequest;
import org.emat.dto.DiaCreditApplicationResponse;
import org.emat.dto.UpdateDiaCreditApplicationRequest;
import org.emat.service.DiaCreditApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dia-credit-application")
@RequiredArgsConstructor
@Slf4j
public class DiaCreditApplicationController {

    private final DiaCreditApplicationService service;

    @PostMapping
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('diaCreditApplicationCreate'))")
    public ResponseEntity<ApiResponse<DiaCreditApplicationResponse>> create(
            @RequestBody CreateDiaCreditApplicationRequest request) {
        log.info("Received request to create DIA Credit Application");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "DIA Credit Application created successfully",
                                service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaCreditApplicationRead'))")
    public ResponseEntity<ApiResponse<DiaCreditApplicationResponse>> getById(
            @PathVariable("id") Long id) {
        log.info("Received request to fetch DIA Credit Application with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA Credit Application fetched successfully", service.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaCreditApplicationRead'))")
    public ResponseEntity<ApiResponse<List<DiaCreditApplicationResponse>>> getAll() {
        log.info("Received request to fetch all DIA Credit Applications");
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA Credit Applications fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('diaCreditApplicationUpdate'))")
    public ResponseEntity<ApiResponse<DiaCreditApplicationResponse>> update(
            @PathVariable("id") Long id, @RequestBody UpdateDiaCreditApplicationRequest request) {
        log.info("Received request to update DIA Credit Application with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA Credit Application updated successfully",
                        service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('diaCreditApplicationDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete DIA Credit Application with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("DIA Credit Application deleted successfully", null));
    }
}
