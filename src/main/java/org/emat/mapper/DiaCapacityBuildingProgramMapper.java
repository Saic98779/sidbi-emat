package org.emat.mapper;

import org.emat.dto.CreateDiaCapacityBuildingProgramRequest;
import org.emat.dto.DiaCapacityBuildingProgramResponse;
import org.emat.dto.UpdateDiaCapacityBuildingProgramRequest;
import org.emat.entity.DiaCapacityBuildingProgram;
import org.springframework.stereotype.Component;

@Component
public class DiaCapacityBuildingProgramMapper {

    public DiaCapacityBuildingProgram toEntity(CreateDiaCapacityBuildingProgramRequest request) {
        return DiaCapacityBuildingProgram.builder()
                .roId(request.getRoId())
                .industryAssociationId(request.getIndustryAssociationId())
                .roName(request.getRoName())
                .industryAssociationName(request.getIndustryAssociationName())
                .participantType(request.getParticipantType())
                .eventName(request.getEventName())
                .eventDate(request.getEventDate())
                .eventDuration(request.getEventDuration())
                .eventNature(request.getEventNature())
                .eventType(request.getEventType())
                .eventTheme(request.getEventTheme())
                .eventDescription(request.getEventDescription())
                .partOfDia(request.getPartOfDia())
                .costInterventionType(request.getCostInterventionType())
                .associationContribution(request.getAssociationContribution())
                .costBreakup(request.getCostBreakup())
                .trainerName(request.getTrainerName())
                .attendeesCount(request.getAttendeesCount())
                .trainingReportPrepared(request.getTrainingReportPrepared())
                .trainingReportPath(request.getTrainingReportPath())
                .feedbackFormShared(request.getFeedbackFormShared())
                .uploadedOnDiaPortal(request.getUploadedOnDiaPortal())
                .isActive(true)
                .build();
    }

    public void applyUpdateRequest(
            DiaCapacityBuildingProgram program, UpdateDiaCapacityBuildingProgramRequest request) {
        if (request.getRoId() != null) program.setRoId(request.getRoId());
        if (request.getIndustryAssociationId() != null)
            program.setIndustryAssociationId(request.getIndustryAssociationId());
        if (request.getRoName() != null) program.setRoName(request.getRoName());
        if (request.getIndustryAssociationName() != null)
            program.setIndustryAssociationName(request.getIndustryAssociationName());
        if (request.getParticipantType() != null)
            program.setParticipantType(request.getParticipantType());
        if (request.getEventName() != null) program.setEventName(request.getEventName());
        if (request.getEventDate() != null) program.setEventDate(request.getEventDate());
        if (request.getEventDuration() != null)
            program.setEventDuration(request.getEventDuration());
        if (request.getEventNature() != null) program.setEventNature(request.getEventNature());
        if (request.getEventType() != null) program.setEventType(request.getEventType());
        if (request.getEventTheme() != null) program.setEventTheme(request.getEventTheme());
        if (request.getEventDescription() != null)
            program.setEventDescription(request.getEventDescription());
        if (request.getPartOfDia() != null) program.setPartOfDia(request.getPartOfDia());
        if (request.getCostInterventionType() != null)
            program.setCostInterventionType(request.getCostInterventionType());
        if (request.getAssociationContribution() != null)
            program.setAssociationContribution(request.getAssociationContribution());
        if (request.getCostBreakup() != null) program.setCostBreakup(request.getCostBreakup());
        if (request.getTrainerName() != null) program.setTrainerName(request.getTrainerName());
        if (request.getAttendeesCount() != null)
            program.setAttendeesCount(request.getAttendeesCount());
        if (request.getTrainingReportPrepared() != null)
            program.setTrainingReportPrepared(request.getTrainingReportPrepared());
        if (request.getTrainingReportPath() != null)
            program.setTrainingReportPath(request.getTrainingReportPath());
        if (request.getFeedbackFormShared() != null)
            program.setFeedbackFormShared(request.getFeedbackFormShared());
        if (request.getUploadedOnDiaPortal() != null)
            program.setUploadedOnDiaPortal(request.getUploadedOnDiaPortal());
    }

    public DiaCapacityBuildingProgramResponse toResponse(DiaCapacityBuildingProgram program) {
        return DiaCapacityBuildingProgramResponse.builder()
                .id(program.getId())
                .roId(program.getRoId())
                .industryAssociationId(program.getIndustryAssociationId())
                .roName(program.getRoName())
                .industryAssociationName(program.getIndustryAssociationName())
                .participantType(program.getParticipantType())
                .eventName(program.getEventName())
                .eventDate(program.getEventDate())
                .eventDuration(program.getEventDuration())
                .eventNature(program.getEventNature())
                .eventType(program.getEventType())
                .eventTheme(program.getEventTheme())
                .eventDescription(program.getEventDescription())
                .partOfDia(program.getPartOfDia())
                .costInterventionType(program.getCostInterventionType())
                .associationContribution(program.getAssociationContribution())
                .costBreakup(program.getCostBreakup())
                .trainerName(program.getTrainerName())
                .attendeesCount(program.getAttendeesCount())
                .trainingReportPrepared(program.getTrainingReportPrepared())
                .trainingReportPath(program.getTrainingReportPath())
                .feedbackFormShared(program.getFeedbackFormShared())
                .uploadedOnDiaPortal(program.getUploadedOnDiaPortal())
                .createdAt(program.getCreatedAt())
                .updatedAt(program.getUpdatedAt())
                .createdBy(program.getCreatedBy())
                .updatedBy(program.getUpdatedBy())
                .isActive(program.getIsActive())
                .build();
    }
}
