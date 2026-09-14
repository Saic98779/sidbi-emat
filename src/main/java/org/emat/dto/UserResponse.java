package org.emat.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emat.dto.serializer.PiiIdEncryptSerializer;
import org.emat.dto.serializer.PiiStringEncryptSerializer;
import org.emat.enums.Role;

/** Data Transfer Object for user response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    @JsonSerialize(using = PiiIdEncryptSerializer.class)
    private Long id;
    private String username;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    private String email;

    private String firstName;
    private String lastName;
    private String district;
    private String state;
    private Role role;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @JsonSerialize(using = PiiStringEncryptSerializer.class)
    private String contactNo;
}
