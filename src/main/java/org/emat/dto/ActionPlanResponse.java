package org.emat.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.enums.Status;
import tools.jackson.databind.annotation.JsonSerialize;

/** DTO for Action Plan response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionPlanResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    private String state;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long registrationId;

    private String industryAssociationName;

    private List<ActionPlanActivityResponse> activities;

    private Status status;
    private String remark;
    private LocalDate approvedDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
}
