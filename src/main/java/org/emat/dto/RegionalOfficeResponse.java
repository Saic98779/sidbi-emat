package org.emat.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;

@Data
@Builder
public class RegionalOfficeResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;

    private String roId;

    private String roName;

    private String city;

    private String district;

    private String state;

    private String address;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    private String contactNo;
}
