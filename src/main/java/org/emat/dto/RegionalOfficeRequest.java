package org.emat.dto;

import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.emat.dto.serializer.PiiStringDecryptDeserializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;

@Data
public class RegionalOfficeRequest {

    @NotBlank private String roId;

    @NotBlank private String roName;

    @NotBlank private String city;

    @NotBlank private String district;

    @NotBlank private String state;

    private String address;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
        @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String contactNo;
}
