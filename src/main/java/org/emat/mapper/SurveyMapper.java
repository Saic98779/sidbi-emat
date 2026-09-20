package org.emat.mapper;

import java.util.ArrayList;
import java.util.List;
import org.emat.dto.CreateSurveyQuestionnaireRequest;
import org.emat.dto.CreateSurveyRequest;
import org.emat.dto.SurveyQuestionnaireResponse;
import org.emat.dto.SurveyResponse;
import org.emat.dto.UpdateSurveyQuestionnaireRequest;
import org.emat.dto.UpdateSurveyRequest;
import org.emat.entity.Survey;
import org.emat.entity.SurveyQuestionnaire;
import org.springframework.stereotype.Component;

@Component
public class SurveyMapper {

    public Survey toEntity(CreateSurveyRequest request) {
        Survey survey =
                Survey.builder()
                        .topic(request.getTopic())
                        .relevanceOfTopic(request.getRelevanceOfTopic())
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .sample(request.getSample())
                        .bulkMessaging(request.getBulkMessaging())
                        .attachment(request.getAttachment())
                        .isActive(true)
                        .build();
        survey.setSurveyQuestionnaires(
                mapCreateQuestionnaires(request.getSurveyQuestionnaires(), survey));
        return survey;
    }

    public void applyUpdateRequest(Survey survey, UpdateSurveyRequest request) {
        if (request.getTopic() != null) survey.setTopic(request.getTopic());
        if (request.getRelevanceOfTopic() != null)
            survey.setRelevanceOfTopic(request.getRelevanceOfTopic());
        if (request.getStartDate() != null) survey.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) survey.setEndDate(request.getEndDate());
        if (request.getSample() != null) survey.setSample(request.getSample());
        if (request.getBulkMessaging() != null) survey.setBulkMessaging(request.getBulkMessaging());
        if (request.getAttachment() != null) survey.setAttachment(request.getAttachment());
    }

    public List<SurveyQuestionnaire> mapCreateQuestionnaires(
            List<CreateSurveyQuestionnaireRequest> requests, Survey parent) {
        if (requests == null) {
            return new ArrayList<>();
        }
        return requests.stream().map(r -> toQuestionnaireEntity(r, parent)).toList();
    }

    private SurveyQuestionnaire toQuestionnaireEntity(
            CreateSurveyQuestionnaireRequest request, Survey parent) {
        return SurveyQuestionnaire.builder()
                .survey(parent)
                .question(request.getQuestion())
                .questionType(request.getQuestionType())
                .options(request.getOptions())
                .build();
    }

    public List<SurveyQuestionnaire> mapUpdateQuestionnaires(
            List<UpdateSurveyQuestionnaireRequest> requests, Survey parent) {
        if (requests == null) {
            return new ArrayList<>();
        }
        return requests.stream().map(r -> toQuestionnaireEntity(r, parent)).toList();
    }

    private SurveyQuestionnaire toQuestionnaireEntity(
            UpdateSurveyQuestionnaireRequest request, Survey parent) {
        return SurveyQuestionnaire.builder()
                .survey(parent)
                .question(request.getQuestion())
                .questionType(request.getQuestionType())
                .options(request.getOptions())
                .build();
    }

    public SurveyResponse toResponse(Survey survey) {
        return SurveyResponse.builder()
                .id(survey.getId())
                .topic(survey.getTopic())
                .relevanceOfTopic(survey.getRelevanceOfTopic())
                .startDate(survey.getStartDate())
                .endDate(survey.getEndDate())
                .sample(survey.getSample())
                .bulkMessaging(survey.getBulkMessaging())
                .attachment(survey.getAttachment())
                .surveyQuestionnaires(toQuestionnaireResponses(survey.getSurveyQuestionnaires()))
                .status(survey.getStatus())
                .remark(survey.getRemark())
                .approvedDate(survey.getApprovedDate())
                .createdAt(survey.getCreatedAt())
                .updatedAt(survey.getUpdatedAt())
                .createdBy(survey.getCreatedBy())
                .updatedBy(survey.getUpdatedBy())
                .isActive(survey.getIsActive())
                .build();
    }

    private List<SurveyQuestionnaireResponse> toQuestionnaireResponses(
            List<SurveyQuestionnaire> questionnaires) {
        if (questionnaires == null) {
            return new ArrayList<>();
        }
        return questionnaires.stream()
                .map(
                        q ->
                                SurveyQuestionnaireResponse.builder()
                                        .id(q.getId())
                                        .question(q.getQuestion())
                                        .questionType(q.getQuestionType())
                                        .options(q.getOptions())
                                        .build())
                .toList();
    }
}
