package org.emat.service;

import java.util.List;
import org.emat.dto.CreateDisbursementNoteCapacityBuildingIaOfficialsRequest;
import org.emat.dto.DisbursementNoteCapacityBuildingIaOfficialsResponse;
import org.emat.dto.UpdateDisbursementNoteCapacityBuildingIaOfficialsRequest;
import org.emat.dto.UpdateDisbursementNoteCapacityBuildingIaOfficialsStatusRequest;

public interface DisbursementNoteCapacityBuildingIaOfficialsService {

    DisbursementNoteCapacityBuildingIaOfficialsResponse create(
            CreateDisbursementNoteCapacityBuildingIaOfficialsRequest request);

    DisbursementNoteCapacityBuildingIaOfficialsResponse getById(Long id);

    DisbursementNoteCapacityBuildingIaOfficialsResponse getByRegistrationId(Long registrationId);

    List<DisbursementNoteCapacityBuildingIaOfficialsResponse> getAll();

    DisbursementNoteCapacityBuildingIaOfficialsResponse update(
            Long id, UpdateDisbursementNoteCapacityBuildingIaOfficialsRequest request);

    DisbursementNoteCapacityBuildingIaOfficialsResponse updateStatus(
            Long id, UpdateDisbursementNoteCapacityBuildingIaOfficialsStatusRequest request);

    void delete(Long id);
}