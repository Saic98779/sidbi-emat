package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBseRecommendationRequest {

    // BSE Details
    private String bseName;
    private String mobileNumber;
    private String emailId;
    private String highestQualification;
    private Boolean experienced;
    private Integer experienceYears;
    private Integer experienceMonths;
    private String employmentStatus;
    private BigDecimal currentSalary;
    private Integer noticePeriodDays;
    private BigDecimal lastDrawnSalary;
    private String relievingLetter;
    private BigDecimal expectedSalary;
    private String resumeStatus;
    private String resumeFile;
    private String salarySlip;
    private String candidateCv;

    // Approval Workflow
    private String gtRecommendation;
    private LocalDate gtRecommendationDate;
    private String gtRemarks;
    private String pmuRecommendation;
    private LocalDate pmuRecommendationDate;
    private String pmuRemarks;
    private String hoRecommendation;
    private LocalDate hoRecommendationDate;
    private String hoRemarks;
    private String committeeRecommendation;
    private LocalDate committeeDate;
    private String committeeMom;
    private String committeeRemarks;

    // Approval Details
    private BigDecimal approvedSalary;
    private BigDecimal approvedTravelAllowance;
    private LocalDate dateOfJoining;
    private Boolean iaMapped;
    private String offerLetter;
}

