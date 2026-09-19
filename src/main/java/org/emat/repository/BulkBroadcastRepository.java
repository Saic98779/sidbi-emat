package org.emat.repository;

import java.util.List;
import org.emat.entity.BulkBroadcast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulkBroadcastRepository extends JpaRepository<BulkBroadcast, Long> {

    List<BulkBroadcast> findAllByIsActiveTrue();
}