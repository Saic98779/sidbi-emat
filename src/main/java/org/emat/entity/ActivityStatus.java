package org.emat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ACTIVITY_STATUS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ACTIVITY_STATUS")
    @SequenceGenerator(name = "SEQ_ACTIVITY_STATUS", sequenceName = "SEQ_ACTIVITY_STATUS", allocationSize = 1)
    @Column(name = "STATUS_ID")
    private Long statusId;

    @Column(name = "ACTIVITY_ID", nullable = false)
    private Long activityId;

    @Column(name = "FOLLOWUP_ACTIVITY_ID")
    private Long followupActivityId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "STATUS_UPDATED_BY_ROLE")
    private String statusUpdatedByRole;

    @Column(name = "STATUS_APPROVAL_REQUIRED")
    private Boolean statusApprovalRequired;

    @Column(name = "STATUS_UPDATED_DT_STAMP")
    private LocalDateTime statusUpdatedDtStamp;

    @Column(name = "STATUS_REMARKS")
    private String statusRemarks;
}
