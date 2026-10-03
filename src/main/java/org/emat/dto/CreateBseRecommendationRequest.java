package org.emat.dto;

import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdDecryptDeserializer;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.dto.serializer.PiiStringDecryptDeserializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBseRecommendationRequest {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    @NotNull
    @Positive
    private Long registrationId;

    // Vendor Details
    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    @Positive
    private Long userId;
    private Boolean iaSelected;
    // BSE Details
    @Size(max = 125)
    private String state;
    @Size(max = 125)
    private String district;
    @Size(max = 250)
    private String industryRegistrationId;
    @Size(max = 250)
    private String bseName;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    @Size(max = 19)
    private String mobileNumber;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    @Email
    @Size(max = 250)
    private String emailId;

    @Size(max = 250)
    private String highestQualification;
    private Boolean experienced;
    @Min(0)
    @Max(80)
    private Integer experienceYears;
    @Min(0)
    @Max(11)
    private Integer experienceMonths;
    @Size(max = 63)
    private String employmentStatus;
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.00")
    private BigDecimal currentSalary;
    @Min(0)
    @Max(365)
    private Integer noticePeriodDays;
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.00")
    private BigDecimal lastDrawnSalary;
    @Size(max = 625)
    private String relievingLetter;
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.00")
    private BigDecimal expectedSalary;
    @Size(max = 63)
    private String resumeStatus;
    @Size(max = 625)
    private String resumeFile;
    @Size(max = 625)
    private String salarySlip;
    @Size(max = 625)
    private String candidateCv;

    // Approval Workflow
    @Size(max = 63)
    private String gtRecommendation;
    private LocalDate gtRecommendationDate;
    @Size(max = 2000)
    private String gtRemarks;
    @Size(max = 1250)
    private String pmuRecommendation;
    private LocalDate pmuRecommendationDate;
    @Size(max = 2000)
    private String pmuRemarks;
    @Size(max = 2000)
    private String hoRecommendation;
    private LocalDate hoRecommendationDate;
    @Size(max = 2000)
    private String hoRemarks;
    @Size(max = 1250)
    private String committeeRecommendation;
    private LocalDate committeeDate;
    @Size(max = 625)
    private String committeeMom;
    @Size(max = 2500)
    private String committeeRemarks;

    // Approval Details
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.00")
    private BigDecimal approvedSalary;
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.00")
    private BigDecimal approvedTravelAllowance;
    private LocalDate dateOfJoining;
    private Boolean iaMapped;
    @Size(max = 625)
    private String offerLetter;

    @DecimalMin(value = "-180.0000000")
    @DecimalMax(value = "180.0000000")
    @Digits(integer = 3, fraction = 7)
    private BigDecimal longitude;
    @DecimalMin(value = "-90.0000000")
    @DecimalMax(value = "90.0000000")
    @Digits(integer = 2, fraction = 7)
    private BigDecimal latitude;
    @Size(max = 625)
    private String momFile;
}
