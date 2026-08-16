package org.emat.repository;

import org.emat.entity.EndpointRolePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndpointRolePolicyRepository extends JpaRepository<EndpointRolePolicy, String> {
}

