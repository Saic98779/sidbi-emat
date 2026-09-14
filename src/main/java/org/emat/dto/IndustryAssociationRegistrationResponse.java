package org.emat.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndustryAssociationRegistrationResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    private String state;
    private String industryAssociationName;
    private String constitutionType;
    private String constitutionOther;
    private LocalDate incorporationDate;
    private String incorporationCertificate;
    private String iaType;
    private String constitutionProof;
    private String district;
    private String pincode;
    private String apexHolderName;
    private String apexHolderDesignation;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    private String apexHolderMobile;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    private String apexHolderEmail;

    private String addressProofType;
    private String addressProof;
    private String idProofType;
    private String idProof;
    private String nodalName;
    private String nodalDesignation;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    private String nodalMobile;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    private String nodalEmail;

    private String sidbiBranch;
    private String sidbiBranchName;
    private Boolean mappedWithCluster;
    private String clusterName;
    private Boolean mappedWithImportantDistrict;
    private Integer districtMsmeCount;
    private Boolean activeMembersAbove200;
    private Integer activeMembersCount;
    private String justification;
    private String approvalLetter;
    private Integer msmeCountWithoutTraders;
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
    private List<String> selectionCriteria;
    private String willingnessComments;
    private Boolean workedWithSidbiBefore;
    private BigDecimal grantProposed;
    private String grantDetails;
    private String envisagedOutput;
    private String envisagedOutcome;
    private String envisagedImpact;
    private String sde;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long sidbeApprovedByUserId;
    private String sidbeApprovedByUsername;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    private String panNo;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    private String email;

    private String currentStage;
    private String comments;
}
