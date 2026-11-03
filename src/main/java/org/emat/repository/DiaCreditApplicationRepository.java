package org.emat.repository;

import java.util.List;
import org.emat.entity.DiaCreditApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaCreditApplicationRepository extends JpaRepository<DiaCreditApplication, Long> {

    List<DiaCreditApplication> findAllByIsActiveTrue();
}
