package org.emat.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisbursementCapexRequest {

    private Long id;

    private Long registrationId;

    private String gstinIa;

    private Boolean gstinNotApplicable;

    private String gstinNotApplicableReason;

    private String gstinSidbi;

    private BigDecimal sanctionedAmount;

    private BigDecimal disbursedTillDate;

    private BigDecimal disbursementSought;

    private LocalDate invoiceDate;

    private String invoiceNumber;

    private String detailsOfItems;

    private BigDecimal valueOfServiceItems;

    private BigDecimal igstAmount;

    private BigDecimal totalAmount;

    private Boolean tdsApplicable;

    private String tdsNotApplicableReason;

    private BigDecimal amountRecommendedForDisbursement;

    private String accountCode;

    private String gtCapexVerificationComments;

    private String preDisbursementCompliance;

    private Boolean recommendation;
}