package org.emat.service;

import lombok.RequiredArgsConstructor;
import org.emat.dto.StageHistoryResponse;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.entity.Stage;
import org.emat.entity.StageHistory;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.emat.repository.StageHistoryRepository;
import org.emat.repository.StageRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StageService {

    private static final String REGISTRATION_NOT_FOUND_MESSAGE = "Registration not found with ID: ";
    private static final String STAGE_NOT_FOUND_MESSAGE = "Stage not found with ID: ";
    private static final String STAGE_NOT_FOUND_WITH_SUB_STAGE_MESSAGE = "Stage not found with sub-stage: ";

    private final IndustryAssociationRegistrationRepository registrationRepository;
    private final StageRepository stageRepository;
    private final StageHistoryRepository stageHistoryRepository;

    @Transactional(readOnly = true)
    public List<StageHistoryResponse> getStageHistoryByRegistrationId(Long registrationId) {
        return stageHistoryRepository.findByRegistration_IdOrderByTimeDesc(registrationId).stream()
                .map(history -> StageHistoryResponse.builder()
                        .id(history.getId())
                        .registrationId(history.getRegistration() != null ? history.getRegistration().getId() : null)
                        .stage(history.getStage())
                        .subStage(history.getSubStage())
                        .time(history.getTime())
                        .createdBy(history.getCreatedBy())
                        .comment(history.getComment())
                        .build())
                .toList();
    }

    @Async
    @Transactional
    public void updateStage(
            Long registrationId,
            Long stageId,
            String comment,
            String createdBy
    ) {

        Stage stage = stageRepository.findById(stageId)
                .orElseThrow(() -> new EntityNotFoundException(STAGE_NOT_FOUND_MESSAGE + stageId));

        persistStageTransition(registrationId, stage, comment, createdBy);
    }

    @Async
    @Transactional
    public void updateStageBySubStage(
            Long registrationId,
            String subStage,
            String comment,
            String createdBy
    ) {

        Stage stage = stageRepository.findFirstBySubStageIgnoreCase(subStage)
                .orElseThrow(() -> new EntityNotFoundException(STAGE_NOT_FOUND_WITH_SUB_STAGE_MESSAGE + subStage));

        persistStageTransition(registrationId, stage, comment, createdBy);
    }

    private void persistStageTransition(
            Long registrationId,
            Stage stage,
            String comment,
            String createdBy
    ) {

        IndustryAssociationRegistration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new EntityNotFoundException(REGISTRATION_NOT_FOUND_MESSAGE + registrationId));


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
    }
}