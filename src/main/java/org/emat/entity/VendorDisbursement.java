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
@Table(name = "VENDOR_DISBURSEMENT")
public class VendorDisbursement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Agency details
    private String manpowerAgencyName;
    private String gstinOfAgency;
    private String reasonForNoGstin;
    private String gstinOfSdbi;
    private String sanctionedAmount;
    private LocalDate disbursedTillDate;
    private String disbursementSoughtIn;

    @Column(length = 1000)
    private String natureOfPayment;

    // Invoice details
    private LocalDate invoiceDate;
    private String invoiceNumber;
    private String detailsOfItems;
    private BigDecimal invoiceValue;
    private BigDecimal gstAmount;
    private BigDecimal totalAmount;
    private Boolean tdsApplicable;
    private String tdsNotApplicableReason;
    private BigDecimal recommendedDisbursementAmount;
    private String accountCode;

    @Column(length = 2000)
    private String complianceTerms;

    @Column(length = 2000)
    private String recommendation;

    // Workflow
    private String status;
    private String createdBy;
    private String verifiedBy;
    private String approvedBy;

    @OneToMany(mappedBy = "vendorDisbursement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VendorDisbursementDetail> details = new ArrayList<>();
}

