package org.emat.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StageHistoryResponse {
    private Long id;
    private Long registrationId;
    private String stage;
    private String subStage;
    private LocalDateTime time;
    private String createdBy;
    private String comment;
}

