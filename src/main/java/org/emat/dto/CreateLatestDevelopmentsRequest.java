package org.emat.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for creating a new Latest Developments. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLatestDevelopmentsRequest {

    private String topic;
    private String relevanceOfTopic;
    private LocalDate startDate;
    private LocalDate endDate;
    private String requestedBy;
    private LocalDate requestDate;
}