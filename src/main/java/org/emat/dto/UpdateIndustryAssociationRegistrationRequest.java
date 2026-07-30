package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.emat.dto.SecretariatStaffDto;

/**
 * Request DTO for updating Industry Association Registration.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateIndustryAssociationRegistrationRequest {

    // Basic Information
    private String state;
    private String industryAssociationName;

    // Constitution Details
    private String constitutionType;
    private String constitutionOther;
    private LocalDate incorporationDate;
    private String incorporationCertificate;
    private String iaType;
    private String constitutionProof;

    // Location Details
    private String district;
    private String pincode;

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
    private String sidbiBranchName;

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
    private String paidServicesDetails;
    private List<SecretariatStaffDto> secretariatStaff;
    private Boolean adverseRemarksAvailable;
    private String adverseRemarks;
    private String webReport;

    // DIA Details - Selection Criteria
    private List<String> selectionCriteria;

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

    // Assigned SDE
    private String sde;

    // Status
    private Boolean isActive;

    // Audit Fields
    private String updatedBy;
}

