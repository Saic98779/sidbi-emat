package org.emat.repository;

import org.emat.entity.RegionalOffice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RegionalOfficeRepository extends JpaRepository<RegionalOffice,String> {

    List<RegionalOffice> findAllByIsActiveTrue();

    Optional<RegionalOffice> findByUuid(UUID uuid);

    boolean existsByRoId(String roId);

    List<RegionalOffice> findAllByIsActiveTrueAndStateAndDistrict(
            String state,
            String district
    );
}