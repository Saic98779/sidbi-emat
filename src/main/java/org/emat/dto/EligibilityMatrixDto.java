package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EligibilityMatrixDto {
    private Long id;

    private Long registrationId;

    private Boolean activeMembers200;
    private Boolean activeMsmes;
    private Boolean membershipFeesBelow30;
    private Boolean businessWithinCluster;
    private Boolean handicraftArtisanal10Percent;
    private Boolean socioEconomicDevelopmentalImpact;
    private Boolean smallSectorFocussedSustainableGrowth;
    private Boolean requiresInstitutionalSupport;
    private Boolean adequateInfrastructure;
    private Boolean conductsFairsInIndia;
    private Boolean partnersGovtEdpSkillDevelopment;
    private Boolean paidStaffAvailable;
    private Boolean conductsInternationalTradeFairs;
    private Boolean earnsRentalsFromInfrastructure;
    private Boolean iaOfficeBearersMeetingConfirmed;
    private Boolean applicationVolume250Cr;
    private Boolean providesCreditFacilities;
    private Boolean utilisesGovtSchemesPpp;
    private Boolean supportsGstCapitalGoodsDuty;
    private Boolean supportsGovtFinancialConvergence;
    private Boolean memberDirectoryAdvertised;
    private Boolean supportsGiAct;

    private Integer totalScore;
}