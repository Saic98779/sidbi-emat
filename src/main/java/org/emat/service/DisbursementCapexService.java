package org.emat.service;

import java.util.List;
import org.emat.dto.DisbursementCapexRequest;
import org.emat.dto.DisbursementCapexResponse;

public interface DisbursementCapexService {

    DisbursementCapexResponse create(DisbursementCapexRequest request);

    DisbursementCapexResponse getById(Long id);

    DisbursementCapexResponse getByRegistrationId(Long registrationId);

    List<DisbursementCapexResponse> getAll();

    DisbursementCapexResponse update(Long id, DisbursementCapexRequest request);

    void delete(Long id);
}
