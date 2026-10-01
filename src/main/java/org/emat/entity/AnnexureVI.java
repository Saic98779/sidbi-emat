package org.emat.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Entity representing a single line item of Annexure VI - Indicative cost list of CAPEX
 * elements (e.g. CAPEX/Hard Interventions - Not more than 2 lakh per IA). Maps to the
 * ANNEXURE_VI table in Oracle database. Has a many-to-one relationship with
 * IndustryAssociationAppraisal.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ANNEXURE_VI")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AnnexureVI extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ANNEXURE_VI")
    @SequenceGenerator(
            name = "SEQ_ANNEXURE_VI",
            sequenceName = "SEQ_ANNEXURE_VI",
            allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPRAISAL_ID", nullable = false)
    private IndustryAssociationAppraisal appraisal;


    @Column(name = "INDICATIVE_ITEM", length = 375)
    private String indicativeItem;

    @Column(name = "NUMBERS")
    private Integer numbers;

    @Column(name = "MAKE", length = 375)
    private String make;

    @Column(name = "MAXIMUM_COST", precision = 15, scale = 2)
    private BigDecimal maximumCost;

}