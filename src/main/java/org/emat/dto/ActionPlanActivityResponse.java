package org.emat.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

/** DTO for an Activity under an Action Plan response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionPlanActivityResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    private Integer activityNo;
    private String nameOfActivity;
    private String monthToBeHeld;
    private String technicalServiceProvider;
    private BigDecimal totalCost;
    private BigDecimal percentSupportBySidbi;
    private BigDecimal percentSupportByOthers;
    private BigDecimal percentContributionByIa;
    private Integer expectedParticipantMembers;
    private Integer expectedParticipantNonMembers;
    private String expectedOutput;
    private String expectedOutcome;
    private String expectedIncomeGeneratingActivity;
}
