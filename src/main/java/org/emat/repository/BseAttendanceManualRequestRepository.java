package org.emat.repository;

import org.emat.entity.BseAttendanceManualRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BseAttendanceManualRequestRepository extends JpaRepository<BseAttendanceManualRequest, UUID> {

    List<BseAttendanceManualRequest> findByBseRecommendationUuid(UUID bseRecommendationId);
    List<BseAttendanceManualRequest> findByIsApproved(Boolean isApproved);

}