package org.emat.service;

import java.util.List;
import org.emat.dto.ActionPlanResponse;
import org.emat.dto.CreateActionPlanRequest;
import org.emat.dto.UpdateActionPlanRequest;
import org.emat.dto.UpdateActionPlanStatusRequest;

public interface ActionPlanService {

    ActionPlanResponse create(CreateActionPlanRequest request);

    ActionPlanResponse getById(Long id);

    List<ActionPlanResponse> getAll();

    ActionPlanResponse update(Long id, UpdateActionPlanRequest request);

    ActionPlanResponse updateStatus(Long id, UpdateActionPlanStatusRequest request);

    void delete(Long id);
}
