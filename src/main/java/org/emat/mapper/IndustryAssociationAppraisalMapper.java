package org.emat.mapper;

import java.util.ArrayList;
import java.util.List;
import org.emat.dto.AnnexureVResponse;
import org.emat.dto.AnnexureVIResponse;
import org.emat.dto.CreateAnnexureVRequest;
import org.emat.dto.CreateAnnexureVIRequest;
import org.emat.dto.CreateIndustryAssociationAppraisalRequest;
import org.emat.dto.IndustryAssociationAppraisalResponse;
import org.emat.dto.UpdateAnnexureVRequest;
import org.emat.dto.UpdateAnnexureVIRequest;
import org.emat.dto.UpdateIndustryAssociationAppraisalRequest;
import org.emat.entity.AnnexureV;
import org.emat.entity.AnnexureVI;
import org.emat.entity.IndustryAssociationAppraisal;
import org.emat.entity.IndustryAssociationRegistration;
import org.springframework.stereotype.Component;

@Component
public class IndustryAssociationAppraisalMapper {

    public IndustryAssociationAppraisal toEntity(
            CreateIndustryAssociationAppraisalRequest request,
            IndustryAssociationRegistration registration) {
        IndustryAssociationAppraisal appraisal =
                IndustryAssociationAppraisal.builder()
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
                .grantProposedCapacityBuilding(request.getGrantProposedCapacityBuilding())
                .grantDetails(request.getGrantDetails())
                .envisagedOutput(request.getEnvisagedOutput())
                .envisagedOutcome(request.getEnvisagedOutcome())
                .holderCibilReferenceNo(request.getHolderCibilReferenceNo())
                .holderCibilDate(request.getHolderCibilDate())
                .holderCibilScore(request.getHolderCibilScore())
                .holderCibilRemarks(request.getHolderCibilRemarks())
                .holderCibilFile(request.getHolderCibilFile())
                .holderSmartAvailable(request.getHolderSmartAvailable())
                .holderSmartDate(request.getHolderSmartDate())
                .holderSmartRemarks(request.getHolderSmartRemarks())
                .beneficialOwnerCibilReferenceNo(request.getBeneficialOwnerCibilReferenceNo())
                .beneficialOwnerCibilDate(request.getBeneficialOwnerCibilDate())
                .beneficialOwnerCibilRanking(request.getBeneficialOwnerCibilRanking())
                .beneficialOwnerCibilFile(request.getBeneficialOwnerCibilFile())
                .beneficialOwnerSmartAvailable(request.getBeneficialOwnerSmartAvailable())
                .beneficialOwnerSmartDate(request.getBeneficialOwnerSmartDate())
                .paidServicesDetails(request.getPaidServicesDetails())
                .smartReportAvailable(request.getSmartReportAvailable())
                .ngoDarpanFile(request.getNgoDarpanFile())
                .nabardBlacklistFile(request.getNabardBlacklistFile())
                .build();
        appraisal.setAnnexureVList(mapCreateAnnexureV(request.getAnnexureVList(), appraisal));
        appraisal.setAnnexureVIList(mapCreateAnnexureVI(request.getAnnexureVIList(), appraisal));
        return appraisal;
    }

    public List<AnnexureV> mapCreateAnnexureV(
            List<CreateAnnexureVRequest> requests, IndustryAssociationAppraisal parent) {
        if (requests == null) {
            return new ArrayList<>();
        }
        return requests.stream().map(r -> toAnnexureVEntity(r, parent)).toList();
    }

    private AnnexureV toAnnexureVEntity(
            CreateAnnexureVRequest request, IndustryAssociationAppraisal parent) {
        return AnnexureV.builder()
                .appraisal(parent)
                .snNo(request.getSnNo())
                .particulars(request.getParticulars())
                .totalCost(request.getTotalCost())
                .sidbiSupport(request.getSidbiSupport())
                .build();
    }

    public List<AnnexureVI> mapCreateAnnexureVI(
            List<CreateAnnexureVIRequest> requests, IndustryAssociationAppraisal parent) {
        if (requests == null) {
            return new ArrayList<>();
        }
        return requests.stream().map(r -> toAnnexureVIEntity(r, parent)).toList();
    }

    private AnnexureVI toAnnexureVIEntity(
            CreateAnnexureVIRequest request, IndustryAssociationAppraisal parent) {
        return AnnexureVI.builder()
                .appraisal(parent)
                .section(request.getSection())
                .sectionNote(request.getSectionNote())
                .indicativeItem(request.getIndicativeItem())
                .numbers(request.getNumbers())
                .make(request.getMake())
                .maximumCost(request.getMaximumCost())
                .maximumCostUnit(request.getMaximumCostUnit())
                .build();
    }

