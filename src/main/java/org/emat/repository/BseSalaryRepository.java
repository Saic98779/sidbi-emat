package org.emat.repository;

import org.emat.entity.BseSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BseSalaryRepository extends JpaRepository<BseSalary, Long> {
}
