package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdDecryptDeserializer;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.enums.Status;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

/** DTO for updating the status of an existing Action Plan. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateActionPlanStatusRequest {

    private Status makerStatus;

    private Status checkerStatus;

    private String remark;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    private Long stageId;

    private String stageComments;
}