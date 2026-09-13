package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.ApprovalRequest;
import org.emat.dto.CreateIndustryAssociationAppraisalRequest;
import org.emat.dto.IndustryAssociationAppraisalResponse;
import org.emat.dto.UpdateIndustryAssociationAppraisalRequest;
import org.emat.entity.IndustryAssociationAppraisal;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.entity.User;
import org.emat.mapper.IndustryAssociationAppraisalMapper;
import org.emat.repository.IndustryAssociationAppraisalRepository;
import org.emat.service.IndustryAssociationAppraisalService;
import org.emat.service.StageService;
import org.emat.util.CommonUtil;
import org.emat.validator.IndustryAssociationAppraisalValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class IndustryAssociationAppraisalServiceImpl
        implements IndustryAssociationAppraisalService {

    private final IndustryAssociationAppraisalRepository appraisalRepository;
    private final IndustryAssociationAppraisalMapper appraisalMapper;
    private final IndustryAssociationAppraisalValidator appraisalValidator;
    private final StageService stageService;
    private final CommonUtil commonUtil;

    @Override
    public IndustryAssociationAppraisalResponse createAppraisal(
            CreateIndustryAssociationAppraisalRequest request) {
        log.info(
                "Creating new Industry Association Appraisal for registration ID: {}",
                request.getRegistrationId());

        Long registrationId = request.getRegistrationId();
        IndustryAssociationRegistration registration =
                appraisalValidator.getRegistrationOrThrow(registrationId);
        appraisalValidator.validateAppraisalNotExists(registrationId);

        IndustryAssociationAppraisal appraisal = appraisalMapper.toEntity(request, registration);

        IndustryAssociationAppraisal saved = appraisalRepository.save(appraisal);
        log.info("Industry Association Appraisal created successfully with ID: {}", saved.getId());
        if (saved != null) {
            stageService.updateStage(
                    saved.getRegistration().getId(),
                    request.getStageId(),
                    request.getStageComments(),
                    commonUtil.getCurrentUsername());
        }
        return appraisalMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public IndustryAssociationAppraisalResponse getAppraisalById(Long id) {
        log.debug("Fetching Industry Association Appraisal with ID: {}", id);
        IndustryAssociationAppraisal appraisal = appraisalValidator.getAppraisalOrThrow(id);
        return appraisalMapper.toResponse(appraisal);
    }

    @Override
    @Transactional(readOnly = true)
    public IndustryAssociationAppraisalResponse getAppraisalByRegistrationId(Long registrationId) {
        log.debug(
                "Fetching Industry Association Appraisal for registration ID: {}", registrationId);
        IndustryAssociationAppraisal appraisal =
                appraisalValidator.getAppraisalByRegistrationOrThrow(registrationId);
        return appraisalMapper.toResponse(appraisal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndustryAssociationAppraisalResponse> getAllAppraisals() {
        log.debug("Fetching all active Industry Association Appraisals");
        List<IndustryAssociationAppraisal> appraisals = appraisalRepository.findAllByIsActiveTrue();
        return appraisals.stream().map(appraisalMapper::toResponse).toList();
    }

    @Override
    public IndustryAssociationAppraisalResponse updateAppraisal(
            Long id, UpdateIndustryAssociationAppraisalRequest request) {
        log.info("Updating Industry Association Appraisal with ID: {}", id);
        IndustryAssociationAppraisal appraisal = appraisalValidator.getAppraisalOrThrow(id);
        appraisalMapper.applyUpdateRequest(appraisal, request);

        IndustryAssociationAppraisal updated = appraisalRepository.save(appraisal);
        log.info("Industry Association Appraisal updated successfully with ID: {}", id);
        if (updated != null) {
            stageService.updateStage(
                    updated.getRegistration().getId(),
                    request.getStageId(),
                    request.getStageComments(),
                    commonUtil.getCurrentUsername());
        }
        return appraisalMapper.toResponse(updated);
    }

    @Override
    public IndustryAssociationAppraisalResponse approveBySidbe(
            Long id, ApprovalRequest approvalRequest, String username) {
        log.info("Processing SIDBE approval for appraisal with ID: {} by user: {}", id, username);
        IndustryAssociationAppraisal appraisal = appraisalValidator.getAppraisalOrThrow(id);
        User approver = appraisalValidator.getUserByUsernameOrThrow(username);

        appraisal.setIsSidbeApproved(approvalRequest.getIsSidbeApproved());
        appraisal.setSidbeApprovedByUser(approver);

        IndustryAssociationAppraisal updated = appraisalRepository.save(appraisal);
        if (updated != null) {
            stageService.updateStage(
                    updated.getRegistration().getId(),
                    approvalRequest.getStageId(),
                    approvalRequest.getStageComments(),
                    commonUtil.getCurrentUsername());
        }
        log.info(
                "SIDBE approval processed successfully for appraisal ID: {} by user: {}",
                id,
                username);

        return appraisalMapper.toResponse(updated);
    }

    @Override
    public void permanentlyDeleteAppraisal(Long id) {
        log.info("Permanently deleting Industry Association Appraisal with ID: {}", id);
        IndustryAssociationAppraisal appraisal = appraisalValidator.getAppraisalOrThrow(id);
        appraisalRepository.delete(appraisal);
        log.info("Industry Association Appraisal permanently deleted with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndustryAssociationAppraisalResponse> getAppraisals(
            String state, String district, Boolean isSidbeApproved) {

        log.debug(
                "Fetching Industry Association Appraisals for state: {}, district: {}, approved:"
                        + " {}",
                state,
                district,
                isSidbeApproved);

        List<IndustryAssociationAppraisal> appraisals =
                appraisalRepository
                        .findAllByIsActiveTrueAndRegistrationStateAndRegistrationDistrictAndIsSidbeApproved(
                                state, district, isSidbeApproved);

        return appraisals.stream().map(appraisalMapper::toResponse).toList();
    }
}
