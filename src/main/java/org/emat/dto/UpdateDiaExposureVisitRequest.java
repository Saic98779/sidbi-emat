package org.emat.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdDecryptDeserializer;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

/** DTO for updating an existing DIA Exposure Visit. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDiaExposureVisitRequest {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    private Long roId;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    private Long industryAssociationId;

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
