package org.emat.repository;

import java.util.List;
import org.emat.entity.DisbursementNoteCapacityBuildingIaOfficials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisbursementNoteCapacityBuildingIaOfficialsRepository
        extends JpaRepository<DisbursementNoteCapacityBuildingIaOfficials, Long> {

    List<DisbursementNoteCapacityBuildingIaOfficials> findAllByIsActiveTrue();

    List<DisbursementNoteCapacityBuildingIaOfficials> findByRegistrationId(Long registrationId);
}