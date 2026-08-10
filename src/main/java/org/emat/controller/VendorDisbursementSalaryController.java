package org.emat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateVendorDisbursementSalaryRequest;
import org.emat.dto.UpdateVendorDisbursementSalaryRequest;
import org.emat.dto.VendorDisbursementSalaryResponse;
import org.emat.service.VendorDisbursementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor-disbursements")
@RequiredArgsConstructor
@Slf4j
public class VendorDisbursementSalaryController {

    private final VendorDisbursementService vendorDisbursementService;

    @PostMapping
    @PreAuthorize("hasAnyRole('GT_FIELD_TEAM', 'SIDBI_HO_MAKER', 'SIDBI_HO_CHECKER', 'SIDBI_RO','MANPOWER_AGENCY')")
    public ResponseEntity<VendorDisbursementSalaryResponse> create(@RequestBody CreateVendorDisbursementSalaryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vendorDisbursementService.create(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('GT_FIELD_TEAM', 'SIDBI_HO_MAKER', 'SIDBI_HO_CHECKER', 'SIDBI_RO','MANPOWER_AGENCY')")
    public ResponseEntity<VendorDisbursementSalaryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vendorDisbursementService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GT_FIELD_TEAM', 'SIDBI_HO_MAKER', 'SIDBI_HO_CHECKER', 'SIDBI_RO','MANPOWER_AGENCY')")
    public ResponseEntity<List<VendorDisbursementSalaryResponse>> getAll() {
        return ResponseEntity.ok(vendorDisbursementService.getAll());
    }

    @GetMapping("/approved-industry-associations")
    @PreAuthorize("hasAnyRole('GT_FIELD_TEAM', 'SIDBI_HO_MAKER', 'SIDBI_HO_CHECKER', 'SIDBI_RO','MANPOWER_AGENCY')")
    public ResponseEntity<List<String>> getApprovedIndustryAssociationNames() {
        return ResponseEntity.ok(vendorDisbursementService.getApprovedIndustryAssociationNames());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('GT_FIELD_TEAM', 'SIDBI_HO_MAKER', 'SIDBI_RO','MANPOWER_AGENCY')")
    public ResponseEntity<VendorDisbursementSalaryResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateVendorDisbursementSalaryRequest request) {
        return ResponseEntity.ok(vendorDisbursementService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('GT_FIELD_TEAM', 'SIDBI_HO_MAKER', 'SIDBI_RO','MANPOWER_AGENCY')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vendorDisbursementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
