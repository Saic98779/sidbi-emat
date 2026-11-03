package org.emat.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

/** DTO for Grant Disbursement response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrantDisbursementResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    private BigDecimal grantAmount;
    private BigDecimal hardIntervention;
    private BigDecimal bseSalary;
    private BigDecimal capacityBuildingTrainings;
    private BigDecimal seminarsWorkshops;
    private BigDecimal ratingAccreditations;
    private BigDecimal others;
    private BigDecimal disbursementAmount;
    private LocalDate disbursementDate;
    private LocalDate expectedDisbursementDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
}
