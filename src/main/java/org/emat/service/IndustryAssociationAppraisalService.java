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
        log.info("Creating new Industry Association Appraisal for appraisal UUID: {}", request.getRegistrationUuid());

        UUID appraisalUuid = UuidUtil.toUuid(request.getRegistrationUuid());

        // Validate appraisal exists
        IndustryAssociationRegistration registration = registrationRepository
                .findByUuid(appraisalUuid)
                .orElseThrow(() -> {
                    log.error(REGISTRATION_NOT_FOUND_MESSAGE + request.getRegistrationUuid());
                    return new EntityNotFoundException(REGISTRATION_NOT_FOUND_MESSAGE + request.getRegistrationUuid());
                });

        // Check if appraisal already exists for this appraisal
        if (appraisalRepository.existsByRegistrationUuid(appraisalUuid)) {
            log.warn("Appraisal already exists for appraisal UUID: {}", request.getRegistrationUuid());
            throw new IllegalArgumentException("Appraisal already exists for this appraisal");
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
                .sectors(request.getSectors())
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

                .financialYear(request.getFinancialYear())
                .apexHolderName(request.getApexHolderName())
                .apexHolderDesignation(request.getApexHolderDesignation())
                .apexHolderMobile(request.getApexHolderMobile())
                .apexHolderEmail(request.getApexHolderEmail())
                .addressProofType(request.getAddressProofType())
                .addressProof(request.getAddressProof())
                .idProofType(request.getIdProofType())
                .idProof(request.getIdProof())
                .nodalName(request.getNodalName())
                .nodalDesignation(request.getNodalDesignation())
                .nodalMobile(request.getNodalMobile())
                .nodalEmail(request.getNodalEmail())
                .sidbiBranch(request.getSidbiBranch())
                .mappedWithCluster(request.getMappedWithCluster())
                .clusterName(request.getClusterName())
                .mappedWithImportantDistrict(request.getMappedWithImportantDistrict())
                .districtMsmeCount(request.getDistrictMsmeCount())
                .activeMembersAbove200(request.getActiveMembersAbove200())
                .activeMembersCount(request.getActiveMembersCount())
                .justification(request.getJustification())
                .approvalLetter(request.getApprovalLetter())
                .msmeCountWithoutTraders(request.getMsmeCountWithoutTraders())
                .memberDirectoryAvailable(request.getMemberDirectoryAvailable())
                .buildingType(request.getBuildingType())
                .declarationSigned(request.getDeclarationSigned())
                .electricityBill(request.getElectricityBill())
                .telephoneBill(request.getTelephoneBill())
                .itInfrastructureAvailable(request.getItInfrastructureAvailable())
                .infrastructureType(request.getInfrastructureType())
                .secretariatStaffAvailable(request.getSecretariatStaffAvailable())
                .websiteAvailable(request.getWebsiteAvailable())
                .websiteUrl(request.getWebsiteUrl())
                .paidServicesAvailable(request.getPaidServicesAvailable())
                .adverseRemarksAvailable(request.getAdverseRemarksAvailable())
                .adverseRemarks(request.getAdverseRemarks())
                .webReport(request.getWebReport())
                .willingnessComments(request.getWillingnessComments())
                .workedWithSidbiBefore(request.getWorkedWithSidbiBefore())
                .grantProposed(request.getGrantProposed())
                .grantDetails(request.getGrantDetails())
                .envisagedOutput(request.getEnvisagedOutput())
                .envisagedOutcome(request.getEnvisagedOutcome())
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
     * Retrieve appraisal by appraisal UUID.
     *
     * @param appraisalUuid the appraisal unique identifier
     * @return the appraisal response
     * @throws EntityNotFoundException if appraisal not found
     */
    @Transactional(readOnly = true)
    public IndustryAssociationAppraisalResponse getAppraisalByRegistrationUuid(String appraisalUuid) {
        log.debug("Fetching Industry Association Appraisal for appraisal UUID: {}", appraisalUuid);
        UUID regUuid = UuidUtil.toUuid(appraisalUuid);
        IndustryAssociationAppraisal appraisal = appraisalRepository.findByRegistrationUuid(regUuid)
                .orElseThrow(() -> {
                    log.error("Appraisal not found for appraisal UUID: " + appraisalUuid);
                    return new EntityNotFoundException("Appraisal not found for appraisal UUID: " + appraisalUuid);
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
        if (request.getSectors() != null) {
            appraisal.setSectors(request.getSectors());
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

        if (request.getNodalName() != null) {
            appraisal.setNodalName(request.getNodalName());
        }
        if (request.getNodalDesignation() != null) {
            appraisal.setNodalDesignation(request.getNodalDesignation());
        }
        if (request.getNodalMobile() != null) {
            appraisal.setNodalMobile(request.getNodalMobile());
        }
        if (request.getNodalEmail() != null) {
            appraisal.setNodalEmail(request.getNodalEmail());
        }
        if (request.getSidbiBranch() != null) {
            appraisal.setSidbiBranch(request.getSidbiBranch());
        }
        if (request.getMappedWithCluster() != null) {
            appraisal.setMappedWithCluster(request.getMappedWithCluster());
        }
        if (request.getClusterName() != null) {
            appraisal.setClusterName(request.getClusterName());
        }
        if (request.getMappedWithImportantDistrict() != null) {
            appraisal.setMappedWithImportantDistrict(request.getMappedWithImportantDistrict());
        }
        if (request.getDistrictMsmeCount() != null) {
            appraisal.setDistrictMsmeCount(request.getDistrictMsmeCount());
        }
        if (request.getActiveMembersAbove200() != null) {
            appraisal.setActiveMembersAbove200(request.getActiveMembersAbove200());
        }
        if (request.getActiveMembersCount() != null) {
            appraisal.setActiveMembersCount(request.getActiveMembersCount());
        }
        if (request.getJustification() != null) {
            appraisal.setJustification(request.getJustification());
        }
        if (request.getApprovalLetter() != null) {
            appraisal.setApprovalLetter(request.getApprovalLetter());
        }
        if (request.getMsmeCountWithoutTraders() != null) {
            appraisal.setMsmeCountWithoutTraders(request.getMsmeCountWithoutTraders());
        }
        if (request.getMemberDirectoryAvailable() != null) {
            appraisal.setMemberDirectoryAvailable(request.getMemberDirectoryAvailable());
        }
        if (request.getBuildingType() != null) {
            appraisal.setBuildingType(request.getBuildingType());
        }
        if (request.getDeclarationSigned() != null) {
            appraisal.setDeclarationSigned(request.getDeclarationSigned());
        }
        if (request.getElectricityBill() != null) {
            appraisal.setElectricityBill(request.getElectricityBill());
        }
        if (request.getTelephoneBill() != null) {
            appraisal.setTelephoneBill(request.getTelephoneBill());
        }
        if (request.getItInfrastructureAvailable() != null) {
            appraisal.setItInfrastructureAvailable(request.getItInfrastructureAvailable());
        }
        if (request.getInfrastructureType() != null) {
            appraisal.setInfrastructureType(request.getInfrastructureType());
        }
        if (request.getSecretariatStaffAvailable() != null) {
            appraisal.setSecretariatStaffAvailable(request.getSecretariatStaffAvailable());
        }
        if (request.getWebsiteAvailable() != null) {
            appraisal.setWebsiteAvailable(request.getWebsiteAvailable());
        }
        if (request.getWebsiteUrl() != null) {
            appraisal.setWebsiteUrl(request.getWebsiteUrl());
        }
        if (request.getPaidServicesAvailable() != null) {
            appraisal.setPaidServicesAvailable(request.getPaidServicesAvailable());
        }
        if (request.getAdverseRemarksAvailable() != null) {
            appraisal.setAdverseRemarksAvailable(request.getAdverseRemarksAvailable());
        }
        if (request.getAdverseRemarks() != null) {
            appraisal.setAdverseRemarks(request.getAdverseRemarks());
        }
        if (request.getWebReport() != null) {
            appraisal.setWebReport(request.getWebReport());
        }
        if (request.getWillingnessComments() != null) {
            appraisal.setWillingnessComments(request.getWillingnessComments());
        }
        if (request.getWorkedWithSidbiBefore() != null) {
            appraisal.setWorkedWithSidbiBefore(request.getWorkedWithSidbiBefore());
        }
        if (request.getGrantProposed() != null) {
            appraisal.setGrantProposed(request.getGrantProposed());
        }
        if (request.getGrantDetails() != null) {
            appraisal.setGrantDetails(request.getGrantDetails());
        }
        if (request.getEnvisagedOutput() != null) {
            appraisal.setEnvisagedOutput(request.getEnvisagedOutput());
        }
        if (request.getEnvisagedOutcome() != null) {
            appraisal.setEnvisagedOutcome(request.getEnvisagedOutcome());
        }

        if (request.getFinancialYear() != null) {
            appraisal.setFinancialYear(request.getFinancialYear());
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
                .sectors(appraisal.getSectors())
                .financialYear(appraisal.getFinancialYear())
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

                .apexHolderName(appraisal.getApexHolderName())
                .apexHolderDesignation(appraisal.getApexHolderDesignation())
                .apexHolderMobile(appraisal.getApexHolderMobile())
                .apexHolderEmail(appraisal.getApexHolderEmail())
                .addressProofType(appraisal.getAddressProofType())
                .addressProof(appraisal.getAddressProof())
                .idProofType(appraisal.getIdProofType())
                .idProof(appraisal.getIdProof())
                .nodalName(appraisal.getNodalName())
                .nodalDesignation(appraisal.getNodalDesignation())
                .nodalMobile(appraisal.getNodalMobile())
                .nodalEmail(appraisal.getNodalEmail())
                .sidbiBranch(appraisal.getSidbiBranch())
                .mappedWithCluster(appraisal.getMappedWithCluster())
                .clusterName(appraisal.getClusterName())
                .mappedWithImportantDistrict(appraisal.getMappedWithImportantDistrict())
                .districtMsmeCount(appraisal.getDistrictMsmeCount())
                .activeMembersAbove200(appraisal.getActiveMembersAbove200())
                .activeMembersCount(appraisal.getActiveMembersCount())
                .justification(appraisal.getJustification())
                .approvalLetter(appraisal.getApprovalLetter())
                .msmeCountWithoutTraders(appraisal.getMsmeCountWithoutTraders())
                .memberDirectoryAvailable(appraisal.getMemberDirectoryAvailable())
                .buildingType(appraisal.getBuildingType())
                .declarationSigned(appraisal.getDeclarationSigned())
                .electricityBill(appraisal.getElectricityBill())
                .telephoneBill(appraisal.getTelephoneBill())
                .itInfrastructureAvailable(appraisal.getItInfrastructureAvailable())
                .infrastructureType(appraisal.getInfrastructureType())
                .secretariatStaffAvailable(appraisal.getSecretariatStaffAvailable())
                .websiteAvailable(appraisal.getWebsiteAvailable())
                .websiteUrl(appraisal.getWebsiteUrl())
                .paidServicesAvailable(appraisal.getPaidServicesAvailable())
                .adverseRemarksAvailable(appraisal.getAdverseRemarksAvailable())
                .adverseRemarks(appraisal.getAdverseRemarks())
                .webReport(appraisal.getWebReport())
                .willingnessComments(appraisal.getWillingnessComments())
                .workedWithSidbiBefore(appraisal.getWorkedWithSidbiBefore())
                .grantProposed(appraisal.getGrantProposed())
                .grantDetails(appraisal.getGrantDetails())
                .envisagedOutput(appraisal.getEnvisagedOutput())
                .envisagedOutcome(appraisal.getEnvisagedOutcome())
                .build();
    }

    /**
     * Retrieve appraisals based on state, district and SIDBI approval status.
     *
     * @param state state name
     * @param district district name
     * @param isSidbeApproved SIDBI approval status
     * @return list of appraisal responses
     */
    @Transactional(readOnly = true)
    public List<IndustryAssociationAppraisalResponse> getAppraisals(
            String state,
            String district,
            Boolean isSidbeApproved) {

        log.debug("Fetching Industry Association Appraisals for state: {}, district: {}, approved: {}",
                state, district, isSidbeApproved);

        List<IndustryAssociationAppraisal> appraisals =
                appraisalRepository
                        .findAllByIsActiveTrueAndRegistrationStateAndRegistrationDistrictAndIsSidbeApproved(
                                state,
                                district,
                                isSidbeApproved);

        return appraisals.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
}


