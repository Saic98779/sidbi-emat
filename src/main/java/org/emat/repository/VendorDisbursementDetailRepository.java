package org.emat.repository;

import org.emat.entity.MonthlySalaryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorDisbursementDetailRepository extends JpaRepository<MonthlySalaryDetails, Long> {
}
