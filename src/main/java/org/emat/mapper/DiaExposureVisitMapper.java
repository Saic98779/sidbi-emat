package org.emat.mapper;

import org.emat.dto.CreateDiaExposureVisitRequest;
import org.emat.dto.DiaExposureVisitResponse;
import org.emat.dto.UpdateDiaExposureVisitRequest;
import org.emat.entity.DiaExposureVisit;
import org.springframework.stereotype.Component;

@Component
public class DiaExposureVisitMapper {

    public DiaExposureVisit toEntity(CreateDiaExposureVisitRequest request) {
        return DiaExposureVisit.builder()
                .roName(request.getRoName())
                .industryAssociationName(request.getIndustryAssociationName())
                .visitDate(request.getVisitDate())
                .visitDuration(request.getVisitDuration())
                .theme(request.getTheme())
                .description(request.getDescription())
                .partOfDia(request.getPartOfDia())
                .costInterventionType(request.getCostInterventionType())
                .associationContribution(request.getAssociationContribution())
                .costBreakup(request.getCostBreakup())
                .trainerName(request.getTrainerName())
                .attendeesCount(request.getAttendeesCount())
                .visitReportPrepared(request.getVisitReportPrepared())
                .visitReportPath(request.getVisitReportPath())
                .feedbackCollected(request.getFeedbackCollected())
                .feedbackPath(request.getFeedbackPath())
                .isActive(true)
                .build();
    }

    public void applyUpdateRequest(DiaExposureVisit visit, UpdateDiaExposureVisitRequest request) {
        if (request.getRoName() != null) visit.setRoName(request.getRoName());
        if (request.getIndustryAssociationName() != null)
            visit.setIndustryAssociationName(request.getIndustryAssociationName());
        if (request.getVisitDate() != null) visit.setVisitDate(request.getVisitDate());
        if (request.getVisitDuration() != null) visit.setVisitDuration(request.getVisitDuration());
        if (request.getTheme() != null) visit.setTheme(request.getTheme());
        if (request.getDescription() != null) visit.setDescription(request.getDescription());
        if (request.getPartOfDia() != null) visit.setPartOfDia(request.getPartOfDia());
        if (request.getCostInterventionType() != null)
            visit.setCostInterventionType(request.getCostInterventionType());
        if (request.getAssociationContribution() != null)
            visit.setAssociationContribution(request.getAssociationContribution());
        if (request.getCostBreakup() != null) visit.setCostBreakup(request.getCostBreakup());
        if (request.getTrainerName() != null) visit.setTrainerName(request.getTrainerName());
        if (request.getAttendeesCount() != null)
            visit.setAttendeesCount(request.getAttendeesCount());
        if (request.getVisitReportPrepared() != null)
            visit.setVisitReportPrepared(request.getVisitReportPrepared());
        if (request.getVisitReportPath() != null)
            visit.setVisitReportPath(request.getVisitReportPath());
        if (request.getFeedbackCollected() != null)
            visit.setFeedbackCollected(request.getFeedbackCollected());
        if (request.getFeedbackPath() != null) visit.setFeedbackPath(request.getFeedbackPath());
    }

    public DiaExposureVisitResponse toResponse(DiaExposureVisit visit) {
        return DiaExposureVisitResponse.builder()
                .id(visit.getId())
                .roName(visit.getRoName())
                .industryAssociationName(visit.getIndustryAssociationName())
                .visitDate(visit.getVisitDate())
                .visitDuration(visit.getVisitDuration())
                .theme(visit.getTheme())
                .description(visit.getDescription())
                .partOfDia(visit.getPartOfDia())
                .costInterventionType(visit.getCostInterventionType())
                .associationContribution(visit.getAssociationContribution())
                .costBreakup(visit.getCostBreakup())
                .trainerName(visit.getTrainerName())
                .attendeesCount(visit.getAttendeesCount())
                .visitReportPrepared(visit.getVisitReportPrepared())
                .visitReportPath(visit.getVisitReportPath())
                .feedbackCollected(visit.getFeedbackCollected())
                .feedbackPath(visit.getFeedbackPath())
                .createdAt(visit.getCreatedAt())
                .updatedAt(visit.getUpdatedAt())
                .createdBy(visit.getCreatedBy())
                .updatedBy(visit.getUpdatedBy())
                .isActive(visit.getIsActive())
                .build();
    }
}
