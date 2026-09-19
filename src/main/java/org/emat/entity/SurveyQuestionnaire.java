package org.emat.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.emat.enums.QuestionType;

/**
 * Survey Questionnaire
 */
@Entity
@Table(name = "SURVEY_QUESTIONNAIRE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SurveyQuestionnaire extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SURVEY_QUESTIONNAIRE")
    @SequenceGenerator(
            name = "SEQ_SURVEY_QUESTIONNAIRE",
            sequenceName = "SEQ_SURVEY_QUESTIONNAIRE",
            allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    // Question
    @Column(name = "QUESTION")
    private String question;

    // Question Type
    @Enumerated(EnumType.STRING)
    @Column(name = "QUESTION_TYPE")
    private QuestionType questionType;

    // Options - multi-value
    @ElementCollection
    @CollectionTable(
            name = "SURVEY_QUESTIONNAIRE_OPTIONS",
            joinColumns = @JoinColumn(name = "SURVEY_QUESTIONNAIRE_ID")
    )
    @Column(name = "OPTION_VALUE")
    private List<String> options = new ArrayList<>();

    // Parent Survey
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SURVEY_ID", nullable = false)
    private Survey survey;
}