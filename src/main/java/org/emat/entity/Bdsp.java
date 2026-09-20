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
 * BDSP
 */
@Entity
@Table(name = "BDSP")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Bdsp extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BDSP")
    @SequenceGenerator(
            name = "SEQ_BDSP",
            sequenceName = "SEQ_BDSP",
            allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    // Name of BDSP
    @Column(name = "NAME_OF_BDSP")
    private String nameOfBdsp;

    // Rationale for onboarding BDSP
    @Column(name = "RATIONALE_FOR_ONBOARDING")
    private String rationaleForOnboarding;

    // Theme
    @Column(name = "THEME")
    private String theme;

    // Area of Service/Expertise
    @Column(name = "AREA_OF_SERVICE_EXPERTISE")
    private String areaOfServiceExpertise;

    // State
    @Column(name = "STATE")
    private String state;

    // District
    @Column(name = "DISTRICT")
    private String district;

    // Contact
    @Column(name = "CONTACT")
    private String contact;

    // Email
    @Column(name = "EMAIL")
    private String email;

    // KYC
    @Column(name = "KYC")
    private String kyc;

    @Column(name = "STATUS")
    private Status status;

    @Column(name = "APPROVED_DATE")
    private LocalDate approvedDate;
}