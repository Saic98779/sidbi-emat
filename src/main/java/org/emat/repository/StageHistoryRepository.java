package org.emat.repository;

import org.emat.entity.StageHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StageHistoryRepository extends JpaRepository<StageHistory, Long> {
	List<StageHistory> findByRegistration_IdOrderByTimeDesc(Long registrationId);
}