package org.emat.service;

import java.util.List;
import org.emat.dto.CreateDiaMsmeParticipantRequest;
import org.emat.dto.DiaMsmeParticipantResponse;
import org.emat.dto.UpdateDiaMsmeParticipantRequest;

public interface DiaMsmeParticipantService {

    DiaMsmeParticipantResponse create(CreateDiaMsmeParticipantRequest request);

    DiaMsmeParticipantResponse getById(Long id);

    List<DiaMsmeParticipantResponse> getAll();

    List<DiaMsmeParticipantResponse> getAllByEventId(Long eventId);

    DiaMsmeParticipantResponse update(Long id, UpdateDiaMsmeParticipantRequest request);

    void delete(Long id);
}
