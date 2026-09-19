package org.emat.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for updating an existing BDSP. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBdspRequest {

    private String nameOfBdsp;
    private String rationaleForOnboarding;
    private String theme;
    private String areaOfServiceExpertise;
    private String state;
    private String district;
    private String contact;
    private String email;
    private String kyc;
    private String requestedBy;
    private LocalDate requestDate;
}