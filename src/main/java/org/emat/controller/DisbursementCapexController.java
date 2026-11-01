package org.emat.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.emat.dto.ApiResponse;
import org.emat.dto.DisbursementCapexRequest;
import org.emat.dto.DisbursementCapexResponse;
import org.emat.dto.IdRequest;
import org.emat.service.DisbursementCapexService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disbursement-capex")
@RequiredArgsConstructor
public class DisbursementCapexController {

    private final DisbursementCapexService disbursementCapexService;

    @PostMapping
    public ResponseEntity<ApiResponse<DisbursementCapexResponse>> create(
            @RequestBody DisbursementCapexRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "Disbursement capex created successfully",
                                disbursementCapexService.create(request)));
    }

    @PostMapping("/get")
    public ResponseEntity<ApiResponse<DisbursementCapexResponse>> getById(
            @Valid @RequestBody IdRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Disbursement capex fetched successfully",
                        disbursementCapexService.getById(request.getId())));
    }

    @PostMapping("/registration")
    public ResponseEntity<ApiResponse<DisbursementCapexResponse>> getByRegistrationId(
            @Valid @RequestBody IdRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Disbursement capex fetched successfully",
                        disbursementCapexService.getByRegistrationId(request.getId())));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DisbursementCapexResponse>>> getAll() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Disbursement capex list fetched successfully",
                        disbursementCapexService.getAll()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DisbursementCapexResponse>> update(
            @PathVariable Long id, @RequestBody DisbursementCapexRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Disbursement capex updated successfully",
                        disbursementCapexService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        disbursementCapexService.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("Disbursement capex deleted successfully", null));
    }
}
