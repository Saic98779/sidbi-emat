package org.emat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationDropdownDto {

    private Long id;
    private String name;
}