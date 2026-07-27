package org.emat.dto;

import lombok.Data;

@Data
public class UpdateRegionalOfficeRequest {

    private String roName;

    private String city;

    private String district;

    private String state;

    private String address;

    private String contactNo;
}