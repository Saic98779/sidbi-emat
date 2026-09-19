package org.emat.mapper;

import org.emat.dto.BdsServiceProvidersOnboardingResponse;
import org.emat.dto.CreateBdsServiceProvidersOnboardingRequest;
import org.emat.dto.UpdateBdsServiceProvidersOnboardingRequest;
import org.emat.entity.BdsServiceProvidersOnboarding;
import org.springframework.stereotype.Component;

@Component
public class BdsServiceProvidersOnboardingMapper {

    public BdsServiceProvidersOnboarding toEntity(
            CreateBdsServiceProvidersOnboardingRequest request) {
        return BdsServiceProvidersOnboarding.builder()
                .bdsProviderName(request.getBdsProviderName())
                .doi(request.getDoi())
                .constitution(request.getConstitution())
                .address(request.getAddress())
                .state(request.getState())
                .district(request.getDistrict())
                .pinCode(request.getPinCode())
                .iaNature(request.getIaNature())
                .noOfOffices(request.getNoOfOffices())
                .catTo242IdenClusterFlag(request.getCatTo242IdenClusterFlag())
                .clusterName(request.getClusterName())
                .otherClusterIndusIa(request.getOtherClusterIndusIa())
                .totIaMembers(request.getTotIaMembers())
                .totMsmeIaMembers(request.getTotMsmeIaMembers())
                .sector(request.getSector())
                .mainExecutiveName(request.getMainExecutiveName())
                .executiveContactNo(request.getExecutiveContactNo())
                .nodalContactName(request.getNodalContactName())
                .contactNumber(request.getContactNumber())
                .emailId(request.getEmailId())
                .ownAssociationIaFlag(request.getOwnAssociationIaFlag())
                .availOfItInfra(request.getAvailOfItInfra())
                .availOfSecretariatStaffFlag(request.getAvailOfSecretariatStaffFlag())
                .totLeadCasesGen(request.getTotLeadCasesGen())
                .casesSanctionedAmt(request.getCasesSanctionedAmt())
                .casesDisbursedAmt(request.getCasesDisbursedAmt())
                .associateNameSidbiRoMappedWith(request.getAssociateNameSidbiRoMappedWith())
                .associateNameSidbiBoMappedWith(request.getAssociateNameSidbiBoMappedWith())
                .sidbiBseName(request.getSidbiBseName())
                .bseContactNumber(request.getBseContactNumber())
                .bseEmailId(request.getBseEmailId())
                .areaOfExpertise(request.getAreaOfExpertise())
                .isActive(true)
                .build();
    }

    public void applyUpdateRequest(
            BdsServiceProvidersOnboarding onboarding,
            UpdateBdsServiceProvidersOnboardingRequest request) {
        if (request.getBdsProviderName() != null)
            onboarding.setBdsProviderName(request.getBdsProviderName());
        if (request.getDoi() != null) onboarding.setDoi(request.getDoi());
        if (request.getConstitution() != null)
            onboarding.setConstitution(request.getConstitution());
        if (request.getAddress() != null) onboarding.setAddress(request.getAddress());
        if (request.getState() != null) onboarding.setState(request.getState());
        if (request.getDistrict() != null) onboarding.setDistrict(request.getDistrict());
        if (request.getPinCode() != null) onboarding.setPinCode(request.getPinCode());
        if (request.getIaNature() != null) onboarding.setIaNature(request.getIaNature());
        if (request.getNoOfOffices() != null) onboarding.setNoOfOffices(request.getNoOfOffices());
        if (request.getCatTo242IdenClusterFlag() != null)
            onboarding.setCatTo242IdenClusterFlag(request.getCatTo242IdenClusterFlag());
        if (request.getClusterName() != null) onboarding.setClusterName(request.getClusterName());
        if (request.getOtherClusterIndusIa() != null)
            onboarding.setOtherClusterIndusIa(request.getOtherClusterIndusIa());
        if (request.getTotIaMembers() != null) onboarding.setTotIaMembers(request.getTotIaMembers());
        if (request.getTotMsmeIaMembers() != null)
            onboarding.setTotMsmeIaMembers(request.getTotMsmeIaMembers());
        if (request.getSector() != null) onboarding.setSector(request.getSector());
        if (request.getMainExecutiveName() != null)
            onboarding.setMainExecutiveName(request.getMainExecutiveName());
        if (request.getExecutiveContactNo() != null)
            onboarding.setExecutiveContactNo(request.getExecutiveContactNo());
        if (request.getNodalContactName() != null)
            onboarding.setNodalContactName(request.getNodalContactName());
        if (request.getContactNumber() != null)
            onboarding.setContactNumber(request.getContactNumber());
        if (request.getEmailId() != null) onboarding.setEmailId(request.getEmailId());
        if (request.getOwnAssociationIaFlag() != null)
            onboarding.setOwnAssociationIaFlag(request.getOwnAssociationIaFlag());
        if (request.getAvailOfItInfra() != null)
            onboarding.setAvailOfItInfra(request.getAvailOfItInfra());
        if (request.getAvailOfSecretariatStaffFlag() != null)
            onboarding.setAvailOfSecretariatStaffFlag(request.getAvailOfSecretariatStaffFlag());
        if (request.getTotLeadCasesGen() != null)
            onboarding.setTotLeadCasesGen(request.getTotLeadCasesGen());
        if (request.getCasesSanctionedAmt() != null)
            onboarding.setCasesSanctionedAmt(request.getCasesSanctionedAmt());
        if (request.getCasesDisbursedAmt() != null)
            onboarding.setCasesDisbursedAmt(request.getCasesDisbursedAmt());
        if (request.getAssociateNameSidbiRoMappedWith() != null)
            onboarding.setAssociateNameSidbiRoMappedWith(
                    request.getAssociateNameSidbiRoMappedWith());
        if (request.getAssociateNameSidbiBoMappedWith() != null)
            onboarding.setAssociateNameSidbiBoMappedWith(
                    request.getAssociateNameSidbiBoMappedWith());
        if (request.getSidbiBseName() != null)
            onboarding.setSidbiBseName(request.getSidbiBseName());
        if (request.getBseContactNumber() != null)
            onboarding.setBseContactNumber(request.getBseContactNumber());
        if (request.getBseEmailId() != null) onboarding.setBseEmailId(request.getBseEmailId());
        if (request.getAreaOfExpertise() != null)
            onboarding.setAreaOfExpertise(request.getAreaOfExpertise());
    }

