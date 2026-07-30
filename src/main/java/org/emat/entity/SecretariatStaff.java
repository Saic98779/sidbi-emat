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

    @Column(name = "STAFF_NAME", length = 200)
    private String name;

    @Column(name = "STAFF_CONTACT", length = 20)
    private String contact;

    @Column(name = "STAFF_EMAIL", length = 200)
    private String email;
}
