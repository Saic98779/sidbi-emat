package org.emat.service;

import java.util.List;
import org.emat.dto.CreateDiaCapacityBuildingProgramRequest;
import org.emat.dto.DiaCapacityBuildingProgramResponse;
import org.emat.dto.UpdateDiaCapacityBuildingProgramRequest;

public interface DiaCapacityBuildingProgramService {

    DiaCapacityBuildingProgramResponse create(CreateDiaCapacityBuildingProgramRequest request);

    DiaCapacityBuildingProgramResponse getById(Long id);

    List<DiaCapacityBuildingProgramResponse> getAll();

    DiaCapacityBuildingProgramResponse update(
            Long id, UpdateDiaCapacityBuildingProgramRequest request);

    void delete(Long id);
}
