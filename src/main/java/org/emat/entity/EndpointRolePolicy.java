package org.emat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Stores editable endpoint role policies.
 */
@Entity
@Table(name = "endpoint_role_policy")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EndpointRolePolicy {

    @Id
    @Column(name = "policy_key", nullable = false, length = 100)
    private String policyKey;

    @Column(name = "roles_csv", nullable = false, length = 2000)
    private String rolesCsv;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

