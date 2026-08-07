package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.BseAttendanceManualRequestDTO;
import org.emat.service.BseAttendanceManualRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bse-attendance-manual-request")
@RequiredArgsConstructor
public class BseAttendanceManualRequestController {

    private final BseAttendanceManualRequestService service;

    /**
     * Creates a new manual attendance request.
     * This API is used when attendance cannot be marked
     * through the regular attendance process.
     */
    @PostMapping
    public ResponseEntity<BseAttendanceManualRequestDTO> save(
            @RequestBody BseAttendanceManualRequestDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    /**
     * Updates an existing manual attendance request.
     * This API allows modification of request details
     * before it is approved or rejected.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BseAttendanceManualRequestDTO> update(
            @PathVariable UUID id,
            @RequestBody BseAttendanceManualRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /**
     * Retrieves a manual attendance request by its UUID.
     * Returns the complete details of the request.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BseAttendanceManualRequestDTO> getById(
            @PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    /**
     * Retrieves all manual attendance requests.
     * Returns the complete list of requests.
     */
    @GetMapping
    public ResponseEntity<List<BseAttendanceManualRequestDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    /**
     * Retrieves all manual attendance requests
     * for a specific BSE recommendation.
     */
    @GetMapping("/recommendation/{recommendationId}")
    public ResponseEntity<List<BseAttendanceManualRequestDTO>> getByRecommendation(
            @PathVariable UUID recommendationId) {
        return ResponseEntity.ok(service.getByRecommendation(recommendationId));
    }

    /**
     * Retrieves manual attendance requests
     * based on their approval status.
     */
    @GetMapping("/approval-status/{status}")
    public ResponseEntity<List<BseAttendanceManualRequestDTO>> getByApprovalStatus(
            @PathVariable Boolean status) {
        return ResponseEntity.ok(service.getByApprovalStatus(status));
    }

    /**
     * Approves a manual attendance request.
     * On successful approval, a BSE attendance
     * record is created automatically.
     */
    @PatchMapping("/{id}/approve")
    public ResponseEntity<BseAttendanceManualRequestDTO> approve(
            @PathVariable UUID id,
            @RequestParam UUID approvedBy) {
        return ResponseEntity.ok(service.approve(id, approvedBy));
    }

    /**
     * Rejects a manual attendance request.
     * Updates the request status as rejected.
     */
    @PatchMapping("/{id}/reject")
    public ResponseEntity<BseAttendanceManualRequestDTO> reject(
            @PathVariable UUID id,
            @RequestParam UUID approvedBy) {
        return ResponseEntity.ok(service.reject(id, approvedBy));
    }

    /**
     * Deletes a manual attendance request.
     * Permanently removes the request from the system.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok("Manual attendance request deleted successfully.");
    }
}