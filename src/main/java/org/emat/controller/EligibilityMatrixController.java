package org.emat.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.emat.dto.ApiResponse;
import org.emat.dto.EligibilityMatrixDto;
import org.emat.dto.IdRequest;
import org.emat.dto.RegistrationDropdownDto;
import org.emat.service.EligibilityMatrixService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eligibility-matrix")
@RequiredArgsConstructor
public class EligibilityMatrixController {

    private final EligibilityMatrixService eligibilityMatrixService;

    @PostMapping
    public ResponseEntity<ApiResponse<EligibilityMatrixDto>> create(
            @RequestBody EligibilityMatrixDto request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "Eligibility matrix created successfully",
                                eligibilityMatrixService.create(request)));
    }

    @PostMapping("/get")
    public ResponseEntity<ApiResponse<EligibilityMatrixDto>> getById(
            @Valid @RequestBody IdRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Eligibility matrix fetched successfully",
                        eligibilityMatrixService.getById(request.getId())));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EligibilityMatrixDto>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Eligibility matrices fetched successfully",
                        eligibilityMatrixService.getAll()));
    }

    @PostMapping("/registration")
    public ResponseEntity<ApiResponse<EligibilityMatrixDto>> getByRegistrationId(
            @Valid @RequestBody IdRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Eligibility matrix fetched successfully",
                        eligibilityMatrixService.getByRegistrationId(request.getId())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EligibilityMatrixDto>> update(
            @PathVariable Long id, @RequestBody EligibilityMatrixDto request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Eligibility matrix updated successfully",
                        eligibilityMatrixService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {

        eligibilityMatrixService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success("Eligibility matrix deleted successfully", null));
    }

    @GetMapping("/registration-dropdown")
    public ResponseEntity<ApiResponse<List<RegistrationDropdownDto>>> getRegistrationDropdown() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Registration dropdown fetched successfully",
                        eligibilityMatrixService.getRegistrationDropdown()));
    }
}
