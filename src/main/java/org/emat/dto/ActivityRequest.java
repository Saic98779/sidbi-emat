package org.emat.dto;

import java.time.LocalDateTime;
import lombok.Data;
import org.emat.dto.serializer.PiiIdDecryptDeserializer;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

@Data
public class ActivityRequest {
    private String activityType;
    private String details;
    private LocalDateTime dateTime;
    private String status;
    private Boolean followUpReq;
    private Long followUpId;
    private String locationDetails;
    private Long createdUserId;
    private LocalDateTime createdDtStamp;
    private LocalDateTime approvedDtStamp;
    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    private Long bseId;
    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    private Long gtId;
}
