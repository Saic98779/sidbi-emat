package org.emat.service;

import java.util.List;
import org.emat.dto.ApprovalRequest;
import org.emat.dto.CreateIndustryAssociationRegistrationRequest;
import org.emat.dto.IndustryAssociationRegistrationResponse;
import org.emat.dto.StageHistoryResponse;
import org.emat.dto.StageResponse;
import org.emat.dto.UpdateIndustryAssociationRegistrationRequest;

public interface IndustryAssociationRegistrationService {

    IndustryAssociationRegistrationResponse createRegistration(
            CreateIndustryAssociationRegistrationRequest request);

    IndustryAssociationRegistrationResponse getRegistrationById(Long id);

    List<IndustryAssociationRegistrationResponse> getAllRegistrations();

    IndustryAssociationRegistrationResponse updateRegistration(
            Long id, UpdateIndustryAssociationRegistrationRequest request);

    void deleteRegistration(Long id);

    IndustryAssociationRegistrationResponse approveBySidbe(
            Long id, ApprovalRequest approvalRequest, String username);

    List<StageHistoryResponse> getStageHistoryByRegistrationId(Long registrationId);

    List<StageResponse> getAllStages();

    List<IndustryAssociationRegistrationResponse> getRegistrationsByStageId(Long stageId);
}
