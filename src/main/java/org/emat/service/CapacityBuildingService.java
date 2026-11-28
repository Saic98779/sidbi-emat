package org.emat.service;

import java.time.LocalDate;
import java.util.List;
import org.emat.dto.CapacityBuildingResponse;

public interface CapacityBuildingService {

    List<CapacityBuildingResponse> getAll(LocalDate fromDate, LocalDate toDate, String source);
}
