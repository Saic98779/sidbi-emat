package org.emat.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.enums.BulkMessaging;
import tools.jackson.databind.annotation.JsonSerialize;

/** DTO for Survey response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    private String topic;
    private String relevanceOfTopic;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer sample;
    private List<BulkMessaging> bulkMessaging;
    private String attachment;
    private List<SurveyQuestionnaireResponse> surveyQuestionnaires;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
}
