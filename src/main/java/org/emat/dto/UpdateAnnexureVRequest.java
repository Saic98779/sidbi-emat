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
public class UpdateAnnexureVRequest {

    private Integer snNo;
    private String particulars;
    private BigDecimal totalCost;
    private BigDecimal sidbiSupport;
}
