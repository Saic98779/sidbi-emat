package org.emat.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.emat.dto.StageHistoryResponse;
import org.emat.dto.StageResponse;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.entity.Stage;
import org.emat.entity.StageHistory;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.emat.repository.StageHistoryRepository;
import org.emat.repository.StageRepository;
import org.emat.service.StageService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StageServiceImpl implements StageService {

    private static final String REGISTRATION_NOT_FOUND_MESSAGE = "Registration not found with ID: ";
    private static final String STAGE_NOT_FOUND_MESSAGE = "Stage not found with ID: ";
    private static final String SUSTAINABILITY_MATRIX_STAGE = "SUSTAINABILITY_MATRIX";
    private static final String ACTION_PLAN_STAGE = "ACTION_PLAN";
    private static final String SUSTAINABILITY_MATRIX_SUBMITTED =
            "SUSTAINABILITY_MATRIX_SUBMITTED";
    private static final String CLUSTER_EXPERT_APPROVED = "CLUSTER_EXPERT_APPROVED";
    private static final String SUSTAINABILITY_MATRIX_AND_ACTION_PLAN_COMPLETED =
            "SUSTAINABILITY_MATRIX_AND_ACTION_PLAN_COMPLETED";

    private final IndustryAssociationRegistrationRepository registrationRepository;
    private final StageRepository stageRepository;
    private final StageHistoryRepository stageHistoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<StageHistoryResponse> getStageHistoryByRegistrationId(Long registrationId) {
        return stageHistoryRepository.findByRegistration_IdOrderByTimeDesc(registrationId).stream()
                .map(
                        history ->
                                StageHistoryResponse.builder()
                                        .id(history.getId())
                                        .registrationId(
                                                history.getRegistration() != null
                                                        ? history.getRegistration().getId()
                                                        : null)
                                        .stage(history.getStage())
                                        .subStage(history.getSubStage())
                                        .time(history.getTime())
                                        .createdBy(history.getCreatedBy())
                                        .comment(history.getComment())
                                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StageResponse> getAllStages() {
        return stageRepository.findAll().stream()
                .map(
                        stage ->
                                StageResponse.builder()
                                        .id(stage.getId())
                                        .stage(stage.getStage())
                                        .subStage(stage.getSubStage())
                                        .build())
                .toList();
    }

    @Override
    @Async
    @Transactional
    public void updateStage(Long registrationId, Long stageId, String comment, String createdBy) {

        Stage stage =
                stageRepository
                        .findById(stageId)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                STAGE_NOT_FOUND_MESSAGE + stageId));

        persistStageTransition(registrationId, stage, comment, createdBy);
    }

    private void persistStageTransition(
            Long registrationId, Stage stage, String comment, String createdBy) {

        IndustryAssociationRegistration registration =
                registrationRepository
                        .findById(registrationId)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                REGISTRATION_NOT_FOUND_MESSAGE + registrationId));

        registration.setCurrentStage(stage);
        registrationRepository.save(registration);

        StageHistory history = new StageHistory();
        history.setRegistration(registration);
        history.setStage(stage.getStage());
        history.setSubStage(stage.getSubStage());
        history.setTime(LocalDateTime.now());
        history.setCreatedBy(createdBy);
        history.setComment(comment);

        stageHistoryRepository.save(history);

        if (shouldAutoMarkSustainabilityAndActionPlanCompleted(registrationId, stage)) {
            Stage completionStage = resolveCompletionStage();
            if (!isCompletionStage(registration.getCurrentStage(), completionStage)) {
                persistStageTransition(
                        registrationId,
                        completionStage,
                        "Auto transition: completion criteria met",
                        createdBy);
            }
        }
    }

    private boolean shouldAutoMarkSustainabilityAndActionPlanCompleted(
            Long registrationId, Stage stage) {
        if (stage == null || stage.getStage() == null || stage.getSubStage() == null) {
            return false;
        }

        boolean sustainabilityMatrixSubmitted =
                SUSTAINABILITY_MATRIX_STAGE.equalsIgnoreCase(stage.getStage())
                        && SUSTAINABILITY_MATRIX_SUBMITTED.equalsIgnoreCase(stage.getSubStage());

        boolean actionPlanApproved =
                ACTION_PLAN_STAGE.equalsIgnoreCase(stage.getStage())
                        && CLUSTER_EXPERT_APPROVED.equalsIgnoreCase(stage.getSubStage());

        if (!sustainabilityMatrixSubmitted && !actionPlanApproved) {
            return false;
        }

        boolean hasSustainabilityMatrixSubmitted =
                stageHistoryRepository.existsByRegistration_IdAndStageIgnoreCaseAndSubStageIgnoreCase(
                        registrationId,
                        SUSTAINABILITY_MATRIX_STAGE,
                        SUSTAINABILITY_MATRIX_SUBMITTED);

        boolean hasActionPlanApproved =
                stageHistoryRepository.existsByRegistration_IdAndStageIgnoreCaseAndSubStageIgnoreCase(
                        registrationId, ACTION_PLAN_STAGE, CLUSTER_EXPERT_APPROVED);

        return hasSustainabilityMatrixSubmitted && hasActionPlanApproved;
    }

    private Stage resolveCompletionStage() {
        return stageRepository
                .findFirstByStageIgnoreCaseAndSubStageIsNull(
                        SUSTAINABILITY_MATRIX_AND_ACTION_PLAN_COMPLETED)
                .or(
                        () ->
                                stageRepository.findFirstByStageIgnoreCase(
                                        SUSTAINABILITY_MATRIX_AND_ACTION_PLAN_COMPLETED))
                .orElseGet(
                        () ->
                                stageRepository.save(
                                        Stage.builder()
                                                .stage(
                                                        SUSTAINABILITY_MATRIX_AND_ACTION_PLAN_COMPLETED)
                                                .subStage(null)
                                                .build()));
    }

    private boolean isCompletionStage(Stage currentStage, Stage completionStage) {
        return currentStage != null
                && currentStage.getStage() != null
                && completionStage != null
                && completionStage.getStage() != null
                && currentStage.getStage().equalsIgnoreCase(completionStage.getStage());
    }
}
