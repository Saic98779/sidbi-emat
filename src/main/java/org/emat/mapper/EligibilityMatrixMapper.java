package org.emat.mapper;

import org.emat.dto.EligibilityMatrixDto;
import org.emat.dto.RegistrationDropdownDto;
import org.emat.entity.EligibilityMatrix;
import org.emat.entity.IndustryAssociationRegistration;
import org.springframework.stereotype.Component;

@Component
public class EligibilityMatrixMapper {

    public void updateEntityFromRequest(EligibilityMatrixDto request, EligibilityMatrix entity) {
        entity.setActiveMembers200(request.getActiveMembers200());
        entity.setActiveMsmes(request.getActiveMsmes());
        entity.setMembershipFeesBelow30(request.getMembershipFeesBelow30());
        entity.setBusinessWithinCluster(request.getBusinessWithinCluster());
        entity.setHandicraftArtisanal10Percent(request.getHandicraftArtisanal10Percent());
        entity.setSocioEconomicDevelopmentalImpact(request.getSocioEconomicDevelopmentalImpact());
        entity.setSmallSectorFocussedSustainableGrowth(request.getSmallSectorFocussedSustainableGrowth());
        entity.setRequiresInstitutionalSupport(request.getRequiresInstitutionalSupport());
        entity.setAdequateInfrastructure(request.getAdequateInfrastructure());
        entity.setConductsFairsInIndia(request.getConductsFairsInIndia());
        entity.setPartnersGovtEdpSkillDevelopment(request.getPartnersGovtEdpSkillDevelopment());
        entity.setPaidStaffAvailable(request.getPaidStaffAvailable());
        entity.setConductsInternationalTradeFairs(request.getConductsInternationalTradeFairs());
        entity.setEarnsRentalsFromInfrastructure(request.getEarnsRentalsFromInfrastructure());
        entity.setIaOfficeBearersMeetingConfirmed(request.getIaOfficeBearersMeetingConfirmed());
        entity.setApplicationVolume250Cr(request.getApplicationVolume250Cr());
        entity.setProvidesCreditFacilities(request.getProvidesCreditFacilities());
        entity.setUtilisesGovtSchemesPpp(request.getUtilisesGovtSchemesPpp());
        entity.setSupportsGstCapitalGoodsDuty(request.getSupportsGstCapitalGoodsDuty());
        entity.setSupportsGovtFinancialConvergence(request.getSupportsGovtFinancialConvergence());
        entity.setMemberDirectoryAdvertised(request.getMemberDirectoryAdvertised());
        entity.setSupportsGiAct(request.getSupportsGiAct());
        entity.setTotalScore(request.getTotalScore());
    }

    public EligibilityMatrixDto toResponse(EligibilityMatrix entity) {
        return EligibilityMatrixDto.builder()
                .id(entity.getId())
                .registrationId(entity.getRegistration() != null ? entity.getRegistration().getId() : null)
                .activeMembers200(entity.getActiveMembers200())
                .activeMsmes(entity.getActiveMsmes())
                .membershipFeesBelow30(entity.getMembershipFeesBelow30())
                .businessWithinCluster(entity.getBusinessWithinCluster())
                .handicraftArtisanal10Percent(entity.getHandicraftArtisanal10Percent())
                .socioEconomicDevelopmentalImpact(entity.getSocioEconomicDevelopmentalImpact())
                .smallSectorFocussedSustainableGrowth(entity.getSmallSectorFocussedSustainableGrowth())
                .requiresInstitutionalSupport(entity.getRequiresInstitutionalSupport())
                .adequateInfrastructure(entity.getAdequateInfrastructure())
                .conductsFairsInIndia(entity.getConductsFairsInIndia())
                .partnersGovtEdpSkillDevelopment(entity.getPartnersGovtEdpSkillDevelopment())
                .paidStaffAvailable(entity.getPaidStaffAvailable())
                .conductsInternationalTradeFairs(entity.getConductsInternationalTradeFairs())
                .earnsRentalsFromInfrastructure(entity.getEarnsRentalsFromInfrastructure())
                .iaOfficeBearersMeetingConfirmed(entity.getIaOfficeBearersMeetingConfirmed())
                .applicationVolume250Cr(entity.getApplicationVolume250Cr())
                .providesCreditFacilities(entity.getProvidesCreditFacilities())
                .utilisesGovtSchemesPpp(entity.getUtilisesGovtSchemesPpp())
                .supportsGstCapitalGoodsDuty(entity.getSupportsGstCapitalGoodsDuty())
                .supportsGovtFinancialConvergence(entity.getSupportsGovtFinancialConvergence())
                .memberDirectoryAdvertised(entity.getMemberDirectoryAdvertised())
                .supportsGiAct(entity.getSupportsGiAct())
                .totalScore(entity.getTotalScore())
                .build();
    }

    public RegistrationDropdownDto toRegistrationDropdown(IndustryAssociationRegistration registration) {
        return RegistrationDropdownDto.builder()
                .id(registration.getId())
                .name(registration.getIndustryAssociationName())
                .build();
    }
}

