package org.emat.repository;

import org.emat.dto.BranchDropdownResponse;
import org.emat.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BranchRepository extends JpaRepository<Branch, String> {

    /**
     * Find all active branches.
     */
    List<Branch> findAllByIsActiveTrue();

    /**
     * Find branch by UUID.
     */
    Optional<Branch> findByUuid(UUID uuid);

    /**
     * Check whether Branch ID already exists.
     */
    boolean existsByBoId(String boId);

    /**
     * Find branches by Regional Office.
     */
    List<Branch> findAllByIsActiveTrueAndRegionalOfficeUuid(UUID regionalOfficeUuid);

    /**
     * Find branches by state and district.
     */
    List<Branch> findAllByIsActiveTrueAndStateAndDistrict(
            String state,
            String district
    );

    @Query("""
    SELECT new org.emat.dto.BranchDropdownResponse(
        b.uuid,
        b.branchName
    )
    FROM Branch b
    WHERE LOWER(b.state) = LOWER(:state)
    ORDER BY b.branchName
    """)
    List<BranchDropdownResponse> findBranchDropdownByState(@Param("state") String state);
}
