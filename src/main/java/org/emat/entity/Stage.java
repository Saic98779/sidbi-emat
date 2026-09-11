package org.emat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "STAGE")
public class Stage {

    @Id
    @SequenceGenerator(name = "stage_seq", sequenceName = "STAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stage_seq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "STAGE")
    private String stage;

    @Column(name = "SUB_STAGE")
    private String subStage;
}
