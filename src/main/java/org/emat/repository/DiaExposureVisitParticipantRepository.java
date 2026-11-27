package org.emat.repository;

import java.util.List;
import java.util.Optional;
import org.emat.entity.DiaExposureVisitParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaExposureVisitParticipantRepository
        extends JpaRepository<DiaExposureVisitParticipant, Long> {

    List<DiaExposureVisitParticipant> findAllByIsActiveTrue();

    List<DiaExposureVisitParticipant> findAllByExposureVisitIdAndIsActiveTrue(Long exposureVisitId);

    /**
     * Returns active participants linked to the visit either through the Many-to-Many join table or
     * through the legacy {@code EXPOSURE_VISIT_ID} column.
     */
    @Query(
            "SELECT DISTINCT p FROM DiaExposureVisitParticipant p"
                    + " LEFT JOIN p.exposureVisits v"
                    + " WHERE p.isActive = true"
                    + " AND (v.id = :exposureVisitId OR p.exposureVisit.id = :exposureVisitId)")
    List<DiaExposureVisitParticipant> findAllActiveAssociatedWithExposureVisit(
            @Param("exposureVisitId") Long exposureVisitId);

    /**
     * Checks whether the participant is already associated with the given exposure visit, either
     * through the join table or the legacy {@code EXPOSURE_VISIT_ID} column.
     */
    @Query(
            "SELECT COUNT(p) > 0 FROM DiaExposureVisitParticipant p LEFT JOIN p.exposureVisits v"
                    + " WHERE p.id = :participantId"
                    + " AND (v.id = :exposureVisitId OR p.exposureVisit.id = :exposureVisitId)")
    boolean existsAssociation(
            @Param("participantId") Long participantId,
            @Param("exposureVisitId") Long exposureVisitId);

    Optional<DiaExposureVisitParticipant> findFirstByPanNoIgnoreCaseAndIsActiveTrueOrderByIdAsc(
            String panNo);

    Optional<DiaExposureVisitParticipant>
            findFirstByUdyamAadharNoIgnoreCaseAndIsActiveTrueOrderByIdAsc(String udyamAadharNo);

    Optional<DiaExposureVisitParticipant> findFirstByEmailIgnoreCaseAndIsActiveTrueOrderByIdAsc(
            String email);

    Optional<DiaExposureVisitParticipant> findFirstByContactNoAndIsActiveTrueOrderByIdAsc(
            String contactNo);
}
