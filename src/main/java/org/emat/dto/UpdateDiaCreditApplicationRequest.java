package org.emat.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiStringDecryptDeserializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

/** DTO for updating an existing DIA Credit Application. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDiaCreditApplicationRequest {

    private String roName;
    private String applicantName;
    private String applicantPan;
    private BigDecimal loanAmountApplied;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String mobileNo;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String email;

    private String officeAddress;
    private String pinCode;
    private String industryAssociationName;
    private String industryAssociationLoginId;
    private String acknowledgementNo;
    private String memberType;
    private String status;
    private Boolean loanSanctioned;
    private LocalDate sanctionDate;
    private BigDecimal amountSanctioned;
    private BigDecimal amountDisbursed;
}
