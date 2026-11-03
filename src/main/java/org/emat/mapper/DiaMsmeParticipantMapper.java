package org.emat.mapper;

import org.emat.dto.CreateDiaMsmeParticipantRequest;
import org.emat.dto.DiaMsmeParticipantResponse;
import org.emat.dto.UpdateDiaMsmeParticipantRequest;
import org.emat.entity.DiaCapacityBuildingProgram;
import org.emat.entity.DiaMsmeParticipant;
import org.springframework.stereotype.Component;

@Component
public class DiaMsmeParticipantMapper {

    public DiaMsmeParticipant toEntity(
            CreateDiaMsmeParticipantRequest request, DiaCapacityBuildingProgram event) {
        return DiaMsmeParticipant.builder()
                .event(event)
                .msmeName(request.getMsmeName())
                .firstTimeParticipant(request.getFirstTimeParticipant())
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
            DiaMsmeParticipant participant, UpdateDiaMsmeParticipantRequest request) {
        if (request.getMsmeName() != null) participant.setMsmeName(request.getMsmeName());
        if (request.getFirstTimeParticipant() != null)
            participant.setFirstTimeParticipant(request.getFirstTimeParticipant());
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

    public DiaMsmeParticipantResponse toResponse(DiaMsmeParticipant participant) {
        DiaCapacityBuildingProgram event = participant.getEvent();
        return DiaMsmeParticipantResponse.builder()
                .id(participant.getId())
                .eventId(event != null ? event.getId() : null)
                .eventName(event != null ? event.getEventName() : null)
                .eventDate(event != null ? event.getEventDate() : null)
                .industryAssociationName(event != null ? event.getIndustryAssociationName() : null)
                .msmeName(participant.getMsmeName())
                .firstTimeParticipant(participant.getFirstTimeParticipant())
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
