package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.BdspResponse;
import org.emat.dto.CreateBdspRequest;
import org.emat.dto.UpdateBdspRequest;
import org.emat.service.BdspService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bdsp")
@RequiredArgsConstructor
@Slf4j
public class BdspController {

    private final BdspService service;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bdspCreate'))")
    public ResponseEntity<ApiResponse<BdspResponse>> create(@RequestBody CreateBdspRequest request) {
        log.info("Received request to create BDSP");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "BDSP created successfully", service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bdspRead'))")
    public ResponseEntity<ApiResponse<BdspResponse>> getById(@PathVariable("id") Long id) {
        log.info("Received request to fetch BDSP with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success("BDSP fetched successfully", service.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bdspRead'))")
    public ResponseEntity<ApiResponse<List<BdspResponse>>> getAll() {
        log.info("Received request to fetch all BDSP");
        return ResponseEntity.ok(
                ApiResponse.success("BDSP fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bdspUpdate'))")
    public ResponseEntity<ApiResponse<BdspResponse>> update(
            @PathVariable("id") Long id, @RequestBody UpdateBdspRequest request) {
        log.info("Received request to update BDSP with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success("BDSP updated successfully", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bdspDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete BDSP with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("BDSP deleted successfully", null));
    }
}