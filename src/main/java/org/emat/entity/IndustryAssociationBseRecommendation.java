package org.emat.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "IA_BSE_RECOMMENDATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class IndustryAssociationBseRecommendation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IA_BSE_RECOMMENDATION")
    @SequenceGenerator(
            name = "SEQ_IA_BSE_RECOMMENDATION",
            sequenceName = "SEQ_IA_BSE_RECOMMENDATION",
            allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    /** Approved Industry Association */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGISTRATION_ID", nullable = false)
    private IndustryAssociationRegistration registration;

    // ==========================================================
    // BSE Details
    // ==========================================================

    @Column(name = "STATE", length = 125)
    private String state;

    @Column(name = "DISTRICT", length = 125)
    private String district;

    @Column(name = "INDUSTRY_REGISTRATION_ID", length = 250)
    private String industryRegistrationId;

    @Column(name = "BSE_NAME", length = 250)
    private String bseName;

    @Column(name = "MOBILE_NUMBER", length = 19)
    private String mobileNumber;

    @Column(name = "EMAIL_ID", length = 250)
    private String emailId;

    @Column(name = "HIGHEST_QUALIFICATION", length = 250)
    private String highestQualification;

    @Column(name = "EXPERIENCE_STATUS")
    private Boolean experienced;

    @Column(name = "EXPERIENCE_YEARS")
    private Integer experienceYears;

    @Column(name = "EXPERIENCE_MONTHS")
    private Integer experienceMonths;

    @Column(name = "EMPLOYMENT_STATUS", length = 63)
    private String employmentStatus;

    @Column(name = "CURRENT_SALARY", precision = 12, scale = 2)
    private BigDecimal currentSalary;

    @Column(name = "NOTICE_PERIOD_DAYS")
    private Integer noticePeriodDays;

    @Column(name = "LAST_DRAWN_SALARY", precision = 12, scale = 2)
    private BigDecimal lastDrawnSalary;

    @Column(name = "RELIEVING_LETTER", length = 625)
    private String relievingLetter;

    @Column(name = "EXPECTED_SALARY", precision = 12, scale = 2)
    private BigDecimal expectedSalary;

    @Column(name = "RESUME_STATUS", length = 63)
    private String resumeStatus;

    @Column(name = "RESUME_FILE", length = 625)
    private String resumeFile;

    @Column(name = "SALARY_SLIP", length = 625)
    private String salarySlip;

    @Column(name = "CANDIDATE_CV", length = 625)
    private String candidateCv;

    // ==========================================================
    // Approval Workflow
    // ==========================================================

    @Column(name = "GT_RECOMMENDATION", length = 63)
    private String gtRecommendation;

    @Column(name = "GT_RECOMMENDATION_DATE")
    private LocalDate gtRecommendationDate;

    @Column(name = "GT_REMARKS", length = 2000)
    private String gtRemarks;

    @Column(name = "PMU_RECOMMENDATION", length = 1250)
    private String pmuRecommendation;

    @Column(name = "PMU_RECOMMENDATION_DATE")
    private LocalDate pmuRecommendationDate;

    @Column(name = "PMU_REMARKS", length = 2000)
    private String pmuRemarks;

    @Column(name = "HO_RECOMMENDATION", length = 2000)
    private String hoRecommendation;

    @Column(name = "HO_RECOMMENDATION_DATE")
    private LocalDate hoRecommendationDate;

    @Column(name = "HO_REMARKS", length = 2000)
    private String hoRemarks;

    @Column(name = "COMMITTEE_RECOMMENDATION", length = 1250)
    private String committeeRecommendation;

    @Column(name = "COMMITTEE_DATE")
    private LocalDate committeeDate;

    @Column(name = "COMMITTEE_MOM", length = 625)
    private String committeeMom;

    @Column(name = "COMMITTEE_REMARKS", length = 2500)
    private String committeeRemarks;

    // ==========================================================
    // Approval Details
    // ==========================================================

    @Column(name = "APPROVED_SALARY", precision = 12, scale = 2)
    private BigDecimal approvedSalary;

    @Column(name = "APPROVED_TRAVEL_ALLOWANCE", precision = 12, scale = 2)
    private BigDecimal approvedTravelAllowance;

    @Column(name = "DATE_OF_JOINING")
    private LocalDate dateOfJoining;

    @Column(name = "IA_MAPPED")
    private Boolean iaMapped;

    @Column(name = "OFFER_LETTER", length = 625)
    private String offerLetter;

    /** User Details */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "IA_SELECTED")
    private Boolean iaSelected;

    @Column(name = "LATITUDE", precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "MOM_FILE")
    private String momFile;
}
