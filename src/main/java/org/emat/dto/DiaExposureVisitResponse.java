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

/** DTO for DIA Exposure Visit response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaExposureVisitResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
}
