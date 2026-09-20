package org.emat.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.emat.config.BulkMessagingListConverter;
import org.emat.enums.BulkMessaging;
import org.emat.enums.Status;

/**
 * Survey
 */
@Entity
@Table(name = "SURVEY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Survey extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SURVEY")
    @SequenceGenerator(
            name = "SEQ_SURVEY",
            sequenceName = "SEQ_SURVEY",
            allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    // Topic
    @Column(name = "TOPIC")
    private String topic;

    // Relevance of the Topic
    @Column(name = "RELEVANCE_OF_TOPIC")
    private String relevanceOfTopic;

    // Duration of Survey - Start Date
    @Column(name = "START_DATE")
    private LocalDate startDate;

    // Duration of Survey - End Date
    @Column(name = "END_DATE")
    private LocalDate endDate;

    // Sample of the Survey
    @Column(name = "SAMPLE")
    private Integer sample;

    // Bulk Messaging (mail/SMS/Whatsapp) - multi-select
    @Convert(converter = BulkMessagingListConverter.class)
    @Column(name = "BULK_MESSAGING")
    private List<BulkMessaging> bulkMessaging;

    // Attachment - Word/PDF
    @Column(name = "ATTACHMENT")
    private String attachment;

    // Survey Questionnaire List
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyQuestionnaire> surveyQuestionnaires = new java.util.ArrayList<>();

    @Column(name = "STATUS")
    private Status status;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "APPROVED_DATE")
    private LocalDate approvedDate;
}