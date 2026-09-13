package org.emat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppraisalDropdownDto {

    private Long id;
    private String name;
}