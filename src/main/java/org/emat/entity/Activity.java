package org.emat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ACTIVITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ACTIVITY")
    @SequenceGenerator(name = "SEQ_ACTIVITY", sequenceName = "SEQ_ACTIVITY", allocationSize = 1)
    @Column(name = "ACTIVITY_ID")
    private Long activityId;

    @Column(name = "ACTIVITY_TYPE")
    private String activityType;

    @Lob
    @Column(name = "DETAILS")
    private String details;

    @Column(name = "DATE_TIME")
    private LocalDateTime dateTime;

    @Column(name = "FOLLOW_UP_REQ")
    private Boolean followUpReq;

    @Column(name = "FOLLOW_UP_ID")
    private Long followUpId;

    @Column(name = "LOCATION_DETAILS")
    private String locationDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_USER_ID", referencedColumnName = "id")
    private User createdUser;

    @Column(name = "CREATED_DT_STAMP")
    private LocalDateTime createdDtStamp;

    @Column(name = "APPROVED_DT_STAMP")
    private LocalDateTime approvedDtStamp;

    @Column(name = "BSE_ID")
    private Long bseId;

    @Column(name = "GT_ID")
    private Long gtId;
}
