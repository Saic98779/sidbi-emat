package org.emat.repository;

import java.util.List;
import org.emat.entity.PopUps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopUpsRepository extends JpaRepository<PopUps, Long> {

    List<PopUps> findAllByIsActiveTrue();
}