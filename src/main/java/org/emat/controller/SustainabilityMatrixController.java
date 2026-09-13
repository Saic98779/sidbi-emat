package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.ApiResponse;
import org.emat.dto.AppraisalDropdownDto;
import org.emat.dto.SustainabilityMatrixRequest;
import org.emat.dto.SustainabilityMatrixResponse;
import org.emat.service.SustainabilityMatrixService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sustainability-matrix")
@RequiredArgsConstructor
public class SustainabilityMatrixController {

    private final SustainabilityMatrixService service;

    @PostMapping
    public ResponseEntity<ApiResponse<SustainabilityMatrixResponse>> create(@RequestBody SustainabilityMatrixRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Sustainability matrix created successfully", service.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SustainabilityMatrixResponse>> update(@PathVariable Long id, @RequestBody SustainabilityMatrixRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Sustainability matrix updated successfully", service.update(id, request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SustainabilityMatrixResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Sustainability matrix fetched successfully", service.getById(id)));
    }

    @GetMapping("/appraisal/{appraisalId}")
    public ResponseEntity<ApiResponse<List<SustainabilityMatrixResponse>>> getByAppraisalId(@PathVariable Long appraisalId) {
        return ResponseEntity.ok(ApiResponse.success("Sustainability matrices fetched successfully", service.getByAppraisalId(appraisalId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Sustainability matrix deleted successfully", null));
    }

    @GetMapping("/appraisal-dropdown")
    public ResponseEntity<ApiResponse<List<AppraisalDropdownDto>>> getAppraisalDropdown() {
        return ResponseEntity.ok(ApiResponse.success("Appraisal dropdown fetched successfully", service.getAppraisalDropdown()));
    }
}