    public void applyUpdateRequest(
            IndustryAssociationAppraisal appraisal,
            UpdateIndustryAssociationAppraisalRequest request) {
        if (request.getCibilReportReferenceNo() != null)
            appraisal.setCibilReportReferenceNo(request.getCibilReportReferenceNo());
        if (request.getCibilReportDate() != null)
            appraisal.setCibilReportDate(request.getCibilReportDate());
        if (request.getCibilRanking() != null) appraisal.setCibilRanking(request.getCibilRanking());
        if (request.getCibilRemarks() != null) appraisal.setCibilRemarks(request.getCibilRemarks());
        if (request.getNgoDarpanNumber() != null)
            appraisal.setNgoDarpanNumber(request.getNgoDarpanNumber());
        if (request.getNabardBlacklisted() != null)
            appraisal.setNabardBlacklisted(request.getNabardBlacklisted());
        if (request.getSmartReportReferenceNo() != null)
            appraisal.setSmartReportReferenceNo(request.getSmartReportReferenceNo());
        if (request.getSmartReportDate() != null)
            appraisal.setSmartReportDate(request.getSmartReportDate());
        if (request.getSmartReportRemarks() != null)
            appraisal.setSmartReportRemarks(request.getSmartReportRemarks());
        if (request.getWebSearchVerified() != null)
            appraisal.setWebSearchVerified(request.getWebSearchVerified());
        if (request.getWebSearchDocument() != null)
            appraisal.setWebSearchDocument(request.getWebSearchDocument());
        if (request.getBeneficialOwnerCibilRemarks() != null)
            appraisal.setBeneficialOwnerCibilRemarks(request.getBeneficialOwnerCibilRemarks());
        if (request.getBeneficialOwnerSmartRemarks() != null)
            appraisal.setBeneficialOwnerSmartRemarks(request.getBeneficialOwnerSmartRemarks());
        if (request.getMajorSourcesOfIncome() != null)
            appraisal.setMajorSourcesOfIncome(request.getMajorSourcesOfIncome());
        if (request.getActivitiesLastYear() != null)
            appraisal.setActivitiesLastYear(request.getActivitiesLastYear());
        if (request.getFormalizationComments() != null)
            appraisal.setFormalizationComments(request.getFormalizationComments());
        if (request.getReferralArrangementComments() != null)
            appraisal.setReferralArrangementComments(request.getReferralArrangementComments());
        if (request.getReferralArrangementReady() != null)
            appraisal.setReferralArrangementReady(request.getReferralArrangementReady());
        if (request.getBseReadinessComments() != null)
            appraisal.setBseReadinessComments(request.getBseReadinessComments());
        if (request.getBseReadinessReady() != null)
            appraisal.setBseReadinessReady(request.getBseReadinessReady());
        if (request.getSectors() != null) appraisal.setSectors(request.getSectors());
        if (request.getFinancingScope() != null)
            appraisal.setFinancingScope(request.getFinancingScope());
        if (request.getFinancingScopeCrore() != null)
            appraisal.setFinancingScopeCrore(request.getFinancingScopeCrore());
        if (request.getProjectLocation() != null)
            appraisal.setProjectLocation(request.getProjectLocation());
        if (request.getClusterExpertComments() != null)
            appraisal.setClusterExpertComments(request.getClusterExpertComments());
        if (request.getBudgetAllocated() != null)
            appraisal.setBudgetAllocated(request.getBudgetAllocated());
        if (request.getUtilizedAmount() != null)
            appraisal.setUtilizedAmount(request.getUtilizedAmount());
        if (request.getAvailableBudget() != null)
            appraisal.setAvailableBudget(request.getAvailableBudget());
        if (request.getTermsAndConditions() != null)
            appraisal.setTermsAndConditions(request.getTermsAndConditions());
        if (request.getDopDate() != null) appraisal.setDopDate(request.getDopDate());
        if (request.getRecommendation() != null)
            appraisal.setRecommendation(request.getRecommendation());
        if (request.getRecommendationRemarks() != null)
            appraisal.setRecommendationRemarks(request.getRecommendationRemarks());
        if (request.getUpdatedBy() != null) appraisal.setUpdatedBy(request.getUpdatedBy());
        if (request.getIsActive() != null) appraisal.setIsActive(request.getIsActive());
        if (request.getNodalName() != null) appraisal.setNodalName(request.getNodalName());
        if (request.getNodalDesignation() != null)
            appraisal.setNodalDesignation(request.getNodalDesignation());
        if (request.getNodalMobile() != null) appraisal.setNodalMobile(request.getNodalMobile());
        if (request.getNodalEmail() != null) appraisal.setNodalEmail(request.getNodalEmail());
        if (request.getSidbiBranch() != null) appraisal.setSidbiBranch(request.getSidbiBranch());
        if (request.getMappedWithCluster() != null)
            appraisal.setMappedWithCluster(request.getMappedWithCluster());
        if (request.getClusterName() != null) appraisal.setClusterName(request.getClusterName());
        if (request.getMappedWithImportantDistrict() != null)
            appraisal.setMappedWithImportantDistrict(request.getMappedWithImportantDistrict());
        if (request.getDistrictMsmeCount() != null)
            appraisal.setDistrictMsmeCount(request.getDistrictMsmeCount());
        if (request.getActiveMembersAbove200() != null)
            appraisal.setActiveMembersAbove200(request.getActiveMembersAbove200());
        if (request.getActiveMembersCount() != null)
            appraisal.setActiveMembersCount(request.getActiveMembersCount());
        if (request.getJustification() != null)
            appraisal.setJustification(request.getJustification());
        if (request.getApprovalLetter() != null)
            appraisal.setApprovalLetter(request.getApprovalLetter());
        if (request.getMsmeCountWithoutTraders() != null)
            appraisal.setMsmeCountWithoutTraders(request.getMsmeCountWithoutTraders());
        if (request.getMemberDirectoryAvailable() != null)
            appraisal.setMemberDirectoryAvailable(request.getMemberDirectoryAvailable());
        if (request.getBuildingType() != null) appraisal.setBuildingType(request.getBuildingType());
        if (request.getDeclarationSigned() != null)
            appraisal.setDeclarationSigned(request.getDeclarationSigned());
        if (request.getElectricityBill() != null)
            appraisal.setElectricityBill(request.getElectricityBill());
        if (request.getTelephoneBill() != null)
            appraisal.setTelephoneBill(request.getTelephoneBill());
        if (request.getItInfrastructureAvailable() != null)
            appraisal.setItInfrastructureAvailable(request.getItInfrastructureAvailable());
        if (request.getInfrastructureType() != null)
            appraisal.setInfrastructureType(request.getInfrastructureType());
        if (request.getSecretariatStaffAvailable() != null)
            appraisal.setSecretariatStaffAvailable(request.getSecretariatStaffAvailable());
        if (request.getWebsiteAvailable() != null)
            appraisal.setWebsiteAvailable(request.getWebsiteAvailable());
        if (request.getWebsiteUrl() != null) appraisal.setWebsiteUrl(request.getWebsiteUrl());
        if (request.getPaidServicesAvailable() != null)
            appraisal.setPaidServicesAvailable(request.getPaidServicesAvailable());
        if (request.getAdverseRemarksAvailable() != null)
            appraisal.setAdverseRemarksAvailable(request.getAdverseRemarksAvailable());
        if (request.getAdverseRemarks() != null)
            appraisal.setAdverseRemarks(request.getAdverseRemarks());
        if (request.getWebReport() != null) appraisal.setWebReport(request.getWebReport());
        if (request.getWillingnessComments() != null)
            appraisal.setWillingnessComments(request.getWillingnessComments());
        if (request.getWorkedWithSidbiBefore() != null)
            appraisal.setWorkedWithSidbiBefore(request.getWorkedWithSidbiBefore());
        if (request.getGrantProposedCapex() != null)
            appraisal.setGrantProposedCapex(request.getGrantProposedCapex());
        if (request.getGrantProposedSalary() != null)
            appraisal.setGrantProposedSalary(request.getGrantProposedSalary());
        if (request.getGrantProposedCapacityBuilding() != null)
            appraisal.setGrantProposedCapacityBuilding(request.getGrantProposedCapacityBuilding());
        if (request.getGrantDetails() != null) appraisal.setGrantDetails(request.getGrantDetails());
        if (request.getEnvisagedOutput() != null)
            appraisal.setEnvisagedOutput(request.getEnvisagedOutput());
        if (request.getEnvisagedOutcome() != null)
            appraisal.setEnvisagedOutcome(request.getEnvisagedOutcome());
        if (request.getFinancialYear() != null)
            appraisal.setFinancialYear(request.getFinancialYear());
        if (request.getHolderCibilReferenceNo() != null)
            appraisal.setHolderCibilReferenceNo(request.getHolderCibilReferenceNo());
        if (request.getHolderCibilDate() != null)
            appraisal.setHolderCibilDate(request.getHolderCibilDate());
        if (request.getHolderCibilScore() != null)
            appraisal.setHolderCibilScore(request.getHolderCibilScore());
        if (request.getHolderCibilRemarks() != null)
            appraisal.setHolderCibilRemarks(request.getHolderCibilRemarks());
        if (request.getHolderCibilFile() != null)
            appraisal.setHolderCibilFile(request.getHolderCibilFile());
        if (request.getHolderSmartAvailable() != null)
            appraisal.setHolderSmartAvailable(request.getHolderSmartAvailable());
        if (request.getHolderSmartDate() != null)
            appraisal.setHolderSmartDate(request.getHolderSmartDate());
        if (request.getHolderSmartRemarks() != null)
            appraisal.setHolderSmartRemarks(request.getHolderSmartRemarks());
        if (request.getBeneficialOwnerCibilReferenceNo() != null)
            appraisal.setBeneficialOwnerCibilReferenceNo(
                    request.getBeneficialOwnerCibilReferenceNo());
        if (request.getBeneficialOwnerCibilDate() != null)
            appraisal.setBeneficialOwnerCibilDate(request.getBeneficialOwnerCibilDate());
        if (request.getBeneficialOwnerCibilRanking() != null)
            appraisal.setBeneficialOwnerCibilRanking(request.getBeneficialOwnerCibilRanking());
        if (request.getBeneficialOwnerCibilFile() != null)
            appraisal.setBeneficialOwnerCibilFile(request.getBeneficialOwnerCibilFile());
        if (request.getBeneficialOwnerSmartAvailable() != null)
            appraisal.setBeneficialOwnerSmartAvailable(request.getBeneficialOwnerSmartAvailable());
        if (request.getBeneficialOwnerSmartDate() != null)
            appraisal.setBeneficialOwnerSmartDate(request.getBeneficialOwnerSmartDate());
        if (request.getPaidServicesDetails() != null)
            appraisal.setPaidServicesDetails(request.getPaidServicesDetails());
        if (request.getSmartReportAvailable() != null)
            appraisal.setSmartReportAvailable(request.getSmartReportAvailable());
        if (request.getNgoDarpanFile() != null)
            appraisal.setNgoDarpanFile(request.getNgoDarpanFile());
        if (request.getNabardBlacklistFile() != null)
            appraisal.setNabardBlacklistFile(request.getNabardBlacklistFile());
        if (request.getAnnexureVList() != null) {
            if (appraisal.getAnnexureVList() == null) {
                appraisal.setAnnexureVList(new ArrayList<>());
            } else {
                appraisal.getAnnexureVList().clear();
            }
            appraisal.getAnnexureVList()
                    .addAll(mapUpdateAnnexureV(request.getAnnexureVList(), appraisal));
        }
        if (request.getAnnexureVIList() != null) {
            if (appraisal.getAnnexureVIList() == null) {
                appraisal.setAnnexureVIList(new ArrayList<>());
            } else {
                appraisal.getAnnexureVIList().clear();
            }
            appraisal.getAnnexureVIList()
                    .addAll(mapUpdateAnnexureVI(request.getAnnexureVIList(), appraisal));
        }
    }

