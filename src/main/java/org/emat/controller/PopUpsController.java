package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreatePopUpsRequest;
import org.emat.dto.PopUpsResponse;
import org.emat.dto.UpdatePopUpsRequest;
import org.emat.service.PopUpsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pop-ups")
@RequiredArgsConstructor
@Slf4j
public class PopUpsController {

    private final PopUpsService service;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('popUpsCreate'))")
    public ResponseEntity<ApiResponse<PopUpsResponse>> create(
            @RequestBody CreatePopUpsRequest request) {
        log.info("Received request to create Pop-Ups");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "Pop-Ups created successfully", service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('popUpsRead'))")
    public ResponseEntity<ApiResponse<PopUpsResponse>> getById(@PathVariable("id") Long id) {
        log.info("Received request to fetch Pop-Ups with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success("Pop-Ups fetched successfully", service.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('popUpsRead'))")
    public ResponseEntity<ApiResponse<List<PopUpsResponse>>> getAll() {
        log.info("Received request to fetch all Pop-Ups");
        return ResponseEntity.ok(
                ApiResponse.success("Pop-Ups fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('popUpsUpdate'))")
    public ResponseEntity<ApiResponse<PopUpsResponse>> update(
            @PathVariable("id") Long id, @RequestBody UpdatePopUpsRequest request) {
        log.info("Received request to update Pop-Ups with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success("Pop-Ups updated successfully", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('popUpsDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete Pop-Ups with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Pop-Ups deleted successfully", null));
    }
}