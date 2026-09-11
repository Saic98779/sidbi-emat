package org.emat.repository;

import java.util.List;
import org.emat.dto.SidbiSdeDropdownResponse;
import org.emat.entity.SidbiSde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SidbiSdeRepository extends JpaRepository<SidbiSde, Long> {

    @Query(
            """
            SELECT new org.emat.dto.SidbiSdeDropdownResponse(
                s.id,
                s.name
            )
            FROM Branch b
            JOIN b.regionalOffice ro
            JOIN ro.sdeList s
            WHERE b.id = :branchId
            ORDER BY s.name
            """)
    List<SidbiSdeDropdownResponse> findDropdownByBranchId(@Param("branchId") Long branchId);
}
