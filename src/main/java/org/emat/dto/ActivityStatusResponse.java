package org.emat.dto;

import java.time.LocalDateTime;
import lombok.Data;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

@Data
public class ActivityStatusResponse {
    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long statusId;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long activityId;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long followupActivityId;

    private String status;
    private String statusUpdatedByRole;
    private Boolean statusApprovalRequired;
    private LocalDateTime statusUpdatedDtStamp;
    private String statusRemarks;
}
