package org.emat.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class MonthlySalaryDetailsUpdateRequest {

    private String iaId;
    private String bseId;
    private String salaryMonth;
    private Integer salaryDays;
    private Integer paidDays;
    private BigDecimal additionalAmount;
    private String additionalAmountReason;
    private BigDecimal paymentToBse;
    private String gtAttendanceComments;
    private String gtAdditionalComments;
}
