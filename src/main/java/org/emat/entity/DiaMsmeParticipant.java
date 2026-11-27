package org.emat.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "DIA_MSME_PARTICIPANT")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DiaMsmeParticipant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DIA_MSME_PARTICIPANT")
    @SequenceGenerator(name = "SEQ_DIA_MSME_PARTICIPANT", sequenceName = "SEQ_DIA_MSME_PARTICIPANT", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    // Gives Name of Event, Date of Event and Name of IA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID", referencedColumnName = "ID")
    private DiaCapacityBuildingProgram event;

    /**
     * Many-to-Many association with capacity building programs: a participant can attend multiple
     * programs and a program can have multiple participants. The join table enforces uniqueness of
     * the (program, participant) pair so a participant is never duplicated within the same program.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "DIA_CAPACITY_BUILDING_PROGRAM_MSME_PARTICIPANT_MAP",
            joinColumns =
                    @JoinColumn(name = "DIA_MSME_PARTICIPANT_ID", referencedColumnName = "ID"),
            inverseJoinColumns =
                    @JoinColumn(
                            name = "DIA_CAPACITY_BUILDING_PROGRAM_ID",
                            referencedColumnName = "ID"),
            uniqueConstraints =
                    @UniqueConstraint(
                            name = "UK_DIA_CBP_MSME_PARTICIPANT_MAP",
                            columnNames = {
                                "DIA_CAPACITY_BUILDING_PROGRAM_ID",
                                "DIA_MSME_PARTICIPANT_ID"
                            }))
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<DiaCapacityBuildingProgram> events = new HashSet<>();

    @Column(name = "MSME_NAME")
    private String msmeName;

    // true = unique (first-time), false = repeated
    @Column(name = "FIRST_TIME_PARTICIPANT")
    private Boolean firstTimeParticipant;

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