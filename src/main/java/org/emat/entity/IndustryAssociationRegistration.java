package org.emat.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Entity representing Industry Association Registration. Maps to the
 * INDUSTRY_ASSOCIATION_REGISTRATION table in Oracle database.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "INDUSTRY_ASSOCIATION_REGISTRATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class IndustryAssociationRegistration extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_INDUSTRY_ASSOCIATION_REGISTRATION")
    @SequenceGenerator(
            name = "SEQ_INDUSTRY_ASSOCIATION_REGISTRATION",
            sequenceName = "SEQ_INDUSTRY_ASSOCIATION_REGISTRATION",
            allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    // Basic Information
    @Column(name = "STATE", nullable = false, length = 125)
    private String state;

    @Column(name = "INDUSTRY_ASSOCIATION_NAME", nullable = false, length = 625)
    private String industryAssociationName;

    // Constitution Details
    @Column(name = "CONSTITUTION_TYPE", length = 125)
    private String constitutionType;

    @Column(name = "CONSTITUTION_OTHER", length = 625)
    private String constitutionOther;

    @Column(name = "INCORPORATION_DATE")
    private LocalDate incorporationDate;

    @Column(name = "INCORPORATION_CERTIFICATE", length = 625)
    private String incorporationCertificate;

    @Column(name = "IA_TYPE", length = 125)
    private String iaType;

    @Column(name = "CONSTITUTION_PROOF", length = 625)
    private String constitutionProof;

    // Location Details
    @Column(name = "DISTRICT", length = 125)
    private String district;

    @Column(name = "PINCODE", length = 13)
    private String pincode;

    @Column(name = "ADDRESS", length = 1250)
    private String address;

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

    @Column(name = "SIDBI_BRANCH_NAME", length = 375)
    private String sidbiBranchName;

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

    @Column(name = "INFRASTRUCTURE_TYPE", length = 625)
    private String infrastructureType;

    @Column(name = "SECRETARIAT_STAFF_AVAILABLE")
    private Boolean secretariatStaffAvailable;

    @Column(name = "WEBSITE_AVAILABLE")
    private Boolean websiteAvailable;

    @Column(name = "WEBSITE_URL", length = 625)
    private String websiteUrl;

    @Column(name = "PAID_SERVICES_AVAILABLE")
    private Boolean paidServicesAvailable;

    @Column(name = "PAID_SERVICES_DETAILS", length = 1250)
    private String paidServicesDetails;

    @Column(name = "SECRETARIAT_STAFF", length = 1250)
    private String secretariatStaff;

    @Column(name = "ADVERSE_REMARKS_AVAILABLE")
    private Boolean adverseRemarksAvailable;

    @Column(name = "ADVERSE_REMARKS", length = 625)
    private String adverseRemarks;

    @Column(name = "WEB_REPORT", length = 625)
    private String webReport;

    // MANPOWER_AGENCY Details - Selection Criteria
    @Column(name = "SELECTION_CRITERIA", length = 2500)
    private String selectionCriteria;

    // Willingness & Output
    @Column(name = "WILLINGNESS_COMMENTS", length = 625)
    private String willingnessComments;

    @Column(name = "WORKED_WITH_SIDBI_BEFORE")
    private Boolean workedWithSidbiBefore;

    // Grant Details
    @Column(name = "GRANT_PROPOSED", precision = 15, scale = 2)
    private BigDecimal grantProposed;

    @Column(name = "GRANT_DETAILS", length = 5125)
    private String grantDetails;

    // Envisaged Outputs, Outcomes, and Impacts
    @Column(name = "ENVISAGED_OUTPUT", length = 625)
    private String envisagedOutput;

    @Column(name = "ENVISAGED_OUTCOME", length = 625)
    private String envisagedOutcome;

    @Column(name = "ENVISAGED_IMPACT", length = 625)
    private String envisagedImpact;

    // Assigned SDE (State Development Executive)
    @Column(name = "SDE", length = 250)
    private String sde;

    // SIDBE approver mapping - stores the actual user who approved
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SIDBE_APPROVED_BY_USER_ID")
    private User sidbeApprovedByUser;

    // Bidirectional 1:1 relationship with IndustryAssociationAppraisal
    @OneToOne(mappedBy = "registration", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private IndustryAssociationAppraisal appraisal;

    @Column(name = "EMAIL", length = 125)
    private String email;

    @Column(name = "PAN_NO", length = 19, unique = true)
    private String panNo;

    // Current Stage Details
    @ManyToOne
    @JoinColumn(name = "current_stage_id")
    private Stage currentStage;

    // Stage History
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "IA_STAGE_HISTORY", joinColumns = @JoinColumn(name = "REGISTRATION_ID"))
    @OrderColumn(name = "HISTORY_ORDER")
    private List<StageHistory> history = new ArrayList<>();
}
