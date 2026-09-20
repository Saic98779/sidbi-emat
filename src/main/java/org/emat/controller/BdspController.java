package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.BdspImportResult;
import org.emat.dto.BdspResponse;
import org.emat.dto.CreateBdspRequest;
import org.emat.dto.UpdateBdspRequest;
import org.emat.dto.UpdateBdspStatusRequest;
import org.emat.service.BdspService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/bdsp")
@RequiredArgsConstructor
@Slf4j
public class BdspController {

    private final BdspService service;

    @GetMapping("/import/template")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bdspCreate'))")
    public ResponseEntity<Resource> downloadImportTemplate() {
        log.info("Received request to download BDSP import template");
        ClassPathResource resource = new ClassPathResource("template/bdsp-import-template.xlsx");
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"bdsp-import-template.xlsx\"")
                .body(resource);
    }

    @PostMapping(
            value = "/import",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bdspCreate'))")
    public ResponseEntity<ApiResponse<BdspImportResult>> importExcel(
            @RequestPart("file") MultipartFile file) {
        log.info("Received request to import BDSP from Excel");
        return ResponseEntity.ok(
                ApiResponse.success(
                        "BDSP imported successfully", service.importFromExcel(file)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bdspCreate'))")
    public ResponseEntity<ApiResponse<BdspResponse>> create(@RequestBody CreateBdspRequest request) {
        log.info("Received request to create BDSP");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "BDSP created successfully", service.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bdspRead'))")
    public ResponseEntity<ApiResponse<BdspResponse>> getById(@PathVariable("id") Long id) {
        log.info("Received request to fetch BDSP with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success("BDSP fetched successfully", service.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bdspRead'))")
    public ResponseEntity<ApiResponse<List<BdspResponse>>> getAll() {
        log.info("Received request to fetch all BDSP");
        return ResponseEntity.ok(
                ApiResponse.success("BDSP fetched successfully", service.getAll()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bdspUpdate'))")
    public ResponseEntity<ApiResponse<BdspResponse>> update(
            @PathVariable("id") Long id, @RequestBody UpdateBdspRequest request) {
        log.info("Received request to update BDSP with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success("BDSP updated successfully", service.update(id, request)));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bdspUpdate'))")
    public ResponseEntity<ApiResponse<BdspResponse>> updateStatus(
            @PathVariable("id") Long id, @RequestBody UpdateBdspStatusRequest request) {
        log.info("Received request to update BDSP status with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "BDSP status updated successfully", service.updateStatus(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('bdspDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete BDSP with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("BDSP deleted successfully", null));
    }
}