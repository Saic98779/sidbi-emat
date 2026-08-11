package org.emat.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class BseSalaryRequest {

    private String bseId;
    private String gstinOfAgency;
    private String reasonForNoGstin;
    private String gstinOfSdbi;

    private String sanctionedAmount;
    private LocalDate disbursedTillDate;
    private String disbursementSoughtIn;

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
    private String complianceTerms;
    private String recommendation;

    // Workflow
    private String status;
    private String createdBy;
    private String verifiedBy;
    private String approvedBy;

    // Monthly salary details
    private List<MonthlySalaryDetailsRequest> details;
}