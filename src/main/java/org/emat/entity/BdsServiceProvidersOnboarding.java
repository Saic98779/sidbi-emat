package org.emat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.emat.enums.Status;

/**
 * BDS Service Providers Onboarding
 */
@Entity
@Table(name = "BDS_SERVICE_PROVIDERS_ONBOARDING")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BdsServiceProvidersOnboarding extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BDS_SERVICE_PROVIDERS")
    @SequenceGenerator(
            name = "SEQ_BDS_SERVICE_PROVIDERS",
            sequenceName = "SEQ_BDS_SERVICE_PROVIDERS",
            allocationSize = 1)
    @Column(name = "PBSP_BDS_ID", nullable = false, updatable = false)
    private Long id;

    // BDS Provider Name
    @Column(name = "PBSP_BDS_PROVIDER_NAME")
    private String bdsProviderName;

    // Date of Incorporation
    @Column(name = "PBSP_DOI")
    private LocalDate doi;

    // Constitution
    @Column(name = "PBSP_CONSTITUTION")
    private String constitution;

    // Address
    @Column(name = "PBSP_ADDRESS")
    private String address;

    // State
    @Column(name = "PBSP_STATE")
    private String state;

    // District
    @Column(name = "PBSP_DISTRICT")
    private String district;

    // Pin Code
    @Column(name = "PBSP_PIN_CODE")
    private String pinCode;

    // IA Nature
    @Column(name = "PBSP_IA_NATURE")
    private String iaNature;

    // Number of Offices
    @Column(name = "PBSP_NO_OF_OFFICES")
    private Integer noOfOffices;

    // Catering to 242 Identified Clusters Flag
    @Column(name = "PBSP_CAT_TO_242_IDEN_CLUSTER_FLAG")
    private Boolean catTo242IdenClusterFlag;

    // Cluster Name
    @Column(name = "PBSP_CLUSTER_NAME")
    private String clusterName;

    // Other Cluster / Industry / IA
    @Column(name = "PBSP_OTHER_CLUSTER_INDUS_IA")
    private String otherClusterIndusIa;

    // Total IA Members
    @Column(name = "PBSP_TOT_IA_MEMBERS")
    private Integer totIaMembers;

    // Total MSME IA Members
    @Column(name = "PBSP_TOT_MSME_IA_MEMBERS")
    private Integer totMsmeIaMembers;

    // Sector
    @Column(name = "PBSP_SECTOR")
    private String sector;

    // Main Executive Name
    @Column(name = "PBSP_MAIN_EXECUTIVE_NAME")
    private String mainExecutiveName;

    // Executive Contact Number
    @Column(name = "PBSP_EXECUTIVE_CONTACT_NO")
    private String executiveContactNo;

    // Nodal Contact Name
    @Column(name = "PBSP_NODAL_CONTACT_NAME")
    private String nodalContactName;

    // Contact Number
    @Column(name = "PBSP_CONTACT_NUMBER")
    private String contactNumber;

    // Email ID
    @Column(name = "PBSP_EMAIL_ID")
    private String emailId;

    // Own Association / IA Flag
    @Column(name = "PBSP_OWN_ASSOCIATION_IA_FLAG")
    private Boolean ownAssociationIaFlag;

    // Availability of IT Infrastructure
    @Column(name = "PBSP_AVAIL_OF_IT_INFRA")
    private Boolean availOfItInfra;

    // Availability of Secretariat Staff Flag
    @Column(name = "PBSP_AVAIL_OF_SECRETARIAT_STAFF_FLAG")
    private Boolean availOfSecretariatStaffFlag;

    // Total Lead Cases Generated
    @Column(name = "PBSP_TOT_LEAD_CASES_GEN")
    private Integer totLeadCasesGen;

    // Cases Sanctioned Amount
    @Column(name = "PBSP_CASES_SANCTIONED_AMT")
    private BigDecimal casesSanctionedAmt;

    // Cases Disbursed Amount
    @Column(name = "PBSP_CASES_DISBURSED_AMT")
    private BigDecimal casesDisbursedAmt;

    // Associate Name - SIDBI RO Mapped With
    @Column(name = "PBSP_ASSOCIATE_NAME_SIDBI_RO_MAPPED_WITH")
    private String associateNameSidbiRoMappedWith;

    // Associate Name - SIDBI BO Mapped With
    @Column(name = "PBSP_ASSOCIATE_NAME_SIDBI_BO_MAPPED_WITH")
    private String associateNameSidbiBoMappedWith;

    // SIDBI BSE Name
    @Column(name = "PBSP_SIDBI_BSE_NAME")
    private String sidbiBseName;

    // BSE Contact Number
    @Column(name = "PBSP_BSE_CONTACT_NUMBER")
    private String bseContactNumber;

    // BSE Email ID
    @Column(name = "PBSP_BSE_EMAIL_ID")
    private String bseEmailId;

    // Area of Expertise
    @Column(name = "PBSP_AREA_OF_EXPERTISE")
    private String areaOfExpertise;

    @Column(name = "STATUS")
    private Status status;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "APPROVED_DATE")
    private LocalDate approvedDate;
}