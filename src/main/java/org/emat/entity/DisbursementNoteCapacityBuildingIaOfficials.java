package org.emat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.emat.enums.Status;

/**
 * Disbursement Note Format for Capacity Building of IA officials
 */
@Entity
@Table(name = "DISBURSEMENT_NOTE_CB_IA_OFFICIALS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DisbursementNoteCapacityBuildingIaOfficials extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DISBURSEMENT_NOTE_CB_IAO")
    @SequenceGenerator(
            name = "SEQ_DISBURSEMENT_NOTE_CB_IAO",
            sequenceName = "SEQ_DISBURSEMENT_NOTE_CB_IAO",
            allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    /** Approved Event Management Agency */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGISTRATION_ID", nullable = false)
    private IndustryAssociationRegistration registration;

    // Event Management Agency Name
    @Column(name = "EVENT_MANAGEMENT_AGENCY_NAME")
    private String eventManagementAgencyName;

    // GSTIN of the Agency
    @Column(name = "GSTIN_OF_AGENCY")
    private String gstinOfAgency;

    // If GSTIN not applicable (Reason for not applicable)
    @Column(name = "GSTIN_NOT_APPLICABLE_REASON")
    private String gstinNotApplicableReason;

    // GSTIN of SIDBI
    @Column(name = "GSTIN_OF_SIDBI")
    private String gstinOfSidbi;

    // Sanctioned Amount (in Rs.)
    @Column(name = "SANCTIONED_AMOUNT")
    private BigDecimal sanctionedAmount;

    // Disbursed till date (in Rs.)
    @Column(name = "DISBURSED_TILL_DATE")
    private BigDecimal disbursedTillDate;

    // Disbursement sought (in Rs.)
    @Column(name = "DISBURSEMENT_SOUGHT")
    private BigDecimal disbursementSought;

    // Nature of Payment
    @Column(name = "NATURE_OF_PAYMENT")
    private String natureOfPayment;

    // Invoice Date
    @Column(name = "INVOICE_DATE")
    private LocalDate invoiceDate;

    // Invoice Number
    @Column(name = "INVOICE_NUMBER")
    private String invoiceNumber;

    // Value of service/Items supplied
    @Column(name = "VALUE_OF_SERVICE_ITEMS_SUPPLIED")
    private BigDecimal valueOfServiceItemsSupplied;

    // IGST @18%
    @Column(name = "IGST_AT_18_PERCENT")
    private BigDecimal igstAt18Percent;

    // Total amount
    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    // Applicability of TDS
    @Column(name = "TDS_APPLICABLE")
    private Boolean tdsApplicable;

    // If TDS not applicable (Reason for not applicable)
    @Column(name = "TDS_NOT_APPLICABLE_REASON")
    private String tdsNotApplicableReason;

    // Amount Recommended for Disbursement (in Rs.)
    @Column(name = "AMOUNT_RECOMMENDED_FOR_DISBURSEMENT")
    private BigDecimal amountRecommendedForDisbursement;

    // Account Code payment to be made
    @Column(name = "ACCOUNT_CODE_FOR_PAYMENT")
    private String accountCodeForPayment;

    // GT Comments on event organisation and its outcome and impact
    @Column(name = "GT_COMMENTS_ON_EVENT_OUTCOME_IMPACT")
    private String gtCommentsOnEventOutcomeImpact;

    // Compliance of Pre-disbursement Terms and conditions
    @Column(name = "COMPLIANCE_PRE_DISBURSEMENT_TERMS")
    private String compliancePreDisbursementTerms;

    // Recommendation
    @Column(name = "RECOMMENDATION")
    private String recommendation;

    @Column(name = "STATUS")
    private Status status;

    @Column(name = "APPROVED_DATE")
    private LocalDate approvedDate;
}