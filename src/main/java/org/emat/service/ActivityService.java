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
import org.emat.mapper.ActivityMapper;
import org.emat.repository.ActivityRepository;
import org.emat.repository.ActivityStatusRepository;
import org.emat.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private static final String ACTIVITY_NOT_FOUND = "Activity not found";
    private static final String USER_NOT_FOUND = "User not found";

    private final ActivityRepository activityRepository;
    private final ActivityStatusRepository activityStatusRepository;
    private final UserRepository userRepository;
    private final ActivityMapper activityMapper;

    public ActivityResponse createActivity(ActivityRequest request) {
        Activity activity = new Activity();
        activityMapper.updateEntityFromRequest(request, activity, this::resolveUser);
        Activity saved = activityRepository.save(activity);
        return activityMapper.toResponse(saved);
    }

    public ActivityResponse updateActivity(Long activityId, ActivityRequest request) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException(ACTIVITY_NOT_FOUND));
        activityMapper.updateEntityFromRequest(request, activity, this::resolveUser);
        Activity saved = activityRepository.save(activity);
        return activityMapper.toResponse(saved);
    }

    public ActivityResponse getActivityById(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException(ACTIVITY_NOT_FOUND));
        return activityMapper.toResponse(activity);
    }

    public List<ActivityResponse> getAllActivities() {
        return activityRepository.findAll()
                .stream()
                .map(activityMapper::toResponse)
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

        ActivityStatus status = activityMapper.toStatusEntity(activityId, request);

        ActivityStatus saved = activityStatusRepository.save(status);
        return activityMapper.toStatusUpdateResponse(saved);
    }

    public List<ActivityStatusResponse> getActivityStatusHistory(Long activityId) {
        return activityStatusRepository.findByActivityIdOrderByStatusUpdatedDtStampDesc(activityId)
                .stream()
                .map(activityMapper::toStatusResponse)
                .toList();
    }

    public ActivityStatusResponse getLatestActivityStatus(Long activityId) {
        ActivityStatus status = activityStatusRepository.findTopByActivityIdOrderByStatusUpdatedDtStampDesc(activityId)
                .orElseThrow(() -> new RuntimeException("Activity status not found"));
        return activityMapper.toStatusResponse(status);
    }

    private User resolveUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
    }
}
