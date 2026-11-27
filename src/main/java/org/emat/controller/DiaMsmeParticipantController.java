package org.emat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApiResponse;
import org.emat.dto.CreateDiaMsmeParticipantRequest;
import org.emat.dto.DiaMsmeParticipantImportResult;
import org.emat.dto.DiaMsmeParticipantResponse;
import org.emat.dto.UpdateDiaMsmeParticipantRequest;
import org.emat.service.DiaMsmeParticipantService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/dia-msme-participant")
@RequiredArgsConstructor
@Slf4j
public class DiaMsmeParticipantController {

    private final DiaMsmeParticipantService service;

    @PostMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaMsmeParticipantCreate'))")
    public ResponseEntity<ApiResponse<DiaMsmeParticipantResponse>> create(
            @RequestBody CreateDiaMsmeParticipantRequest request) {
        log.info("Received request to create DIA MSME Participant");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "DIA MSME Participant created successfully",
                                service.create(request)));
    }

    @GetMapping("/import/template")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaMsmeParticipantCreate'))")
    public ResponseEntity<byte[]> downloadImportTemplate() {
        log.info("Received request to download DIA MSME Participant import template");
        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"dia-msme-participant-import-template.xlsx\"")
                .body(service.generateImportTemplate());
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaMsmeParticipantCreate'))")
    public ResponseEntity<ApiResponse<DiaMsmeParticipantImportResult>> importExcel(
            @RequestParam("diaCapacityBuildingProgramId") Long diaCapacityBuildingProgramId,
            @RequestPart("file") MultipartFile file) {
        log.info(
                "Received request to import DIA MSME Participants from Excel for DIA Capacity"
                        + " Building Program ID: {}",
                diaCapacityBuildingProgramId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA MSME Participants imported successfully",
                        service.importFromExcel(diaCapacityBuildingProgramId, file)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaMsmeParticipantRead'))")
    public ResponseEntity<ApiResponse<DiaMsmeParticipantResponse>> getById(
            @PathVariable("id") Long id) {
        log.info("Received request to fetch DIA MSME Participant with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA MSME Participant fetched successfully", service.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaMsmeParticipantRead'))")
    public ResponseEntity<ApiResponse<List<DiaMsmeParticipantResponse>>> getAll() {
        log.info("Received request to fetch all DIA MSME Participants");
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA MSME Participants fetched successfully", service.getAll()));
    }

    @GetMapping("/event/{eventId}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaMsmeParticipantRead'))")
    public ResponseEntity<ApiResponse<List<DiaMsmeParticipantResponse>>> getAllByEventId(
            @PathVariable("eventId") Long eventId) {
        log.info(
                "Received request to fetch DIA MSME Participants for DIA Capacity Building Program"
                        + " ID: {}",
                eventId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA MSME Participants fetched successfully",
                        service.getAllByEventId(eventId)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaMsmeParticipantUpdate'))")
    public ResponseEntity<ApiResponse<DiaMsmeParticipantResponse>> update(
            @PathVariable("id") Long id, @RequestBody UpdateDiaMsmeParticipantRequest request) {
        log.info("Received request to update DIA MSME Participant with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "DIA MSME Participant updated successfully", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('diaMsmeParticipantDelete'))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        log.info("Received request to delete DIA MSME Participant with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("DIA MSME Participant deleted successfully", null));
    }
}
