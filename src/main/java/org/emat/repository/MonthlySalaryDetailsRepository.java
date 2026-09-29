package org.emat.repository;

import java.util.List;
import org.emat.entity.MonthlySalaryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MonthlySalaryDetailsRepository extends JpaRepository<MonthlySalaryDetails, Long> {
    @Query("""
            select d from MonthlySalaryDetails d
            join fetch d.bse b
            join fetch d.bseSalary s
            where b.registration.id = :registrationId
              and d.isActive = true and b.isActive = true and s.isActive = true
            order by s.id, d.id
            """)
    List<MonthlySalaryDetails> findForDiaDocument(@Param("registrationId") Long registrationId);
}
