package org.emat.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Entity representing a single line item of Annexure V - Indicative list of cost of
 * Soft Interventions (for both CBO and CBM activities). Maps to the ANNEXURE_V table
 * in Oracle database. Has a many-to-one relationship with IndustryAssociationAppraisal.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ANNEXURE_V")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AnnexureV extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ANNEXURE_V")
    @SequenceGenerator(
            name = "SEQ_ANNEXURE_V",
            sequenceName = "SEQ_ANNEXURE_V",
            allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPRAISAL_ID", nullable = false)
    private IndustryAssociationAppraisal appraisal;

    // SN
    @Column(name = "SN_NO")
    private Integer snNo;

    @Column(name = "PARTICULARS", length = 500)
    private String particulars;

    @Column(name = "TOTAL_COST", precision = 15, scale = 2)
    private BigDecimal totalCost;

    @Column(name = "SIDBI_SUPPORT", precision = 15, scale = 2)
    private BigDecimal sidbiSupport;
}