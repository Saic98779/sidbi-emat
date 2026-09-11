package org.emat.repository;

import java.util.Optional;
import org.emat.entity.EligibilityMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EligibilityMatrixRepository extends JpaRepository<EligibilityMatrix, Long> {

    boolean existsByRegistrationId(Long registrationId);

    Optional<EligibilityMatrix> findByRegistrationId(Long registrationId);
}
