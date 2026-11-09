package org.emat.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

/** DTO for DIA Capacity Building Program response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaCapacityBuildingProgramResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long roId;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long industryAssociationId;

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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
}
