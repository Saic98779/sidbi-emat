package org.emat.service.impl;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateDisbursementNoteCapacityBuildingIaRequest;
import org.emat.dto.DisbursementNoteCapacityBuildingIaResponse;
import org.emat.dto.UpdateDisbursementNoteCapacityBuildingIaRequest;
import org.emat.dto.UpdateDisbursementNoteCapacityBuildingIaStatusRequest;
import org.emat.entity.DisbursementNoteCapacityBuildingIa;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.enums.Status;
import org.emat.mapper.DisbursementNoteCapacityBuildingIaMapper;
import org.emat.repository.DisbursementNoteCapacityBuildingIaRepository;
import org.emat.service.DisbursementNoteCapacityBuildingIaService;
import org.emat.validator.DisbursementNoteCapacityBuildingIaValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DisbursementNoteCapacityBuildingIaServiceImpl
        implements DisbursementNoteCapacityBuildingIaService {

    private final DisbursementNoteCapacityBuildingIaRepository repository;
    private final DisbursementNoteCapacityBuildingIaMapper mapper;
    private final DisbursementNoteCapacityBuildingIaValidator validator;

    @Override
    public DisbursementNoteCapacityBuildingIaResponse create(
            CreateDisbursementNoteCapacityBuildingIaRequest request) {
        log.info(
                "Creating Disbursement Note Capacity Building IA for registration ID: {}",
                request.getRegistrationId());
        IndustryAssociationRegistration registration =
                validator.getRegistrationOrThrow(request.getRegistrationId());
        DisbursementNoteCapacityBuildingIa note = mapper.toEntity(request, registration);
        return mapper.toResponse(repository.save(note));
    }

    @Override
    @Transactional(readOnly = true)
    public DisbursementNoteCapacityBuildingIaResponse getById(Long id) {
        log.debug("Fetching Disbursement Note Capacity Building IA with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public DisbursementNoteCapacityBuildingIaResponse getByRegistrationId(Long registrationId) {
        log.debug(
                "Fetching Disbursement Note Capacity Building IA for registration ID: {}",
                registrationId);
        return mapper.toResponse(validator.getByRegistrationIdOrThrow(registrationId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DisbursementNoteCapacityBuildingIaResponse> getAll() {
        log.debug("Fetching all active Disbursement Note Capacity Building IA");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public DisbursementNoteCapacityBuildingIaResponse update(
            Long id, UpdateDisbursementNoteCapacityBuildingIaRequest request) {
        log.info("Updating Disbursement Note Capacity Building IA with ID: {}", id);
        DisbursementNoteCapacityBuildingIa note = validator.getByIdOrThrow(id);

        if (request.getRegistrationId() != null
                && !request.getRegistrationId().equals(note.getRegistration().getId())) {
            note.setRegistration(validator.getRegistrationOrThrow(request.getRegistrationId()));
        }

        mapper.applyUpdateRequest(note, request);
        return mapper.toResponse(repository.save(note));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting Disbursement Note Capacity Building IA with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }

    @Override
    public DisbursementNoteCapacityBuildingIaResponse updateStatus(
            Long id, UpdateDisbursementNoteCapacityBuildingIaStatusRequest request) {
        log.info("Updating status for Disbursement Note Capacity Building IA with ID: {}", id);
        if (request == null || request.getStatus() == null) {
            throw new IllegalArgumentException("Status must not be null");
        }
        DisbursementNoteCapacityBuildingIa note = validator.getByIdOrThrow(id);
        note.setStatus(request.getStatus());
        note.setRemark(request.getRemark());
        if (request.getStatus() == Status.APPROVED) {
            note.setApprovedDate(LocalDate.now());
        } else {
            note.setApprovedDate(null);
        }
        return mapper.toResponse(repository.save(note));
    }
}