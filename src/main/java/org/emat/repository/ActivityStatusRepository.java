package org.emat.repository;

import org.emat.entity.ActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityStatusRepository extends JpaRepository<ActivityStatus, Long> {
    List<ActivityStatus> findByActivityIdOrderByStatusUpdatedDtStampDesc(Long activityId);
    Optional<ActivityStatus> findTopByActivityIdOrderByStatusUpdatedDtStampDesc(Long activityId);
}

