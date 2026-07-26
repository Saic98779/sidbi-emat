package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for Industry Association Appraisal response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndustryAssociationAppraisalResponse {

    private String uuid;
    private String registrationUuid;
    private String registrationName;

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
    private String bseReadinessComments;
    private List<String> topThreeSectors;
    private String financingScope;
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

    // SIDBE Approval
    private Boolean isSidbeApproved;
    private Long sidbeApprovedByUserId;
    private String sidbeApprovedByUsername;

    // Audit Fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
}

