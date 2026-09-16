package org.emat.dto;

import java.time.LocalDateTime;
import lombok.Data;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

@Data
public class ActivityResponse {
    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long activityId;

    private String activityType;
    private String details;
    private LocalDateTime dateTime;
    private Boolean followUpReq;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long followUpId;

    private String locationDetails;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long createdUserId;

    private LocalDateTime createdDtStamp;
    private LocalDateTime approvedDtStamp;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long bseId;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long gtId;
}
