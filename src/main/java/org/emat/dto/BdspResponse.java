package org.emat.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;

/** DTO for BDSP response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BdspResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
}