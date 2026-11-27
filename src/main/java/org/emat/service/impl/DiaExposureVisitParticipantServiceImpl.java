package org.emat.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateDiaExposureVisitParticipantRequest;
import org.emat.dto.DiaExposureVisitParticipantImportResult;
import org.emat.dto.DiaExposureVisitParticipantImportRowResult;
import org.emat.dto.DiaExposureVisitParticipantImportRowResult.Status;
import org.emat.dto.DiaExposureVisitParticipantResponse;
import org.emat.dto.ParticipantImportRow;
import org.emat.dto.UpdateDiaExposureVisitParticipantRequest;
import org.emat.entity.DiaExposureVisit;
import org.emat.entity.DiaExposureVisitParticipant;
import org.emat.mapper.DiaExposureVisitParticipantMapper;
import org.emat.repository.DiaExposureVisitParticipantRepository;
import org.emat.service.DiaExposureVisitParticipantService;
import org.emat.util.ParticipantExcelParser;
import org.emat.util.ParticipantExcelParser.ParsedRow;
import org.emat.validator.DiaExposureVisitParticipantValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DiaExposureVisitParticipantServiceImpl implements DiaExposureVisitParticipantService {

    private final DiaExposureVisitParticipantRepository repository;
    private final DiaExposureVisitParticipantMapper mapper;
    private final DiaExposureVisitParticipantValidator validator;
    private final ParticipantExcelParser excelParser;

    @Override
    public DiaExposureVisitParticipantResponse create(
            CreateDiaExposureVisitParticipantRequest request) {
        log.info(
                "Creating DIA Exposure Visit Participant for DIA Exposure Visit ID: {}",
                request.getExposureVisitId());
        DiaExposureVisit exposureVisit =
                validator.getExposureVisitOrThrow(request.getExposureVisitId());
        DiaExposureVisitParticipant participant = mapper.toEntity(request, exposureVisit);
        participant.getExposureVisits().add(exposureVisit);
        return mapper.toResponse(repository.save(participant));
    }

    @Override
    @Transactional(readOnly = true)
    public DiaExposureVisitParticipantResponse getById(Long id) {
        log.debug("Fetching DIA Exposure Visit Participant with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaExposureVisitParticipantResponse> getAll() {
        log.debug("Fetching all active DIA Exposure Visit Participants");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaExposureVisitParticipantResponse> getAllByExposureVisitId(Long exposureVisitId) {
        log.debug(
                "Fetching all active DIA Exposure Visit Participants for DIA Exposure Visit ID: {}",
                exposureVisitId);
        return repository.findAllActiveAssociatedWithExposureVisit(exposureVisitId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public DiaExposureVisitParticipantResponse update(
            Long id, UpdateDiaExposureVisitParticipantRequest request) {
        log.info("Updating DIA Exposure Visit Participant with ID: {}", id);
        DiaExposureVisitParticipant participant = validator.getByIdOrThrow(id);

        if (request.getExposureVisitId() != null
                && (participant.getExposureVisit() == null
                        || !request.getExposureVisitId()
                                .equals(participant.getExposureVisit().getId()))) {
            DiaExposureVisit newVisit =
                    validator.getExposureVisitOrThrow(request.getExposureVisitId());
            // Keep the Many-to-Many association in step with the primary exposure visit.
            if (participant.getExposureVisit() != null) {
                Long oldVisitId = participant.getExposureVisit().getId();
                participant.getExposureVisits().removeIf(v -> oldVisitId.equals(v.getId()));
            }
            participant.getExposureVisits().add(newVisit);
            participant.setExposureVisit(newVisit);
        }

        mapper.applyUpdateRequest(participant, request);
        return mapper.toResponse(repository.save(participant));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting DIA Exposure Visit Participant with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }

    @Override
    public DiaExposureVisitParticipantImportResult importFromExcel(
            Long diaExposureVisitId, MultipartFile file) {
        DiaExposureVisit exposureVisit = validator.getExposureVisitOrThrow(diaExposureVisitId);
        excelParser.validateFile(file);
        log.info(
                "Importing DIA Exposure Visit Participants for DIA Exposure Visit ID: {} from"
                        + " file: {}",
                diaExposureVisitId,
                file.getOriginalFilename());

        List<ParsedRow> parsedRows = excelParser.parse(file);

        List<DiaExposureVisitParticipantImportRowResult> imported = new ArrayList<>();
        List<DiaExposureVisitParticipantImportRowResult> skipped = new ArrayList<>();
        List<DiaExposureVisitParticipantImportRowResult> invalid = new ArrayList<>();
        // Identifier keys of participants already handled in this file, to skip in-file repeats.
        Set<String> seenKeys = new HashSet<>();

        for (ParsedRow parsedRow : parsedRows) {
            DiaExposureVisitParticipantImportRowResult result =
                    processImportRow(parsedRow, exposureVisit, seenKeys);
            switch (result.getStatus()) {
                case IMPORTED -> imported.add(result);
                case SKIPPED -> skipped.add(result);
                default -> invalid.add(result);
            }
        }

        log.info(
                "DIA Exposure Visit Participant import completed for visit ID {}: {} total, {}"
                        + " imported, {} skipped, {} invalid",
                diaExposureVisitId,
                parsedRows.size(),
                imported.size(),
                skipped.size(),
                invalid.size());

        return DiaExposureVisitParticipantImportResult.builder()
                .diaExposureVisitId(diaExposureVisitId)
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
        log.debug("Generating DIA Exposure Visit Participant import template");
        return excelParser.buildTemplate();
    }

    private DiaExposureVisitParticipantImportRowResult processImportRow(
            ParsedRow parsedRow, DiaExposureVisit exposureVisit, Set<String> seenKeys) {
        DiaExposureVisitParticipantImportRowResult result =
                DiaExposureVisitParticipantImportRowResult.builder()
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

        Optional<DiaExposureVisitParticipant> existing = findExistingParticipant(row);
        if (existing.isPresent()) {
            DiaExposureVisitParticipant participant = existing.get();
            result.setParticipantId(participant.getId());
            if (repository.existsAssociation(participant.getId(), exposureVisit.getId())) {
                return markSkipped(
                        result, "Participant already associated with this exposure visit");
            }
            participant.getExposureVisits().add(exposureVisit);
            repository.save(participant);
            result.setStatus(Status.IMPORTED);
            result.setMessage("Existing participant associated with this exposure visit");
            return result;
        }

        CreateDiaExposureVisitParticipantRequest request =
                CreateDiaExposureVisitParticipantRequest.builder()
                        .exposureVisitId(exposureVisit.getId())
                        .msmeName(row.msmeName())
                        .designation(row.designation())
                        .gender(row.gender())
                        .fatherHusbandName(row.fatherHusbandName())
                        .dateOfBirth(row.dateOfBirth())
                        .email(row.email())
                        .contactNo(row.contactNo())
                        .panNo(row.panNo())
                        .udyamAadharNo(row.udyamAadharNo())
                        .build();
        DiaExposureVisitParticipant participant = mapper.toEntity(request, exposureVisit);
        participant.getExposureVisits().add(exposureVisit);
        DiaExposureVisitParticipant saved = repository.save(participant);
        result.setParticipantId(saved.getId());
        result.setStatus(Status.IMPORTED);
        result.setMessage("New participant created and associated with this exposure visit");
        return result;
    }

    /**
     * Finds an existing participant using, in priority order, PAN No, Udyam Aadhar No, Email and
     * Contact No.
     */
    private Optional<DiaExposureVisitParticipant> findExistingParticipant(
            ParticipantImportRow row) {
        if (row.panNo() != null) {
            Optional<DiaExposureVisitParticipant> match =
                    repository.findFirstByPanNoIgnoreCaseAndIsActiveTrueOrderByIdAsc(row.panNo());
            if (match.isPresent()) return match;
        }
        if (row.udyamAadharNo() != null) {
            Optional<DiaExposureVisitParticipant> match =
                    repository.findFirstByUdyamAadharNoIgnoreCaseAndIsActiveTrueOrderByIdAsc(
                            row.udyamAadharNo());
            if (match.isPresent()) return match;
        }
        if (row.email() != null) {
            Optional<DiaExposureVisitParticipant> match =
                    repository.findFirstByEmailIgnoreCaseAndIsActiveTrueOrderByIdAsc(row.email());
            if (match.isPresent()) return match;
        }
        if (row.contactNo() != null) {
            return repository.findFirstByContactNoAndIsActiveTrueOrderByIdAsc(row.contactNo());
        }
        return Optional.empty();
    }

    private DiaExposureVisitParticipantImportRowResult markSkipped(
            DiaExposureVisitParticipantImportRowResult result, String message) {
        result.setStatus(Status.SKIPPED);
        result.setMessage(message);
        return result;
    }

    private DiaExposureVisitParticipantImportRowResult markInvalid(
            DiaExposureVisitParticipantImportRowResult result, String message) {
        result.setStatus(Status.INVALID);
        result.setMessage(message);
        return result;
    }
}
