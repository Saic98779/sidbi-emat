package org.emat.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "DIA_EXPOSURE_VISIT_PARTICIPANT")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DiaExposureVisitParticipant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DIA_EXPOSURE_VISIT_PARTICIPANT")
    @SequenceGenerator(name = "SEQ_DIA_EXPOSURE_VISIT_PARTICIPANT", sequenceName = "SEQ_DIA_EXPOSURE_VISIT_PARTICIPANT", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    // Gives RO, Name of IA, Name of Exposure Visit (via visit details) and Date of Exposure Visit
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXPOSURE_VISIT_ID", referencedColumnName = "ID")
    private DiaExposureVisit exposureVisit;

    /**
     * Many-to-Many association with exposure visits: a participant can attend multiple visits and a
     * visit can have multiple participants. The join table enforces uniqueness of the (visit,
     * participant) pair so a participant is never duplicated within the same visit.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "DIA_EXPOSURE_VISIT_PARTICIPANT_MAP",
            joinColumns =
                    @JoinColumn(
                            name = "DIA_EXPOSURE_VISIT_PARTICIPANT_ID",
                            referencedColumnName = "ID"),
            inverseJoinColumns =
                    @JoinColumn(name = "DIA_EXPOSURE_VISIT_ID", referencedColumnName = "ID"),
            uniqueConstraints =
                    @UniqueConstraint(
                            name = "UK_DIA_EV_PARTICIPANT_MAP",
                            columnNames = {
                                "DIA_EXPOSURE_VISIT_ID",
                                "DIA_EXPOSURE_VISIT_PARTICIPANT_ID"
                            }))
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<DiaExposureVisit> exposureVisits = new HashSet<>();

    @Column(name = "MSME_NAME")
    private String msmeName;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "FATHER_HUSBAND_NAME")
    private String fatherHusbandName;

    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CONTACT_NO")
    private String contactNo;

    @Column(name = "PAN_NO")
    private String panNo;

    @Column(name = "UDYAM_AADHAR_NO")
    private String udyamAadharNo;
}