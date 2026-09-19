package org.emat.repository;

import java.util.List;
import org.emat.entity.DisbursementNoteCapacityBuildingIa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisbursementNoteCapacityBuildingIaRepository
        extends JpaRepository<DisbursementNoteCapacityBuildingIa, Long> {

    List<DisbursementNoteCapacityBuildingIa> findAllByIsActiveTrue();

    List<DisbursementNoteCapacityBuildingIa> findByRegistrationId(Long registrationId);
}