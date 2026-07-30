package org.emat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Entity representing Industry Association Appraisal.
 * Maps to the INDUSTRY_ASSOCIATION_APPRAISAL table in Oracle database.
 * Has a 1:1 relationship with IndustryAssociationRegistration.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "INDUSTRY_ASSOCIATION_APPRAISAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class IndustryAssociationAppraisal extends BaseEntity {

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

    @Column(name = "REFERRAL_ARRANGEMENT_READY")
    private Boolean referralArrangementReady;

    @Column(name = "BSE_READINESS_COMMENTS", length = 1000)
    private String bseReadinessComments;

    @Column(name = "BSE_READINESS_READY")
    private Boolean bseReadinessReady;

    @ElementCollection
    @CollectionTable(
            name = "IA_APPRAISAL_TOP_SECTORS",
            joinColumns = @JoinColumn(name = "APPRAISAL_UUID"))
    @Column(name = "SECTOR_NAME", length = 200)
    private List<String> topThreeSectors;

    @Column(name = "FINANCING_SCOPE", length = 500)
    private String financingScope;

    @Column(name = "FINANCING_SCOPE_CRORE", precision = 15, scale = 2)
    private BigDecimal financingScopeCrore;

    @Column(name = "PROJECT_LOCATION", length = 500)
    private String projectLocation;

    // Cluster Expert
    @Column(name = "CLUSTER_EXPERT_COMMENTS", length = 2000)
    private String clusterExpertComments;

    // Budget
    @Column(name = "BUDGET_ALLOCATED", precision = 15, scale = 2)
    private BigDecimal budgetAllocated;

    @Column(name = "FINANCIAL_YEAR")
    private LocalDate financialYear;

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

    // SIDBE Approval
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SIDBE_APPROVED_BY_USER_ID")
    private User sidbeApprovedByUser;

    @Column(name = "IS_SIDBE_APPROVED")
    private Boolean isSidbeApproved;



    // Apex Holder Information (KYC)
    @Column(name = "APEX_HOLDER_NAME", length = 200)
    private String apexHolderName;

    @Column(name = "APEX_HOLDER_DESIGNATION", length = 200)
    private String apexHolderDesignation;

    @Column(name = "APEX_HOLDER_MOBILE", length = 20)
    private String apexHolderMobile;

    @Column(name = "APEX_HOLDER_EMAIL", length = 200)
    private String apexHolderEmail;

    @Column(name = "ADDRESS_PROOF_TYPE", length = 100)
    private String addressProofType;

    @Column(name = "ADDRESS_PROOF", length = 500)
    private String addressProof;

    @Column(name = "ID_PROOF_TYPE", length = 100)
    private String idProofType;

    @Column(name = "ID_PROOF", length = 500)
    private String idProof;

    // Nodal Contact Information
    @Column(name = "NODAL_NAME", length = 200)
    private String nodalName;

    @Column(name = "NODAL_DESIGNATION", length = 200)
    private String nodalDesignation;

    @Column(name = "NODAL_MOBILE", length = 20)
    private String nodalMobile;

    @Column(name = "NODAL_EMAIL", length = 200)
    private String nodalEmail;

    // SIDBI Details
    @Column(name = "SIDBI_BRANCH", length = 200)
    private String sidbiBranch;

    // Cluster Details
    @Column(name = "MAPPED_WITH_CLUSTER")
    private Boolean mappedWithCluster;

    @Column(name = "CLUSTER_NAME", length = 300)
    private String clusterName;

    @Column(name = "MAPPED_WITH_IMPORTANT_DISTRICT")
    private Boolean mappedWithImportantDistrict;

    @Column(name = "DISTRICT_MSME_COUNT")
    private Integer districtMsmeCount;

    // Existing Infrastructure
    @Column(name = "ACTIVE_MEMBERS_ABOVE_200")
    private Boolean activeMembersAbove200;

    @Column(name = "ACTIVE_MEMBERS_COUNT")
    private Integer activeMembersCount;

    // Documentation and Justification
    @Column(name = "JUSTIFICATION", length = 2000)
    private String justification;

    @Column(name = "APPROVAL_LETTER", length = 500)
    private String approvalLetter;

    @Column(name = "MSME_COUNT_WITHOUT_TRADERS")
    private Integer msmeCountWithoutTraders;

    // Infrastructure & Services
    @Column(name = "MEMBER_DIRECTORY_AVAILABLE")
    private Boolean memberDirectoryAvailable;

    @Column(name = "BUILDING_TYPE", length = 100)
    private String buildingType;

    @Column(name = "DECLARATION_SIGNED")
    private Boolean declarationSigned;

    @Column(name = "ELECTRICITY_BILL", length = 500)
    private String electricityBill;

    @Column(name = "TELEPHONE_BILL", length = 500)
    private String telephoneBill;

    @Column(name = "IT_INFRASTRUCTURE_AVAILABLE")
    private Boolean itInfrastructureAvailable;

    @Column(name = "INFRASTRUCTURE_TYPE", length = 200)
    private String infrastructureType;

    @Column(name = "SECRETARIAT_STAFF_AVAILABLE")
    private Boolean secretariatStaffAvailable;

    @Column(name = "WEBSITE_AVAILABLE")
    private Boolean websiteAvailable;

    @Column(name = "WEBSITE_URL", length = 500)
    private String websiteUrl;

    @Column(name = "PAID_SERVICES_AVAILABLE")
    private Boolean paidServicesAvailable;

    @Column(name = "ADVERSE_REMARKS_AVAILABLE")
    private Boolean adverseRemarksAvailable;

    @Column(name = "ADVERSE_REMARKS", length = 500)
    private String adverseRemarks;

    @Column(name = "WEB_REPORT", length = 500)
    private String webReport;


    // Willingness & Output
    @Column(name = "WILLINGNESS_COMMENTS", length = 500)
    private String willingnessComments;

    @Column(name = "WORKED_WITH_SIDBI_BEFORE")
    private Boolean workedWithSidbiBefore;

    // Grant Details
    @Column(name = "GRANT_PROPOSED", precision = 15, scale = 2)
    private BigDecimal grantProposed;

    @Column(name = "GRANT_DETAILS", length = 2000)
    private String grantDetails;

    // Envisaged Outputs, Outcomes, and Impacts
    @Column(name = "ENVISAGED_OUTPUT", length = 500)
    private String envisagedOutput;

    @Column(name = "ENVISAGED_OUTCOME", length = 500)
    private String envisagedOutcome;


}

