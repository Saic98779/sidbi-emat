package org.emat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SidbiSdeResponse {

    private UUID uuid;

    private String sdeId;

    private String name;

    private String email;

    private String mobileNo;

    private String regionalOfficeUuid;

    private String roId;

    private String roName;

    private String city;

    private String district;

    private String state;

    private Boolean isActive;
}