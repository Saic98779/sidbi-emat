package org.emat.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityStatusResponse {
    private Long statusId;
    private Long activityId;
    private Long followupActivityId;
    private String status;
    private String statusUpdatedByRole;
    private Boolean statusApprovalRequired;
    private LocalDateTime statusUpdatedDtStamp;
    private String statusRemarks;
}

