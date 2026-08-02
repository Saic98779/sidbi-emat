package org.emat.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class VendorDisbursementDetailResponse {
    private Long id;
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
