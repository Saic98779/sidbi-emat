package org.emat.dto;

import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdDecryptDeserializer;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.dto.serializer.PiiStringDecryptDeserializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateIndustryAssociationRegistrationRequest {

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
    private String address;
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
    private String nodalName;
    private String nodalDesignation;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
        @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String nodalMobile;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
        @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
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
    private String secretariatStaff;
    private Boolean adverseRemarksAvailable;
    private String adverseRemarks;
    private String webReport;
    private String selectionCriteria;
    private String willingnessComments;
    private Boolean workedWithSidbiBefore;
    private BigDecimal grantProposed;
    private String grantDetails;
    private String envisagedOutput;
    private String envisagedOutcome;
    private String envisagedImpact;
    private String sde;
    private Boolean isActive;
    private String updatedBy;

    private String panNo;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
        @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String email;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
        @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    private Long stageId;
    private String stageComments;
}
