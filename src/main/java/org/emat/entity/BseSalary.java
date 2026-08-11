package org.emat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "BSE_SALARY")
public class BseSalary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "GSTIN_OF_AGENCY")
    private String gstinOfAgency;

    @Column(name = "REASON_FOR_NO_GSTIN")
    private String reasonForNoGstin;

    @Column(name = "GSTIN_OF_SIDBI")
    private String gstinOfSdbi;

    @Column(name = "SANCTIONED_AMOUNT")
    private BigDecimal sanctionedAmount;

    @Column(name = "DISBURSED_TILL_DATE")
    private BigDecimal disbursedTillDate;

    @Column(name = "DISBURSEMENT_SOUGHT_IN")
    private BigDecimal disbursementSoughtIn;

    @Column(name = "NATURE_OF_PAYMENT", length =2000)
    private String natureOfPayment;

    // Invoice details
    @Column(name = "INVOICE_DATE")
    private LocalDate invoiceDate;

    @Column(name = "INVOICE_NUMBER")
    private String invoiceNumber;

    @Column(name = "DETAILS_OF_ITEMS", length =2000)
    private String detailsOfItems;

    @Column(name = "INVOICE_VALUE")
    private BigDecimal invoiceValue;

    @Column(name = "GST_AMOUNT")
    private BigDecimal gstAmount;

    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    @Column(name = "TDS_APPLICABLE")
    private Boolean tdsApplicable;

    @Column(name = "TDS_NOT_APPLICABLE_REASON")
    private String tdsNotApplicableReason;

    @Column(name = "RECOMMENDED_DISBURSEMENT_AMOUNT")
    private BigDecimal recommendedDisbursementAmount;

    @Column(name = "ACCOUNT_CODE")
    private String accountCode;

    @Column(name = "COMPLIANCE_TERMS", length =2000)
    private String complianceTerms;

    @Column(name = "RECOMMENDATION", length =2000)
    private String recommendation;

    // Workflow
    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "VERIFIED_BY")
    private String verifiedBy;

    @Column(name = "APPROVED_BY")
    private String approvedBy;

    @OneToMany(
            mappedBy = "bseSalary",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MonthlySalaryDetails> monthlySalaryDetails = new ArrayList<>();
}