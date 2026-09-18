package org.emat.dto;

import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdDecryptDeserializer;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.dto.serializer.PiiStringDecryptDeserializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;

/** Request DTO for creating Industry Association Registration. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateIndustryAssociationRegistrationRequest {

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

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
        @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String apexHolderMobile;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
        @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String apexHolderEmail;

    private String addressProofType;
    private String addressProof;
    private String idProofType;
    private String idProof;

    // Nodal Contact Information
    private String nodalName;
    private String nodalDesignation;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
        @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String nodalMobile;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
        @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
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

    // MANPOWER_AGENCY Details - Selection Criteria
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

    // SIDBI Approval Fields
    private Boolean isSidbeApproved;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
        @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    private Long sidbeApprovedByUserId;

    // Audit Fields
    private String createdBy;

    private String panNo;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
        @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String email;

    private Boolean isEligibleMatricsAdded;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
        @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    private Long stageId;
    private String stageComments;
}
