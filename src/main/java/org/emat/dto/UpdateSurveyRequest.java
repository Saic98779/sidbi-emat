package org.emat.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.enums.BulkMessaging;

/** DTO for updating an existing Survey. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateSurveyRequest {

    private String topic;
    private String relevanceOfTopic;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer sample;
    private List<BulkMessaging> bulkMessaging;
    private String attachment;
    private List<UpdateSurveyQuestionnaireRequest> surveyQuestionnaires;
}
