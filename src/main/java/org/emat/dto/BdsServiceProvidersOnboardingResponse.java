package org.emat.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.enums.Status;

/** DTO for BDS Service Providers Onboarding response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BdsServiceProvidersOnboardingResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

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

    private Status status;
    private String remark;
    private LocalDate approvedDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
}