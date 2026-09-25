package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiStringDecryptDeserializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

/** DTO for creating a new BDSP. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBdspRequest {

    private String nameOfBdsp;
    private String rationaleForOnboarding;
    private String theme;
    private String areaOfServiceExpertise;
    private String state;
    private String district;
    private String contact;
    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String email;
    private String kyc;
}