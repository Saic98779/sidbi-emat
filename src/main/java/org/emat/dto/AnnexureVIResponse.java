package org.emat.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnexureVIResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    private String section;
    private String sectionNote;
    private String indicativeItem;
    private Integer numbers;
    private String make;
    private BigDecimal maximumCost;
    private String maximumCostUnit;
}
