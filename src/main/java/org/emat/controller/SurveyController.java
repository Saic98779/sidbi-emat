package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateSurveyRequest;
import org.emat.dto.SurveyResponse;
import org.emat.dto.UpdateSurveyRequest;
import org.emat.dto.UpdateSurveyStatusRequest;
import org.emat.service.SurveyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/surveys")
@RequiredArgsConstructor
@Slf4j
public class SurveyController {

    private final SurveyService service;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('surveyCreate'))")
    public ResponseEntity<ApiResponse<SurveyResponse>> create(
            @RequestBody CreateSurveyRequest request) {
        log.info("Received request to create Survey");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Survey created successfully", service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('surveyRead'))")
    public ResponseEntity<ApiResponse<SurveyResponse>> getById(@PathVariable("id") Long id) {
        log.info("Received request to fetch Survey with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success("Survey fetched successfully", service.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('surveyRead'))")
    public ResponseEntity<ApiResponse<List<SurveyResponse>>> getAll() {
        log.info("Received request to fetch all Surveys");
        return ResponseEntity.ok(
                ApiResponse.success("Surveys fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('surveyUpdate'))")
    public ResponseEntity<ApiResponse<SurveyResponse>> update(
            @PathVariable("id") Long id, @RequestBody UpdateSurveyRequest request) {
        log.info("Received request to update Survey with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success("Survey updated successfully", service.update(id, request)));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('surveyUpdate'))")
    public ResponseEntity<ApiResponse<SurveyResponse>> updateStatus(
            @PathVariable("id") Long id, @RequestBody UpdateSurveyStatusRequest request) {
        log.info("Received request to update Survey status with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Survey status updated successfully", service.updateStatus(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('surveyDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete Survey with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Survey deleted successfully", null));
    }
}
