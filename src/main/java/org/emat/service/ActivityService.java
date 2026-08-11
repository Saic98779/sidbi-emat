package org.emat.service;

import lombok.RequiredArgsConstructor;
import org.emat.dto.ActivityRequest;
import org.emat.dto.ActivityResponse;
import org.emat.dto.ActivityStatusResponse;
import org.emat.dto.ActivityStatusUpdateRequest;
import org.emat.dto.ActivityStatusUpdateResponse;
import org.emat.entity.Activity;
import org.emat.entity.ActivityStatus;
import org.emat.entity.User;
import org.emat.repository.ActivityRepository;
import org.emat.repository.ActivityStatusRepository;
import org.emat.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private static final String ACTIVITY_NOT_FOUND = "Activity not found";
    private static final String USER_NOT_FOUND = "User not found";

    private final ActivityRepository activityRepository;
    private final ActivityStatusRepository activityStatusRepository;
    private final UserRepository userRepository;

    public ActivityResponse createActivity(ActivityRequest request) {
        Activity activity = new Activity();
        mapRequestToEntity(request, activity);
        Activity saved = activityRepository.save(activity);
        return mapToResponse(saved);
    }

    public ActivityResponse updateActivity(Long activityId, ActivityRequest request) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException(ACTIVITY_NOT_FOUND));
        mapRequestToEntity(request, activity);
        Activity saved = activityRepository.save(activity);
        return mapToResponse(saved);
    }

    public ActivityResponse getActivityById(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException(ACTIVITY_NOT_FOUND));
        return mapToResponse(activity);
    }

    public List<ActivityResponse> getAllActivities() {
        return activityRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void deleteActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException(ACTIVITY_NOT_FOUND));
        activityRepository.delete(activity);
    }

    public ActivityStatusUpdateResponse updateActivityStatus(Long activityId, ActivityStatusUpdateRequest request) {
        return patchActivityStatus(activityId, request);
    }

    public ActivityStatusUpdateResponse patchActivityStatus(Long activityId, ActivityStatusUpdateRequest request) {
        activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException(ACTIVITY_NOT_FOUND));

        ActivityStatus status = new ActivityStatus();
        status.setActivityId(activityId);
        if(request.getFollowupActivityId() != null) {
            status.setFollowupActivityId(request.getFollowupActivityId());
        }
        status.setStatus(request.getStatus());
        status.setStatusUpdatedByRole(request.getStatusUpdatedByRole());
        status.setStatusApprovalRequired(request.getStatusApprovalRequired());
        status.setStatusUpdatedDtStamp(request.getStatusUpdatedDtStamp() != null
                ? request.getStatusUpdatedDtStamp()
                : LocalDateTime.now());
        status.setStatusRemarks(request.getStatusRemarks());

        ActivityStatus saved = activityStatusRepository.save(status);
        return mapToStatusUpdateResponse(saved);
    }

    public List<ActivityStatusResponse> getActivityStatusHistory(Long activityId) {
        return activityStatusRepository.findByActivityIdOrderByStatusUpdatedDtStampDesc(activityId)
                .stream()
                .map(this::mapToStatusResponse)
                .toList();
    }

    public ActivityStatusResponse getLatestActivityStatus(Long activityId) {
        ActivityStatus status = activityStatusRepository.findTopByActivityIdOrderByStatusUpdatedDtStampDesc(activityId)
                .orElseThrow(() -> new RuntimeException("Activity status not found"));
        return mapToStatusResponse(status);
    }

    private void mapRequestToEntity(ActivityRequest request, Activity activity) {
        activity.setActivityType(request.getActivityType());
        activity.setDetails(request.getDetails());
        activity.setDateTime(request.getDateTime());
        activity.setFollowUpReq(request.getFollowUpReq());
        activity.setFollowUpId(request.getFollowUpId());
        activity.setLocationDetails(request.getLocationDetails());
        activity.setCreatedUser(request.getCreatedUserId() == null ? null : resolveUser(request.getCreatedUserId()));
        activity.setCreatedDtStamp(request.getCreatedDtStamp());
        activity.setApprovedDtStamp(request.getApprovedDtStamp());
        activity.setBseId(request.getBseId());
        activity.setGtId(request.getGtId());
    }

    private User resolveUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
    }

    private ActivityResponse mapToResponse(Activity activity) {
        ActivityResponse response = new ActivityResponse();
        response.setActivityId(activity.getActivityId());
        response.setActivityType(activity.getActivityType());
        response.setDetails(activity.getDetails());
        response.setDateTime(activity.getDateTime());
        response.setFollowUpReq(activity.getFollowUpReq());
        response.setFollowUpId(activity.getFollowUpId());
        response.setLocationDetails(activity.getLocationDetails());
        response.setCreatedUserId(activity.getCreatedUser() != null ? activity.getCreatedUser().getId() : null);
        response.setCreatedDtStamp(activity.getCreatedDtStamp());
        response.setApprovedDtStamp(activity.getApprovedDtStamp());
        response.setBseId(activity.getBseId());
        response.setGtId(activity.getGtId());
        return response;
    }

    private ActivityStatusUpdateResponse mapToStatusUpdateResponse(ActivityStatus status) {
        ActivityStatusUpdateResponse response = new ActivityStatusUpdateResponse();
        response.setStatusId(status.getStatusId());
        response.setActivityId(status.getActivityId());
        response.setFollowupActivityId(status.getFollowupActivityId());
        response.setStatus(status.getStatus());
        response.setStatusUpdatedByRole(status.getStatusUpdatedByRole());
        response.setStatusApprovalRequired(status.getStatusApprovalRequired());
        response.setStatusUpdatedDtStamp(status.getStatusUpdatedDtStamp());
        response.setStatusRemarks(status.getStatusRemarks());
        return response;
    }

    private ActivityStatusResponse mapToStatusResponse(ActivityStatus status) {
        ActivityStatusResponse response = new ActivityStatusResponse();
        response.setStatusId(status.getStatusId());
        response.setActivityId(status.getActivityId());
        response.setFollowupActivityId(status.getFollowupActivityId());
        response.setStatus(status.getStatus());
        response.setStatusUpdatedByRole(status.getStatusUpdatedByRole());
        response.setStatusApprovalRequired(status.getStatusApprovalRequired());
        response.setStatusUpdatedDtStamp(status.getStatusUpdatedDtStamp());
        response.setStatusRemarks(status.getStatusRemarks());
        return response;
    }
}
