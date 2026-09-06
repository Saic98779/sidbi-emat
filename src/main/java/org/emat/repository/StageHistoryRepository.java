package org.emat.repository;

import org.emat.entity.StageHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageHistoryRepository extends JpaRepository<StageHistory, Long> {
}