package org.emat.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BseRecommendationResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long registrationId;

    private String industryAssociationName;

    // Vendor Details

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long userId;

    private String userName;
    private Boolean iaSelected;

    // BSE Details
    private String state;
    private String district;
    private String industryRegistrationId;
    private String bseName;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    private String mobileNumber;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
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

    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String momFile;
}
