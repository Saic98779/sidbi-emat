package org.emat.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.enums.QuestionType;

/** DTO for creating a Survey Questionnaire row. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSurveyQuestionnaireRequest {

    private String question;
    private QuestionType questionType;
    private List<String> options;
}
