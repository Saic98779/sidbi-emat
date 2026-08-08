package org.emat.repository;

import org.emat.entity.VendorExpenditure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VendorExpenditureRepository
        extends JpaRepository<VendorExpenditure, UUID> {

    Optional<VendorExpenditure> findByRegistrationUuid(UUID registrationUuid);
}