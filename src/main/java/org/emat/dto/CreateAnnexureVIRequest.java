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
public class CreateAnnexureVIRequest {

    private String section;
    private String sectionNote;
    private String indicativeItem;
    private Integer numbers;
    private String make;
    private BigDecimal maximumCost;
    private String maximumCostUnit;
}
