package org.emat.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MonthlySalaryDetailsRequest {

    private Long bseId;
    private String salaryMonth;
    private Integer salaryDays;
    private Integer paidDays;
    private BigDecimal additionalAmount;
    private String additionalAmountReason;
    private BigDecimal paymentToBse;
    private String gtAttendanceComments;
    private String gtAdditionalComments;
    private BigDecimal monthlySalary;
}