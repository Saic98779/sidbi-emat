package org.emat.dto;

import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiStringDecryptDeserializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;
import org.emat.enums.Role;

/** Data Transfer Object for creating a new user. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private String username;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
        @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String password;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
        @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String email;

    private String firstName;
    private String lastName;
    private String district;
    private String state;
    private Role role;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
        @JsonDeserialize(using = PiiStringDecryptDeserializer.class)
    private String contactNo;
}
