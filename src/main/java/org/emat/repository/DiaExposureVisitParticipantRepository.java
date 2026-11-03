package org.emat.repository;

import java.util.List;
import org.emat.entity.DiaExposureVisitParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaExposureVisitParticipantRepository
        extends JpaRepository<DiaExposureVisitParticipant, Long> {

    List<DiaExposureVisitParticipant> findAllByIsActiveTrue();

    List<DiaExposureVisitParticipant> findAllByExposureVisitIdAndIsActiveTrue(Long exposureVisitId);
}
