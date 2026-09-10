package org.emat.mapper;

import org.emat.dto.BseAttendanceManualRequestDTO;
import org.emat.entity.BseAttendance;
import org.emat.entity.BseAttendanceManualRequest;
import org.emat.entity.IndustryAssociationBseRecommendation;
import org.springframework.stereotype.Component;

@Component
public class BseAttendanceManualRequestMapper {

    public BseAttendanceManualRequest toEntity(
            BseAttendanceManualRequestDTO dto,
            IndustryAssociationBseRecommendation recommendation) {
        BseAttendanceManualRequest entity = new BseAttendanceManualRequest();
        updateEntityFromRequest(dto, entity, recommendation);
        return entity;
    }

    public void updateEntityFromRequest(
            BseAttendanceManualRequestDTO dto,
            BseAttendanceManualRequest entity,
            IndustryAssociationBseRecommendation recommendation) {
        entity.setBseRecommendation(recommendation);
        entity.setAttendanceDate(dto.getAttendanceDate());
        entity.setInTime(dto.getInTime());
        entity.setOutTime(dto.getOutTime());
        entity.setReason(dto.getReason());
        entity.setIsApproved(dto.getIsApproved());
        entity.setApprovedDate(dto.getApprovedDate());
        entity.setApprovedBy(dto.getApprovedBy());
    }

    public BseAttendance toAttendanceEntity(BseAttendanceManualRequest request) {
        BseAttendance attendance = new BseAttendance();
        attendance.setBseRecommendation(request.getBseRecommendation());
        attendance.setAttendanceDate(request.getAttendanceDate());
        attendance.setInTime(request.getInTime());
        attendance.setOutTime(request.getOutTime());
        return attendance;
    }

    public BseAttendanceManualRequestDTO toDto(BseAttendanceManualRequest entity) {
        return BseAttendanceManualRequestDTO.builder()
                .id(entity.getId())
                .bseRecommendationId(entity.getBseRecommendation().getId())
                .attendanceDate(entity.getAttendanceDate())
                .inTime(entity.getInTime())
                .outTime(entity.getOutTime())
                .reason(entity.getReason())
                .isApproved(entity.getIsApproved())
                .approvedDate(entity.getApprovedDate())
                .approvedBy(entity.getApprovedBy())
                .build();
    }
}

