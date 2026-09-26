package org.emat.repository;

import java.util.List;
import org.emat.entity.PincodeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PincodeMasterRepository extends JpaRepository<PincodeMaster, Long> {

    @Query(
            """
            SELECT DISTINCT p.stateName
            FROM PincodeMaster p
            ORDER BY p.stateName
            """)
    List<String> findDistinctStates();

    @Query(
            """
            SELECT DISTINCT p.district
            FROM PincodeMaster p
            WHERE LOWER(TRIM(p.stateName)) = LOWER(TRIM(:state))
            ORDER BY p.district
            """)
    List<String> findDistinctDistrictsByState(@Param("state") String state);

    @Query(
            """
            SELECT DISTINCT p.pincode
            FROM PincodeMaster p
            WHERE LOWER(TRIM(p.stateName)) = LOWER(TRIM(:state))
              AND LOWER(TRIM(p.district)) = LOWER(TRIM(:district))
            ORDER BY p.pincode
            """)
    List<String> findPincodesByStateAndDistrict(
            @Param("state") String state, @Param("district") String district);

    boolean existsByStateNameAndDistrictAndPincode(
            String stateName, String district, String pincode);
}
