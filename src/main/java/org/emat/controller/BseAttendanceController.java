package org.emat.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.emat.dto.ApiResponse;
import org.emat.dto.BseAttendanceDTO;
import org.emat.dto.IdRequest;
import org.emat.service.BseAttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bse-attendance")
@RequiredArgsConstructor
public class BseAttendanceController {

    private final BseAttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<ApiResponse<BseAttendanceDTO>> save(@RequestBody BseAttendanceDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "Attendance created successfully", attendanceService.save(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BseAttendanceDTO>> update(
            @PathVariable Long id, @RequestBody BseAttendanceDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Attendance updated successfully", attendanceService.update(id, dto)));
    }

    @PostMapping("/get")
    public ResponseEntity<ApiResponse<BseAttendanceDTO>> getById(
            @Valid @RequestBody IdRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Attendance fetched successfully",
                        attendanceService.getById(request.getId())));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BseAttendanceDTO>>> getAll() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Attendances fetched successfully", attendanceService.getAll()));
    }

    @PostMapping("/recommendation")
    public ResponseEntity<ApiResponse<List<BseAttendanceDTO>>> getByRecommendation(
            @Valid @RequestBody IdRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Attendances fetched successfully",
                        attendanceService.getByRecommendation(request.getId())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        attendanceService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Attendance deleted successfully", null));
    }
}
