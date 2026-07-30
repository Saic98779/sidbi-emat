package org.emat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecretariatStaffDto {
    private String name;
    private String contact;
    private String email;
}
