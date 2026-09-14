package org.emat.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import org.emat.dto.serializer.PiiIdEncryptSerializer;

@Data
@Builder
public class AppraisalDropdownDto {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;
    private String name;
}
