package org.emat.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

/** DTO for DIA Enhancement response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaEnhancementResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    private String platform;
    private String enhancementType;
    private String enhancementDetails;
    private LocalDate initiationDate;
    private LocalDate completionDate;
    private String status;
    private String pocSidbi;
    private String pocGt;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long sidbiPocId;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long gtPocId;
    private String enhancementSupportFilePath;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
}
