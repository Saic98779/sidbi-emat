package org.emat.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "VENDOR_EXPENDITURE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VendorExpenditure extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID", nullable = false, updatable = false)
    private UUID uuid;

    // Industry Association Registration
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGISTRATION_UUID", nullable = false, unique = true)
    private IndustryAssociationRegistration registration;

    // GSTIN of Industry Association
    @Column(name = "GSTIN_IA", length = 15)
    private String gstinIa;

    // GSTIN not applicable
    @Column(name = "GSTIN_NOT_APPLICABLE")
    private Boolean gstinNotApplicable;

    // Reason for GSTIN not applicable
    @Column(name = "GSTIN_NOT_APPLICABLE_REASON", length = 500)
    private String gstinNotApplicableReason;

    // GSTIN of SIDBI
    @Column(name = "GSTIN_SIDBI", length = 15)
    private String gstinSidbi;

    // Sanctioned Amount
    @Column(name = "SANCTIONED_AMOUNT", precision = 15, scale = 2)
    private BigDecimal sanctionedAmount;

    // Disbursed till date
    @Column(name = "DISBURSED_TILL_DATE", precision = 15, scale = 2)
    private BigDecimal disbursedTillDate;

    // Disbursement sought
    @Column(name = "DISBURSEMENT_SOUGHT", precision = 15, scale = 2)
    private BigDecimal disbursementSought;

    // Nature of Payment
    @Column(name = "NATURE_OF_PAYMENT", length = 2000)
    private String natureOfPayment;

    // Invoice Date
    @Column(name = "INVOICE_DATE")
    private LocalDate invoiceDate;

    // Invoice Number
    @Column(name = "INVOICE_NUMBER", length = 100)
    private String invoiceNumber;

    // Details of Items
    @Column(name = "DETAILS_OF_ITEMS", length = 1000)
    private String detailsOfItems;

    // Value of service/items supplied
    @Column(name = "VALUE_OF_SERVICE_ITEMS", precision = 15, scale = 2)
    private BigDecimal valueOfServiceItems;

    // IGST @18%
    @Column(name = "IGST_AMOUNT", precision = 15, scale = 2)
    private BigDecimal igstAmount;

    // Total Amount
    @Column(name = "TOTAL_AMOUNT", precision = 15, scale = 2)
    private BigDecimal totalAmount;

    // Applicability of TDS
    @Column(name = "TDS_APPLICABLE")
    private Boolean tdsApplicable;

    // Reason for TDS not applicable
    @Column(name = "TDS_NOT_APPLICABLE_REASON", length = 500)
    private String tdsNotApplicableReason;

    // Amount Recommended for Disbursement
    @Column(name = "AMOUNT_RECOMMENDED_FOR_DISBURSEMENT", precision = 15, scale = 2)
    private BigDecimal amountRecommendedForDisbursement;

    // Account Code payment to be made
    @Column(name = "ACCOUNT_CODE", length = 100)
    private String accountCode;

    // GT Comments on CAPEX Verification in IA premises
    @Column(name = "GT_CAPEX_VERIFICATION_COMMENTS", length = 2000)
    private String gtCapexVerificationComments;

    // Compliance of Pre-disbursement Terms and Conditions
    @Column(name = "PRE_DISBURSEMENT_COMPLIANCE", length = 2000)
    private String preDisbursementCompliance;

    // Recommendation
    @Column(name = "RECOMMENDATION", length = 2000)
    private String recommendation;
}