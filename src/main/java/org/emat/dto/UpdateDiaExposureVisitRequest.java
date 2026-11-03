package org.emat.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for updating an existing DIA Exposure Visit. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDiaExposureVisitRequest {

    private String roName;
    private String industryAssociationName;
    private LocalDate visitDate;
    private String visitDuration;
    private String theme;
    private String description;
    private Boolean partOfDia;
    private String costInterventionType;
    private BigDecimal associationContribution;
    private String costBreakup;
    private String trainerName;
    private Integer attendeesCount;
    private Boolean visitReportPrepared;
    private String visitReportPath;
    private Boolean feedbackCollected;
    private String feedbackPath;
}
