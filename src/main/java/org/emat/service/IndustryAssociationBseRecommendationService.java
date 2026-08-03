package org.emat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.BseRecommendationResponse;
import org.emat.dto.CreateBseRecommendationRequest;
import org.emat.dto.UpdateBseRecommendationRequest;
import org.emat.entity.IndustryAssociationBseRecommendation;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.IndustryAssociationBseRecommendationRepository;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndustryAssociationBseRecommendationService {

    private final IndustryAssociationBseRecommendationRepository bseRecommendationRepository;
    private final IndustryAssociationRegistrationRepository registrationRepository;

    /**
     * Create a new BSE recommendation
     */
    @Transactional
    public BseRecommendationResponse createBseRecommendation(CreateBseRecommendationRequest request) {
        log.info("Creating BSE recommendation for registration: {}", request.getRegistrationUuid());

        // Validate registration exists
        IndustryAssociationRegistration registration = registrationRepository
                .findByUuid(request.getRegistrationUuid())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Industry Association Registration not found with UUID: " + request.getRegistrationUuid()));

        // Create entity
        IndustryAssociationBseRecommendation bseRecommendation = IndustryAssociationBseRecommendation.builder()
                .registration(registration)
                .state(request.getState())
                .district(request.getDistrict())
                .industryRegistrationId(request.getIndustryRegistrationId())
                .bseName(request.getBseName())
                .mobileNumber(request.getMobileNumber())
                .emailId(request.getEmailId())
                .highestQualification(request.getHighestQualification())
                .experienced(request.getExperienced())
                .experienceYears(request.getExperienceYears())
                .experienceMonths(request.getExperienceMonths())
                .employmentStatus(request.getEmploymentStatus())
                .currentSalary(request.getCurrentSalary())
                .noticePeriodDays(request.getNoticePeriodDays())
                .lastDrawnSalary(request.getLastDrawnSalary())
                .relievingLetter(request.getRelievingLetter())
                .expectedSalary(request.getExpectedSalary())
                .resumeStatus(request.getResumeStatus())
                .resumeFile(request.getResumeFile())
                .salarySlip(request.getSalarySlip())
                .candidateCv(request.getCandidateCv())
                .gtRecommendation(request.getGtRecommendation())
                .gtRecommendationDate(request.getGtRecommendationDate())
                .gtRemarks(request.getGtRemarks())
                .pmuRecommendation(request.getPmuRecommendation())
                .pmuRecommendationDate(request.getPmuRecommendationDate())
                .pmuRemarks(request.getPmuRemarks())
                .hoRecommendation(request.getHoRecommendation())
                .hoRecommendationDate(request.getHoRecommendationDate())
                .hoRemarks(request.getHoRemarks())
                .committeeRecommendation(request.getCommitteeRecommendation())
                .committeeDate(request.getCommitteeDate())
                .committeeMom(request.getCommitteeMom())
                .committeeRemarks(request.getCommitteeRemarks())
                .approvedSalary(request.getApprovedSalary())
                .approvedTravelAllowance(request.getApprovedTravelAllowance())
                .dateOfJoining(request.getDateOfJoining())
                .iaMapped(request.getIaMapped())
                .offerLetter(request.getOfferLetter())
                .createdBy(getCurrentUsername())
                .build();

        IndustryAssociationBseRecommendation saved = bseRecommendationRepository.save(bseRecommendation);
        log.info("BSE recommendation created successfully with UUID: {}", saved.getUuid());

        return mapToResponse(saved);
    }

    /**
     * Get all BSE recommendations
     */
    @Transactional(readOnly = true)
    public List<BseRecommendationResponse> getAllBseRecommendations() {
        log.info("Fetching all BSE recommendations");
        return bseRecommendationRepository.findByIsActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Get BSE recommendation by UUID
     */
    @Transactional(readOnly = true)
    public BseRecommendationResponse getBseRecommendationByUuid(UUID uuid) {
        log.info("Fetching BSE recommendation with UUID: {}", uuid);
        IndustryAssociationBseRecommendation bseRecommendation = bseRecommendationRepository
                .findByUuidAndIsActiveTrue(uuid)
                .orElseThrow(() -> new EntityNotFoundException(
                        "BSE Recommendation not found with UUID: " + uuid));

        return mapToResponse(bseRecommendation);
    }

    /**
     * Get BSE recommendations by registration UUID
     */
    @Transactional(readOnly = true)
    public List<BseRecommendationResponse> getBseRecommendationsByRegistration(UUID registrationUuid) {
        log.info("Fetching BSE recommendations for registration: {}", registrationUuid);
        return bseRecommendationRepository.findByRegistrationUuid(registrationUuid)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Update BSE recommendation
     */
    @Transactional
    public BseRecommendationResponse updateBseRecommendation(UUID uuid, UpdateBseRecommendationRequest request) {
        log.info("Updating BSE recommendation with UUID: {}", uuid);

        IndustryAssociationBseRecommendation bseRecommendation = bseRecommendationRepository
                .findByUuidAndIsActiveTrue(uuid)
                .orElseThrow(() -> new EntityNotFoundException(
                        "BSE Recommendation not found with UUID: " + uuid));

        applyBseDetails(request, bseRecommendation);
        applyApprovalWorkflow(request, bseRecommendation);
        applyApprovalDetails(request, bseRecommendation);
        bseRecommendation.setUpdatedBy(getCurrentUsername());

        IndustryAssociationBseRecommendation updated = bseRecommendationRepository.save(bseRecommendation);
        log.info("BSE recommendation updated successfully with UUID: {}", uuid);

        return mapToResponse(updated);
    }

    private void applyBseDetails(UpdateBseRecommendationRequest request, IndustryAssociationBseRecommendation bseRecommendation) {
        applyBasicBseDetails(request, bseRecommendation);
        applyFinancialAndDocumentDetails(request, bseRecommendation);
    }

    private void applyBasicBseDetails(UpdateBseRecommendationRequest request, IndustryAssociationBseRecommendation bseRecommendation) {
        if (request.getState() != null) bseRecommendation.setState(request.getState());
        if (request.getDistrict() != null) bseRecommendation.setDistrict(request.getDistrict());
        if (request.getIndustryRegistrationId() != null) bseRecommendation.setIndustryRegistrationId(request.getIndustryRegistrationId());
        if (request.getBseName() != null) bseRecommendation.setBseName(request.getBseName());
        if (request.getMobileNumber() != null) bseRecommendation.setMobileNumber(request.getMobileNumber());
        if (request.getEmailId() != null) bseRecommendation.setEmailId(request.getEmailId());
        if (request.getHighestQualification() != null) bseRecommendation.setHighestQualification(request.getHighestQualification());
        if (request.getExperienced() != null) bseRecommendation.setExperienced(request.getExperienced());
        if (request.getExperienceYears() != null) bseRecommendation.setExperienceYears(request.getExperienceYears());
        if (request.getExperienceMonths() != null) bseRecommendation.setExperienceMonths(request.getExperienceMonths());
        if (request.getEmploymentStatus() != null) bseRecommendation.setEmploymentStatus(request.getEmploymentStatus());
    }

    private void applyFinancialAndDocumentDetails(UpdateBseRecommendationRequest request, IndustryAssociationBseRecommendation bseRecommendation) {
        if (request.getCurrentSalary() != null) bseRecommendation.setCurrentSalary(request.getCurrentSalary());
        if (request.getNoticePeriodDays() != null) bseRecommendation.setNoticePeriodDays(request.getNoticePeriodDays());
        if (request.getLastDrawnSalary() != null) bseRecommendation.setLastDrawnSalary(request.getLastDrawnSalary());
        if (request.getRelievingLetter() != null) bseRecommendation.setRelievingLetter(request.getRelievingLetter());
        if (request.getExpectedSalary() != null) bseRecommendation.setExpectedSalary(request.getExpectedSalary());
        if (request.getResumeStatus() != null) bseRecommendation.setResumeStatus(request.getResumeStatus());
        if (request.getResumeFile() != null) bseRecommendation.setResumeFile(request.getResumeFile());
        if (request.getSalarySlip() != null) bseRecommendation.setSalarySlip(request.getSalarySlip());
        if (request.getCandidateCv() != null) bseRecommendation.setCandidateCv(request.getCandidateCv());
    }

    private void applyApprovalWorkflow(UpdateBseRecommendationRequest request, IndustryAssociationBseRecommendation bseRecommendation) {
        if (request.getGtRecommendation() != null) bseRecommendation.setGtRecommendation(request.getGtRecommendation());
        if (request.getGtRecommendationDate() != null) bseRecommendation.setGtRecommendationDate(request.getGtRecommendationDate());
        if (request.getGtRemarks() != null) bseRecommendation.setGtRemarks(request.getGtRemarks());
        if (request.getPmuRecommendation() != null) bseRecommendation.setPmuRecommendation(request.getPmuRecommendation());
        if (request.getPmuRecommendationDate() != null) bseRecommendation.setPmuRecommendationDate(request.getPmuRecommendationDate());
        if (request.getPmuRemarks() != null) bseRecommendation.setPmuRemarks(request.getPmuRemarks());
        if (request.getHoRecommendation() != null) bseRecommendation.setHoRecommendation(request.getHoRecommendation());
        if (request.getHoRecommendationDate() != null) bseRecommendation.setHoRecommendationDate(request.getHoRecommendationDate());
        if (request.getHoRemarks() != null) bseRecommendation.setHoRemarks(request.getHoRemarks());
        if (request.getCommitteeRecommendation() != null) bseRecommendation.setCommitteeRecommendation(request.getCommitteeRecommendation());
        if (request.getCommitteeDate() != null) bseRecommendation.setCommitteeDate(request.getCommitteeDate());
        if (request.getCommitteeMom() != null) bseRecommendation.setCommitteeMom(request.getCommitteeMom());
        if (request.getCommitteeRemarks() != null) bseRecommendation.setCommitteeRemarks(request.getCommitteeRemarks());
    }

    private void applyApprovalDetails(UpdateBseRecommendationRequest request, IndustryAssociationBseRecommendation bseRecommendation) {
        if (request.getApprovedSalary() != null) bseRecommendation.setApprovedSalary(request.getApprovedSalary());
        if (request.getApprovedTravelAllowance() != null) bseRecommendation.setApprovedTravelAllowance(request.getApprovedTravelAllowance());
        if (request.getDateOfJoining() != null) bseRecommendation.setDateOfJoining(request.getDateOfJoining());
        if (request.getIaMapped() != null) bseRecommendation.setIaMapped(request.getIaMapped());
        if (request.getOfferLetter() != null) bseRecommendation.setOfferLetter(request.getOfferLetter());
    }

    /**
     * Search BSE recommendations by name
     */
    @Transactional(readOnly = true)
    public List<BseRecommendationResponse> searchByBseName(String bseName) {
        log.info("Searching BSE recommendations by name: {}", bseName);
        return bseRecommendationRepository.findByBseNameContainingIgnoreCaseAndIsActiveTrue(bseName)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Get BSE recommendations by GT recommendation status
     */
    @Transactional(readOnly = true)
    public List<BseRecommendationResponse> getByGtRecommendation(boolean isRecommended) {
        log.info("Fetching BSE recommendations with GT recommendation set");
        List<IndustryAssociationBseRecommendation> bseRecommendationList;
        if (isRecommended) {
            bseRecommendationList = bseRecommendationRepository.findByGtRecommendationIsNotNullAndIsActiveTrue();
        } else {
            bseRecommendationList = bseRecommendationRepository.findByGtRecommendationIsNullAndIsActiveTrue();
        }
        return bseRecommendationList.stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Get BSE recommendations by PMU recommendation status
     */
    @Transactional(readOnly = true)
    public List<BseRecommendationResponse> getByPmuRecommendation(boolean isRecommended) {
        log.info("Fetching BSE recommendations with PMU recommendation set");
        List<IndustryAssociationBseRecommendation> bseRecommendationList;
        if (isRecommended) {
            bseRecommendationList = bseRecommendationRepository.findByPmuRecommendationIsNotNullAndIsActiveTrue();
        } else {
            bseRecommendationList = bseRecommendationRepository.findByPmuRecommendationIsNullAndIsActiveTrue();
        }
        return bseRecommendationList.stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Get BSE recommendations by HO recommendation status
     */
    @Transactional(readOnly = true)
    public List<BseRecommendationResponse> getByHoRecommendation(boolean isRecommended) {
        log.info("Fetching BSE recommendations with HO recommendation set");
        List<BseRecommendationResponse> bseRecommendations;
        List<IndustryAssociationBseRecommendation> bseRecommendationList;
        if(isRecommended) {
            bseRecommendationList = bseRecommendationRepository.findByHoRecommendationIsNotNullAndIsActiveTrue();
        }
        else {
            bseRecommendationList = bseRecommendationRepository.findByHoRecommendationIsNullAndIsActiveTrue();
        }
        bseRecommendations = bseRecommendationList.stream().map(this::mapToResponse).toList();

        return bseRecommendations;
    }

    /**
     * Get mapped/unmapped BSE recommendations
     */
    @Transactional(readOnly = true)
    public List<BseRecommendationResponse> getByMappedStatus(Boolean iaMapped) {
        log.info("Fetching BSE recommendations by mapped status: {}", iaMapped);
        return bseRecommendationRepository.findByIaMappedAndIsActiveTrue(iaMapped)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Map entity to response DTO
     */
    private BseRecommendationResponse mapToResponse(IndustryAssociationBseRecommendation entity) {
        return BseRecommendationResponse.builder()
                .uuid(entity.getUuid())
                .registrationUuid(entity.getRegistration().getUuid())
                .industryAssociationName(entity.getRegistration().getIndustryAssociationName())
                .state(entity.getState())
                .district(entity.getDistrict())
                .industryRegistrationId(entity.getIndustryRegistrationId())
                .bseName(entity.getBseName())
                .mobileNumber(entity.getMobileNumber())
                .emailId(entity.getEmailId())
                .highestQualification(entity.getHighestQualification())
                .experienced(entity.getExperienced())
                .experienceYears(entity.getExperienceYears())
                .experienceMonths(entity.getExperienceMonths())
                .employmentStatus(entity.getEmploymentStatus())
                .currentSalary(entity.getCurrentSalary())
                .noticePeriodDays(entity.getNoticePeriodDays())
                .lastDrawnSalary(entity.getLastDrawnSalary())
                .relievingLetter(entity.getRelievingLetter())
                .expectedSalary(entity.getExpectedSalary())
                .resumeStatus(entity.getResumeStatus())
                .resumeFile(entity.getResumeFile())
                .salarySlip(entity.getSalarySlip())
                .candidateCv(entity.getCandidateCv())
                .gtRecommendation(entity.getGtRecommendation())
                .gtRecommendationDate(entity.getGtRecommendationDate())
                .gtRemarks(entity.getGtRemarks())
                .pmuRecommendation(entity.getPmuRecommendation())
                .pmuRecommendationDate(entity.getPmuRecommendationDate())
                .pmuRemarks(entity.getPmuRemarks())
                .hoRecommendation(entity.getHoRecommendation())
                .hoRecommendationDate(entity.getHoRecommendationDate())
                .hoRemarks(entity.getHoRemarks())
                .committeeRecommendation(entity.getCommitteeRecommendation())
                .committeeDate(entity.getCommitteeDate())
                .committeeMom(entity.getCommitteeMom())
                .committeeRemarks(entity.getCommitteeRemarks())
                .approvedSalary(entity.getApprovedSalary())
                .approvedTravelAllowance(entity.getApprovedTravelAllowance())
                .dateOfJoining(entity.getDateOfJoining())
                .iaMapped(entity.getIaMapped())
                .offerLetter(entity.getOfferLetter())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .isActive(entity.getIsActive())
                .build();
    }

    /**
     * Get current username from security context
     */
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : "system";
    }
}
