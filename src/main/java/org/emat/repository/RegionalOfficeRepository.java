package org.emat.repository;

import java.util.List;
import org.emat.entity.RegionalOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionalOfficeRepository extends JpaRepository<RegionalOffice, Long> {

    List<RegionalOffice> findAllByIsActiveTrue();
}
