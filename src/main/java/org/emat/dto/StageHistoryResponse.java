package org.emat.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

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
