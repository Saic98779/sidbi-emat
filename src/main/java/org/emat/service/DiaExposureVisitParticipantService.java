package org.emat.service;

import java.util.List;
import org.emat.dto.CreateDiaExposureVisitParticipantRequest;
import org.emat.dto.DiaExposureVisitParticipantImportResult;
import org.emat.dto.DiaExposureVisitParticipantResponse;
import org.emat.dto.UpdateDiaExposureVisitParticipantRequest;
import org.springframework.web.multipart.MultipartFile;

public interface DiaExposureVisitParticipantService {

    DiaExposureVisitParticipantResponse create(CreateDiaExposureVisitParticipantRequest request);

    DiaExposureVisitParticipantResponse getById(Long id);

    List<DiaExposureVisitParticipantResponse> getAll();

    List<DiaExposureVisitParticipantResponse> getAllByExposureVisitId(Long exposureVisitId);

    DiaExposureVisitParticipantResponse update(
            Long id, UpdateDiaExposureVisitParticipantRequest request);

    void delete(Long id);

    /**
     * Imports participants from an Excel file and associates them with the given exposure visit.
     * Participants already associated with the visit, or repeated within the file, are skipped;
     * invalid rows are reported without aborting the import.
     */
    DiaExposureVisitParticipantImportResult importFromExcel(
            Long diaExposureVisitId, MultipartFile file);

    /** Builds the Excel (.xlsx) template expected by {@link #importFromExcel}. */
    byte[] generateImportTemplate();
}
