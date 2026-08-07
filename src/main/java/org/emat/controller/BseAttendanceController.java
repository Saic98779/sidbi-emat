package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.BseAttendanceDTO;
import org.emat.service.BseAttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bse-attendance")
@RequiredArgsConstructor
public class BseAttendanceController {

    private final BseAttendanceService attendanceService;
    /**
     * Creates a new BSE attendance record.
     * This API is used to mark attendance for a participant
     * against a specific BSE recommendation.
     */
    @PostMapping
    public BseAttendanceDTO save(@RequestBody BseAttendanceDTO dto) {
        return attendanceService.save(dto);
    }

    /**
     * Updates an existing BSE attendance record.
     * This API allows modification of attendance date,
     * in-time, and out-time.
     */
    @PutMapping("/{id}")
    public BseAttendanceDTO update(@PathVariable UUID id,
                                   @RequestBody BseAttendanceDTO dto) {
        return attendanceService.update(id, dto);
    }

    /**
     * Retrieves a BSE attendance record by its UUID.
     * Returns the attendance details if the record exists.
     */
    @GetMapping("/{id}")
    public BseAttendanceDTO getById(@PathVariable UUID id) {
        return attendanceService.getById(id);
    }

    /**
     * Retrieves all BSE attendance records.
     * This API returns the complete list of attendance entries.
     */
    @GetMapping
    public List<BseAttendanceDTO> getAll() {
        return attendanceService.getAll();
    }

    /**
     * Retrieves all attendance records for a specific
     * BSE recommendation using its UUID.
     */
    @GetMapping("/recommendation/{recommendationId}")
    public List<BseAttendanceDTO> getByRecommendation(
            @PathVariable UUID recommendationId) {
        return attendanceService.getByRecommendation(recommendationId);
    }

    /**
     * Deletes a BSE attendance record by its UUID.
     * Permanently removes the attendance entry from the system.
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        attendanceService.delete(id);
        return "Attendance deleted successfully";
    }
}