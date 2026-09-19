package org.emat.service;

import java.util.List;
import org.emat.dto.CreateLatestDevelopmentsRequest;
import org.emat.dto.LatestDevelopmentsResponse;
import org.emat.dto.UpdateLatestDevelopmentsRequest;

public interface LatestDevelopmentsService {

    LatestDevelopmentsResponse create(CreateLatestDevelopmentsRequest request);

    LatestDevelopmentsResponse getById(Long id);

    List<LatestDevelopmentsResponse> getAll();

    LatestDevelopmentsResponse update(Long id, UpdateLatestDevelopmentsRequest request);

    void delete(Long id);
}