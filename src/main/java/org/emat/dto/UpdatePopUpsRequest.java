package org.emat.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for updating an existing Pop-Ups. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePopUpsRequest {

    private String topic;
    private String relevanceOfPopUp;
    private LocalDate startDate;
    private LocalDate endDate;
    private String attachments;
}