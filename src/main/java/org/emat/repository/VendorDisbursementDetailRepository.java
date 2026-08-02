package org.emat.repository;

import org.emat.entity.VendorDisbursementDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorDisbursementDetailRepository extends JpaRepository<VendorDisbursementDetail, Long> {
}
