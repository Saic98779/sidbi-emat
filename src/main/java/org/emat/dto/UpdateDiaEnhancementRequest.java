package org.emat.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdDecryptDeserializer;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

/** DTO for updating an existing DIA Enhancement. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDiaEnhancementRequest {

    private String platform;
    private String enhancementType;
    private String enhancementDetails;
    private LocalDate initiationDate;
    private LocalDate completionDate;
    private String status;
    private String pocSidbi;
    private String pocGt;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    private Long sidbiPocId;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    private Long gtPocId;

    private String enhancementSupportFilePath;
}
