package org.emat.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for updating an Activity under an Action Plan. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateActionPlanActivityRequest {

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
