package org.emat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RegistrationDropdownDto {

    private UUID uuid;
    private String name;
}