package org.emat.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.*;

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
