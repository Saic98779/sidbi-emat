package org.emat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateIndustryAssociationAppraisalRequest;
import org.emat.dto.IndustryAssociationAppraisalResponse;
import org.emat.dto.UpdateIndustryAssociationAppraisalRequest;
import org.emat.dto.ApprovalRequest;
import org.emat.entity.IndustryAssociationAppraisal;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.entity.User;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.IndustryAssociationAppraisalRepository;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.emat.repository.UserRepository;
import org.emat.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service layer for IndustryAssociationAppraisal business logic.
 * Handles CRUD operations and business rules for Industry Association Appraisals.
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class IndustryAssociationAppraisalService {

    private static final String APPRAISAL_NOT_FOUND_MESSAGE = "Appraisal not found with UUID: ";
    private static final String REGISTRATION_NOT_FOUND_MESSAGE = "Registration not found with UUID: ";

    private final IndustryAssociationAppraisalRepository appraisalRepository;
    private final IndustryAssociationRegistrationRepository registrationRepository;
    private final UserRepository userRepository;

    /**
     * Create a new Industry Association Appraisal.
     *
     * @param request the create request DTO
     * @return the created appraisal response
     */
    public IndustryAssociationAppraisalResponse createAppraisal(
            CreateIndustryAssociationAppraisalRequest request) {
        log.info("Creating new Industry Association Appraisal for registration UUID: {}", request.getRegistrationUuid());

        UUID registrationUuid = UuidUtil.toUuid(request.getRegistrationUuid());

        // Validate registration exists
        IndustryAssociationRegistration registration = registrationRepository
                .findByUuid(registrationUuid)
                .orElseThrow(() -> {
                    log.error(REGISTRATION_NOT_FOUND_MESSAGE + request.getRegistrationUuid());
                    return new EntityNotFoundException(REGISTRATION_NOT_FOUND_MESSAGE + request.getRegistrationUuid());
                });

        // Check if appraisal already exists for this registration
        if (appraisalRepository.existsByRegistrationUuid(registrationUuid)) {
            log.warn("Appraisal already exists for registration UUID: {}", request.getRegistrationUuid());
            throw new IllegalArgumentException("Appraisal already exists for this registration");
        }

        IndustryAssociationAppraisal appraisal = IndustryAssociationAppraisal.builder()
                .registration(registration)
                .cibilReportReferenceNo(request.getCibilReportReferenceNo())
                .cibilReportDate(request.getCibilReportDate())
                .cibilRanking(request.getCibilRanking())
                .cibilRemarks(request.getCibilRemarks())
                .ngoDarpanNumber(request.getNgoDarpanNumber())
                .nabardBlacklisted(request.getNabardBlacklisted())
                .smartReportReferenceNo(request.getSmartReportReferenceNo())
                .smartReportDate(request.getSmartReportDate())
                .smartReportRemarks(request.getSmartReportRemarks())
                .webSearchVerified(request.getWebSearchVerified())
                .webSearchDocument(request.getWebSearchDocument())
                .beneficialOwnerCibilRemarks(request.getBeneficialOwnerCibilRemarks())
                .beneficialOwnerSmartRemarks(request.getBeneficialOwnerSmartRemarks())
                .majorSourcesOfIncome(request.getMajorSourcesOfIncome())
                .activitiesLastYear(request.getActivitiesLastYear())
                .formalizationComments(request.getFormalizationComments())
                .referralArrangementComments(request.getReferralArrangementComments())
                .referralArrangementReady(request.getReferralArrangementReady())
                .bseReadinessComments(request.getBseReadinessComments())
                .bseReadinessReady(request.getBseReadinessReady())
                .topThreeSectors(request.getTopThreeSectors())
                .financingScope(request.getFinancingScope())
                .financingScopeCrore(request.getFinancingScopeCrore())
                .projectLocation(request.getProjectLocation())
                .clusterExpertComments(request.getClusterExpertComments())
                .budgetAllocated(request.getBudgetAllocated())
                .utilizedAmount(request.getUtilizedAmount())
                .availableBudget(request.getAvailableBudget())
                .termsAndConditions(request.getTermsAndConditions())
                .dopDate(request.getDopDate())
                .recommendation(request.getRecommendation())
                .recommendationRemarks(request.getRecommendationRemarks())
                .createdBy(request.getCreatedBy())
                .isActive(true)
                .build();

        IndustryAssociationAppraisal saved = appraisalRepository.save(appraisal);
        log.info("Industry Association Appraisal created successfully with UUID: {}", saved.getUuid());
        return convertToResponse(saved);
    }

    /**
     * Retrieve an appraisal by UUID.
     *
     * @param uuid the unique identifier
     * @return the appraisal response
     * @throws EntityNotFoundException if appraisal not found
     */
    @Transactional(readOnly = true)
    public IndustryAssociationAppraisalResponse getAppraisalById(String uuid) {
        log.debug("Fetching Industry Association Appraisal with UUID: {}", uuid);
        UUID appraisalUuid = UuidUtil.toUuid(uuid);
        IndustryAssociationAppraisal appraisal = appraisalRepository.findByUuid(appraisalUuid)
                .orElseThrow(() -> {
                    log.error(APPRAISAL_NOT_FOUND_MESSAGE + uuid);
                    return new EntityNotFoundException(APPRAISAL_NOT_FOUND_MESSAGE + uuid);
                });
        return convertToResponse(appraisal);
    }

    /**
     * Retrieve appraisal by registration UUID.
     *
     * @param registrationUuid the registration unique identifier
     * @return the appraisal response
     * @throws EntityNotFoundException if appraisal not found
     */
    @Transactional(readOnly = true)
    public IndustryAssociationAppraisalResponse getAppraisalByRegistrationUuid(String registrationUuid) {
        log.debug("Fetching Industry Association Appraisal for registration UUID: {}", registrationUuid);
        UUID regUuid = UuidUtil.toUuid(registrationUuid);
        IndustryAssociationAppraisal appraisal = appraisalRepository.findByRegistrationUuid(regUuid)
                .orElseThrow(() -> {
                    log.error("Appraisal not found for registration UUID: " + registrationUuid);
                    return new EntityNotFoundException("Appraisal not found for registration UUID: " + registrationUuid);
                });
        return convertToResponse(appraisal);
    }

    /**
     * Retrieve all active appraisals.
     *
     * @return list of active appraisal responses
     */
    @Transactional(readOnly = true)
    public List<IndustryAssociationAppraisalResponse> getAllAppraisals() {
        log.debug("Fetching all active Industry Association Appraisals");
        List<IndustryAssociationAppraisal> appraisals = appraisalRepository.findAllByIsActiveTrue();
        return appraisals.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing appraisal.
     *
     * @param uuid the unique identifier
     * @param request the update request DTO
     * @return the updated appraisal response
     * @throws EntityNotFoundException if appraisal not found
     */
    public IndustryAssociationAppraisalResponse updateAppraisal(
            String uuid, UpdateIndustryAssociationAppraisalRequest request) {
        log.info("Updating Industry Association Appraisal with UUID: {}", uuid);

        UUID appraisalUuid = UuidUtil.toUuid(uuid);
        IndustryAssociationAppraisal appraisal = appraisalRepository.findByUuid(appraisalUuid)
                .orElseThrow(() -> {
                    log.error(APPRAISAL_NOT_FOUND_MESSAGE + uuid);
                    return new EntityNotFoundException(APPRAISAL_NOT_FOUND_MESSAGE + uuid);
                });

        // Update fields selectively based on request
        if (request.getCibilReportReferenceNo() != null) {
            appraisal.setCibilReportReferenceNo(request.getCibilReportReferenceNo());
        }
        if (request.getCibilReportDate() != null) {
            appraisal.setCibilReportDate(request.getCibilReportDate());
        }
        if (request.getCibilRanking() != null) {
            appraisal.setCibilRanking(request.getCibilRanking());
        }
        if (request.getCibilRemarks() != null) {
            appraisal.setCibilRemarks(request.getCibilRemarks());
        }
        if (request.getNgoDarpanNumber() != null) {
            appraisal.setNgoDarpanNumber(request.getNgoDarpanNumber());
        }
        if (request.getNabardBlacklisted() != null) {
            appraisal.setNabardBlacklisted(request.getNabardBlacklisted());
        }
        if (request.getSmartReportReferenceNo() != null) {
            appraisal.setSmartReportReferenceNo(request.getSmartReportReferenceNo());
        }
        if (request.getSmartReportDate() != null) {
            appraisal.setSmartReportDate(request.getSmartReportDate());
        }
        if (request.getSmartReportRemarks() != null) {
            appraisal.setSmartReportRemarks(request.getSmartReportRemarks());
        }
        if (request.getWebSearchVerified() != null) {
            appraisal.setWebSearchVerified(request.getWebSearchVerified());
        }
        if (request.getWebSearchDocument() != null) {
            appraisal.setWebSearchDocument(request.getWebSearchDocument());
        }
        if (request.getBeneficialOwnerCibilRemarks() != null) {
            appraisal.setBeneficialOwnerCibilRemarks(request.getBeneficialOwnerCibilRemarks());
        }
        if (request.getBeneficialOwnerSmartRemarks() != null) {
            appraisal.setBeneficialOwnerSmartRemarks(request.getBeneficialOwnerSmartRemarks());
        }
        if (request.getMajorSourcesOfIncome() != null) {
            appraisal.setMajorSourcesOfIncome(request.getMajorSourcesOfIncome());
        }
        if (request.getActivitiesLastYear() != null) {
            appraisal.setActivitiesLastYear(request.getActivitiesLastYear());
        }
        if (request.getFormalizationComments() != null) {
            appraisal.setFormalizationComments(request.getFormalizationComments());
        }
        if (request.getReferralArrangementComments() != null) {
            appraisal.setReferralArrangementComments(request.getReferralArrangementComments());
        }
        if (request.getReferralArrangementReady() != null) {
            appraisal.setReferralArrangementReady(request.getReferralArrangementReady());
        }
        if (request.getBseReadinessComments() != null) {
            appraisal.setBseReadinessComments(request.getBseReadinessComments());
        }
        if (request.getBseReadinessReady() != null) {
            appraisal.setBseReadinessReady(request.getBseReadinessReady());
        }
        if (request.getTopThreeSectors() != null) {
            appraisal.setTopThreeSectors(request.getTopThreeSectors());
        }
        if (request.getFinancingScope() != null) {
            appraisal.setFinancingScope(request.getFinancingScope());
        }
        if (request.getFinancingScopeCrore() != null) {
            appraisal.setFinancingScopeCrore(request.getFinancingScopeCrore());
        }
        if (request.getProjectLocation() != null) {
            appraisal.setProjectLocation(request.getProjectLocation());
        }
        if (request.getClusterExpertComments() != null) {
            appraisal.setClusterExpertComments(request.getClusterExpertComments());
        }
        if (request.getBudgetAllocated() != null) {
            appraisal.setBudgetAllocated(request.getBudgetAllocated());
        }
        if (request.getUtilizedAmount() != null) {
            appraisal.setUtilizedAmount(request.getUtilizedAmount());
        }
        if (request.getAvailableBudget() != null) {
            appraisal.setAvailableBudget(request.getAvailableBudget());
        }
        if (request.getTermsAndConditions() != null) {
            appraisal.setTermsAndConditions(request.getTermsAndConditions());
        }
        if (request.getDopDate() != null) {
            appraisal.setDopDate(request.getDopDate());
        }
        if (request.getRecommendation() != null) {
            appraisal.setRecommendation(request.getRecommendation());
        }
        if (request.getRecommendationRemarks() != null) {
            appraisal.setRecommendationRemarks(request.getRecommendationRemarks());
        }
        if (request.getUpdatedBy() != null) {
            appraisal.setUpdatedBy(request.getUpdatedBy());
        }
        if (request.getIsActive() != null) {
            appraisal.setIsActive(request.getIsActive());
        }

        IndustryAssociationAppraisal updated = appraisalRepository.save(appraisal);
        log.info("Industry Association Appraisal updated successfully with UUID: {}", uuid);
        return convertToResponse(updated);
    }

    /**
     * Approve or reject an appraisal by SIDBE.
     *
     * @param uuid the unique identifier of the appraisal
     * @param approvalRequest the approval request containing approval status
     * @param username the username of the user approving/rejecting
     * @return the updated appraisal response
     * @throws EntityNotFoundException if appraisal or user not found
     */
    public IndustryAssociationAppraisalResponse approveBySidbe(
            String uuid, ApprovalRequest approvalRequest, String username) {
        log.info("Processing SIDBE approval for appraisal with UUID: {} by user: {}", uuid, username);
        UUID appraisalUuid = UuidUtil.toUuid(uuid);

        // Fetch the appraisal
        IndustryAssociationAppraisal appraisal = appraisalRepository.findByUuid(appraisalUuid)
                .orElseThrow(() -> {
                    log.error(APPRAISAL_NOT_FOUND_MESSAGE + uuid);
                    return new EntityNotFoundException(APPRAISAL_NOT_FOUND_MESSAGE + uuid);
                });

        // Fetch the approving user
        User approver = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", username);
                    return new EntityNotFoundException("User not found with username: " + username);
                });

        // Update approval fields
        appraisal.setIsSidbeApproved(approvalRequest.getIsSidbeApproved());
        appraisal.setSidbeApprovedByUser(approver);

        IndustryAssociationAppraisal updated = appraisalRepository.save(appraisal);
        log.info("SIDBE approval processed successfully for appraisal UUID: {} by user: {}", uuid, username);

        return convertToResponse(updated);
    }

    /**
     * Permanently delete an appraisal.
     *
     * @param uuid the unique identifier
     * @throws EntityNotFoundException if appraisal not found
     */
    public void permanentlyDeleteAppraisal(String uuid) {
        log.info("Permanently deleting Industry Association Appraisal with UUID: {}", uuid);
        UUID appraisalUuid = UuidUtil.toUuid(uuid);
        IndustryAssociationAppraisal appraisal = appraisalRepository.findByUuid(appraisalUuid)
                .orElseThrow(() -> {
                    log.error(APPRAISAL_NOT_FOUND_MESSAGE + uuid);
                    return new EntityNotFoundException(APPRAISAL_NOT_FOUND_MESSAGE + uuid);
                });
        appraisalRepository.delete(appraisal);
        log.info("Industry Association Appraisal permanently deleted with UUID: {}", uuid);
    }

    /**
     * Convert IndustryAssociationAppraisal entity to response DTO.
     *
     * @param appraisal the entity
     * @return the response DTO
     */
    private IndustryAssociationAppraisalResponse convertToResponse(IndustryAssociationAppraisal appraisal) {
        return IndustryAssociationAppraisalResponse.builder()
                .uuid(appraisal.getUuid().toString())
                .registrationUuid(appraisal.getRegistration().getUuid().toString())
                .registrationName(appraisal.getRegistration().getIndustryAssociationName())
                .sidbiBranch(appraisal.getRegistration().getSidbiBranch())
                .sidbiBranchName(appraisal.getRegistration().getSidbiBranchName())
                .cibilReportReferenceNo(appraisal.getCibilReportReferenceNo())
                .cibilReportDate(appraisal.getCibilReportDate())
                .cibilRanking(appraisal.getCibilRanking())
                .cibilRemarks(appraisal.getCibilRemarks())
                .ngoDarpanNumber(appraisal.getNgoDarpanNumber())
                .nabardBlacklisted(appraisal.getNabardBlacklisted())
                .smartReportReferenceNo(appraisal.getSmartReportReferenceNo())
                .smartReportDate(appraisal.getSmartReportDate())
                .smartReportRemarks(appraisal.getSmartReportRemarks())
                .webSearchVerified(appraisal.getWebSearchVerified())
                .webSearchDocument(appraisal.getWebSearchDocument())
                .beneficialOwnerCibilRemarks(appraisal.getBeneficialOwnerCibilRemarks())
                .beneficialOwnerSmartRemarks(appraisal.getBeneficialOwnerSmartRemarks())
                .majorSourcesOfIncome(appraisal.getMajorSourcesOfIncome())
                .activitiesLastYear(appraisal.getActivitiesLastYear())
                .formalizationComments(appraisal.getFormalizationComments())
                .referralArrangementComments(appraisal.getReferralArrangementComments())
                .referralArrangementReady(appraisal.getReferralArrangementReady())
                .bseReadinessComments(appraisal.getBseReadinessComments())
                .bseReadinessReady(appraisal.getBseReadinessReady())
                .topThreeSectors(appraisal.getTopThreeSectors())
                .financingScope(appraisal.getFinancingScope())
                .financingScopeCrore(appraisal.getFinancingScopeCrore())
                .projectLocation(appraisal.getProjectLocation())
                .clusterExpertComments(appraisal.getClusterExpertComments())
                .budgetAllocated(appraisal.getBudgetAllocated())
                .utilizedAmount(appraisal.getUtilizedAmount())
                .availableBudget(appraisal.getAvailableBudget())
                .termsAndConditions(appraisal.getTermsAndConditions())
                .dopDate(appraisal.getDopDate())
                .recommendation(appraisal.getRecommendation())
                .recommendationRemarks(appraisal.getRecommendationRemarks())
                .isSidbeApproved(appraisal.getIsSidbeApproved())
                .sidbeApprovedByUserId(appraisal.getSidbeApprovedByUser() != null ?
                        appraisal.getSidbeApprovedByUser().getId() : null)
                .sidbeApprovedByUsername(appraisal.getSidbeApprovedByUser() != null ?
                        appraisal.getSidbeApprovedByUser().getUsername() : null)
                .createdAt(appraisal.getCreatedAt())
                .updatedAt(appraisal.getUpdatedAt())
                .createdBy(appraisal.getCreatedBy())
                .updatedBy(appraisal.getUpdatedBy())
                .isActive(appraisal.getIsActive())
                .build();
    }
}


