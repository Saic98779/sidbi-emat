package org.emat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

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
    @Column(name = "TOPIC")
    private String topic;

    // Module Name
    @Column(name = "MODULE_NAME")
    private String moduleName;

    // Relevance/Rationale of the Topic
    @Column(name = "RELEVANCE_RATIONALE_OF_TOPIC")
    private String relevanceRationaleOfTopic;

    // Brief of the Content
    @Column(name = "BRIEF_OF_CONTENT")
    private String briefOfContent;

    // Main Content
    @Column(name = "MAIN_CONTENT")
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

    // Requested by
    @Column(name = "REQUESTED_BY")
    private String requestedBy;

    // Request date
    @Column(name = "REQUEST_DATE")
    private LocalDate requestDate;
}