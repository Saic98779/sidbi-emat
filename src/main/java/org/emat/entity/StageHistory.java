package org.emat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "STAGE_HISTORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StageHistory {

    @Id
    @SequenceGenerator(
            name = "stage_history_seq",
            sequenceName = "STAGE_HISTORY_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "stage_history_seq"
    )
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGISTRATION_ID", nullable = false)
    private IndustryAssociationRegistration registration;

    @Column(name = "STAGE")
    private String stage;

    @Column(name = "SUB_STAGE")
    private String subStage;

    @Column(name = "STAGE_TIME")
    private LocalDateTime time;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "COMMENTS", length = 2000)
    private String comment;
}