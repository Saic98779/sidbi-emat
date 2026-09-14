package org.emat.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SidbiSdeDropdownResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;
    private String name;
}
