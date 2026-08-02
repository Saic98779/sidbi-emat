package org.emat.repository;

import org.emat.dto.SidbiSdeDropdownResponse;
import org.emat.entity.SidbiSde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SidbiSdeRepository extends JpaRepository<SidbiSde, String> {

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