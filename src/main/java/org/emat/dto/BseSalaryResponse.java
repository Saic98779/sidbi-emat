package org.emat.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.emat.dto.serializer.PiiIdEncryptSerializer;

@Data
@Builder
public class BseSalaryResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    private String manpowerAgencyName;

    private String gstinOfAgency;

    private String reasonForNoGstin;

    private String gstinOfSdbi;

    private BigDecimal sanctionedAmount;
    private BigDecimal disbursedTillDate;
    private BigDecimal disbursementSoughtIn;

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

    private List<MonthlySalaryDetailsResponse> monthlySalaryDetails;
}
