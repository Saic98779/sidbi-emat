package org.emat.repository;

import java.util.List;
import java.util.Optional;
import org.emat.entity.ActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityStatusRepository extends JpaRepository<ActivityStatus, Long> {
    List<ActivityStatus> findByActivityIdOrderByStatusUpdatedDtStampDesc(Long activityId);

    Optional<ActivityStatus> findTopByActivityIdOrderByStatusUpdatedDtStampDesc(Long activityId);
}
