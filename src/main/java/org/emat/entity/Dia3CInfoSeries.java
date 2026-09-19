package org.emat.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> fdef0fd707e19cdc92c3230611ac59f08b69b024
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.emat.config.BulkMessagingListConverter;
import org.emat.enums.BulkMessaging;

/**
 * DIA 3C-Info-Series
 */
@Entity
@Table(name = "DIA_3C_INFO_SERIES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Dia3CInfoSeries extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DIA_3C_INFO_SERIES")
    @SequenceGenerator(
            name = "SEQ_DIA_3C_INFO_SERIES",
            sequenceName = "SEQ_DIA_3C_INFO_SERIES",
            allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    // Topic
    @Column(name = "TOPIC")
    private String topic;

    // Relevance of the Topic
    @Column(name = "RELEVANCE_OF_TOPIC")
    private String relevanceOfTopic;

    // Brief of the Content
    @Column(name = "BRIEF_OF_CONTENT")
    private String briefOfContent;

    // Chapter No.
    @Column(name = "CHAPTER_NO")
    private String chapterNo;

    // Subject Line
    @Column(name = "SUBJECT_LINE")
    private String subjectLine;

    // Main Content
    @Column(name = "MAIN_CONTENT")
    private String mainContent;

    // Attachment - PDF, Word, PPT, Image, etc
    @Column(name = "ATTACHMENT")
    private String attachment;

    @ElementCollection
    @CollectionTable(
            name = "DIA_3C_INFO_SERIES_BULK_MESSAGING",
            joinColumns = @JoinColumn(name = "DIA_3C_INFO_SERIES_ID")
    )
    @Column(name = "BULK_MESSAGING")
    @Enumerated(EnumType.STRING)
    private List<BulkMessaging> bulkMessaging = new ArrayList<>();

    // Proposed Publish date
    @Column(name = "PROPOSED_PUBLISH_DATE")
    private LocalDate proposedPublishDate;
}