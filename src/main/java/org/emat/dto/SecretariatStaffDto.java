package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiStringDecryptDeserializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecretariatStaffDto {
    private String name;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String contact;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String email;
}