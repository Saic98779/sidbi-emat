package org.emat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Pop-Ups
 */
@Entity
@Table(name = "POP_UPS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PopUps extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_POP_UPS")
    @SequenceGenerator(
            name = "SEQ_POP_UPS",
            sequenceName = "SEQ_POP_UPS",
            allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    // Topic
    @Column(name = "TOPIC")
    private String topic;

    // Relevance of the Pop-Up
    @Column(name = "RELEVANCE_OF_POP_UP")
    private String relevanceOfPopUp;

    // Duration - Start Date
    @Column(name = "START_DATE")
    private LocalDate startDate;

    // Duration - End Date
    @Column(name = "END_DATE")
    private LocalDate endDate;

    // Attachments
    @Column(name = "ATTACHMENTS")
    private String attachments;

    // Requested by
    @Column(name = "REQUESTED_BY")
    private String requestedBy;

    // Request date
    @Column(name = "REQUEST_DATE")
    private LocalDate requestDate;
}