package org.emat.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "DIA_EXPOSURE_VISIT")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DiaExposureVisit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DIA_EXPOSURE_VISIT")
    @SequenceGenerator(name = "SEQ_DIA_EXPOSURE_VISIT", sequenceName = "SEQ_DIA_EXPOSURE_VISIT", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "RO_NAME")
    private String roName;

    @Column(name = "INDUSTRY_ASSOCIATION_NAME")
    private String industryAssociationName;

    @Column(name = "VISIT_DATE")
    private LocalDate visitDate;

    // e.g. "2 days" or "6 hours"
    @Column(name = "VISIT_DURATION")
    private String visitDuration;

    // Productivity Improvement / Export Competitiveness / Energy Efficiency / Marketing / Skilling
    @Column(name = "THEME")
    private String theme;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "PART_OF_DIA")
    private Boolean partOfDia;

    // DIA Cost Intervention / No Cost Intervention
    @Column(name = "COST_INTERVENTION_TYPE")
    private String costInterventionType;

    @Column(name = "ASSOCIATION_CONTRIBUTION", precision = 15, scale = 2)
    private BigDecimal associationContribution;

    @Column(name = "COST_BREAKUP", length = 2000)
    private String costBreakup;

    @Column(name = "TRAINER_NAME")
    private String trainerName;

    @Column(name = "ATTENDEES_COUNT")
    private Integer attendeesCount;

    @Column(name = "VISIT_REPORT_PREPARED")
    private Boolean visitReportPrepared;

    @Column(name = "VISIT_REPORT_PATH")
    private String visitReportPath;

    @Column(name = "FEEDBACK_COLLECTED")
    private Boolean feedbackCollected;

    @Column(name = "FEEDBACK_PATH")
    private String feedbackPath;
}