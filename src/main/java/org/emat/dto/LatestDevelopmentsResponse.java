package org.emat.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.enums.Status;

/** DTO for Latest Developments response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LatestDevelopmentsResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    private String topic;
    private String relevanceOfTopic;
    private LocalDate startDate;
    private LocalDate endDate;

    private Status status;
    private String remark;
    private LocalDate approvedDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
}