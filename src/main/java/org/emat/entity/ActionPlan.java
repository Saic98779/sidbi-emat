package org.emat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.emat.enums.Status;

import java.time.LocalDate;
import java.util.List;

/**
 * Action Plan
 */
@Entity
@Table(name = "ACTION_PLAN")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ActionPlan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ACTION_PLAN")
    @SequenceGenerator(
            name = "SEQ_ACTION_PLAN",
            sequenceName = "SEQ_ACTION_PLAN",
            allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    // State
    @Column(name = "STATE")
    private String state;

    /** Name of Industry Association (IA) - mapped with State */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGISTRATION_ID", nullable = false)
    private IndustryAssociationRegistration registration;

    // Activities under this Action Plan (Activity 1, Activity 2, ...)
    @OneToMany(mappedBy = "actionPlan", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ActionPlanActivity> activities;

    @Column(name = "STATUS")
    private Status status;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "APPROVED_DATE")
    private LocalDate approvedDate;
}