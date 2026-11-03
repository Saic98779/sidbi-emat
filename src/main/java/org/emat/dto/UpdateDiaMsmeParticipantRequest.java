package org.emat.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdDecryptDeserializer;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.dto.serializer.PiiStringDecryptDeserializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

/** DTO for updating an existing DIA MSME Participant. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDiaMsmeParticipantRequest {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    @JsonDeserialize(using = PiiIdDecryptDeserializer.class)
    private Long eventId;

    private String msmeName;
    private Boolean firstTimeParticipant;
    private String designation;
    private String gender;
    private String fatherHusbandName;
    private LocalDate dateOfBirth;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String email;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String contactNo;

    private String panNo;
    private String udyamAadharNo;
}
