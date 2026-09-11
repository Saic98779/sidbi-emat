package org.emat.service;

import java.util.List;
import org.emat.dto.ActivityRequest;
import org.emat.dto.ActivityResponse;
import org.emat.dto.ActivityStatusResponse;
import org.emat.dto.ActivityStatusUpdateRequest;
import org.emat.dto.ActivityStatusUpdateResponse;

public interface ActivityService {

    ActivityResponse createActivity(ActivityRequest request);

    ActivityResponse updateActivity(Long activityId, ActivityRequest request);

    ActivityResponse getActivityById(Long activityId);

    List<ActivityResponse> getAllActivities();

    void deleteActivity(Long activityId);

    ActivityStatusUpdateResponse updateActivityStatus(
            Long activityId, ActivityStatusUpdateRequest request);

    ActivityStatusUpdateResponse patchActivityStatus(
            Long activityId, ActivityStatusUpdateRequest request);

    List<ActivityStatusResponse> getActivityStatusHistory(Long activityId);

    ActivityStatusResponse getLatestActivityStatus(Long activityId);
}
