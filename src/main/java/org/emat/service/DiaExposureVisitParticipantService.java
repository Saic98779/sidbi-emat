package org.emat.service;

import java.util.List;
import org.emat.dto.CreateDiaExposureVisitParticipantRequest;
import org.emat.dto.DiaExposureVisitParticipantResponse;
import org.emat.dto.UpdateDiaExposureVisitParticipantRequest;

public interface DiaExposureVisitParticipantService {

    DiaExposureVisitParticipantResponse create(CreateDiaExposureVisitParticipantRequest request);

    DiaExposureVisitParticipantResponse getById(Long id);

    List<DiaExposureVisitParticipantResponse> getAll();

    List<DiaExposureVisitParticipantResponse> getAllByExposureVisitId(Long exposureVisitId);

    DiaExposureVisitParticipantResponse update(
            Long id, UpdateDiaExposureVisitParticipantRequest request);

    void delete(Long id);
}
