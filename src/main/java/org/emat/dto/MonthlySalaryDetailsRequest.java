package org.emat.dto;

import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import lombok.Data;
import org.emat.dto.serializer.PiiIdDecryptDeserializer;
import org.emat.dto.serializer.PiiIdEncryptSerializer;

@Data
public class MonthlySalaryDetailsRequest {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
        @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
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
