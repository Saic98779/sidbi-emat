package org.emat.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "DIA_CAPACITY_BUILDING_PROGRAM")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DiaCapacityBuildingProgram extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DIA_CAPACITY_BUILDING_PROGRAM")
    @SequenceGenerator(name = "SEQ_DIA_CAPACITY_BUILDING_PROGRAM", sequenceName = "SEQ_DIA_CAPACITY_BUILDING_PROGRAM", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "RO_ID")
    private Long roId;

    @Column(name = "INDUSTRY_ASSOCIATION_ID")
    private Long industryAssociationId;

    @Column(name = "RO_NAME")
    private String roName;

    @Column(name = "INDUSTRY_ASSOCIATION_NAME")
    private String industryAssociationName;

    // IA Member / Business Support Executive (BSE)
    @Column(name = "PARTICIPANT_TYPE")
    private String participantType;

    @Column(name = "EVENT_NAME")
    private String eventName;

    @Column(name = "EVENT_DATE")
    private LocalDate eventDate;

    // e.g. "2 days" or "6 hours"
    @Column(name = "EVENT_DURATION")
    private String eventDuration;

    // Virtual / Physical / Hybrid
    @Column(name = "EVENT_NATURE")
    private String eventNature;

    // Training / Awareness / Webinar / Workshop
    @Column(name = "EVENT_TYPE")
    private String eventType;

    // Productivity Improvement / Export Competitiveness / Energy Efficiency / Marketing / Skilling
    @Column(name = "EVENT_THEME")
    private String eventTheme;

    @Column(name = "EVENT_DESCRIPTION", length = 2000)
    private String eventDescription;

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

    @Column(name = "TRAINING_REPORT_PREPARED")
    private Boolean trainingReportPrepared;

    @Column(name = "TRAINING_REPORT_PATH")
    private String trainingReportPath;

    @Column(name = "FEEDBACK_FORM_SHARED")
    private Boolean feedbackFormShared;

    @Column(name = "UPLOADED_ON_DIA_PORTAL")
    private Boolean uploadedOnDiaPortal;
}