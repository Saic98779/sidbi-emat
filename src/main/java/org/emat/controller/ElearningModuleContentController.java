package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateElearningModuleContentRequest;
import org.emat.dto.ElearningModuleContentResponse;
import org.emat.dto.UpdateElearningModuleContentRequest;
import org.emat.service.ElearningModuleContentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elearning-module-content")
@RequiredArgsConstructor
@Slf4j
public class ElearningModuleContentController {

    private final ElearningModuleContentService service;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('elearningModuleContentCreate'))")
    public ResponseEntity<ApiResponse<ElearningModuleContentResponse>> create(
            @RequestBody CreateElearningModuleContentRequest request) {
        log.info("Received request to create E-learning Module Content");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "E-learning Module Content created successfully",
                                service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('elearningModuleContentRead'))")
    public ResponseEntity<ApiResponse<ElearningModuleContentResponse>> getById(
            @PathVariable("id") Long id) {
        log.info("Received request to fetch E-learning Module Content with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "E-learning Module Content fetched successfully",
                        service.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('elearningModuleContentRead'))")
    public ResponseEntity<ApiResponse<List<ElearningModuleContentResponse>>> getAll() {
        log.info("Received request to fetch all E-learning Module Content");
        return ResponseEntity.ok(
                ApiResponse.success(
                        "E-learning Module Content fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('elearningModuleContentUpdate'))")
    public ResponseEntity<ApiResponse<ElearningModuleContentResponse>> update(
            @PathVariable("id") Long id,
            @RequestBody UpdateElearningModuleContentRequest request) {
        log.info("Received request to update E-learning Module Content with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "E-learning Module Content updated successfully",
                        service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('elearningModuleContentDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete E-learning Module Content with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("E-learning Module Content deleted successfully", null));
    }
}