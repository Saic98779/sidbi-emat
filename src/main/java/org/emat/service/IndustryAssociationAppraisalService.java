package org.emat.service;

import java.util.List;
import org.emat.dto.ApprovalRequest;
import org.emat.dto.CreateIndustryAssociationAppraisalRequest;
import org.emat.dto.IndustryAssociationAppraisalResponse;
import org.emat.dto.UpdateIndustryAssociationAppraisalRequest;

public interface IndustryAssociationAppraisalService {

    IndustryAssociationAppraisalResponse createAppraisal(
            CreateIndustryAssociationAppraisalRequest request);

    IndustryAssociationAppraisalResponse getAppraisalById(Long id);

    IndustryAssociationAppraisalResponse getAppraisalByRegistrationId(Long registrationId);

    List<IndustryAssociationAppraisalResponse> getAllAppraisals();

    IndustryAssociationAppraisalResponse updateAppraisal(
            Long id, UpdateIndustryAssociationAppraisalRequest request);

    IndustryAssociationAppraisalResponse approveBySidbe(
            Long id, ApprovalRequest approvalRequest, String username);

    void permanentlyDeleteAppraisal(Long id);

    List<IndustryAssociationAppraisalResponse> getAppraisals(
            String state, String district, Boolean isSidbeApproved);
}
