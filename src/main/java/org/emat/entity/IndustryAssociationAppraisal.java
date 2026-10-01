package org.emat.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Entity representing Industry Association Appraisal. Maps to the INDUSTRY_ASSOCOCIATION_APPRAISAL
 * table in Oracle database. Has a 1:1 relationship with IndustryAssociationRegistration.
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
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_INDUSTRY_ASSOCIATION_APPRAISAL")
    @SequenceGenerator(
            name = "SEQ_INDUSTRY_ASSOCIATION_APPRAISAL",
            sequenceName = "SEQ_INDUSTRY_ASSOCIATION_APPRAISAL",
            allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGISTRATION_ID", nullable = false, unique = true)
    private IndustryAssociationRegistration registration;

    // Due Diligence
    @Column(name = "CIBIL_REPORT_REFERENCE_NO", length = 250)
    private String cibilReportReferenceNo;

    @Column(name = "CIBIL_REPORT_DATE")
    private LocalDate cibilReportDate;

    @Column(name = "CIBIL_RANKING", length = 125)
    private String cibilRanking;

    @Column(name = "CIBIL_REMARKS", length = 1250)
    private String cibilRemarks;

    @Column(name = "NGO_DARPAN_NUMBER", length = 250)
    private String ngoDarpanNumber;

    @Column(name = "NABARD_BLACKLISTED")
    private Boolean nabardBlacklisted;

    @Column(name = "SMART_REPORT_REFERENCE_NO", length = 250)
    private String smartReportReferenceNo;

    @Column(name = "SMART_REPORT_DATE")
    private LocalDate smartReportDate;

    @Column(name = "SMART_REPORT_REMARKS", length = 1250)
    private String smartReportRemarks;

    @Column(name = "WEB_SEARCH_VERIFIED")
    private Boolean webSearchVerified;

    @Column(name = "WEB_SEARCH_DOCUMENT", length = 625)
    private String webSearchDocument;

    // Beneficial Owners
    @Column(name = "BENEFICIAL_OWNER_CIBIL_REMARKS", length = 1250)
    private String beneficialOwnerCibilRemarks;

    @Column(name = "BENEFICIAL_OWNER_SMART_REMARKS", length = 1250)
    private String beneficialOwnerSmartRemarks;

    // Infrastructure
    @Column(name = "MAJOR_SOURCES_OF_INCOME", length = 1250)
    private String majorSourcesOfIncome;

    @Column(name = "ACTIVITIES_LAST_YEAR", length = 2500)
    private String activitiesLastYear;

    // MANPOWER_AGENCY Details
    @Column(name = "FORMALIZATION_COMMENTS", length = 1250)
    private String formalizationComments;

    @Column(name = "REFERRAL_ARRANGEMENT_COMMENTS", length = 1250)
    private String referralArrangementComments;

    @Column(name = "REFERRAL_ARRANGEMENT_READY")
    private Boolean referralArrangementReady;

    @Column(name = "BSE_READINESS_COMMENTS", length = 1250)
    private String bseReadinessComments;

    @Column(name = "BSE_READINESS_READY")
    private Boolean bseReadinessReady;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "IA_APPRAISAL_TOP_SECTORS",
            joinColumns = @JoinColumn(name = "APPRAISAL_ID"))
    private List<SectorDetail> sectors;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SectorDetail {

        @Column(name = "SECTOR_NAME", length = 250)
        private String sector;

        @Column(name = "SECTOR_KEY_PROBLEMS", length = 1250)
        private String sectorKeyProblems;
    }

    @Column(name = "FINANCING_SCOPE", length = 625)
    private String financingScope;

    @Column(name = "FINANCING_SCOPE_CRORE", precision = 15, scale = 2)
    private BigDecimal financingScopeCrore;

    @Column(name = "PROJECT_LOCATION", length = 625)
    private String projectLocation;

    // Cluster Expert
    @Column(name = "CLUSTER_EXPERT_COMMENTS", length = 2500)
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
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "IA_APPRAISAL_TERMS_CONDITIONS",
            joinColumns = @JoinColumn(name = "APPRAISAL_ID"))
    @Column(name = "TERM_CONDITION", length = 2500)
    private List<String> termsAndConditions;

    // DoP (Date of Presentation)
    @Column(name = "DOP_DATE")
    private LocalDate dopDate;

    // Recommendation
    @Column(name = "RECOMMENDATION", length = 125)
    private String recommendation;

    @Column(name = "RECOMMENDATION_REMARKS", length = 2500)
    private String recommendationRemarks;

    // SIDBE Approval
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SIDBE_APPROVED_BY_USER_ID")
    private User sidbeApprovedByUser;

    @Column(name = "IS_SIDBE_APPROVED")
    private Boolean isSidbeApproved;

    // Apex Holder Information (KYC)
    @Column(name = "APEX_HOLDER_NAME", length = 250)
    private String apexHolderName;

    @Column(name = "APEX_HOLDER_DESIGNATION", length = 250)
    private String apexHolderDesignation;

    @Column(name = "APEX_HOLDER_MOBILE", length = 25)
    private String apexHolderMobile;

    @Column(name = "APEX_HOLDER_EMAIL", length = 250)
    private String apexHolderEmail;

    @Column(name = "ADDRESS_PROOF_TYPE", length = 125)
    private String addressProofType;

    @Column(name = "ADDRESS_PROOF", length = 625)
    private String addressProof;

    @Column(name = "ID_PROOF_TYPE", length = 125)
    private String idProofType;

    @Column(name = "ID_PROOF", length = 625)
    private String idProof;

    // Nodal Contact Information
    @Column(name = "NODAL_NAME", length = 250)
    private String nodalName;

    @Column(name = "NODAL_DESIGNATION", length = 250)
    private String nodalDesignation;

    @Column(name = "NODAL_MOBILE", length = 25)
    private String nodalMobile;

    @Column(name = "NODAL_EMAIL", length = 250)
    private String nodalEmail;

    // SIDBI Details
    @Column(name = "SIDBI_BRANCH", length = 250)
    private String sidbiBranch;

    // Cluster Details
    @Column(name = "MAPPED_WITH_CLUSTER")
    private Boolean mappedWithCluster;

    @Column(name = "CLUSTER_NAME", length = 375)
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
    @Column(name = "JUSTIFICATION", length = 2500)
    private String justification;

    @Column(name = "APPROVAL_LETTER", length = 625)
    private String approvalLetter;

    @Column(name = "MSME_COUNT_WITHOUT_TRADERS")
    private Integer msmeCountWithoutTraders;

    // Infrastructure & Services
    @Column(name = "MEMBER_DIRECTORY_AVAILABLE")
    private Boolean memberDirectoryAvailable;

    @Column(name = "BUILDING_TYPE", length = 125)
    private String buildingType;

    @Column(name = "DECLARATION_SIGNED")
    private Boolean declarationSigned;

    @Column(name = "ELECTRICITY_BILL", length = 625)
    private String electricityBill;

    @Column(name = "TELEPHONE_BILL", length = 625)
    private String telephoneBill;

    @Column(name = "IT_INFRASTRUCTURE_AVAILABLE")
    private Boolean itInfrastructureAvailable;

    @Column(name = "INFRASTRUCTURE_TYPE", length = 250)
    private String infrastructureType;

    @Column(name = "SECRETARIAT_STAFF_AVAILABLE")
    private Boolean secretariatStaffAvailable;

    @Column(name = "SECRETARIAT_STAFF_DEATAILS")
    private String secretariatStaffDetail;

    @Column(name = "PENNAL_APPROVAL_LETTER")
    private String pennalApprovalLetter;

    @Column(name = "WEBSITE_AVAILABLE")
    private Boolean websiteAvailable;

    @Column(name = "WEBSITE_URL", length = 625)
    private String websiteUrl;

    @Column(name = "PAID_SERVICES_AVAILABLE")
    private Boolean paidServicesAvailable;

    @Column(name = "ADVERSE_REMARKS_AVAILABLE")
    private Boolean adverseRemarksAvailable;

    @Column(name = "ADVERSE_REMARKS", length = 625)
    private String adverseRemarks;

    @Column(name = "WEB_REPORT", length = 625)
    private String webReport;

    // Willingness & Output
    @Column(name = "WILLINGNESS_COMMENTS", length = 625)
    private String willingnessComments;

    @Column(name = "WORKED_WITH_SIDBI_BEFORE")
    private Boolean workedWithSidbiBefore;

    // Grant Details
    @Column(name = "GRANT_PROPOSED_SALARY", precision = 15, scale = 2)
    private BigDecimal grantProposedSalary;

    @Column(name = "GRANT_PROPOSED_CAPEX", precision = 15, scale = 2)
    private BigDecimal grantProposedCapex;

    @Column(name = "GRANT_PROPOSED_CAPACITY_BUILDING", precision = 15, scale = 2)
    private BigDecimal grantProposedCapacityBuilding;

    @Column(name = "GRANT_DETAILS", length = 5000)
    private String grantDetails;

    // Envisaged Outputs, Outcomes, and Impacts
    @Column(name = "ENVISAGED_OUTPUT", length = 625)
    private String envisagedOutput;

    @Column(name = "ENVISAGED_OUTCOME", length = 625)
    private String envisagedOutcome;

    // Office Holder CIBIL
    @Column(name = "HOLDER_CIBIL_REFERENCE_NO")
    private String holderCibilReferenceNo;

    @Column(name = "HOLDER_CIBIL_DATE")
    private LocalDate holderCibilDate;

    @Column(name = "HOLDER_CIBIL_SCORE")
    private String holderCibilScore;

    @Column(name = "HOLDER_CIBIL_REMARKS",length=625)
    private String holderCibilRemarks;

    @Column(name = "HOLDER_CIBIL_FILE")
    private String holderCibilFile;

    // Office Holder SMART
    @Column(name = "HOLDER_SMART_AVAILABLE")
    private Boolean holderSmartAvailable;

    @Column(name = "HOLDER_SMART_DATE")
    private LocalDate holderSmartDate;

    @Column(name = "HOLDER_SMART_REMARKS", length=625)
    private String holderSmartRemarks;

    // Beneficial Owner CIBIL
    @Column(name = "BENEFICIAL_OWNER_CIBIL_REFERENCE_NO")
    private String beneficialOwnerCibilReferenceNo;

    @Column(name = "BENEFICIAL_OWNER_CIBIL_DATE")
    private LocalDate beneficialOwnerCibilDate;

    @Column(name = "BENEFICIAL_OWNER_CIBIL_RANKING")
    private String beneficialOwnerCibilRanking;

    @Column(name = "BENEFICIAL_OWNER_CIBIL_FILE")
    private String beneficialOwnerCibilFile;

    // Beneficial Owner SMART
    @Column(name = "BENEFICIAL_OWNER_SMART_AVAILABLE")
    private Boolean beneficialOwnerSmartAvailable;

    @Column(name = "BENEFICIAL_OWNER_SMART_DATE")
    private LocalDate beneficialOwnerSmartDate;

    // Paid Services
    @Column(name = "PAID_SERVICES_DETAILS")
    private String paidServicesDetails;

    @Column(name = "SMART_REPORT_AVAILABLE")
    private Boolean smartReportAvailable;

    @Column(name = "NGO_DARPAN_FILES")
    private String ngoDarpanFile;

    @Column(name = "NABARD_BLACKLIST_FILE")
    private String nabardBlacklistFile;

    // Annexure V - Indicative list of cost of Soft Interventions (CBO/CBM activities)
    @OneToMany(mappedBy = "appraisal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AnnexureV> annexureVList;

    // Annexure VI - Indicative cost list of CAPEX elements
    @OneToMany(mappedBy = "appraisal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AnnexureVI> annexureVIList;

    @Column(name = "SANCTION_MARKING")
    private String sanctionMarking;

    @Column(name = "DOP_REFERENCE", length = 750)
    private String dopReference;

    @Column(name = "COMMITTEE_COMMENTS", length = 2500)
    private String committeeComments;
}
