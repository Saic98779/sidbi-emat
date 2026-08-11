package org.emat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @PreAuthorize("hasAnyRole('GT_FIELD_TEAM', 'SIDBI_HO_MAKER', 'SIDBI_HO_CHECKER', 'SIDBI_RO','MANPOWER_AGENCY')")
    public ResponseEntity<BseSalaryResponse> create(@RequestBody BseSalaryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bseSalaryService.create(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('GT_FIELD_TEAM', 'SIDBI_HO_MAKER', 'SIDBI_HO_CHECKER', 'SIDBI_RO','MANPOWER_AGENCY')")
    public ResponseEntity<BseSalaryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bseSalaryService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GT_FIELD_TEAM', 'SIDBI_HO_MAKER', 'SIDBI_HO_CHECKER', 'SIDBI_RO','MANPOWER_AGENCY')")
    public ResponseEntity<List<BseSalaryResponse>> getAll() {
        return ResponseEntity.ok(bseSalaryService.getAll());
    }

    @GetMapping("/approved-industry-associations")
    @PreAuthorize("hasAnyRole('GT_FIELD_TEAM', 'SIDBI_HO_MAKER', 'SIDBI_HO_CHECKER', 'SIDBI_RO','MANPOWER_AGENCY')")
    public ResponseEntity<List<String>> getApprovedIndustryAssociationNames() {
        return ResponseEntity.ok(bseSalaryService.getApprovedIndustryAssociationNames());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('GT_FIELD_TEAM', 'SIDBI_HO_MAKER', 'SIDBI_RO','MANPOWER_AGENCY')")
    public ResponseEntity<BseSalaryResponse> update(
            @PathVariable Long id,
            @RequestBody BseSalaryUpdateRequest request) {
        return ResponseEntity.ok(bseSalaryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('GT_FIELD_TEAM', 'SIDBI_HO_MAKER', 'SIDBI_RO','MANPOWER_AGENCY')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bseSalaryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
