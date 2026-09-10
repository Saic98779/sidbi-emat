package org.emat.repository;

import org.emat.entity.BseAttendanceManualRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BseAttendanceManualRequestRepository extends JpaRepository<BseAttendanceManualRequest, Long> {

    List<BseAttendanceManualRequest> findByBseRecommendationId(Long bseRecommendationId);
    List<BseAttendanceManualRequest> findByIsApproved(Boolean isApproved);

}