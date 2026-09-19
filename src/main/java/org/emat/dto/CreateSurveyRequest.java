package org.emat.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.enums.BulkMessaging;

/** DTO for creating a new Survey. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSurveyRequest {

    private String topic;
    private String relevanceOfTopic;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer sample;
    private List<BulkMessaging> bulkMessaging;
    private String attachment;
    private List<CreateSurveyQuestionnaireRequest> surveyQuestionnaires;
}
