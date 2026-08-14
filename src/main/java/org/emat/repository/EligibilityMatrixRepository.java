package org.emat.repository;

import org.emat.entity.EligibilityMatrix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EligibilityMatrixRepository extends JpaRepository<EligibilityMatrix, UUID> {

    Optional<EligibilityMatrix> findByRegistration_Uuid(UUID registrationUuid);

    boolean existsByRegistration_Uuid(UUID registrationUuid);
}