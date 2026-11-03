package org.emat.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "DIA_CREDIT_APPLICATION")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DiaCreditApplication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DIA_CREDIT_APPLICATION")
    @SequenceGenerator(name = "SEQ_DIA_CREDIT_APPLICATION", sequenceName = "SEQ_DIA_CREDIT_APPLICATION", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "RO_NAME")
    private String roName;

    @Column(name = "APPLICANT_NAME")
    private String applicantName;

    @Column(name = "APPLICANT_PAN")
    private String applicantPan;

    // Loan amount applied (Rs. lakh)
    @Column(name = "LOAN_AMOUNT_APPLIED", precision = 15, scale = 2)
    private BigDecimal loanAmountApplied;

    @Column(name = "MOBILE_NO")
    private String mobileNo;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "OFFICE_ADDRESS", length = 1000)
    private String officeAddress;

    @Column(name = "PIN_CODE")
    private String pinCode;

    @Column(name = "INDUSTRY_ASSOCIATION_NAME")
    private String industryAssociationName;

    @Column(name = "INDUSTRY_ASSOCIATION_LOGIN_ID")
    private String industryAssociationLoginId;

    @Column(name = "ACKNOWLEDGEMENT_NO", unique = true)
    private String acknowledgementNo;

    // IA Member / Non Member
    @Column(name = "MEMBER_TYPE")
    private String memberType;

    // NEW, IN PROGRESS, SANCTION, DISBURSED, SUBMITTED, REJECTED/CLOSED
    @Column(name = "STATUS")
    private String status;

    @Column(name = "LOAN_SANCTIONED")
    private Boolean loanSanctioned;

    @Column(name = "SANCTION_DATE")
    private LocalDate sanctionDate;

    // Amount sanctioned (Rs. lakh)
    @Column(name = "AMOUNT_SANCTIONED", precision = 15, scale = 2)
    private BigDecimal amountSanctioned;

    // Amount disbursed (Rs. lakh)
    @Column(name = "AMOUNT_DISBURSED", precision = 15, scale = 2)
    private BigDecimal amountDisbursed;
}