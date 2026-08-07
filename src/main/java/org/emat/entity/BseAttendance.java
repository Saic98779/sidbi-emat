package org.emat.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "BSE_ATTENDANCE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BseAttendance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID", nullable = false, updatable = false)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BSE_ID", nullable = false)
    private IndustryAssociationBseRecommendation bseRecommendation;

    @Column(name = "ATTENDANCE_DATE", nullable = false)
    private LocalDate attendanceDate;

    @Column(name = "IN_TIME")
    private LocalTime inTime;

    @Column(name = "OUT_TIME")
    private LocalTime outTime;
}