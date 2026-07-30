package org.emat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RegionalOfficeResponse {

    private UUID uuid;

    private String roId;

    private String roName;

    private String city;

    private String district;

    private String state;

    private String address;

    private String contactNo;
}