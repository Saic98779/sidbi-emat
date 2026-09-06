package org.emat.service;

import lombok.RequiredArgsConstructor;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.entity.Stage;
import org.emat.entity.StageHistory;
import org.emat.repository.StageHistoryRepository;
import org.emat.repository.StageRepository;
import org.emat.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StageService {

    private final StageRepository stageRepository;
    private final StageHistoryRepository stageHistoryRepository;
    private final CommonUtil commonUtil;

    @Transactional
    public void updateStage(
            IndustryAssociationRegistration registration,
            Long stageId,
            String comment
    ) {

        Stage stage = stageRepository.findById(stageId).orElseThrow();
        registration.setCurrentStage(stage);

        StageHistory history = new StageHistory();
        history.setRegistration(registration);
        history.setStage(stage.getStage());
        history.setSubStage(stage.getSubStage());
        history.setTime(LocalDateTime.now());
        history.setCreatedBy(commonUtil.getCurrentUsername());
        history.setComment(comment);

        stageHistoryRepository.save(history);
    }
}