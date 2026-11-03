package org.emat.mapper;

import org.emat.dto.CreateDiaExposureVisitParticipantRequest;
import org.emat.dto.DiaExposureVisitParticipantResponse;
import org.emat.dto.UpdateDiaExposureVisitParticipantRequest;
import org.emat.entity.DiaExposureVisit;
import org.emat.entity.DiaExposureVisitParticipant;
import org.springframework.stereotype.Component;

@Component
public class DiaExposureVisitParticipantMapper {

    public DiaExposureVisitParticipant toEntity(
            CreateDiaExposureVisitParticipantRequest request, DiaExposureVisit exposureVisit) {
        return DiaExposureVisitParticipant.builder()
                .exposureVisit(exposureVisit)
                .msmeName(request.getMsmeName())
                .designation(request.getDesignation())
                .gender(request.getGender())
                .fatherHusbandName(request.getFatherHusbandName())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .contactNo(request.getContactNo())
                .panNo(request.getPanNo())
                .udyamAadharNo(request.getUdyamAadharNo())
                .isActive(true)
                .build();
    }

    public void applyUpdateRequest(
            DiaExposureVisitParticipant participant,
            UpdateDiaExposureVisitParticipantRequest request) {
        if (request.getMsmeName() != null) participant.setMsmeName(request.getMsmeName());
        if (request.getDesignation() != null) participant.setDesignation(request.getDesignation());
        if (request.getGender() != null) participant.setGender(request.getGender());
        if (request.getFatherHusbandName() != null)
            participant.setFatherHusbandName(request.getFatherHusbandName());
        if (request.getDateOfBirth() != null) participant.setDateOfBirth(request.getDateOfBirth());
        if (request.getEmail() != null) participant.setEmail(request.getEmail());
        if (request.getContactNo() != null) participant.setContactNo(request.getContactNo());
        if (request.getPanNo() != null) participant.setPanNo(request.getPanNo());
        if (request.getUdyamAadharNo() != null)
            participant.setUdyamAadharNo(request.getUdyamAadharNo());
    }

    public DiaExposureVisitParticipantResponse toResponse(DiaExposureVisitParticipant participant) {
        DiaExposureVisit exposureVisit = participant.getExposureVisit();
        return DiaExposureVisitParticipantResponse.builder()
                .id(participant.getId())
                .exposureVisitId(exposureVisit != null ? exposureVisit.getId() : null)
                .roName(exposureVisit != null ? exposureVisit.getRoName() : null)
                .industryAssociationName(
                        exposureVisit != null ? exposureVisit.getIndustryAssociationName() : null)
                .visitDate(exposureVisit != null ? exposureVisit.getVisitDate() : null)
                .msmeName(participant.getMsmeName())
                .designation(participant.getDesignation())
                .gender(participant.getGender())
                .fatherHusbandName(participant.getFatherHusbandName())
                .dateOfBirth(participant.getDateOfBirth())
                .email(participant.getEmail())
                .contactNo(participant.getContactNo())
                .panNo(participant.getPanNo())
                .udyamAadharNo(participant.getUdyamAadharNo())
                .createdAt(participant.getCreatedAt())
                .updatedAt(participant.getUpdatedAt())
                .createdBy(participant.getCreatedBy())
                .updatedBy(participant.getUpdatedBy())
                .isActive(participant.getIsActive())
                .build();
    }
}
