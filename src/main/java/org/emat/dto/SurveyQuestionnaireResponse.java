package org.emat.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.enums.QuestionType;
import tools.jackson.databind.annotation.JsonSerialize;

/** DTO for a Survey Questionnaire row response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyQuestionnaireResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    private String question;
    private QuestionType questionType;
    private List<String> options;
}
