package org.emat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import org.emat.enums.Status;

/**
 * Bulk Broadcast
 */
@Entity
@Table(name = "BULK_BROADCAST")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BulkBroadcast extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BULK_BROADCAST")
    @SequenceGenerator(
            name = "SEQ_BULK_BROADCAST",
            sequenceName = "SEQ_BULK_BROADCAST",
            allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    // Topic
    @Column(name = "TOPIC")
    private String topic;

    // Relevance of the Topic
    @Column(name = "RELEVANCE_OF_TOPIC")
    private String relevanceOfTopic;

    // Sample for the Bulk Broadcast
    @Column(name = "SAMPLE_FOR_BROADCAST")
    private String sampleForBroadcast;

    // Subject Line
    @Column(name = "SUBJECT_LINE")
    private String subjectLine;

    // Date of Broadcast
    @Column(name = "DATE_OF_BROADCAST")
    private LocalDate dateOfBroadcast;

    // Main Content (under 5000 characters)
    @Column(name = "MAIN_CONTENT", length = 5000)
    private String mainContent;

    // Broadcast through - SMS/WhatsApp
    @Column(name = "BROADCAST_THROUGH")
    private String broadcastThrough;

    // Attachment (PDF, Word, PPT, JPG etc)
    @Column(name = "ATTACHMENT")
    private String attachment;

    // Link
    @Column(name = "LINK")
    private String link;

    @Column(name = "STATUS")
    private Status status;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "APPROVED_DATE")
    private LocalDate approvedDate;
}