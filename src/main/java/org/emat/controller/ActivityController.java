package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.ActivityRequest;
import org.emat.dto.ActivityResponse;
import org.emat.dto.ActivityStatusResponse;
import org.emat.dto.ActivityStatusUpdateRequest;
import org.emat.dto.ActivityStatusUpdateResponse;
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
    public ResponseEntity<ActivityResponse> createActivity(@RequestBody ActivityRequest request) {
        return new ResponseEntity<>(activityService.createActivity(request), HttpStatus.CREATED);
    }

    @PutMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable Long activityId,
                                                           @RequestBody ActivityRequest request) {
        return ResponseEntity.ok(activityService.updateActivity(activityId, request));
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getActivityById(@PathVariable Long activityId) {
        return ResponseEntity.ok(activityService.getActivityById(activityId));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getAllActivities() {
        return ResponseEntity.ok(activityService.getAllActivities());
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<String> deleteActivity(@PathVariable Long activityId) {
        activityService.deleteActivity(activityId);
        return ResponseEntity.ok("Activity deleted successfully.");
    }

    @PatchMapping("/{activityId}/status")
    public ResponseEntity<ActivityStatusUpdateResponse> updateActivityStatus(@PathVariable Long activityId,
                                                                             @RequestBody ActivityStatusUpdateRequest request) {
        return new ResponseEntity<>(activityService.patchActivityStatus(activityId, request), HttpStatus.CREATED);
    }

    @GetMapping("/{activityId}/status")
    public ResponseEntity<ActivityStatusResponse> getLatestActivityStatus(@PathVariable Long activityId) {
        return ResponseEntity.ok(activityService.getLatestActivityStatus(activityId));
    }

    @GetMapping("/{activityId}/status/history")
    public ResponseEntity<List<ActivityStatusResponse>> getActivityStatusHistory(@PathVariable Long activityId) {
        return ResponseEntity.ok(activityService.getActivityStatusHistory(activityId));
    }
}
