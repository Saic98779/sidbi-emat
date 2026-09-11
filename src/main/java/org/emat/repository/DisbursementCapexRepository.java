package org.emat.repository;

import java.util.Optional;
import org.emat.entity.DisbursementCapex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisbursementCapexRepository extends JpaRepository<DisbursementCapex, Long> {

    Optional<DisbursementCapex> findByRegistrationId(Long registrationId);
}
