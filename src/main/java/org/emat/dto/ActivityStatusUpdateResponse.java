package org.emat.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ActivityStatusUpdateResponse {
    private Long statusId;
    private Long activityId;
    private Long followupActivityId;
    private String status;
    private String statusUpdatedByRole;
    private Boolean statusApprovalRequired;
    private LocalDateTime statusUpdatedDtStamp;
    private String statusRemarks;
}
