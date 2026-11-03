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

/** DTO for DIA MSME Participant response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaMsmeParticipantResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long eventId;

    private String eventName;
    private LocalDate eventDate;
    private String industryAssociationName;

    private String msmeName;
    private Boolean firstTimeParticipant;
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
