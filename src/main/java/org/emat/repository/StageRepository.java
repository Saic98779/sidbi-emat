package org.emat.repository;

import java.util.Optional;
import org.emat.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, Long> {
    Optional<Stage> findFirstBySubStageIgnoreCase(String subStage);

    Optional<Stage> findFirstByStageIgnoreCaseAndSubStageIsNull(String stage);

    Optional<Stage> findFirstByStageIgnoreCase(String stage);
}
