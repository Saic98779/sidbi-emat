package org.emat.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityResponse {
    private Long activityId;
    private String activityType;
    private String details;
    private LocalDateTime dateTime;
    private Boolean followUpReq;
    private Long followUpId;
    private String locationDetails;
    private Long createdUserId;
    private LocalDateTime createdDtStamp;
    private LocalDateTime approvedDtStamp;
    private Long bseId;
    private Long gtId;
}
