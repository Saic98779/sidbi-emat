package org.emat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.BseRecommendationResponse;
import org.emat.dto.CreateBseRecommendationRequest;
import org.emat.dto.UpdateBseRecommendationRequest;
import org.emat.service.IndustryAssociationBseRecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bse-recommendations")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "BSE Recommendation Management", description = "APIs for managing BSE (Business Support Executive) recommendations")
@SecurityRequirement(name = "bearerAuth")
public class IndustryAssociationBseRecommendationController {

    private final IndustryAssociationBseRecommendationService bseRecommendationService;

    /**
     * Create a new BSE recommendation
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('BSE', 'GT_FIELD_TEAM', 'DIA', 'SIDBI_HO_MAKER', 'SIDBI_RO')")
    @Operation(summary = "Create BSE recommendation", description = "Create a new BSE recommendation for an industry association")
    public ResponseEntity<BseRecommendationResponse> createBseRecommendation(
            @RequestBody CreateBseRecommendationRequest request) {
        log.info("REST request to create BSE recommendation for registration: {}", request.getRegistrationUuid());
        BseRecommendationResponse response = bseRecommendationService.createBseRecommendation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all BSE recommendations
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('BSE', 'GT_FIELD_TEAM', 'GT_PMU', 'DIA', 'SIDBI_SDE', 'SIDBI_RO', 'SIDBI_HO_MAKER', 'SIDBI_HO_CHECKER', 'CLUSTER_EXPERT')")
    @Operation(summary = "Get all BSE recommendations", description = "Retrieve all active BSE recommendations")
    public ResponseEntity<List<BseRecommendationResponse>> getAllBseRecommendations() {
        log.info("REST request to get all BSE recommendations");
        List<BseRecommendationResponse> recommendations = bseRecommendationService.getAllBseRecommendations();
        return ResponseEntity.ok(recommendations);
    }

    /**
     * Get BSE recommendation by UUID
     */
    @GetMapping("/{uuid}")
    @PreAuthorize("hasAnyRole('BSE', 'GT_FIELD_TEAM', 'GT_PMU', 'DIA', 'SIDBI_SDE', 'SIDBI_RO', 'SIDBI_HO_MAKER', 'SIDBI_HO_CHECKER', 'CLUSTER_EXPERT')")
    @Operation(summary = "Get BSE recommendation by UUID", description = "Retrieve a specific BSE recommendation by its UUID")
    public ResponseEntity<BseRecommendationResponse> getBseRecommendationByUuid(@PathVariable UUID uuid) {
        log.info("REST request to get BSE recommendation with UUID: {}", uuid);
        BseRecommendationResponse response = bseRecommendationService.getBseRecommendationByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    /**
     * Get BSE recommendations by registration UUID
     */
    @GetMapping("/registration/{registrationUuid}")
    @PreAuthorize("hasAnyRole('BSE', 'GT_FIELD_TEAM', 'GT_PMU', 'DIA', 'SIDBI_SDE', 'SIDBI_RO', 'SIDBI_HO_MAKER', 'SIDBI_HO_CHECKER', 'CLUSTER_EXPERT')")
    @Operation(summary = "Get BSE recommendations by registration", description = "Retrieve all BSE recommendations for a specific industry association")
    public ResponseEntity<List<BseRecommendationResponse>> getBseRecommendationsByRegistration(
            @PathVariable UUID registrationUuid) {
        log.info("REST request to get BSE recommendations for registration: {}", registrationUuid);
        List<BseRecommendationResponse> recommendations = bseRecommendationService
                .getBseRecommendationsByRegistration(registrationUuid);
        return ResponseEntity.ok(recommendations);
    }

    /**
     * Update BSE recommendation
     */
    @PutMapping("/{uuid}")
    @PreAuthorize("hasAnyRole('BSE', 'GT_FIELD_TEAM', 'DIA', 'SIDBI_HO_MAKER', 'SIDBI_RO')")
    @Operation(summary = "Update BSE recommendation", description = "Update an existing BSE recommendation")
    public ResponseEntity<BseRecommendationResponse> updateBseRecommendation(
            @PathVariable UUID uuid,
            @RequestBody UpdateBseRecommendationRequest request) {
        log.info("REST request to update BSE recommendation with UUID: {}", uuid);
        BseRecommendationResponse response = bseRecommendationService.updateBseRecommendation(uuid, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Search BSE recommendations by name
     */
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('BSE', 'GT_FIELD_TEAM', 'GT_PMU', 'DIA', 'SIDBI_SDE', 'SIDBI_RO', 'SIDBI_HO_MAKER', 'SIDBI_HO_CHECKER', 'CLUSTER_EXPERT')")
    @Operation(summary = "Search BSE recommendations by name", description = "Search BSE recommendations by BSE name")
    public ResponseEntity<List<BseRecommendationResponse>> searchByBseName(@RequestParam String bseName) {
        log.info("REST request to search BSE recommendations by name: {}", bseName);
        List<BseRecommendationResponse> recommendations = bseRecommendationService.searchByBseName(bseName);
        return ResponseEntity.ok(recommendations);
    }

    /**
     * Get BSE recommendations by GT recommendation status
     */
    @GetMapping("/gt-recommendation")
    @PreAuthorize("hasAnyRole('GT_FIELD_TEAM', 'GT_PMU', 'DIA', 'SIDBI_HO_MAKER', 'SIDBI_RO')")
    @Operation(summary = "Get BSE recommendations by GT status", description = "Filter BSE recommendations where GT recommendation is not null")
    public ResponseEntity<List<BseRecommendationResponse>> getByGtRecommendation() {
        log.info("REST request to get BSE recommendations with GT recommendation set");
        List<BseRecommendationResponse> recommendations = bseRecommendationService.getByGtRecommendation();
        return ResponseEntity.ok(recommendations);
    }

    /**
     * Get BSE recommendations by PMU recommendation status
     */
    @GetMapping("/pmu-recommendation")
    @PreAuthorize("hasAnyRole('GT_PMU', 'SIDBI_HO_MAKER', 'SIDBI_RO')")
    @Operation(summary = "Get BSE recommendations by PMU status", description = "Filter BSE recommendations where PMU recommendation is not null")
    public ResponseEntity<List<BseRecommendationResponse>> getByPmuRecommendation() {
        log.info("REST request to get BSE recommendations with PMU recommendation set");
        List<BseRecommendationResponse> recommendations = bseRecommendationService.getByPmuRecommendation();
        return ResponseEntity.ok(recommendations);
    }

    /**
     * Get BSE recommendations by HO recommendation status
     */
    @GetMapping("/ho-recommendation")
    @PreAuthorize("hasAnyRole('SIDBI_HO_MAKER', 'SIDBI_HO_CHECKER', 'SIDBI_RO')")
    @Operation(summary = "Get BSE recommendations by HO status", description = "Filter BSE recommendations where HO recommendation is not null")
    public ResponseEntity<List<BseRecommendationResponse>> getByHoRecommendation() {
        log.info("REST request to get BSE recommendations with HO recommendation set");
        List<BseRecommendationResponse> recommendations = bseRecommendationService.getByHoRecommendation();
        return ResponseEntity.ok(recommendations);
    }

    /**
     * Get mapped/unmapped BSE recommendations
     */
    @GetMapping("/mapped/{status}")
    @PreAuthorize("hasAnyRole('GT_FIELD_TEAM', 'GT_PMU', 'BSE', 'DIA', 'SIDBI_SDE', 'SIDBI_RO', 'SIDBI_HO_MAKER', 'SIDBI_HO_CHECKER', 'CLUSTER_EXPERT')")
    @Operation(summary = "Get BSE recommendations by mapped status", description = "Filter BSE recommendations by IA mapping status")
    public ResponseEntity<List<BseRecommendationResponse>> getByMappedStatus(@PathVariable Boolean status) {
        log.info("REST request to get BSE recommendations by mapped status: {}", status);
        List<BseRecommendationResponse> recommendations = bseRecommendationService.getByMappedStatus(status);
        return ResponseEntity.ok(recommendations);
    }
}
