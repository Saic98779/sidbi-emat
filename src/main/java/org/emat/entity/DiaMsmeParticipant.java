package org.emat.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
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