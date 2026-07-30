package org.emat.repository;

import org.emat.dto.SidbiSdeDropdownResponse;
import org.emat.entity.SidbiSde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SidbiSdeRepository extends JpaRepository<SidbiSde, String> {

    /**
     * Find all active SIDBI SDEs.
     */
    List<SidbiSde> findAllByIsActiveTrue();

    /**
     * Find SIDBI SDE by UUID.
     */
    Optional<SidbiSde> findByUuid(UUID uuid);

    /**
     * Check whether SDE ID already exists.
     */
    boolean existsBySdeId(String sdeId);

    /**
     * Find SIDBI SDEs by State and District.
     */
    List<SidbiSde> findAllByIsActiveTrueAndRegionalOfficeStateAndRegionalOfficeDistrict(
            String state,
            String district
    );

    /**
     * Find SIDBI SDEs by Regional Office.
     */
    List<SidbiSde> findAllByIsActiveTrueAndRegionalOfficeUuid(UUID regionalOfficeUuid);

    //List<SidbiSde> findAllByIsActiveTrueAndRegionalOfficeStateAndDistrict(String state, String district);

    @Query("""
        SELECT new org.emat.dto.SidbiSdeDropdownResponse(
            s.uuid,
            s.name
        )
        FROM Branch b
        JOIN b.regionalOffice ro
        JOIN ro.sdeList s
        WHERE b.uuid = :branchUuid
        ORDER BY s.name
        """)
    List<SidbiSdeDropdownResponse> findDropdownByBranchUuid(@Param("branchUuid") UUID branchUuid);

}