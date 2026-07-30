package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * DTO for updating an existing Industry Association Appraisal.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateIndustryAssociationAppraisalRequest {

    // Due Diligence
    private String cibilReportReferenceNo;
    private LocalDate cibilReportDate;
    private String cibilRanking;
    private String cibilRemarks;
    private String ngoDarpanNumber;
    private Boolean nabardBlacklisted;
    private String smartReportReferenceNo;
    private LocalDate smartReportDate;
    private String smartReportRemarks;
    private Boolean webSearchVerified;
    private String webSearchDocument;

    // Beneficial Owners
    private String beneficialOwnerCibilRemarks;
    private String beneficialOwnerSmartRemarks;

    // Infrastructure
    private String majorSourcesOfIncome;
    private String activitiesLastYear;

    // DIA Details
    private String formalizationComments;
    private String referralArrangementComments;
    private Boolean referralArrangementReady;
    private String bseReadinessComments;
    private Boolean bseReadinessReady;
    private List<String> topThreeSectors;
    private LocalDate financialYear;
    private String financingScope;
    private BigDecimal financingScopeCrore;
    private String projectLocation;

    // Cluster Expert
    private String clusterExpertComments;

    // Budget
    private BigDecimal budgetAllocated;
    private BigDecimal utilizedAmount;
    private BigDecimal availableBudget;

    // Terms
    private String termsAndConditions;

    // DoP
    private LocalDate dopDate;

    // Recommendation
    private String recommendation;
    private String recommendationRemarks;

    // Audit
    private String updatedBy;
    private Boolean isActive;

    // Apex Holder Information (KYC)
    private String apexHolderName;
    private String apexHolderDesignation;
    private String apexHolderMobile;
    private String apexHolderEmail;
    private String addressProofType;
    private String addressProof;
    private String idProofType;
    private String idProof;

    // Nodal Contact Information
    private String nodalName;
    private String nodalDesignation;
    private String nodalMobile;
    private String nodalEmail;

    // SIDBI Details
    private String sidbiBranch;

    // Cluster Details
    private Boolean mappedWithCluster;
    private String clusterName;
    private Boolean mappedWithImportantDistrict;
    private Integer districtMsmeCount;

    // Existing Infrastructure
    private Boolean activeMembersAbove200;
    private Integer activeMembersCount;

    // Documentation and Justification
    private String justification;
    private String approvalLetter;
    private Integer msmeCountWithoutTraders;

    // Infrastructure & Services
    private Boolean memberDirectoryAvailable;
    private String buildingType;
    private Boolean declarationSigned;
    private String electricityBill;
    private String telephoneBill;
    private Boolean itInfrastructureAvailable;
    private String infrastructureType;
    private Boolean secretariatStaffAvailable;
    private Boolean websiteAvailable;
    private String websiteUrl;
    private Boolean paidServicesAvailable;
    private Boolean adverseRemarksAvailable;
    private String adverseRemarks;
    private String webReport;

    // Willingness & Output
    private String willingnessComments;
    private Boolean workedWithSidbiBefore;

    // Grant Details
    private BigDecimal grantProposed;
    private String grantDetails;

    // Envisaged Outputs, Outcomes, and Impacts
    private String envisagedOutput;
    private String envisagedOutcome;
    private String envisagedImpact;
}

