package org.emat.repository;

import org.emat.entity.VendorDisbursementSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorDisbursementRepository extends JpaRepository<VendorDisbursementSalary, Long> {
}

