package org.emat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "VENDOR_DISBURSEMENT_DETAIL")
public class VendorDisbursementDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VENDOR_DISBURSEMENT_ID")
    private VendorDisbursementSalary vendorDisbursement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IA_ID")
    private IndustryAssociationRegistration industryRegistrationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BSE_ID")
    private IndustryAssociationBseRecommendation bse;

    private String salaryMonth;
    private Integer salaryDays;
    private Integer paidDays;
    private BigDecimal additionalAmount;

    @Column(length = 1000)
    private String additionalAmountReason;

    private BigDecimal paymentToBse;

    @Column(length = 1000)
    private String gtAttendanceComments;

    @Column(length = 1000)
    private String gtAdditionalComments;
}
