package org.emat.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class UpdateVendorDisbursementSalaryRequest {
    private UUID registrationUuid;
    private String gstinOfAgency;
    private String reasonForNoGstin;
    private String gstinOfSdbi;
    private String sanctionedAmount;
    private LocalDate disbursedTillDate;
    private String disbursementSoughtIn;
    private String natureOfPayment;
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
    private String status;
    private String createdBy;
    private String verifiedBy;
    private String approvedBy;
    private List<UpdateVendorDisbursementDetailRequest> details;
}

