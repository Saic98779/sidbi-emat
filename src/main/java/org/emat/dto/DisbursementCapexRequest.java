package org.emat.dto;

import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;
import org.emat.dto.serializer.PiiIdDecryptDeserializer;
import org.emat.dto.serializer.PiiIdEncryptSerializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisbursementCapexRequest {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
        @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    private Long id;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
        @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
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
