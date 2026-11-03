package org.emat.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for updating an existing DIA Capacity Building Program. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDiaCapacityBuildingProgramRequest {

    private String roName;
    private String industryAssociationName;
    private String participantType;
    private String eventName;
    private LocalDate eventDate;
    private String eventDuration;
    private String eventNature;
    private String eventType;
    private String eventTheme;
    private String eventDescription;
    private Boolean partOfDia;
    private String costInterventionType;
    private BigDecimal associationContribution;
    private String costBreakup;
    private String trainerName;
    private Integer attendeesCount;
    private Boolean trainingReportPrepared;
    private String trainingReportPath;
    private Boolean feedbackFormShared;
    private Boolean uploadedOnDiaPortal;
}
