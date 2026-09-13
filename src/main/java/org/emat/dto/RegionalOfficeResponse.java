package org.emat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegionalOfficeResponse {

    private Long id;

    private String roId;

    private String roName;

    private String city;

    private String district;

    private String state;

    private String address;

    private String contactNo;
}