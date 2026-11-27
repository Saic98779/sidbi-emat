package org.emat.service;

import java.util.List;
import org.emat.dto.CreateDiaMsmeParticipantRequest;
import org.emat.dto.DiaMsmeParticipantImportResult;
import org.emat.dto.DiaMsmeParticipantResponse;
import org.emat.dto.UpdateDiaMsmeParticipantRequest;
import org.springframework.web.multipart.MultipartFile;

public interface DiaMsmeParticipantService {

    DiaMsmeParticipantResponse create(CreateDiaMsmeParticipantRequest request);

    DiaMsmeParticipantResponse getById(Long id);

    List<DiaMsmeParticipantResponse> getAll();

    List<DiaMsmeParticipantResponse> getAllByEventId(Long eventId);

    DiaMsmeParticipantResponse update(Long id, UpdateDiaMsmeParticipantRequest request);

    void delete(Long id);

    /**
     * Imports MSME participants from an Excel file and associates them with the given capacity
     * building program. Participants already associated with the program, or repeated within the
     * file, are skipped; invalid rows are reported without aborting the import.
     */
    DiaMsmeParticipantImportResult importFromExcel(
            Long diaCapacityBuildingProgramId, MultipartFile file);

    /** Builds the Excel (.xlsx) template expected by {@link #importFromExcel}. */
    byte[] generateImportTemplate();
}
