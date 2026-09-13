package org.emat.service;

import java.util.List;
import org.emat.dto.StageHistoryResponse;
import org.emat.dto.StageResponse;

public interface StageService {

    List<StageHistoryResponse> getStageHistoryByRegistrationId(Long registrationId);

    List<StageResponse> getAllStages();

    void updateStage(Long registrationId, Long stageId, String comment, String createdBy);
}
