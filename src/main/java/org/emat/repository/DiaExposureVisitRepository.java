package org.emat.repository;

import java.util.List;
import org.emat.entity.DiaExposureVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaExposureVisitRepository extends JpaRepository<DiaExposureVisit, Long> {

    List<DiaExposureVisit> findAllByIsActiveTrue();
}
