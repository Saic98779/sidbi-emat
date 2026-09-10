package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.ApiResponse;
import org.emat.dto.BseAttendanceManualRequestDTO;
import org.emat.service.BseAttendanceManualRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bse-attendance-manual-request")
@RequiredArgsConstructor
public class BseAttendanceManualRequestController {

    private final BseAttendanceManualRequestService service;

    @PostMapping
    public ResponseEntity<ApiResponse<BseAttendanceManualRequestDTO>> save(
            @RequestBody BseAttendanceManualRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Manual attendance request created successfully", service.save(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BseAttendanceManualRequestDTO>> update(
            @PathVariable Long id,
            @RequestBody BseAttendanceManualRequestDTO dto) {
        return ResponseEntity.ok(ApiResponse.success("Manual attendance request updated successfully", service.update(id, dto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BseAttendanceManualRequestDTO>> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Manual attendance request fetched successfully", service.getById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BseAttendanceManualRequestDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Manual attendance requests fetched successfully", service.getAll()));
    }

    @GetMapping("/recommendation/{recommendationId}")
    public ResponseEntity<ApiResponse<List<BseAttendanceManualRequestDTO>>> getByRecommendation(
            @PathVariable Long recommendationId) {
        return ResponseEntity.ok(ApiResponse.success("Manual attendance requests fetched successfully", service.getByRecommendation(recommendationId)));
    }

    @GetMapping("/approval-status/{status}")
    public ResponseEntity<ApiResponse<List<BseAttendanceManualRequestDTO>>> getByApprovalStatus(
            @PathVariable Boolean status) {
        return ResponseEntity.ok(ApiResponse.success("Manual attendance requests fetched successfully", service.getByApprovalStatus(status)));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<BseAttendanceManualRequestDTO>> approve(
            @PathVariable Long id,
            @RequestParam Long approvedBy) {
        return ResponseEntity.ok(ApiResponse.success("Manual attendance request approved successfully", service.approve(id, approvedBy)));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<BseAttendanceManualRequestDTO>> reject(
            @PathVariable Long id,
            @RequestParam Long approvedBy) {
        return ResponseEntity.ok(ApiResponse.success("Manual attendance request rejected successfully", service.reject(id, approvedBy)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Manual attendance request deleted successfully", null));
    }
}