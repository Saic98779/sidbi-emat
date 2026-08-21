package org.emat.service;

import lombok.RequiredArgsConstructor;
import org.emat.dto.EligibilityMatrixDto;
import org.emat.dto.RegistrationDropdownDto;
import org.emat.entity.EligibilityMatrix;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.repository.EligibilityMatrixRepository;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.emat.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EligibilityMatrixService {

    private final EligibilityMatrixRepository eligibilityMatrixRepository;
    private final IndustryAssociationRegistrationRepository registrationRepository;

    @Transactional
    public EligibilityMatrixDto create(EligibilityMatrixDto request) {

        UUID registrationUuid =
                UuidUtil.toUuid(request.getRegistrationUuid());

        // Check whether eligibility matrix already exists
        if (eligibilityMatrixRepository.existsByRegistration_Uuid(registrationUuid)) {
            throw new RuntimeException(
                    "Eligibility Matrix already exists for Registration UUID: "
                            + request.getRegistrationUuid()
            );
        }

        IndustryAssociationRegistration registration =
                registrationRepository.findById(registrationUuid)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Industry Association Registration not found: "
                                                + request.getRegistrationUuid()
                                ));

        EligibilityMatrix entity = new EligibilityMatrix();

        entity.setRegistration(registration);

        mapRequestToEntity(request, entity);

        EligibilityMatrix saved =
                eligibilityMatrixRepository.save(entity);
        registration.setIsEligibleMatricsAdded(true);
        registrationRepository.save(registration);
        return mapToResponse(saved);
    }
    @Transactional(readOnly = true)
    public EligibilityMatrixDto getById(UUID uuid) {

        EligibilityMatrix entity =
                eligibilityMatrixRepository.findById(uuid)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Eligibility Matrix not found: " + uuid
                                ));

        return mapToResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<EligibilityMatrixDto> getAll() {

        return eligibilityMatrixRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EligibilityMatrixDto getByRegistrationUuid(String registrationUuid) {

        UUID uuid = UuidUtil.toUuid(registrationUuid);

        EligibilityMatrix entity =
                eligibilityMatrixRepository
                        .findByRegistration_Uuid(uuid)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Eligibility Matrix not found for registration: "
                                                + registrationUuid
                                ));

        return mapToResponse(entity);
    }

    @Transactional
    public EligibilityMatrixDto update(UUID uuid, EligibilityMatrixDto request) {

        EligibilityMatrix entity =
                eligibilityMatrixRepository.findById(uuid)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Eligibility Matrix not found: " + uuid
                                ));

        if (request.getRegistrationUuid() != null) {

            UUID registrationUuid =
                    UuidUtil.toUuid(request.getRegistrationUuid());

            IndustryAssociationRegistration registration =
                    registrationRepository.findById(registrationUuid)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Industry Association Registration not found: "
                                                    + request.getRegistrationUuid()
                                    ));

            entity.setRegistration(registration);
        }

        mapRequestToEntity(request, entity);

        EligibilityMatrix updated =
                eligibilityMatrixRepository.save(entity);

        return mapToResponse(updated);
    }

    @Transactional
    public void delete(UUID uuid) {

        if (!eligibilityMatrixRepository.existsById(uuid)) {
            throw new RuntimeException(
                    "Eligibility Matrix not found: " + uuid
            );
        }

        eligibilityMatrixRepository.deleteById(uuid);
    }

    private void mapRequestToEntity(EligibilityMatrixDto request, EligibilityMatrix entity) {
        entity.setActiveMembers200(request.getActiveMembers200());
        entity.setActiveMsmes(request.getActiveMsmes());
        entity.setMembershipFeesBelow30(request.getMembershipFeesBelow30());
        entity.setBusinessWithinCluster(request.getBusinessWithinCluster());
        entity.setHandicraftArtisanal10Percent(
                request.getHandicraftArtisanal10Percent());
        entity.setSocioEconomicDevelopmentalImpact(
                request.getSocioEconomicDevelopmentalImpact());
        entity.setSmallSectorFocussedSustainableGrowth(
                request.getSmallSectorFocussedSustainableGrowth());
        entity.setRequiresInstitutionalSupport(
                request.getRequiresInstitutionalSupport());
        entity.setAdequateInfrastructure(
                request.getAdequateInfrastructure());
        entity.setConductsFairsInIndia(
                request.getConductsFairsInIndia());
        entity.setPartnersGovtEdpSkillDevelopment(
                request.getPartnersGovtEdpSkillDevelopment());
        entity.setPaidStaffAvailable(
                request.getPaidStaffAvailable());
        entity.setConductsInternationalTradeFairs(
                request.getConductsInternationalTradeFairs());
        entity.setEarnsRentalsFromInfrastructure(
                request.getEarnsRentalsFromInfrastructure());
        entity.setIaOfficeBearersMeetingConfirmed(
                request.getIaOfficeBearersMeetingConfirmed());
        entity.setApplicationVolume250Cr(
                request.getApplicationVolume250Cr());
        entity.setProvidesCreditFacilities(
                request.getProvidesCreditFacilities());
        entity.setUtilisesGovtSchemesPpp(
                request.getUtilisesGovtSchemesPpp());
        entity.setSupportsGstCapitalGoodsDuty(
                request.getSupportsGstCapitalGoodsDuty());
        entity.setSupportsGovtFinancialConvergence(
                request.getSupportsGovtFinancialConvergence());
        entity.setMemberDirectoryAdvertised(
                request.getMemberDirectoryAdvertised());
        entity.setSupportsGiAct(
                request.getSupportsGiAct());

        entity.setTotalScore(request.getTotalScore());
    }

    private EligibilityMatrixDto mapToResponse(
            EligibilityMatrix entity) {

        return EligibilityMatrixDto.builder()
                .uuid(entity.getUuid())

                .registrationUuid(
                        entity.getRegistration() != null
                                ? entity.getRegistration()
                                .getUuid()
                                .toString()
                                : null
                )

                .activeMembers200(entity.getActiveMembers200())
                .activeMsmes(entity.getActiveMsmes())
                .membershipFeesBelow30(
                        entity.getMembershipFeesBelow30())
                .businessWithinCluster(
                        entity.getBusinessWithinCluster())
                .handicraftArtisanal10Percent(
                        entity.getHandicraftArtisanal10Percent())
                .socioEconomicDevelopmentalImpact(
                        entity.getSocioEconomicDevelopmentalImpact())
                .smallSectorFocussedSustainableGrowth(
                        entity.getSmallSectorFocussedSustainableGrowth())
                .requiresInstitutionalSupport(
                        entity.getRequiresInstitutionalSupport())
                .adequateInfrastructure(
                        entity.getAdequateInfrastructure())
                .conductsFairsInIndia(
                        entity.getConductsFairsInIndia())
                .partnersGovtEdpSkillDevelopment(
                        entity.getPartnersGovtEdpSkillDevelopment())
                .paidStaffAvailable(
                        entity.getPaidStaffAvailable())
                .conductsInternationalTradeFairs(
                        entity.getConductsInternationalTradeFairs())
                .earnsRentalsFromInfrastructure(
                        entity.getEarnsRentalsFromInfrastructure())
                .iaOfficeBearersMeetingConfirmed(
                        entity.getIaOfficeBearersMeetingConfirmed())
                .applicationVolume250Cr(
                        entity.getApplicationVolume250Cr())
                .providesCreditFacilities(
                        entity.getProvidesCreditFacilities())
                .utilisesGovtSchemesPpp(
                        entity.getUtilisesGovtSchemesPpp())
                .supportsGstCapitalGoodsDuty(
                        entity.getSupportsGstCapitalGoodsDuty())
                .supportsGovtFinancialConvergence(
                        entity.getSupportsGovtFinancialConvergence())
                .memberDirectoryAdvertised(
                        entity.getMemberDirectoryAdvertised())
                .supportsGiAct(
                        entity.getSupportsGiAct())
                .totalScore(entity.getTotalScore())

                .build();
    }

    @Transactional(readOnly = true)
    public List<RegistrationDropdownDto> getRegistrationDropdown() {

        return registrationRepository.findAll()
                .stream()
                .map(registration -> RegistrationDropdownDto.builder()
                        .uuid(registration.getUuid())
                        .name(registration.getIndustryAssociationName())
                        .build())
                .toList();
    }
}