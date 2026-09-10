package org.emat.repository;

import org.emat.entity.BseAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BseAttendanceRepository extends JpaRepository<BseAttendance, Long> {

    List<BseAttendance> findByBseRecommendationId(Long bseRecommendationId);

}