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
 * Discussion Forum
 */
@Entity
@Table(name = "DISCUSSION_FORUM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DiscussionForum extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DISCUSSION_FORUM")
    @SequenceGenerator(
            name = "SEQ_DISCUSSION_FORUM",
            sequenceName = "SEQ_DISCUSSION_FORUM",
            allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    // Topic
    @Column(name = "TOPIC", length = 2000)
    private String topic;

    // Theme
    @Column(name = "THEME", length = 2000)
    private String theme;

    // Relevance of the Topic
    @Column(name = "RELEVANCE_OF_TOPIC", length = 2000)
    private String relevanceOfTopic;

    // Duration - Start Date
    @Column(name = "START_DATE")
    private LocalDate startDate;

    // Duration - End Date
    @Column(name = "END_DATE")
    private LocalDate endDate;

    // Global / Only Members
    @Column(name = "GLOBAL_OR_ONLY_MEMBERS")
    private String globalOrOnlyMembers;

    @Column(name = "MAKER_STATUS")
    private Status makerStatus;

    @Column(name = "CHECKER_STATUS")
    private Status checkerStatus;

    @Column(name = "REMARK", length = 2000)
    private String remark;

    @Column(name = "APPROVED_DATE")
    private LocalDate approvedDate;
}