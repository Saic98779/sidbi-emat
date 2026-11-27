package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateDiaExposureVisitParticipantRequest;
import org.emat.dto.DiaExposureVisitParticipantImportResult;
import org.emat.dto.DiaExposureVisitParticipantResponse;
import org.emat.dto.UpdateDiaExposureVisitParticipantRequest;
import org.emat.service.DiaExposureVisitParticipantService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/dia-exposure-visit-participant")
@RequiredArgsConstructor
@Slf4j
public class DiaExposureVisitParticipantController {

    private final DiaExposureVisitParticipantService service;

    @PostMapping
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('diaExposureVisitParticipantCreate'))")
    public ResponseEntity<ApiResponse<DiaExposureVisitParticipantResponse>> create(
            @RequestBody CreateDiaExposureVisitParticipantRequest request) {
        log.info("Received request to create DIA Exposure Visit Participant");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "DIA Exposure Visit Participant created successfully",
                                service.create(request)));
    }

    @GetMapping("/import/template")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('diaExposureVisitParticipantCreate'))")
    public ResponseEntity<byte[]> downloadImportTemplate() {
        log.info("Received request to download DIA Exposure Visit Participant import template");
        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"dia-exposure-visit-participant-import-template.xlsx\"")
                .body(service.generateImportTemplate());
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('diaExposureVisitParticipantCreate'))")
    public ResponseEntity<ApiResponse<DiaExposureVisitParticipantImportResult>> importExcel(
            @RequestParam("diaExposureVisitId") Long diaExposureVisitId,
            @RequestPart("file") MultipartFile file) {
        log.info(
                "Received request to import DIA Exposure Visit Participants from Excel for DIA"
                        + " Exposure Visit ID: {}",
                diaExposureVisitId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA Exposure Visit Participants imported successfully",
                        service.importFromExcel(diaExposureVisitId, file)));
    }

    @GetMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('diaExposureVisitParticipantRead'))")
    public ResponseEntity<ApiResponse<DiaExposureVisitParticipantResponse>> getById(
            @PathVariable("id") Long id) {
        log.info("Received request to fetch DIA Exposure Visit Participant with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA Exposure Visit Participant fetched successfully",
                        service.getById(id)));
    }

    @GetMapping
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('diaExposureVisitParticipantRead'))")
    public ResponseEntity<ApiResponse<List<DiaExposureVisitParticipantResponse>>> getAll() {
        log.info("Received request to fetch all DIA Exposure Visit Participants");
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA Exposure Visit Participants fetched successfully", service.getAll()));
    }

    @GetMapping("/exposure-visit/{exposureVisitId}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('diaExposureVisitParticipantRead'))")
    public ResponseEntity<ApiResponse<List<DiaExposureVisitParticipantResponse>>>
            getAllByExposureVisitId(@PathVariable("exposureVisitId") Long exposureVisitId) {
        log.info(
                "Received request to fetch DIA Exposure Visit Participants for DIA Exposure Visit"
                        + " ID: {}",
                exposureVisitId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA Exposure Visit Participants fetched successfully",
                        service.getAllByExposureVisitId(exposureVisitId)));
    }

    @PutMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('diaExposureVisitParticipantUpdate'))")
    public ResponseEntity<ApiResponse<DiaExposureVisitParticipantResponse>> update(
            @PathVariable("id") Long id,
            @RequestBody UpdateDiaExposureVisitParticipantRequest request) {
        log.info("Received request to update DIA Exposure Visit Participant with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA Exposure Visit Participant updated successfully",
                        service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('diaExposureVisitParticipantDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete DIA Exposure Visit Participant with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("DIA Exposure Visit Participant deleted successfully", null));
    }
}
