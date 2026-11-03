package org.emat.service;

import java.util.List;
import org.emat.dto.CreateDiaEnhancementRequest;
import org.emat.dto.DiaEnhancementResponse;
import org.emat.dto.UpdateDiaEnhancementRequest;

public interface DiaEnhancementService {

    DiaEnhancementResponse create(CreateDiaEnhancementRequest request);

    DiaEnhancementResponse getById(Long id);

    List<DiaEnhancementResponse> getAll();

    DiaEnhancementResponse update(Long id, UpdateDiaEnhancementRequest request);

    void delete(Long id);
}
