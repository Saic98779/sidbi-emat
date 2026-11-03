package org.emat.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "DIA_ENHANCEMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DiaEnhancement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DIA_ENHANCEMENT")
    @SequenceGenerator(name = "SEQ_DIA_ENHANCEMENT", sequenceName = "SEQ_DIA_ENHANCEMENT", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    // DIA portal, eMAT, Referral Module etc.
    @Column(name = "PLATFORM")
    private String platform;

    @Column(name = "ENHANCEMENT_TYPE")
    private String enhancementType;

    @Column(name = "ENHANCEMENT_DETAILS", length = 2000)
    private String enhancementDetails;

    @Column(name = "INITIATION_DATE")
    private LocalDate initiationDate;

    @Column(name = "COMPLETION_DATE")
    private LocalDate completionDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "POC_SIDBI")
    private String pocSidbi;

    @Column(name = "POC_GT")
    private String pocGt;
}