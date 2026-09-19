package org.emat.dto;

import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdDecryptDeserializer;
import org.emat.dto.serializer.PiiIdEncryptSerializer;

/** DTO for updating an existing Disbursement Note for Capacity Building of IA members. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDisbursementNoteCapacityBuildingIaRequest {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    private Long registrationId;

    private String industryAssociationName;
    private String gstinOfIa;
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
}