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
 * Latest Developments
 */
@Entity
@Table(name = "LATEST_DEVELOPMENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class LatestDevelopments extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LATEST_DEVELOPMENTS")
    @SequenceGenerator(
            name = "SEQ_LATEST_DEVELOPMENTS",
            sequenceName = "SEQ_LATEST_DEVELOPMENTS",
            allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    // Topic
    @Column(name = "TOPIC", length = 2000)
    private String topic;

    // Relevance of the Topic
    @Column(name = "RELEVANCE_OF_TOPIC", length = 2000)
    private String relevanceOfTopic;

    // Duration - Start Date
    @Column(name = "START_DATE")
    private LocalDate startDate;

    // Duration - End Date
    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "MAKER_STATUS")
    private Status makerStatus;

    @Column(name = "CHECKER_STATUS")
    private Status checkerStatus;

    @Column(name = "REMARK", length = 2000)
    private String remark;

    @Column(name = "APPROVED_DATE")
    private LocalDate approvedDate;
}