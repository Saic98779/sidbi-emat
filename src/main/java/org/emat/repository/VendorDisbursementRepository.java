package org.emat.repository;

import org.emat.entity.VendorDisbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorDisbursementRepository extends JpaRepository<VendorDisbursement, Long> {
}

