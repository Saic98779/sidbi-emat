package org.emat.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BseAttendanceManualRequestDTO {

    private Long id;

    private Long bseRecommendationId;

    private LocalDate attendanceDate;

    private LocalTime inTime;

    private LocalTime outTime;

    private String reason;

    private Boolean isApproved;

    private LocalDateTime approvedDate;

    private Long approvedBy;
}