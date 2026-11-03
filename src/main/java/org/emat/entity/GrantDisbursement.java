package org.emat.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "GRANT_DISBURSEMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GrantDisbursement extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GRANT_DISBURSEMENT")
    @SequenceGenerator(name = "SEQ_GRANT_DISBURSEMENT", sequenceName = "SEQ_GRANT_DISBURSEMENT", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    // Grant amount (Rs. lakh)
    @Column(name = "GRANT_AMOUNT", precision = 15, scale = 2)
    private BigDecimal grantAmount;

    @Column(name = "HARD_INTERVENTION", precision = 15, scale = 2)
    private BigDecimal hardIntervention;

    @Column(name = "BSE_SALARY", precision = 15, scale = 2)
    private BigDecimal bseSalary;

    @Column(name = "CAPACITY_BUILDING_TRAININGS", precision = 15, scale = 2)
    private BigDecimal capacityBuildingTrainings;

    @Column(name = "SEMINARS_WORKSHOPS", precision = 15, scale = 2)
    private BigDecimal seminarsWorkshops;

    @Column(name = "RATING_ACCREDITATIONS", precision = 15, scale = 2)
    private BigDecimal ratingAccreditations;

    @Column(name = "OTHERS", precision = 15, scale = 2)
    private BigDecimal others;

    @Column(name = "DISBURSEMENT_AMOUNT", precision = 15, scale = 2)
    private BigDecimal disbursementAmount;

    @Column(name = "DISBURSEMENT_DATE")
    private LocalDate disbursementDate;

    @Column(name = "EXPECTED_DISBURSEMENT_DATE")
    private LocalDate expectedDisbursementDate;

}