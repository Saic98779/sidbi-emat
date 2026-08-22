package org.emat.mapper;

import org.emat.dto.ActivityRequest;
import org.emat.dto.ActivityResponse;
import org.emat.dto.ActivityStatusResponse;
import org.emat.dto.ActivityStatusUpdateRequest;
import org.emat.dto.ActivityStatusUpdateResponse;
import org.emat.entity.Activity;
import org.emat.entity.ActivityStatus;
import org.emat.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component
public class ActivityMapper {

    public void updateEntityFromRequest(ActivityRequest request, Activity activity, Function<Long, User> userResolver) {
        activity.setActivityType(request.getActivityType());
        activity.setDetails(request.getDetails());
        activity.setDateTime(request.getDateTime());
        activity.setFollowUpReq(request.getFollowUpReq());
        activity.setFollowUpId(request.getFollowUpId());
        activity.setLocationDetails(request.getLocationDetails());
        activity.setCreatedUser(request.getCreatedUserId() == null ? null : userResolver.apply(request.getCreatedUserId()));
        activity.setCreatedDtStamp(request.getCreatedDtStamp());
        activity.setApprovedDtStamp(request.getApprovedDtStamp());
        activity.setBseId(request.getBseId());
        activity.setGtId(request.getGtId());
    }

    public ActivityResponse toResponse(Activity activity) {
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

    public ActivityStatus toStatusEntity(Long activityId, ActivityStatusUpdateRequest request) {
        ActivityStatus status = new ActivityStatus();
        status.setActivityId(activityId);
        status.setFollowupActivityId(request.getFollowupActivityId());
        status.setStatus(request.getStatus());
        status.setStatusUpdatedByRole(request.getStatusUpdatedByRole());
        status.setStatusApprovalRequired(request.getStatusApprovalRequired());
        status.setStatusUpdatedDtStamp(request.getStatusUpdatedDtStamp() != null
                ? request.getStatusUpdatedDtStamp()
                : LocalDateTime.now());
        status.setStatusRemarks(request.getStatusRemarks());
        return status;
    }

    public ActivityStatusUpdateResponse toStatusUpdateResponse(ActivityStatus status) {
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

    public ActivityStatusResponse toStatusResponse(ActivityStatus status) {
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

