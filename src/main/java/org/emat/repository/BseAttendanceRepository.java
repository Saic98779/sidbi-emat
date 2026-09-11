package org.emat.repository;

import java.util.List;
import org.emat.entity.BseAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BseAttendanceRepository extends JpaRepository<BseAttendance, Long> {

    List<BseAttendance> findByBseRecommendationId(Long bseRecommendationId);
}
