package org.emat.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityStatusUpdateRequest {
    private Long followupActivityId;
    private String status;
    private String statusUpdatedByRole; // BSE, GT_FT, etc.
    private Boolean statusApprovalRequired;
    private LocalDateTime statusUpdatedDtStamp;
    private String statusRemarks;
}
