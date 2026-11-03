package org.emat.service;

import java.util.List;
import org.emat.dto.CreateDiaExposureVisitRequest;
import org.emat.dto.DiaExposureVisitResponse;
import org.emat.dto.UpdateDiaExposureVisitRequest;

public interface DiaExposureVisitService {

    DiaExposureVisitResponse create(CreateDiaExposureVisitRequest request);

    DiaExposureVisitResponse getById(Long id);

    List<DiaExposureVisitResponse> getAll();

    DiaExposureVisitResponse update(Long id, UpdateDiaExposureVisitRequest request);

    void delete(Long id);
}
