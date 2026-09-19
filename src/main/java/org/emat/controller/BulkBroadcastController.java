package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.BulkBroadcastResponse;
import org.emat.dto.CreateBulkBroadcastRequest;
import org.emat.dto.UpdateBulkBroadcastRequest;
import org.emat.service.BulkBroadcastService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bulk-broadcast")
@RequiredArgsConstructor
@Slf4j
public class BulkBroadcastController {

    private final BulkBroadcastService service;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bulkBroadcastCreate'))")
    public ResponseEntity<ApiResponse<BulkBroadcastResponse>> create(
            @RequestBody CreateBulkBroadcastRequest request) {
        log.info("Received request to create Bulk Broadcast");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "Bulk Broadcast created successfully", service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bulkBroadcastRead'))")
    public ResponseEntity<ApiResponse<BulkBroadcastResponse>> getById(
            @PathVariable("id") Long id) {
        log.info("Received request to fetch Bulk Broadcast with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Bulk Broadcast fetched successfully", service.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bulkBroadcastRead'))")
    public ResponseEntity<ApiResponse<List<BulkBroadcastResponse>>> getAll() {
        log.info("Received request to fetch all Bulk Broadcast");
        return ResponseEntity.ok(
                ApiResponse.success("Bulk Broadcast fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bulkBroadcastUpdate'))")
    public ResponseEntity<ApiResponse<BulkBroadcastResponse>> update(
            @PathVariable("id") Long id, @RequestBody UpdateBulkBroadcastRequest request) {
        log.info("Received request to update Bulk Broadcast with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Bulk Broadcast updated successfully", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bulkBroadcastDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete Bulk Broadcast with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("Bulk Broadcast deleted successfully", null));
    }
}