package org.emat.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BseAttendanceDTO {
    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;
    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long bseRecommendationId;

    private LocalDate attendanceDate;

    private LocalTime inTime;

    private LocalTime outTime;
}
