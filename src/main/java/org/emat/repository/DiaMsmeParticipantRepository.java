package org.emat.repository;

import java.util.List;
import java.util.Optional;
import org.emat.entity.DiaMsmeParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaMsmeParticipantRepository extends JpaRepository<DiaMsmeParticipant, Long> {

    List<DiaMsmeParticipant> findAllByIsActiveTrue();

    List<DiaMsmeParticipant> findAllByEventIdAndIsActiveTrue(Long eventId);

    /**
     * Returns active participants linked to the program either through the Many-to-Many join table
     * or through the legacy {@code EVENT_ID} column.
     */
    @Query(
            "SELECT DISTINCT p FROM DiaMsmeParticipant p"
                    + " LEFT JOIN p.events e"
                    + " WHERE p.isActive = true"
                    + " AND (e.id = :eventId OR p.event.id = :eventId)")
    List<DiaMsmeParticipant> findAllActiveAssociatedWithEvent(@Param("eventId") Long eventId);

    /**
     * Checks whether the participant is already associated with the given program, either through
     * the join table or the legacy {@code EVENT_ID} column.
     */
    @Query(
            "SELECT COUNT(p) > 0 FROM DiaMsmeParticipant p LEFT JOIN p.events e"
                    + " WHERE p.id = :participantId"
                    + " AND (e.id = :eventId OR p.event.id = :eventId)")
    boolean existsAssociation(
            @Param("participantId") Long participantId, @Param("eventId") Long eventId);

    Optional<DiaMsmeParticipant> findFirstByPanNoIgnoreCaseAndIsActiveTrueOrderByIdAsc(
            String panNo);

    Optional<DiaMsmeParticipant> findFirstByUdyamAadharNoIgnoreCaseAndIsActiveTrueOrderByIdAsc(
            String udyamAadharNo);

    Optional<DiaMsmeParticipant> findFirstByEmailIgnoreCaseAndIsActiveTrueOrderByIdAsc(
            String email);

    Optional<DiaMsmeParticipant> findFirstByContactNoAndIsActiveTrueOrderByIdAsc(
            String contactNo);
}
