package org.emat.repository;

import org.emat.entity.BseAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BseAttendanceRepository extends JpaRepository<BseAttendance, UUID> {

    List<BseAttendance> findByBseRecommendationUuid(UUID bseRecommendationId);

}