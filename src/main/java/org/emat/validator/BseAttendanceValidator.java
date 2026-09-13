package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.BseAttendance;
import org.emat.entity.IndustryAssociationBseRecommendation;
import org.emat.repository.BseAttendanceRepository;
import org.emat.repository.IndustryAssociationBseRecommendationRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BseAttendanceValidator {

    private static final String BSE_RECOMMENDATION_NOT_FOUND = "BSE Recommendation not found";
    private static final String ATTENDANCE_NOT_FOUND = "Attendance not found";

    private final BseAttendanceRepository attendanceRepository;
    private final IndustryAssociationBseRecommendationRepository recommendationRepository;

    public IndustryAssociationBseRecommendation getRecommendationOrThrow(Long recommendationId) {
        return recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new RuntimeException(BSE_RECOMMENDATION_NOT_FOUND));
    }

    public BseAttendance getAttendanceOrThrow(Long attendanceId) {
        return attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException(ATTENDANCE_NOT_FOUND));
    }
}

