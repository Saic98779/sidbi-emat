package org.emat.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for creating a new Pop-Ups. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePopUpsRequest {

    private String topic;
    private String relevanceOfPopUp;
    private LocalDate startDate;
    private LocalDate endDate;
    private String attachments;
    private String requestedBy;
    private LocalDate requestDate;
}