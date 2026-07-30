package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for creating a new Industry Association Appraisal.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateIndustryAssociationAppraisalRequest {

    private String registrationUuid;

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
    private String createdBy;
}

