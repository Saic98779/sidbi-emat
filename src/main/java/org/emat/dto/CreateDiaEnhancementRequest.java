package org.emat.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for creating a new DIA Enhancement. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDiaEnhancementRequest {

    private String platform;
    private String enhancementType;
    private String enhancementDetails;
    private LocalDate initiationDate;
    private LocalDate completionDate;
    private String status;
    private String pocSidbi;
    private String pocGt;
}
