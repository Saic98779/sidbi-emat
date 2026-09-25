package org.emat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * Action Plan Activity - one row per Activity (Activity 1, Activity 2, ...) under an Action Plan.
 */
@Entity
@Table(name = "ACTION_PLAN_ACTIVITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ActionPlanActivity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ACTION_PLAN_ACTIVITY")
    @SequenceGenerator(
            name = "SEQ_ACTION_PLAN_ACTIVITY",
            sequenceName = "SEQ_ACTION_PLAN_ACTIVITY",
            allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    /** Parent Action Plan */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTION_PLAN_ID", nullable = false)
    private ActionPlan actionPlan;

    // Activity number (Activity 1 = 1, Activity 2 = 2, ...)
    @Column(name = "ACTIVITY_NO")
    private Integer activityNo;

    // Name of the Activity
    @Column(name = "NAME_OF_ACTIVITY")
    private String nameOfActivity;

    // Month to be held
    @Column(name = "MONTH_TO_BE_HELD")
    private String monthToBeHeld;

    // Technical Service Provider
    @Column(name = "TECHNICAL_SERVICE_PROVIDER")
    private String technicalServiceProvider;

    // Total Cost
    @Column(name = "TOTAL_COST")
    private BigDecimal totalCost;

    // Percentage support by SIDBI
    @Column(name = "PERCENT_SUPPORT_BY_SIDBI")
    private BigDecimal percentSupportBySidbi;

    // Percentage support by Others
    @Column(name = "PERCENT_SUPPORT_BY_OTHERS")
    private BigDecimal percentSupportByOthers;

    // Percentage contribution by IA
    @Column(name = "PERCENT_CONTRIBUTION_BY_IA")
    private BigDecimal percentContributionByIa;

    // Expected No. of participant members
    @Column(name = "EXPECTED_PARTICIPANT_MEMBERS")
    private Integer expectedParticipantMembers;

    // Expected No. of participant non-members
    @Column(name = "EXPECTED_PARTICIPANT_NON_MEMBERS")
    private Integer expectedParticipantNonMembers;

    // Expected Output
    @Column(name = "EXPECTED_OUTPUT", length = 2000)
    private String expectedOutput;

    // Expected Outcome
    @Column(name = "EXPECTED_OUTCOME", length = 2000)
    private String expectedOutcome;

    // Expected Income-generating activity now/in the future
    @Column(name = "EXPECTED_INCOME_GENERATING_ACTIVITY", length = 2000)
    private String expectedIncomeGeneratingActivity;
}