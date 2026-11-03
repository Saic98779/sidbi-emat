package org.emat.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

/** DTO for DIA Credit Application response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaCreditApplicationResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    private String roName;
    private String applicantName;
    private String applicantPan;
    private BigDecimal loanAmountApplied;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    private String mobileNo;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
}
