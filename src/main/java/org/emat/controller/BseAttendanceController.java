package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.ApiResponse;
import org.emat.dto.BseAttendanceDTO;
import org.emat.service.BseAttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bse-attendance")
@RequiredArgsConstructor
public class BseAttendanceController {

    private final BseAttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<ApiResponse<BseAttendanceDTO>> save(@RequestBody BseAttendanceDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Attendance created successfully", attendanceService.save(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BseAttendanceDTO>> update(@PathVariable Long id,
                                                                 @RequestBody BseAttendanceDTO dto) {
        return ResponseEntity.ok(ApiResponse.success("Attendance updated successfully", attendanceService.update(id, dto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BseAttendanceDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Attendance fetched successfully", attendanceService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BseAttendanceDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Attendances fetched successfully", attendanceService.getAll()));
    }

    @GetMapping("/recommendation/{recommendationId}")
    public ResponseEntity<ApiResponse<List<BseAttendanceDTO>>> getByRecommendation(@PathVariable Long recommendationId) {
        return ResponseEntity.ok(ApiResponse.success("Attendances fetched successfully", attendanceService.getByRecommendation(recommendationId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        attendanceService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Attendance deleted successfully", null));
    }
}