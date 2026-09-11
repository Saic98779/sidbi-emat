package org.emat.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BseAttendanceDTO {

    private Long id;

    private Long bseRecommendationId;

    private LocalDate attendanceDate;

    private LocalTime inTime;

    private LocalTime outTime;
}
