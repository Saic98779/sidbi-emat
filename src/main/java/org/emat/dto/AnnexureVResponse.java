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
public class AnnexureVResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    private Integer snNo;
    private String particulars;
    private BigDecimal totalCost;
    private BigDecimal sidbiSupport;
}
