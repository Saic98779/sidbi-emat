package org.emat.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAnnexureVRequest {

    private Integer snNo;
    private String particulars;
    private BigDecimal totalCost;
    private BigDecimal sidbiSupport;
}
