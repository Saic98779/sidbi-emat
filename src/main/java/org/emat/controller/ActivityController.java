package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.ActivityRequest;
import org.emat.dto.ActivityResponse;
import org.emat.dto.ActivityStatusResponse;
import org.emat.dto.ActivityStatusUpdateRequest;
import org.emat.dto.ActivityStatusUpdateResponse;
import org.emat.dto.ApiResponse;
import org.emat.service.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ApiResponse<ActivityResponse>> createActivity(@RequestBody ActivityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Activity created successfully", activityService.createActivity(request)));
    }

    @PutMapping("/{activityId}")
    public ResponseEntity<ApiResponse<ActivityResponse>> updateActivity(@PathVariable Long activityId,
                                                                         @RequestBody ActivityRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Activity updated successfully", activityService.updateActivity(activityId, request)));
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ApiResponse<ActivityResponse>> getActivityById(@PathVariable Long activityId) {
        return ResponseEntity.ok(ApiResponse.success("Activity fetched successfully", activityService.getActivityById(activityId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ActivityResponse>>> getAllActivities() {
        return ResponseEntity.ok(ApiResponse.success("Activities fetched successfully", activityService.getAllActivities()));
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<ApiResponse<Void>> deleteActivity(@PathVariable Long activityId) {
        activityService.deleteActivity(activityId);
        return ResponseEntity.ok(ApiResponse.success("Activity deleted successfully", null));
    }

    @PatchMapping("/{activityId}/status")
    public ResponseEntity<ApiResponse<ActivityStatusUpdateResponse>> updateActivityStatus(@PathVariable Long activityId,
                                                                                           @RequestBody ActivityStatusUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Activity status updated successfully", activityService.patchActivityStatus(activityId, request)));
    }

    @GetMapping("/{activityId}/status")
    public ResponseEntity<ApiResponse<ActivityStatusResponse>> getLatestActivityStatus(@PathVariable Long activityId) {
        return ResponseEntity.ok(ApiResponse.success("Activity status fetched successfully", activityService.getLatestActivityStatus(activityId)));
    }

    @GetMapping("/{activityId}/status/history")
    public ResponseEntity<ApiResponse<List<ActivityStatusResponse>>> getActivityStatusHistory(@PathVariable Long activityId) {
        return ResponseEntity.ok(ApiResponse.success("Activity status history fetched successfully", activityService.getActivityStatusHistory(activityId)));
    }
}