    public BdsServiceProvidersOnboardingResponse toResponse(
            BdsServiceProvidersOnboarding onboarding) {
        return BdsServiceProvidersOnboardingResponse.builder()
                .id(onboarding.getId())
                .bdsProviderName(onboarding.getBdsProviderName())
                .doi(onboarding.getDoi())
                .constitution(onboarding.getConstitution())
                .address(onboarding.getAddress())
                .state(onboarding.getState())
                .district(onboarding.getDistrict())
                .pinCode(onboarding.getPinCode())
                .iaNature(onboarding.getIaNature())
                .noOfOffices(onboarding.getNoOfOffices())
                .catTo242IdenClusterFlag(onboarding.getCatTo242IdenClusterFlag())
                .clusterName(onboarding.getClusterName())
                .otherClusterIndusIa(onboarding.getOtherClusterIndusIa())
                .totIaMembers(onboarding.getTotIaMembers())
                .totMsmeIaMembers(onboarding.getTotMsmeIaMembers())
                .sector(onboarding.getSector())
                .mainExecutiveName(onboarding.getMainExecutiveName())
                .executiveContactNo(onboarding.getExecutiveContactNo())
                .nodalContactName(onboarding.getNodalContactName())
                .contactNumber(onboarding.getContactNumber())
                .emailId(onboarding.getEmailId())
                .ownAssociationIaFlag(onboarding.getOwnAssociationIaFlag())
                .availOfItInfra(onboarding.getAvailOfItInfra())
                .availOfSecretariatStaffFlag(onboarding.getAvailOfSecretariatStaffFlag())
                .totLeadCasesGen(onboarding.getTotLeadCasesGen())
                .casesSanctionedAmt(onboarding.getCasesSanctionedAmt())
                .casesDisbursedAmt(onboarding.getCasesDisbursedAmt())
                .associateNameSidbiRoMappedWith(onboarding.getAssociateNameSidbiRoMappedWith())
                .associateNameSidbiBoMappedWith(onboarding.getAssociateNameSidbiBoMappedWith())
                .sidbiBseName(onboarding.getSidbiBseName())
                .bseContactNumber(onboarding.getBseContactNumber())
                .bseEmailId(onboarding.getBseEmailId())
                .areaOfExpertise(onboarding.getAreaOfExpertise())
                .createdAt(onboarding.getCreatedAt())
                .updatedAt(onboarding.getUpdatedAt())
                .createdBy(onboarding.getCreatedBy())
                .updatedBy(onboarding.getUpdatedBy())
                .isActive(onboarding.getIsActive())
                .build();
    }
}