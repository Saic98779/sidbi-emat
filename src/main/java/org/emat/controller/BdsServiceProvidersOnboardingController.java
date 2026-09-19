package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.BdsServiceProvidersOnboardingResponse;
import org.emat.dto.CreateBdsServiceProvidersOnboardingRequest;
import org.emat.dto.UpdateBdsServiceProvidersOnboardingRequest;
import org.emat.service.BdsServiceProvidersOnboardingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bds-service-providers-onboarding")
@RequiredArgsConstructor
@Slf4j
public class BdsServiceProvidersOnboardingController {

    private final BdsServiceProvidersOnboardingService service;

    @PostMapping
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('bdsServiceProvidersOnboardingCreate'))")
    public ResponseEntity<ApiResponse<BdsServiceProvidersOnboardingResponse>> create(
            @RequestBody CreateBdsServiceProvidersOnboardingRequest request) {
        log.info("Received request to create BDS Service Providers Onboarding");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "BDS Service Providers Onboarding created successfully",
                                service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('bdsServiceProvidersOnboardingRead'))")
    public ResponseEntity<ApiResponse<BdsServiceProvidersOnboardingResponse>> getById(
            @PathVariable("id") Long id) {
        log.info(
                "Received request to fetch BDS Service Providers Onboarding with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "BDS Service Providers Onboarding fetched successfully",
                        service.getById(id)));
    }

    @GetMapping
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('bdsServiceProvidersOnboardingRead'))")
    public ResponseEntity<ApiResponse<List<BdsServiceProvidersOnboardingResponse>>> getAll() {
        log.info("Received request to fetch all BDS Service Providers Onboarding");
        return ResponseEntity.ok(
                ApiResponse.success(
                        "BDS Service Providers Onboarding fetched successfully",
                        service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('bdsServiceProvidersOnboardingUpdate'))")
    public ResponseEntity<ApiResponse<BdsServiceProvidersOnboardingResponse>> update(
            @PathVariable("id") Long id,
            @RequestBody UpdateBdsServiceProvidersOnboardingRequest request) {
        log.info(
                "Received request to update BDS Service Providers Onboarding with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "BDS Service Providers Onboarding updated successfully",
                        service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('bdsServiceProvidersOnboardingDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info(
                "Received request to delete BDS Service Providers Onboarding with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("BDS Service Providers Onboarding deleted successfully", null));
    }
}