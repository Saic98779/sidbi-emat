package org.emat.repository;

import java.util.List;
import org.emat.entity.DiaEnhancement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaEnhancementRepository extends JpaRepository<DiaEnhancement, Long> {

    List<DiaEnhancement> findAllByIsActiveTrue();
}
