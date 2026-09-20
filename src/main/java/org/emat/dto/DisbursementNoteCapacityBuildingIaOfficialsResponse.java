package org.emat.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.enums.Status;

/** DTO for Disbursement Note for Capacity Building of IA officials response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisbursementNoteCapacityBuildingIaOfficialsResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long registrationId;

    private String registrationName;
    private String eventManagementAgencyName;
    private String gstinOfAgency;
    private String gstinNotApplicableReason;
    private String gstinOfSidbi;
    private BigDecimal sanctionedAmount;
    private BigDecimal disbursedTillDate;
    private BigDecimal disbursementSought;
    private String natureOfPayment;
    private LocalDate invoiceDate;
    private String invoiceNumber;
    private BigDecimal valueOfServiceItemsSupplied;
    private BigDecimal igstAt18Percent;
    private BigDecimal totalAmount;
    private Boolean tdsApplicable;
    private String tdsNotApplicableReason;
    private BigDecimal amountRecommendedForDisbursement;
    private String accountCodeForPayment;
    private String gtCommentsOnEventOutcomeImpact;
    private String compliancePreDisbursementTerms;
    private String recommendation;

    private Status status;
    private String remark;
    private LocalDate approvedDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
}