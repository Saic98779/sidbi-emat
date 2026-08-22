package org.emat.repository;

import org.emat.entity.RegionalOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionalOfficeRepository extends JpaRepository<RegionalOffice, Long> {

    List<RegionalOffice> findAllByIsActiveTrue();
}