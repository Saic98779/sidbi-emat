package org.emat.mapper;

import org.emat.dto.CreateIndustryAssociationAppraisalRequest;
import org.emat.dto.IndustryAssociationAppraisalResponse;
import org.emat.dto.UpdateIndustryAssociationAppraisalRequest;
import org.emat.entity.IndustryAssociationAppraisal;
import org.emat.entity.IndustryAssociationRegistration;
import org.springframework.stereotype.Component;

@Component
public class IndustryAssociationAppraisalMapper {

    public IndustryAssociationAppraisal toEntity(
            CreateIndustryAssociationAppraisalRequest request,
            IndustryAssociationRegistration registration) {
        return IndustryAssociationAppraisal.builder()
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
                .grantProposedCapex(request.getGrantProposedCapex())
                .grantProposedSalary(request.getGrantProposedSalary())
                .grantDetails(request.getGrantDetails())
                .envisagedOutput(request.getEnvisagedOutput())
                .envisagedOutcome(request.getEnvisagedOutcome())
                .build();
    }

    public void applyUpdateRequest(
            IndustryAssociationAppraisal appraisal,
            UpdateIndustryAssociationAppraisalRequest request) {
        if (request.getCibilReportReferenceNo() != null) appraisal.setCibilReportReferenceNo(request.getCibilReportReferenceNo());
        if (request.getCibilReportDate() != null) appraisal.setCibilReportDate(request.getCibilReportDate());
        if (request.getCibilRanking() != null) appraisal.setCibilRanking(request.getCibilRanking());
        if (request.getCibilRemarks() != null) appraisal.setCibilRemarks(request.getCibilRemarks());
        if (request.getNgoDarpanNumber() != null) appraisal.setNgoDarpanNumber(request.getNgoDarpanNumber());
        if (request.getNabardBlacklisted() != null) appraisal.setNabardBlacklisted(request.getNabardBlacklisted());
        if (request.getSmartReportReferenceNo() != null) appraisal.setSmartReportReferenceNo(request.getSmartReportReferenceNo());
        if (request.getSmartReportDate() != null) appraisal.setSmartReportDate(request.getSmartReportDate());
        if (request.getSmartReportRemarks() != null) appraisal.setSmartReportRemarks(request.getSmartReportRemarks());
        if (request.getWebSearchVerified() != null) appraisal.setWebSearchVerified(request.getWebSearchVerified());
        if (request.getWebSearchDocument() != null) appraisal.setWebSearchDocument(request.getWebSearchDocument());
        if (request.getBeneficialOwnerCibilRemarks() != null) appraisal.setBeneficialOwnerCibilRemarks(request.getBeneficialOwnerCibilRemarks());
        if (request.getBeneficialOwnerSmartRemarks() != null) appraisal.setBeneficialOwnerSmartRemarks(request.getBeneficialOwnerSmartRemarks());
        if (request.getMajorSourcesOfIncome() != null) appraisal.setMajorSourcesOfIncome(request.getMajorSourcesOfIncome());
        if (request.getActivitiesLastYear() != null) appraisal.setActivitiesLastYear(request.getActivitiesLastYear());
        if (request.getFormalizationComments() != null) appraisal.setFormalizationComments(request.getFormalizationComments());
        if (request.getReferralArrangementComments() != null) appraisal.setReferralArrangementComments(request.getReferralArrangementComments());
        if (request.getReferralArrangementReady() != null) appraisal.setReferralArrangementReady(request.getReferralArrangementReady());
        if (request.getBseReadinessComments() != null) appraisal.setBseReadinessComments(request.getBseReadinessComments());
        if (request.getBseReadinessReady() != null) appraisal.setBseReadinessReady(request.getBseReadinessReady());
        if (request.getSectors() != null) appraisal.setSectors(request.getSectors());
        if (request.getFinancingScope() != null) appraisal.setFinancingScope(request.getFinancingScope());
        if (request.getFinancingScopeCrore() != null) appraisal.setFinancingScopeCrore(request.getFinancingScopeCrore());
        if (request.getProjectLocation() != null) appraisal.setProjectLocation(request.getProjectLocation());
        if (request.getClusterExpertComments() != null) appraisal.setClusterExpertComments(request.getClusterExpertComments());
        if (request.getBudgetAllocated() != null) appraisal.setBudgetAllocated(request.getBudgetAllocated());
        if (request.getUtilizedAmount() != null) appraisal.setUtilizedAmount(request.getUtilizedAmount());
        if (request.getAvailableBudget() != null) appraisal.setAvailableBudget(request.getAvailableBudget());
        if (request.getTermsAndConditions() != null) appraisal.setTermsAndConditions(request.getTermsAndConditions());
        if (request.getDopDate() != null) appraisal.setDopDate(request.getDopDate());
        if (request.getRecommendation() != null) appraisal.setRecommendation(request.getRecommendation());
        if (request.getRecommendationRemarks() != null) appraisal.setRecommendationRemarks(request.getRecommendationRemarks());
        if (request.getUpdatedBy() != null) appraisal.setUpdatedBy(request.getUpdatedBy());
        if (request.getIsActive() != null) appraisal.setIsActive(request.getIsActive());
        if (request.getNodalName() != null) appraisal.setNodalName(request.getNodalName());
        if (request.getNodalDesignation() != null) appraisal.setNodalDesignation(request.getNodalDesignation());
        if (request.getNodalMobile() != null) appraisal.setNodalMobile(request.getNodalMobile());
        if (request.getNodalEmail() != null) appraisal.setNodalEmail(request.getNodalEmail());
        if (request.getSidbiBranch() != null) appraisal.setSidbiBranch(request.getSidbiBranch());
        if (request.getMappedWithCluster() != null) appraisal.setMappedWithCluster(request.getMappedWithCluster());
        if (request.getClusterName() != null) appraisal.setClusterName(request.getClusterName());
        if (request.getMappedWithImportantDistrict() != null) appraisal.setMappedWithImportantDistrict(request.getMappedWithImportantDistrict());
        if (request.getDistrictMsmeCount() != null) appraisal.setDistrictMsmeCount(request.getDistrictMsmeCount());
        if (request.getActiveMembersAbove200() != null) appraisal.setActiveMembersAbove200(request.getActiveMembersAbove200());
        if (request.getActiveMembersCount() != null) appraisal.setActiveMembersCount(request.getActiveMembersCount());
        if (request.getJustification() != null) appraisal.setJustification(request.getJustification());
        if (request.getApprovalLetter() != null) appraisal.setApprovalLetter(request.getApprovalLetter());
        if (request.getMsmeCountWithoutTraders() != null) appraisal.setMsmeCountWithoutTraders(request.getMsmeCountWithoutTraders());
        if (request.getMemberDirectoryAvailable() != null) appraisal.setMemberDirectoryAvailable(request.getMemberDirectoryAvailable());
        if (request.getBuildingType() != null) appraisal.setBuildingType(request.getBuildingType());
        if (request.getDeclarationSigned() != null) appraisal.setDeclarationSigned(request.getDeclarationSigned());
        if (request.getElectricityBill() != null) appraisal.setElectricityBill(request.getElectricityBill());
        if (request.getTelephoneBill() != null) appraisal.setTelephoneBill(request.getTelephoneBill());
        if (request.getItInfrastructureAvailable() != null) appraisal.setItInfrastructureAvailable(request.getItInfrastructureAvailable());
        if (request.getInfrastructureType() != null) appraisal.setInfrastructureType(request.getInfrastructureType());
        if (request.getSecretariatStaffAvailable() != null) appraisal.setSecretariatStaffAvailable(request.getSecretariatStaffAvailable());
        if (request.getWebsiteAvailable() != null) appraisal.setWebsiteAvailable(request.getWebsiteAvailable());
        if (request.getWebsiteUrl() != null) appraisal.setWebsiteUrl(request.getWebsiteUrl());
        if (request.getPaidServicesAvailable() != null) appraisal.setPaidServicesAvailable(request.getPaidServicesAvailable());
        if (request.getAdverseRemarksAvailable() != null) appraisal.setAdverseRemarksAvailable(request.getAdverseRemarksAvailable());
        if (request.getAdverseRemarks() != null) appraisal.setAdverseRemarks(request.getAdverseRemarks());
        if (request.getWebReport() != null) appraisal.setWebReport(request.getWebReport());
        if (request.getWillingnessComments() != null) appraisal.setWillingnessComments(request.getWillingnessComments());
        if (request.getWorkedWithSidbiBefore() != null) appraisal.setWorkedWithSidbiBefore(request.getWorkedWithSidbiBefore());
        if (request.getGrantProposedSalary() != null) appraisal.setGrantProposedCapex(request.getGrantProposedSalary());
        if (request.getGrantDetails() != null) appraisal.setGrantDetails(request.getGrantDetails());
        if (request.getEnvisagedOutput() != null) appraisal.setEnvisagedOutput(request.getEnvisagedOutput());
        if (request.getEnvisagedOutcome() != null) appraisal.setEnvisagedOutcome(request.getEnvisagedOutcome());
        if (request.getFinancialYear() != null) appraisal.setFinancialYear(request.getFinancialYear());
    }

    public IndustryAssociationAppraisalResponse toResponse(IndustryAssociationAppraisal appraisal) {
        return IndustryAssociationAppraisalResponse.builder()
                .id(appraisal.getId())
                .registrationId(appraisal.getRegistration().getId())
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
                .sidbeApprovedByUserId(appraisal.getSidbeApprovedByUser() != null ? appraisal.getSidbeApprovedByUser().getId() : null)
                .sidbeApprovedByUsername(appraisal.getSidbeApprovedByUser() != null ? appraisal.getSidbeApprovedByUser().getUsername() : null)
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
                .grantProposedCapex(appraisal.getGrantProposedCapex())
                .grantProposedSalary(appraisal.getGrantProposedSalary())
                .grantDetails(appraisal.getGrantDetails())
                .envisagedOutput(appraisal.getEnvisagedOutput())
                .envisagedOutcome(appraisal.getEnvisagedOutcome())
                .build();
    }
}

