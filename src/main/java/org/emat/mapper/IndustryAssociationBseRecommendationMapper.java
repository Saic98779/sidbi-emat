package org.emat.mapper;

import org.emat.dto.BseRecommendationResponse;
import org.emat.dto.CreateBseRecommendationRequest;
import org.emat.dto.UpdateBseRecommendationRequest;
import org.emat.entity.IndustryAssociationBseRecommendation;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.entity.User;
import org.springframework.stereotype.Component;

@Component
public class IndustryAssociationBseRecommendationMapper {

    public IndustryAssociationBseRecommendation toEntity(
            CreateBseRecommendationRequest request,
            IndustryAssociationRegistration registration,
            User user,
            String createdBy) {
        return IndustryAssociationBseRecommendation.builder()
                .registration(registration)
                .user(user)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .iaSelected(request.getIaSelected())
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
                .createdBy(createdBy)
                .build();
    }

    public void applyUpdateRequest(
            UpdateBseRecommendationRequest request,
            IndustryAssociationBseRecommendation bseRecommendation,
            User user,
            String updatedBy) {
        applyBasicBseDetails(request, bseRecommendation);
        applyFinancialAndDocumentDetails(request, bseRecommendation);
        applyApprovalWorkflow(request, bseRecommendation);
        applyApprovalDetails(request, bseRecommendation);

        bseRecommendation.setUpdatedBy(updatedBy);
        bseRecommendation.setUser(user);
        bseRecommendation.setIaSelected(request.getIaSelected());
        if (request.getLatitude() != null) bseRecommendation.setLatitude(request.getLatitude());
        if (request.getLongitude() != null) bseRecommendation.setLongitude(request.getLongitude());
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

    public BseRecommendationResponse toResponse(IndustryAssociationBseRecommendation entity) {
        return BseRecommendationResponse.builder()
                .id(entity.getId())
                .registrationId(entity.getRegistration().getId())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
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
                .iaSelected(entity.getIaSelected())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .userName(entity.getUser() != null ? entity.getUser().getUsername() : null)
                .build();
    }
}

