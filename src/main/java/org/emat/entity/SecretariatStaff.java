package org.emat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecretariatStaff {

    @Column(name = "STAFF_NAME", length = 250)
    private String name;

    @Column(name = "STAFF_CONTACT", length = 25)
    private String contact;

    @Column(name = "STAFF_EMAIL", length = 250)
    private String email;
}
