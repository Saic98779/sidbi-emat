package org.emat.repository;

import java.util.List;
import org.emat.entity.GrantDisbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrantDisbursementRepository extends JpaRepository<GrantDisbursement, Long> {

    List<GrantDisbursement> findAllByIsActiveTrue();
}