    public List<AnnexureV> mapUpdateAnnexureV(
            List<UpdateAnnexureVRequest> requests, IndustryAssociationAppraisal parent) {
        if (requests == null) {
            return new ArrayList<>();
        }
        return requests.stream().map(r -> toAnnexureVEntity(r, parent)).toList();
    }

    private AnnexureV toAnnexureVEntity(
            UpdateAnnexureVRequest request, IndustryAssociationAppraisal parent) {
        return AnnexureV.builder()
                .appraisal(parent)
                .snNo(request.getSnNo())
                .particulars(request.getParticulars())
                .totalCost(request.getTotalCost())
                .sidbiSupport(request.getSidbiSupport())
                .build();
    }

    public List<AnnexureVI> mapUpdateAnnexureVI(
            List<UpdateAnnexureVIRequest> requests, IndustryAssociationAppraisal parent) {
        if (requests == null) {
            return new ArrayList<>();
        }
        return requests.stream().map(r -> toAnnexureVIEntity(r, parent)).toList();
    }

    private AnnexureVI toAnnexureVIEntity(
            UpdateAnnexureVIRequest request, IndustryAssociationAppraisal parent) {
        return AnnexureVI.builder()
                .appraisal(parent)
                .section(request.getSection())
                .sectionNote(request.getSectionNote())
                .indicativeItem(request.getIndicativeItem())
                .numbers(request.getNumbers())
                .make(request.getMake())
                .maximumCost(request.getMaximumCost())
                .maximumCostUnit(request.getMaximumCostUnit())
                .build();
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
                .annexureVList(toAnnexureVResponses(appraisal.getAnnexureVList()))
                .annexureVIList(toAnnexureVIResponses(appraisal.getAnnexureVIList()))
                .dopDate(appraisal.getDopDate())
                .recommendation(appraisal.getRecommendation())
                .recommendationRemarks(appraisal.getRecommendationRemarks())
                .isSidbeApproved(appraisal.getIsSidbeApproved())
                .sidbeApprovedByUserId(
                        appraisal.getSidbeApprovedByUser() != null
                                ? appraisal.getSidbeApprovedByUser().getId()
                                : null)
                .sidbeApprovedByUsername(
                        appraisal.getSidbeApprovedByUser() != null
                                ? appraisal.getSidbeApprovedByUser().getUsername()
                                : null)
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
                .grantProposedCapacityBuilding(appraisal.getGrantProposedCapacityBuilding())
                .grantDetails(appraisal.getGrantDetails())
                .envisagedOutput(appraisal.getEnvisagedOutput())
                .envisagedOutcome(appraisal.getEnvisagedOutcome())
                .holderCibilReferenceNo(appraisal.getHolderCibilReferenceNo())
                .holderCibilDate(appraisal.getHolderCibilDate())
                .holderCibilScore(appraisal.getHolderCibilScore())
                .holderCibilRemarks(appraisal.getHolderCibilRemarks())
                .holderCibilFile(appraisal.getHolderCibilFile())
                .holderSmartAvailable(appraisal.getHolderSmartAvailable())
                .holderSmartDate(appraisal.getHolderSmartDate())
                .holderSmartRemarks(appraisal.getHolderSmartRemarks())
                .beneficialOwnerCibilReferenceNo(appraisal.getBeneficialOwnerCibilReferenceNo())
                .beneficialOwnerCibilDate(appraisal.getBeneficialOwnerCibilDate())
                .beneficialOwnerCibilRanking(appraisal.getBeneficialOwnerCibilRanking())
                .beneficialOwnerCibilFile(appraisal.getBeneficialOwnerCibilFile())
                .beneficialOwnerSmartAvailable(appraisal.getBeneficialOwnerSmartAvailable())
                .beneficialOwnerSmartDate(appraisal.getBeneficialOwnerSmartDate())
                .paidServicesDetails(appraisal.getPaidServicesDetails())
                .smartReportAvailable(appraisal.getSmartReportAvailable())
                .ngoDarpanFile(appraisal.getNgoDarpanFile())
                .nabardBlacklistFile(appraisal.getNabardBlacklistFile())
                .build();
    }

    private List<AnnexureVResponse> toAnnexureVResponses(List<AnnexureV> annexures) {
        if (annexures == null) {
            return new ArrayList<>();
        }
        return annexures.stream()
                .map(
                        annexure ->
                                AnnexureVResponse.builder()
                                        .id(annexure.getId())
                                        .snNo(annexure.getSnNo())
                                        .particulars(annexure.getParticulars())
                                        .totalCost(annexure.getTotalCost())
                                        .sidbiSupport(annexure.getSidbiSupport())
                                        .build())
                .toList();
    }

    private List<AnnexureVIResponse> toAnnexureVIResponses(List<AnnexureVI> annexures) {
        if (annexures == null) {
            return new ArrayList<>();
        }
        return annexures.stream()
                .map(
                        annexure ->
                                AnnexureVIResponse.builder()
                                        .id(annexure.getId())
                                        .section(annexure.getSection())
                                        .sectionNote(annexure.getSectionNote())
                                        .indicativeItem(annexure.getIndicativeItem())
                                        .numbers(annexure.getNumbers())
                                        .make(annexure.getMake())
                                        .maximumCost(annexure.getMaximumCost())
                                        .maximumCostUnit(annexure.getMaximumCostUnit())
                                        .build())
                .toList();
    }
}
