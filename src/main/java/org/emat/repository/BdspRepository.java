package org.emat.repository;

import java.util.List;
import org.emat.entity.Bdsp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BdspRepository extends JpaRepository<Bdsp, Long> {

    List<Bdsp> findAllByIsActiveTrue();
}