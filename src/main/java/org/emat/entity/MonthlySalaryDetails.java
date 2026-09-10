package org.emat.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "MONTHLY_SALARY_DETAIL")
public class MonthlySalaryDetails extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MONTHLY_SALARY_DETAIL")
    @SequenceGenerator(name = "SEQ_MONTHLY_SALARY_DETAIL", sequenceName = "SEQ_MONTHLY_SALARY_DETAIL", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    // BSE Recommendation relation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BSE_ID")
    private IndustryAssociationBseRecommendation bse;

    // Parent BSE Salary
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BSE_SALARY_ID", nullable = false)
    private BseSalary bseSalary;

    @Column(name = "SALARY_MONTH")
    private String salaryMonth;

    @Column(name = "SALARY_DAYS")
    private Integer salaryDays;

    @Column(name = "PAID_DAYS")
    private Integer paidDays;

    @Column(name = "ADDITIONAL_AMOUNT")
    private BigDecimal additionalAmount;

    @Column(name = "ADDITIONAL_AMOUNT_REASON", length = 1000)
    private String additionalAmountReason;

    @Column(name = "PAYMENT_TO_BSE")
    private BigDecimal paymentToBse;

    @Column(name = "GT_ATTENDANCE_COMMENTS", length = 1000)
    private String gtAttendanceComments;

    @Column(name = "GT_ADDITIONAL_COMMENTS", length = 1000)
    private String gtAdditionalComments;

    @Column(name = "MONTHLY_SALARY")
    private BigDecimal monthlySalary;
}
