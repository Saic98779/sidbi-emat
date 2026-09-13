package org.emat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.BseSalaryRequest;
import org.emat.dto.BseSalaryUpdateRequest;
import org.emat.dto.BseSalaryResponse;
import org.emat.service.BseSalaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bse-salary")
@RequiredArgsConstructor
@Slf4j
public class BseSalaryController {

    private final BseSalaryService bseSalaryService;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseSalaryCreate'))")
    public ResponseEntity<ApiResponse<BseSalaryResponse>> create(@RequestBody BseSalaryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("BSE salary created successfully", bseSalaryService.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseSalaryRead'))")
    public ResponseEntity<ApiResponse<BseSalaryResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("BSE salary fetched successfully", bseSalaryService.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseSalaryRead'))")
    public ResponseEntity<ApiResponse<List<BseSalaryResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("BSE salaries fetched successfully", bseSalaryService.getAll()));
    }

    @GetMapping("/approved-industry-associations")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseSalaryApprovedIndustryAssociationsRead'))")
    public ResponseEntity<ApiResponse<List<String>>> getApprovedIndustryAssociationNames() {
        return ResponseEntity.ok(ApiResponse.success("Approved industry associations fetched successfully", bseSalaryService.getApprovedIndustryAssociationNames()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseSalaryUpdate'))")
    public ResponseEntity<ApiResponse<BseSalaryResponse>> update(@PathVariable Long id,
                                                                   @RequestBody BseSalaryUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("BSE salary updated successfully", bseSalaryService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bseSalaryDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        bseSalaryService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("BSE salary deleted successfully", null));
    }
}
