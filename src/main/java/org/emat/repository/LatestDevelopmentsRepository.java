package org.emat.repository;

import java.util.List;
import org.emat.entity.LatestDevelopments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LatestDevelopmentsRepository extends JpaRepository<LatestDevelopments, Long> {

    List<LatestDevelopments> findAllByIsActiveTrue();
}