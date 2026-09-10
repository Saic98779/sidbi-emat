package org.emat.mapper;

import org.emat.dto.BseAttendanceDTO;
import org.emat.entity.BseAttendance;
import org.emat.entity.IndustryAssociationBseRecommendation;
import org.springframework.stereotype.Component;

@Component
public class BseAttendanceMapper {

    public BseAttendance toEntity(BseAttendanceDTO request, IndustryAssociationBseRecommendation recommendation) {
        BseAttendance attendance = new BseAttendance();
        attendance.setBseRecommendation(recommendation);
        attendance.setAttendanceDate(request.getAttendanceDate());
        attendance.setInTime(request.getInTime());
        attendance.setOutTime(request.getOutTime());
        return attendance;
    }

    public void updateEntityFromRequest(
            BseAttendanceDTO request,
            BseAttendance attendance,
            IndustryAssociationBseRecommendation recommendation) {
        attendance.setBseRecommendation(recommendation);
        attendance.setAttendanceDate(request.getAttendanceDate());
        attendance.setInTime(request.getInTime());
        attendance.setOutTime(request.getOutTime());
    }

    public BseAttendanceDTO toDto(BseAttendance attendance) {
        return BseAttendanceDTO.builder()
                .id(attendance.getId())
                .bseRecommendationId(attendance.getBseRecommendation().getId())
                .attendanceDate(attendance.getAttendanceDate())
                .inTime(attendance.getInTime())
                .outTime(attendance.getOutTime())
                .build();
    }
}

