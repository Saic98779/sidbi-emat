package org.emat.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateDiaMsmeParticipantRequest;
import org.emat.dto.DiaMsmeParticipantImportResult;
import org.emat.dto.DiaMsmeParticipantImportRowResult;
import org.emat.dto.DiaMsmeParticipantImportRowResult.Status;
import org.emat.dto.DiaMsmeParticipantResponse;
import org.emat.dto.ParticipantImportRow;
import org.emat.dto.UpdateDiaMsmeParticipantRequest;
import org.emat.entity.DiaCapacityBuildingProgram;
import org.emat.entity.DiaMsmeParticipant;
import org.emat.mapper.DiaMsmeParticipantMapper;
import org.emat.repository.DiaMsmeParticipantRepository;
import org.emat.service.DiaMsmeParticipantService;
import org.emat.util.ParticipantExcelParser;
import org.emat.util.ParticipantExcelParser.ParsedRow;
import org.emat.validator.DiaMsmeParticipantValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DiaMsmeParticipantServiceImpl implements DiaMsmeParticipantService {

    private final DiaMsmeParticipantRepository repository;
    private final DiaMsmeParticipantMapper mapper;
    private final DiaMsmeParticipantValidator validator;
    private final ParticipantExcelParser excelParser;

    @Override
    public DiaMsmeParticipantResponse create(CreateDiaMsmeParticipantRequest request) {
        log.info(
                "Creating DIA MSME Participant for DIA Capacity Building Program ID: {}",
                request.getEventId());
        DiaCapacityBuildingProgram event = validator.getEventOrThrow(request.getEventId());
        DiaMsmeParticipant participant = mapper.toEntity(request, event);
        participant.getEvents().add(event);
        return mapper.toResponse(repository.save(participant));
    }

    @Override
    @Transactional(readOnly = true)
    public DiaMsmeParticipantResponse getById(Long id) {
        log.debug("Fetching DIA MSME Participant with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaMsmeParticipantResponse> getAll() {
        log.debug("Fetching all active DIA MSME Participants");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaMsmeParticipantResponse> getAllByEventId(Long eventId) {
        log.debug(
                "Fetching all active DIA MSME Participants for DIA Capacity Building Program ID:"
                        + " {}",
                eventId);
        return repository.findAllActiveAssociatedWithEvent(eventId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public DiaMsmeParticipantResponse update(Long id, UpdateDiaMsmeParticipantRequest request) {
        log.info("Updating DIA MSME Participant with ID: {}", id);
        DiaMsmeParticipant participant = validator.getByIdOrThrow(id);

        if (request.getEventId() != null
                && (participant.getEvent() == null
                        || !request.getEventId().equals(participant.getEvent().getId()))) {
            DiaCapacityBuildingProgram newEvent = validator.getEventOrThrow(request.getEventId());
            // Keep the Many-to-Many association in step with the primary event.
            if (participant.getEvent() != null) {
                Long oldEventId = participant.getEvent().getId();
                participant.getEvents().removeIf(e -> oldEventId.equals(e.getId()));
            }
            participant.getEvents().add(newEvent);
            participant.setEvent(newEvent);
        }

        mapper.applyUpdateRequest(participant, request);
        return mapper.toResponse(repository.save(participant));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting DIA MSME Participant with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }

    @Override
    public DiaMsmeParticipantImportResult importFromExcel(
            Long diaCapacityBuildingProgramId, MultipartFile file) {
        DiaCapacityBuildingProgram event = validator.getEventOrThrow(diaCapacityBuildingProgramId);
        excelParser.validateFile(file);
        log.info(
                "Importing DIA MSME Participants for DIA Capacity Building Program ID: {} from"
                        + " file: {}",
                diaCapacityBuildingProgramId,
                file.getOriginalFilename());

        List<ParsedRow> parsedRows = excelParser.parse(file);

        List<DiaMsmeParticipantImportRowResult> imported = new ArrayList<>();
        List<DiaMsmeParticipantImportRowResult> skipped = new ArrayList<>();
        List<DiaMsmeParticipantImportRowResult> invalid = new ArrayList<>();
        // Identifier keys of participants already handled in this file, to skip in-file repeats.
        Set<String> seenKeys = new HashSet<>();

        for (ParsedRow parsedRow : parsedRows) {
            DiaMsmeParticipantImportRowResult result =
                    processImportRow(parsedRow, event, seenKeys);
            switch (result.getStatus()) {
                case IMPORTED -> imported.add(result);
                case SKIPPED -> skipped.add(result);
                default -> invalid.add(result);
            }
        }

        log.info(
                "DIA MSME Participant import completed for program ID {}: {} total, {} imported,"
                        + " {} skipped, {} invalid",
                diaCapacityBuildingProgramId,
                parsedRows.size(),
                imported.size(),
                skipped.size(),
                invalid.size());

        return DiaMsmeParticipantImportResult.builder()
                .diaCapacityBuildingProgramId(diaCapacityBuildingProgramId)
                .totalRecords(parsedRows.size())
                .importedCount(imported.size())
                .skippedCount(skipped.size())
                .invalidCount(invalid.size())
                .imported(imported)
                .skipped(skipped)
                .invalid(invalid)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] generateImportTemplate() {
        log.debug("Generating DIA MSME Participant import template");
        return excelParser.buildTemplate();
    }

    private DiaMsmeParticipantImportRowResult processImportRow(
            ParsedRow parsedRow, DiaCapacityBuildingProgram event, Set<String> seenKeys) {
        DiaMsmeParticipantImportRowResult result =
                DiaMsmeParticipantImportRowResult.builder()
                        .rowNumber(parsedRow.rowNumber())
                        .build();

        ParticipantImportRow row = parsedRow.row();
        if (row != null) {
            result.setMsmeName(row.msmeName());
        }
        if (parsedRow.hasError()) {
            return markInvalid(result, parsedRow.error());
        }

        List<String> keys = row.identifierKeys();
        if (keys.stream().anyMatch(seenKeys::contains)) {
            return markSkipped(result, "Duplicate participant record within the Excel file");
        }
        seenKeys.addAll(keys);

        Optional<DiaMsmeParticipant> existing = findExistingParticipant(row);
        if (existing.isPresent()) {
            DiaMsmeParticipant participant = existing.get();
            result.setParticipantId(participant.getId());
            if (repository.existsAssociation(participant.getId(), event.getId())) {
                return markSkipped(result, "Participant already associated with this program");
            }
            participant.getEvents().add(event);
            // Attending another program makes this a repeated (not first-time) participant.
            participant.setFirstTimeParticipant(false);
            repository.save(participant);
            result.setStatus(Status.IMPORTED);
            result.setMessage("Existing participant associated with this program");
            return result;
        }

        CreateDiaMsmeParticipantRequest request =
                CreateDiaMsmeParticipantRequest.builder()
                        .eventId(event.getId())
                        .msmeName(row.msmeName())
                        .firstTimeParticipant(true)
                        .designation(row.designation())
                        .gender(row.gender())
                        .fatherHusbandName(row.fatherHusbandName())
                        .dateOfBirth(row.dateOfBirth())
                        .email(row.email())
                        .contactNo(row.contactNo())
                        .panNo(row.panNo())
                        .udyamAadharNo(row.udyamAadharNo())
                        .build();
        DiaMsmeParticipant participant = mapper.toEntity(request, event);
        participant.getEvents().add(event);
        DiaMsmeParticipant saved = repository.save(participant);
        result.setParticipantId(saved.getId());
        result.setStatus(Status.IMPORTED);
        result.setMessage("New participant created and associated with this program");
        return result;
    }

    /**
     * Finds an existing participant using, in priority order, PAN No, Udyam Aadhar No, Email and
     * Contact No.
     */
    private Optional<DiaMsmeParticipant> findExistingParticipant(ParticipantImportRow row) {
        if (row.panNo() != null) {
            Optional<DiaMsmeParticipant> match =
                    repository.findFirstByPanNoIgnoreCaseAndIsActiveTrueOrderByIdAsc(row.panNo());
            if (match.isPresent()) return match;
        }
        if (row.udyamAadharNo() != null) {
            Optional<DiaMsmeParticipant> match =
                    repository.findFirstByUdyamAadharNoIgnoreCaseAndIsActiveTrueOrderByIdAsc(
                            row.udyamAadharNo());
            if (match.isPresent()) return match;
        }
        if (row.email() != null) {
            Optional<DiaMsmeParticipant> match =
                    repository.findFirstByEmailIgnoreCaseAndIsActiveTrueOrderByIdAsc(row.email());
            if (match.isPresent()) return match;
        }
        if (row.contactNo() != null) {
            return repository.findFirstByContactNoAndIsActiveTrueOrderByIdAsc(row.contactNo());
        }
        return Optional.empty();
    }

    private DiaMsmeParticipantImportRowResult markSkipped(
            DiaMsmeParticipantImportRowResult result, String message) {
        result.setStatus(Status.SKIPPED);
        result.setMessage(message);
        return result;
    }

    private DiaMsmeParticipantImportRowResult markInvalid(
            DiaMsmeParticipantImportRowResult result, String message) {
        result.setStatus(Status.INVALID);
        result.setMessage(message);
        return result;
    }
}
