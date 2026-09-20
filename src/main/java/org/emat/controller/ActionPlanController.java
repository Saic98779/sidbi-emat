package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ActionPlanResponse;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateActionPlanRequest;
import org.emat.dto.UpdateActionPlanRequest;
import org.emat.dto.UpdateActionPlanStatusRequest;
import org.emat.service.ActionPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/action-plans")
@RequiredArgsConstructor
@Slf4j
public class ActionPlanController {

    private final ActionPlanService service;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('actionPlanCreate'))")
    public ResponseEntity<ApiResponse<ActionPlanResponse>> create(
            @RequestBody CreateActionPlanRequest request) {
        log.info("Received request to create Action Plan");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "Action Plan created successfully", service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('actionPlanRead'))")
    public ResponseEntity<ApiResponse<ActionPlanResponse>> getById(@PathVariable("id") Long id) {
        log.info("Received request to fetch Action Plan with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success("Action Plan fetched successfully", service.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('actionPlanRead'))")
    public ResponseEntity<ApiResponse<List<ActionPlanResponse>>> getAll() {
        log.info("Received request to fetch all Action Plans");
        return ResponseEntity.ok(
                ApiResponse.success("Action Plans fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('actionPlanUpdate'))")
    public ResponseEntity<ApiResponse<ActionPlanResponse>> update(
            @PathVariable("id") Long id, @RequestBody UpdateActionPlanRequest request) {
        log.info("Received request to update Action Plan with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Action Plan updated successfully", service.update(id, request)));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('actionPlanUpdate'))")
    public ResponseEntity<ApiResponse<ActionPlanResponse>> updateStatus(
            @PathVariable("id") Long id, @RequestBody UpdateActionPlanStatusRequest request) {
        log.info("Received request to update Action Plan status with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Action Plan status updated successfully",
                        service.updateStatus(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('actionPlanDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete Action Plan with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Action Plan deleted successfully", null));
    }
}
