package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    private String apexHolderName;
    private String apexHolderDesignation;
    private String apexHolderMobile;
    private String apexHolderEmail;
    private String addressProofType;
    private String addressProof;
    private String idProofType;
    private String idProof;
    private String nodalName;
    private String nodalDesignation;
    private String nodalMobile;
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
    private Boolean isActive;
    private String updatedBy;
    private String panNo;
    private String email;
    private Boolean isEligibleMatricsAdded;
}

