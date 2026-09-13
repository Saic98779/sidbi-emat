package org.emat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.BseRecommendationResponse;
import org.emat.dto.CreateBseRecommendationRequest;
import org.emat.dto.UpdateBseRecommendationRequest;
import org.emat.service.IndustryAssociationBseRecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bse-recommendations")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "BSE Recommendation Management", description = "APIs for managing BSE (Business Support Executive) recommendations")
@SecurityRequirement(name = "bearerAuth")
public class IndustryAssociationBseRecommendationController {

    private final IndustryAssociationBseRecommendationService bseRecommendationService;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseRecommendationCreate'))")
    @Operation(summary = "Create BSE recommendation", description = "Create a new BSE recommendation for an industry association")
    public ResponseEntity<ApiResponse<BseRecommendationResponse>> createBseRecommendation(
            @RequestBody CreateBseRecommendationRequest request) {
        log.info("REST request to create BSE recommendation for registration: {}", request.getRegistrationId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("BSE recommendation created successfully", bseRecommendationService.createBseRecommendation(request)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseRecommendationRead'))")
    @Operation(summary = "Get all BSE recommendations", description = "Retrieve all active BSE recommendations")
    public ResponseEntity<ApiResponse<List<BseRecommendationResponse>>> getAllBseRecommendations() {
        log.info("REST request to get all BSE recommendations");
        return ResponseEntity.ok(ApiResponse.success("BSE recommendations fetched successfully", bseRecommendationService.getAllBseRecommendations()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseRecommendationRead'))")
    @Operation(summary = "Get BSE recommendation by ID", description = "Retrieve a specific BSE recommendation by its ID")
    public ResponseEntity<ApiResponse<BseRecommendationResponse>> getBseRecommendationById(@PathVariable("id") Long id) {
        log.info("REST request to get BSE recommendation with ID: {}", id);
        return ResponseEntity.ok(ApiResponse.success("BSE recommendation fetched successfully", bseRecommendationService.getBseRecommendationById(id)));
    }

    @GetMapping("/registration/{registrationId}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseRecommendationRead'))")
    @Operation(summary = "Get BSE recommendations by registration", description = "Retrieve all BSE recommendations for a specific industry association")
    public ResponseEntity<ApiResponse<List<BseRecommendationResponse>>> getBseRecommendationsByRegistration(
            @PathVariable Long registrationId) {
        log.info("REST request to get BSE recommendations for registration: {}", registrationId);
        return ResponseEntity.ok(ApiResponse.success("BSE recommendations fetched successfully", bseRecommendationService.getBseRecommendationsByRegistration(registrationId)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseRecommendationUpdate'))")
    @Operation(summary = "Update BSE recommendation", description = "Update an existing BSE recommendation")
    public ResponseEntity<ApiResponse<BseRecommendationResponse>> updateBseRecommendation(
            @PathVariable("id") Long id,
            @RequestBody UpdateBseRecommendationRequest request) {
        log.info("REST request to update BSE recommendation with ID: {}", id);
        return ResponseEntity.ok(ApiResponse.success("BSE recommendation updated successfully", bseRecommendationService.updateBseRecommendation(id, request)));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseRecommendationRead'))")
    @Operation(summary = "Search BSE recommendations by name", description = "Search BSE recommendations by BSE name")
    public ResponseEntity<ApiResponse<List<BseRecommendationResponse>>> searchByBseName(@RequestParam String bseName) {
        log.info("REST request to search BSE recommendations by name: {}", bseName);
        return ResponseEntity.ok(ApiResponse.success("BSE recommendations fetched successfully", bseRecommendationService.searchByBseName(bseName)));
    }

    @GetMapping("/gt-recommendation")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseRecommendationRead'))")
    @Operation(summary = "Get BSE recommendations by GT status", description = "Filter BSE recommendations where GT recommendation is not null or null based on isRecommended")
    public ResponseEntity<ApiResponse<List<BseRecommendationResponse>>> getByGtRecommendation(@RequestParam boolean isRecommended) {
        log.info("REST request to get BSE recommendations with GT recommendation set");
        return ResponseEntity.ok(ApiResponse.success("BSE recommendations fetched successfully", bseRecommendationService.getByGtRecommendation(isRecommended)));
    }

    @GetMapping("/pmu-recommendation")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseRecommendationRead'))")
    @Operation(summary = "Get BSE recommendations by PMU status", description = "Filter BSE recommendations where PMU recommendation is not null or null based on isRecommended")
    public ResponseEntity<ApiResponse<List<BseRecommendationResponse>>> getByPmuRecommendation(@RequestParam boolean isRecommended) {
        log.info("REST request to get BSE recommendations with PMU recommendation set");
        return ResponseEntity.ok(ApiResponse.success("BSE recommendations fetched successfully", bseRecommendationService.getByPmuRecommendation(isRecommended)));
    }

    @GetMapping("/ho-recommendation")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseRecommendationRead'))")
    @Operation(summary = "Get BSE recommendations by HO status", description = "Filter BSE recommendations where HO recommendation is not null")
    public ResponseEntity<ApiResponse<List<BseRecommendationResponse>>> getByHoRecommendation(@RequestParam boolean isRecommended) {
        log.info("REST request to get BSE recommendations with HO recommendation set");
        return ResponseEntity.ok(ApiResponse.success("BSE recommendations fetched successfully", bseRecommendationService.getByHoRecommendation(isRecommended)));
    }

    @GetMapping("/mapped/{status}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseRecommendationRead'))")
    @Operation(summary = "Get BSE recommendations by mapped status", description = "Filter BSE recommendations by IA mapping status")
    public ResponseEntity<ApiResponse<List<BseRecommendationResponse>>> getByMappedStatus(@PathVariable Boolean status) {
        log.info("REST request to get BSE recommendations by mapped status: {}", status);
        return ResponseEntity.ok(ApiResponse.success("BSE recommendations fetched successfully", bseRecommendationService.getByMappedStatus(status)));
    }

    @Operation(
            summary = "Get selected BSE recommendations by vendor",
            description = "Fetches all active BSE recommendations mapped to the specified vendor where the recommendation is marked as selected."
    )
    @GetMapping("/user/{userId}/selected")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseRecommendationRead'))")
    public ResponseEntity<ApiResponse<List<BseRecommendationResponse>>> getSelectedBseByVendor(
            @PathVariable Long userId) {

        return ResponseEntity.ok(ApiResponse.success("BSE recommendations fetched successfully", bseRecommendationService.getSelectedBseByVendor(userId)));
    }
}
