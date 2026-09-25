package org.emat.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.emat.enums.Status;

/**
 * E-learning Module Content
 */
@Entity
@Table(name = "ELEARNING_MODULE_CONTENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ElearningModuleContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ELEARNING_MODULE_CONTENT")
    @SequenceGenerator(
            name = "SEQ_ELEARNING_MODULE_CONTENT",
            sequenceName = "SEQ_ELEARNING_MODULE_CONTENT",
            allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    // Topic
    @Column(name = "TOPIC", length = 2000)
    private String topic;

    // Module Name
    @Column(name = "MODULE_NAME")
    private String moduleName;

    // Relevance/Rationale of the Topic
    @Column(name = "RELEVANCE_OF_TOPIC", length = 2000)
    private String relevanceOfTopic;

    // Brief of the Content
    @Column(name = "BRIEF_OF_CONTENT", length = 2000)
    private String briefOfContent;

    // Main Content
    @Column(name = "MAIN_CONTENT", length = 2000)
    private String mainContent;

    // Attachment - PDF, Word, PPT, Image, Video etc
    @Column(name = "ATTACHMENT")
    private String attachment;

    // Link
    @Column(name = "LINK")
    private String link;

    // Placement of the Module
    @Column(name = "PLACEMENT_OF_MODULE")
    private String placementOfModule;

    @Column(name = "MAKER_STATUS")
    private Status makerStatus;

    @Column(name = "CHECKER_STATUS")
    private Status checkerStatus;

    @Column(name = "REMARK", length = 2000)
    private String remark;

    @Column(name = "APPROVED_DATE")
    private LocalDate approvedDate;
}