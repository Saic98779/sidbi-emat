package org.emat.repository;

import org.emat.entity.DisbursementCapex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisbursementCapexRepository extends JpaRepository<DisbursementCapex, Long> {

    Optional<DisbursementCapex> findByRegistrationId(Long registrationId);
}