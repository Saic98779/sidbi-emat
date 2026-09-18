package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdDecryptDeserializer;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

/** DTO for SIDBE approval request. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalRequest {
    private Boolean isSidbeApproved;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
        @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    private Long stageId;

    private String stageComments;
}
