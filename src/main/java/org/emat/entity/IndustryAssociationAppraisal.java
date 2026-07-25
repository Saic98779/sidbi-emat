package org.emat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Entity representing Industry Association Appraisal.
 * Maps to the INDUSTRY_ASSOCIATION_APPRAISAL table in Oracle database.
 * Has a 1:1 relationship with IndustryAssociationRegistration.
 */
@Entity
@Table(name = "INDUSTRY_ASSOCIATION_APPRAISAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndustryAssociationAppraisal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID", updatable = false, nullable = false)
    private UUID uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGISTRATION_UUID", nullable = false, unique = true)
    private IndustryAssociationRegistration registration;

    // Due Diligence
    @Column(name = "CIBIL_REPORT_REFERENCE_NO", length = 200)
    private String cibilReportReferenceNo;

    @Column(name = "CIBIL_REPORT_DATE")
    private LocalDate cibilReportDate;

    @Column(name = "CIBIL_RANKING", length = 100)
    private String cibilRanking;

    @Column(name = "CIBIL_REMARKS", length = 1000)
    private String cibilRemarks;

    @Column(name = "NGO_DARPAN_NUMBER", length = 200)
    private String ngoDarpanNumber;

    @Column(name = "NABARD_BLACKLISTED")
    private Boolean nabardBlacklisted;

    @Column(name = "SMART_REPORT_REFERENCE_NO", length = 200)
    private String smartReportReferenceNo;

    @Column(name = "SMART_REPORT_DATE")
    private LocalDate smartReportDate;

    @Column(name = "SMART_REPORT_REMARKS", length = 1000)
    private String smartReportRemarks;

    @Column(name = "WEB_SEARCH_VERIFIED")
    private Boolean webSearchVerified;

    @Column(name = "WEB_SEARCH_DOCUMENT", length = 500)
    private String webSearchDocument;

    // Beneficial Owners
    @Column(name = "BENEFICIAL_OWNER_CIBIL_REMARKS", length = 1000)
    private String beneficialOwnerCibilRemarks;

    @Column(name = "BENEFICIAL_OWNER_SMART_REMARKS", length = 1000)
    private String beneficialOwnerSmartRemarks;

    // Infrastructure
    @Column(name = "MAJOR_SOURCES_OF_INCOME", length = 1000)
    private String majorSourcesOfIncome;

    @Column(name = "ACTIVITIES_LAST_YEAR", length = 2000)
    private String activitiesLastYear;

    // DIA Details
    @Column(name = "FORMALIZATION_COMMENTS", length = 1000)
    private String formalizationComments;

    @Column(name = "REFERRAL_ARRANGEMENT_COMMENTS", length = 1000)
    private String referralArrangementComments;

    @Column(name = "BSE_READINESS_COMMENTS", length = 1000)
    private String bseReadinessComments;

    @ElementCollection
    @CollectionTable(
            name = "IA_APPRAISAL_TOP_SECTORS",
            joinColumns = @JoinColumn(name = "APPRAISAL_UUID"))
    @Column(name = "SECTOR_NAME", length = 200)
    private List<String> topThreeSectors;

    @Column(name = "FINANCING_SCOPE", length = 500)
    private String financingScope;

    @Column(name = "PROJECT_LOCATION", length = 500)
    private String projectLocation;

    // Cluster Expert
    @Column(name = "CLUSTER_EXPERT_COMMENTS", length = 2000)
    private String clusterExpertComments;

    // Budget
    @Column(name = "BUDGET_ALLOCATED", precision = 15, scale = 2)
    private BigDecimal budgetAllocated;

    @Column(name = "UTILIZED_AMOUNT", precision = 15, scale = 2)
    private BigDecimal utilizedAmount;

    @Column(name = "AVAILABLE_BUDGET", precision = 15, scale = 2)
    private BigDecimal availableBudget;

    // Terms
    @Column(name = "TERMS_AND_CONDITIONS", length = 2000)
    private String termsAndConditions;

    // DoP (Date of Presentation)
    @Column(name = "DOP_DATE")
    private LocalDate dopDate;

    // Recommendation
    @Column(name = "RECOMMENDATION", length = 100)
    private String recommendation;

    @Column(name = "RECOMMENDATION_REMARKS", length = 2000)
    private String recommendationRemarks;

    // Audit Fields
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "CREATED_BY", length = 100)
    private String createdBy;

    @Column(name = "UPDATED_BY", length = 100)
    private String updatedBy;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive = true;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isActive == null) {
            isActive = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

