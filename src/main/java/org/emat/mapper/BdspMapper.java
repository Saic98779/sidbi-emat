package org.emat.mapper;

import org.emat.dto.BdspResponse;
import org.emat.dto.CreateBdspRequest;
import org.emat.dto.UpdateBdspRequest;
import org.emat.entity.Bdsp;
import org.springframework.stereotype.Component;

@Component
public class BdspMapper {

    public Bdsp toEntity(CreateBdspRequest request) {
        return Bdsp.builder()
                .nameOfBdsp(request.getNameOfBdsp())
                .rationaleForOnboarding(request.getRationaleForOnboarding())
                .theme(request.getTheme())
                .areaOfServiceExpertise(request.getAreaOfServiceExpertise())
                .state(request.getState())
                .district(request.getDistrict())
                .contact(request.getContact())
                .email(request.getEmail())
                .kyc(request.getKyc())
                .isActive(true)
                .build();
    }

    public void applyUpdateRequest(Bdsp bdsp, UpdateBdspRequest request) {
        if (request.getNameOfBdsp() != null) bdsp.setNameOfBdsp(request.getNameOfBdsp());
        if (request.getRationaleForOnboarding() != null)
            bdsp.setRationaleForOnboarding(request.getRationaleForOnboarding());
        if (request.getTheme() != null) bdsp.setTheme(request.getTheme());
        if (request.getAreaOfServiceExpertise() != null)
            bdsp.setAreaOfServiceExpertise(request.getAreaOfServiceExpertise());
        if (request.getState() != null) bdsp.setState(request.getState());
        if (request.getDistrict() != null) bdsp.setDistrict(request.getDistrict());
        if (request.getContact() != null) bdsp.setContact(request.getContact());
        if (request.getEmail() != null) bdsp.setEmail(request.getEmail());
        if (request.getKyc() != null) bdsp.setKyc(request.getKyc());
    }

    public BdspResponse toResponse(Bdsp bdsp) {
        return BdspResponse.builder()
                .id(bdsp.getId())
                .nameOfBdsp(bdsp.getNameOfBdsp())
                .rationaleForOnboarding(bdsp.getRationaleForOnboarding())
                .theme(bdsp.getTheme())
                .areaOfServiceExpertise(bdsp.getAreaOfServiceExpertise())
                .state(bdsp.getState())
                .district(bdsp.getDistrict())
                .contact(bdsp.getContact())
                .email(bdsp.getEmail())
                .kyc(bdsp.getKyc())
                .createdAt(bdsp.getCreatedAt())
                .updatedAt(bdsp.getUpdatedAt())
                .createdBy(bdsp.getCreatedBy())
                .updatedBy(bdsp.getUpdatedBy())
                .isActive(bdsp.getIsActive())
                .build();
    }
}