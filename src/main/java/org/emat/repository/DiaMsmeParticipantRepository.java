package org.emat.repository;

import java.util.List;
import org.emat.entity.DiaMsmeParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaMsmeParticipantRepository extends JpaRepository<DiaMsmeParticipant, Long> {

    List<DiaMsmeParticipant> findAllByIsActiveTrue();

    List<DiaMsmeParticipant> findAllByEventIdAndIsActiveTrue(Long eventId);
}
