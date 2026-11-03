package org.emat.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

/** DTO for DIA Exposure Visit Participant response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaExposureVisitParticipantResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long exposureVisitId;

    private String roName;
    private String industryAssociationName;
    private LocalDate visitDate;

    private String msmeName;
    private String designation;
    private String gender;
    private String fatherHusbandName;
    private LocalDate dateOfBirth;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    private String email;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    private String contactNo;

    private String panNo;
    private String udyamAadharNo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
}
