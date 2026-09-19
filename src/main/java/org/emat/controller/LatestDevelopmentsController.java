package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateLatestDevelopmentsRequest;
import org.emat.dto.LatestDevelopmentsResponse;
import org.emat.dto.UpdateLatestDevelopmentsRequest;
import org.emat.service.LatestDevelopmentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/latest-developments")
@RequiredArgsConstructor
@Slf4j
public class LatestDevelopmentsController {

    private final LatestDevelopmentsService service;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('latestDevelopmentsCreate'))")
    public ResponseEntity<ApiResponse<LatestDevelopmentsResponse>> create(
            @RequestBody CreateLatestDevelopmentsRequest request) {
        log.info("Received request to create Latest Developments");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "Latest Developments created successfully",
                                service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('latestDevelopmentsRead'))")
    public ResponseEntity<ApiResponse<LatestDevelopmentsResponse>> getById(
            @PathVariable("id") Long id) {
        log.info("Received request to fetch Latest Developments with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Latest Developments fetched successfully", service.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('latestDevelopmentsRead'))")
    public ResponseEntity<ApiResponse<List<LatestDevelopmentsResponse>>> getAll() {
        log.info("Received request to fetch all Latest Developments");
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Latest Developments fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('latestDevelopmentsUpdate'))")
    public ResponseEntity<ApiResponse<LatestDevelopmentsResponse>> update(
            @PathVariable("id") Long id, @RequestBody UpdateLatestDevelopmentsRequest request) {
        log.info("Received request to update Latest Developments with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Latest Developments updated successfully",
                        service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('latestDevelopmentsDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete Latest Developments with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("Latest Developments deleted successfully", null));
    }
}