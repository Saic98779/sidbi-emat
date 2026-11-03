package org.emat.repository;

import java.util.List;
import org.emat.entity.DiaCapacityBuildingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaCapacityBuildingProgramRepository
        extends JpaRepository<DiaCapacityBuildingProgram, Long> {

    List<DiaCapacityBuildingProgram> findAllByIsActiveTrue();
}
