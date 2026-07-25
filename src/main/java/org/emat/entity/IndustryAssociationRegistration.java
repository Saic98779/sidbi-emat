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
 * Entity representing Industry Association Registration.
 * Maps to the INDUSTRY_ASSOCIATION_REGISTRATION table in Oracle database.
 */
@Entity
@Table(name = "INDUSTRY_ASSOCIATION_REGISTRATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndustryAssociationRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID", updatable = false, nullable = false)
    private UUID uuid;

    // Basic Information
    @Column(name = "STATE", nullable = false, length = 100)
    private String state;

    @Column(name = "INDUSTRY_ASSOCIATION_NAME", nullable = false, length = 500)
    private String industryAssociationName;

    // Constitution Details
    @Column(name = "CONSTITUTION_TYPE", length = 100)
    private String constitutionType;

    @Column(name = "CONSTITUTION_OTHER", length = 500)
    private String constitutionOther;

    @Column(name = "INCORPORATION_DATE")
    private LocalDate incorporationDate;

    @Column(name = "INCORPORATION_CERTIFICATE", length = 500)
    private String incorporationCertificate;

    @Column(name = "IA_TYPE", length = 100)
    private String iaType;

    @Column(name = "CONSTITUTION_PROOF", length = 500)
    private String constitutionProof;

    // Location Details
    @Column(name = "DISTRICT", length = 100)
    private String district;

    @Column(name = "PINCODE", length = 10)
    private String pincode;

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

    // DIA Details - Selection Criteria
    @ElementCollection
    @CollectionTable(
            name = "IA_BASIS_SELECTION",
            joinColumns = @JoinColumn(name = "UUID"))
    @Column(name = "BASIS")
    private List<String> selectionCriteria;

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

    @Column(name = "ENVISAGED_IMPACT", length = 500)
    private String envisagedImpact;

    // Assigned SDE (State Development Executive)
    @Column(name = "SDE", length = 200)
    private String sde;

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

    // SIDBE approver mapping - stores the actual user who approved
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SIDBE_APPROVED_BY_USER_ID")
    private User sidbeApprovedByUser;

    private Boolean isSidbeApproved;

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
