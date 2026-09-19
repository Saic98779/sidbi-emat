package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateDia3CInfoSeriesRequest;
import org.emat.dto.Dia3CInfoSeriesResponse;
import org.emat.dto.UpdateDia3CInfoSeriesRequest;
import org.emat.service.Dia3CInfoSeriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dia-3c-info-series")
@RequiredArgsConstructor
@Slf4j
public class Dia3CInfoSeriesController {

    private final Dia3CInfoSeriesService service;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('dia3CInfoSeriesCreate'))")
    public ResponseEntity<ApiResponse<Dia3CInfoSeriesResponse>> create(
            @RequestBody CreateDia3CInfoSeriesRequest request) {
        log.info("Received request to create DIA 3C Info Series");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "DIA 3C Info Series created successfully",
                                service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('dia3CInfoSeriesRead'))")
    public ResponseEntity<ApiResponse<Dia3CInfoSeriesResponse>> getById(
            @PathVariable("id") Long id) {
        log.info("Received request to fetch DIA 3C Info Series with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA 3C Info Series fetched successfully", service.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('dia3CInfoSeriesRead'))")
    public ResponseEntity<ApiResponse<List<Dia3CInfoSeriesResponse>>> getAll() {
        log.info("Received request to fetch all DIA 3C Info Series");
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA 3C Info Series fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('dia3CInfoSeriesUpdate'))")
    public ResponseEntity<ApiResponse<Dia3CInfoSeriesResponse>> update(
            @PathVariable("id") Long id, @RequestBody UpdateDia3CInfoSeriesRequest request) {
        log.info("Received request to update DIA 3C Info Series with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA 3C Info Series updated successfully",
                        service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('dia3CInfoSeriesDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete DIA 3C Info Series with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("DIA 3C Info Series deleted successfully", null));
    }
}