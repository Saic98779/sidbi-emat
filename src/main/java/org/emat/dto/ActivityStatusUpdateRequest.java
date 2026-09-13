package org.emat.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ActivityStatusUpdateRequest {
    private Long followupActivityId;
    private String status;
    private String statusUpdatedByRole; // BSE, GT_FT, etc.
    private Boolean statusApprovalRequired;
    private LocalDateTime statusUpdatedDtStamp;
    private String statusRemarks;
}
