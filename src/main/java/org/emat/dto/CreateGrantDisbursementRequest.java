package org.emat.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for creating a new Grant Disbursement. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateGrantDisbursementRequest {

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
}
