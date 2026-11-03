package org.emat.service;

import java.util.List;
import org.emat.dto.CreateGrantDisbursementRequest;
import org.emat.dto.GrantDisbursementResponse;
import org.emat.dto.UpdateGrantDisbursementRequest;

public interface GrantDisbursementService {

    GrantDisbursementResponse create(CreateGrantDisbursementRequest request);

    GrantDisbursementResponse getById(Long id);

    List<GrantDisbursementResponse> getAll();

    GrantDisbursementResponse update(Long id, UpdateGrantDisbursementRequest request);

    void delete(Long id);
}
