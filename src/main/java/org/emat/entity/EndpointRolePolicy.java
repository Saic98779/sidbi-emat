package org.emat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Stores editable endpoint role policies. */
@Entity
@Table(name = "endpoint_role_policy")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EndpointRolePolicy {

    @Id
    @Column(name = "policy_key", nullable = false, length = 125)
    private String policyKey;

    @Column(name = "roles_csv", nullable = false, length = 2500)
    private String rolesCsv;

    @Column(name = "description", length = 625)
    private String description;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
