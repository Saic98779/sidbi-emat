package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.ApiResponse;
import org.emat.dto.DisbursementCapexRequest;
import org.emat.dto.DisbursementCapexResponse;
import org.emat.service.DisbursementCapexService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disbursement-capex")
@RequiredArgsConstructor
public class DisbursementCapexController {

    private final DisbursementCapexService disbursementCapexService;

    @PostMapping
    public ResponseEntity<ApiResponse<DisbursementCapexResponse>> create(
            @RequestBody DisbursementCapexRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Disbursement capex created successfully", disbursementCapexService.create(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DisbursementCapexResponse>> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
                "Disbursement capex fetched successfully",
                disbursementCapexService.getById(id)));
    }

    @GetMapping("/registration/{registrationId}")
    public ResponseEntity<ApiResponse<DisbursementCapexResponse>> getByRegistrationId(
            @PathVariable Long registrationId) {
        return ResponseEntity.ok(ApiResponse.success(
                "Disbursement capex fetched successfully",
                disbursementCapexService.getByRegistrationId(registrationId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DisbursementCapexResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(
                "Disbursement capex list fetched successfully",
                disbursementCapexService.getAll()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DisbursementCapexResponse>> update(
            @PathVariable Long id,
            @RequestBody DisbursementCapexRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                "Disbursement capex updated successfully",
                disbursementCapexService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {
        disbursementCapexService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Disbursement capex deleted successfully", null));
    }
}