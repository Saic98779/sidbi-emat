package org.emat.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import org.emat.dto.serializer.PiiIdEncryptSerializer;

@Data
@Builder
public class MonthlySalaryDetailsResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;
    private String bseName;
    private String manpowerAgencyName;
    private String salaryMonth;
    private Integer salaryDays;
    private Integer paidDays;
    private BigDecimal additionalAmount;
    private String additionalAmountReason;
    private BigDecimal paymentToBse;
    private String gtAttendanceComments;
    private String gtAdditionalComments;
}
