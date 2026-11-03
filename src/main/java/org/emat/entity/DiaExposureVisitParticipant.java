package org.emat.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
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