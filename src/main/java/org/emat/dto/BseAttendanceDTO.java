package org.emat.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BseAttendanceDTO {

    private UUID uuid;

    private UUID bseRecommendationId;

    private LocalDate attendanceDate;

    private LocalTime inTime;

    private LocalTime outTime;
}