package org.emat.mapper;

import java.util.ArrayList;
import java.util.List;
import org.emat.dto.ActionPlanActivityResponse;
import org.emat.dto.ActionPlanResponse;
import org.emat.dto.CreateActionPlanActivityRequest;
import org.emat.dto.CreateActionPlanRequest;
import org.emat.dto.UpdateActionPlanActivityRequest;
import org.emat.dto.UpdateActionPlanRequest;
import org.emat.entity.ActionPlan;
import org.emat.entity.ActionPlanActivity;
import org.emat.entity.IndustryAssociationRegistration;
import org.springframework.stereotype.Component;

@Component
public class ActionPlanMapper {

    public ActionPlan toEntity(
            CreateActionPlanRequest request, IndustryAssociationRegistration registration) {
        ActionPlan actionPlan =
                ActionPlan.builder()
                        .state(
                                request.getState() != null
                                        ? request.getState()
                                        : registration.getState())
                        .registration(registration)
                        .isActive(true)
                        .build();
        actionPlan.setActivities(mapCreateActivities(request.getActivities(), actionPlan));
        return actionPlan;
    }

    public List<ActionPlanActivity> mapCreateActivities(
            List<CreateActionPlanActivityRequest> requests, ActionPlan parent) {
        if (requests == null) {
            return new ArrayList<>();
        }
        return requests.stream().map(r -> toActivityEntity(r, parent)).toList();
    }

    private ActionPlanActivity toActivityEntity(
            CreateActionPlanActivityRequest request, ActionPlan parent) {
        return ActionPlanActivity.builder()
                .actionPlan(parent)
                .activityNo(request.getActivityNo())
                .nameOfActivity(request.getNameOfActivity())
                .monthToBeHeld(request.getMonthToBeHeld())
                .technicalServiceProvider(request.getTechnicalServiceProvider())
                .totalCost(request.getTotalCost())
                .percentSupportBySidbi(request.getPercentSupportBySidbi())
                .percentSupportByOthers(request.getPercentSupportByOthers())
                .percentContributionByIa(request.getPercentContributionByIa())
                .expectedParticipantMembers(request.getExpectedParticipantMembers())
                .expectedParticipantNonMembers(request.getExpectedParticipantNonMembers())
                .expectedOutput(request.getExpectedOutput())
                .expectedOutcome(request.getExpectedOutcome())
                .expectedIncomeGeneratingActivity(request.getExpectedIncomeGeneratingActivity())
                .build();
    }

    public void applyUpdateRequest(ActionPlan actionPlan, UpdateActionPlanRequest request) {
        if (request.getState() != null) actionPlan.setState(request.getState());
    }

    public List<ActionPlanActivity> mapUpdateActivities(
            List<UpdateActionPlanActivityRequest> requests, ActionPlan parent) {
        if (requests == null) {
            return new ArrayList<>();
        }
        return requests.stream().map(r -> toActivityEntity(r, parent)).toList();
    }

    private ActionPlanActivity toActivityEntity(
            UpdateActionPlanActivityRequest request, ActionPlan parent) {
        return ActionPlanActivity.builder()
                .actionPlan(parent)
                .activityNo(request.getActivityNo())
                .nameOfActivity(request.getNameOfActivity())
                .monthToBeHeld(request.getMonthToBeHeld())
                .technicalServiceProvider(request.getTechnicalServiceProvider())
                .totalCost(request.getTotalCost())
                .percentSupportBySidbi(request.getPercentSupportBySidbi())
                .percentSupportByOthers(request.getPercentSupportByOthers())
                .percentContributionByIa(request.getPercentContributionByIa())
                .expectedParticipantMembers(request.getExpectedParticipantMembers())
                .expectedParticipantNonMembers(request.getExpectedParticipantNonMembers())
                .expectedOutput(request.getExpectedOutput())
                .expectedOutcome(request.getExpectedOutcome())
                .expectedIncomeGeneratingActivity(request.getExpectedIncomeGeneratingActivity())
                .build();
    }

    public ActionPlanResponse toResponse(ActionPlan actionPlan) {
        return ActionPlanResponse.builder()
                .id(actionPlan.getId())
                .state(actionPlan.getState())
                .registrationId(
                        actionPlan.getRegistration() != null
                                ? actionPlan.getRegistration().getId()
                                : null)
                .industryAssociationName(
                        actionPlan.getRegistration() != null
                                ? actionPlan.getRegistration().getIndustryAssociationName()
                                : null)
                .activities(toActivityResponses(actionPlan.getActivities()))
                .createdAt(actionPlan.getCreatedAt())
                .updatedAt(actionPlan.getUpdatedAt())
                .createdBy(actionPlan.getCreatedBy())
                .updatedBy(actionPlan.getUpdatedBy())
                .isActive(actionPlan.getIsActive())
                .build();
    }

    private List<ActionPlanActivityResponse> toActivityResponses(
            List<ActionPlanActivity> activities) {
        if (activities == null) {
            return new ArrayList<>();
        }
        return activities.stream()
                .map(
                        a ->
                                ActionPlanActivityResponse.builder()
                                        .id(a.getId())
                                        .activityNo(a.getActivityNo())
                                        .nameOfActivity(a.getNameOfActivity())
                                        .monthToBeHeld(a.getMonthToBeHeld())
                                        .technicalServiceProvider(a.getTechnicalServiceProvider())
                                        .totalCost(a.getTotalCost())
                                        .percentSupportBySidbi(a.getPercentSupportBySidbi())
                                        .percentSupportByOthers(a.getPercentSupportByOthers())
                                        .percentContributionByIa(a.getPercentContributionByIa())
                                        .expectedParticipantMembers(
                                                a.getExpectedParticipantMembers())
                                        .expectedParticipantNonMembers(
                                                a.getExpectedParticipantNonMembers())
                                        .expectedOutput(a.getExpectedOutput())
                                        .expectedOutcome(a.getExpectedOutcome())
                                        .expectedIncomeGeneratingActivity(
                                                a.getExpectedIncomeGeneratingActivity())
                                        .build())
                .toList();
    }
}
