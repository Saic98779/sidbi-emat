package org.emat.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for updating an existing BDS Service Providers Onboarding. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBdsServiceProvidersOnboardingRequest {

    private String bdsProviderName;
    private LocalDate doi;
    private String constitution;
    private String address;
    private String state;
    private String district;
    private String pinCode;
    private String iaNature;
    private Integer noOfOffices;
    private Boolean catTo242IdenClusterFlag;
    private String clusterName;
    private String otherClusterIndusIa;
    private Integer totIaMembers;
    private Integer totMsmeIaMembers;
    private String sector;
    private String mainExecutiveName;
    private String executiveContactNo;
    private String nodalContactName;
    private String contactNumber;
    private String emailId;
    private Boolean ownAssociationIaFlag;
    private Boolean availOfItInfra;
    private Boolean availOfSecretariatStaffFlag;
    private Integer totLeadCasesGen;
    private BigDecimal casesSanctionedAmt;
    private BigDecimal casesDisbursedAmt;
    private String associateNameSidbiRoMappedWith;
    private String associateNameSidbiBoMappedWith;
    private String sidbiBseName;
    private String bseContactNumber;
    private String bseEmailId;
    private String areaOfExpertise;
}