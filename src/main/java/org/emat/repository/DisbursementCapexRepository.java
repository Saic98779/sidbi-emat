package org.emat.repository;

import org.emat.entity.DisbursementCapex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DisbursementCapexRepository extends JpaRepository<DisbursementCapex, UUID> {

    Optional<DisbursementCapex> findByRegistrationUuid(UUID registrationUuid);
}