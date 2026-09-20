package org.emat.service;

import java.util.List;
import org.emat.dto.CreateDisbursementNoteCapacityBuildingIaRequest;
import org.emat.dto.DisbursementNoteCapacityBuildingIaResponse;
import org.emat.dto.UpdateDisbursementNoteCapacityBuildingIaRequest;
import org.emat.dto.UpdateDisbursementNoteCapacityBuildingIaStatusRequest;

public interface DisbursementNoteCapacityBuildingIaService {

    DisbursementNoteCapacityBuildingIaResponse create(
            CreateDisbursementNoteCapacityBuildingIaRequest request);

    DisbursementNoteCapacityBuildingIaResponse getById(Long id);

    DisbursementNoteCapacityBuildingIaResponse getByRegistrationId(Long registrationId);

    List<DisbursementNoteCapacityBuildingIaResponse> getAll();

    DisbursementNoteCapacityBuildingIaResponse update(
            Long id, UpdateDisbursementNoteCapacityBuildingIaRequest request);

    DisbursementNoteCapacityBuildingIaResponse updateStatus(
            Long id, UpdateDisbursementNoteCapacityBuildingIaStatusRequest request);

    void delete(Long id);
}