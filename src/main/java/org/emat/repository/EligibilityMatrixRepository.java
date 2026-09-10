package org.emat.repository;

import org.emat.entity.EligibilityMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EligibilityMatrixRepository extends JpaRepository<EligibilityMatrix, Long> {

    boolean existsByRegistrationId(Long registrationId);

    Optional<EligibilityMatrix> findByRegistrationId(Long registrationId);
}