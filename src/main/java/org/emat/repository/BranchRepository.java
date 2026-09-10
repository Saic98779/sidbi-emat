package org.emat.repository;

import org.emat.dto.BranchDropdownResponse;
import org.emat.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    @Query("""
    SELECT new org.emat.dto.BranchDropdownResponse(
        b.id,
        b.branchName
    )
    FROM Branch b
    WHERE LOWER(b.state) = LOWER(:state)
    ORDER BY b.branchName
    """)
    List<BranchDropdownResponse> findBranchDropdownByState(@Param("state") String state);
}
