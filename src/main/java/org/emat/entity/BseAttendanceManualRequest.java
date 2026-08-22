package org.emat.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "BSE_ATTENDANCE_MANUAL_REQ")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BseAttendanceManualRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BSE_ATTENDANCE_MANUAL_REQ")
    @SequenceGenerator(name = "SEQ_BSE_ATTENDANCE_MANUAL_REQ", sequenceName = "SEQ_BSE_ATTENDANCE_MANUAL_REQ", allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BSE_ID", nullable = false)
    private IndustryAssociationBseRecommendation bseRecommendation;

    @Column(name = "ATTENDANCE_DATE", nullable = false)
    private LocalDate attendanceDate;

    @Column(name = "IN_TIME")
    private LocalTime inTime;

    @Column(name = "OUT_TIME")
    private LocalTime outTime;

    @Column(name = "REASON", length = 1000)
    private String reason;

    @Column(name = "IS_APPROVED")
    private Boolean isApproved;

    @Column(name = "APPROVED_DATE")
    private LocalDateTime approvedDate;

    @Column(name = "APPROVED_BY")
    private Long approvedBy;
}