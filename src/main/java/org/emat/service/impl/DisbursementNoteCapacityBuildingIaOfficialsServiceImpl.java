package org.emat.service.impl;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateDisbursementNoteCapacityBuildingIaOfficialsRequest;
import org.emat.dto.DisbursementNoteCapacityBuildingIaOfficialsResponse;
import org.emat.dto.UpdateDisbursementNoteCapacityBuildingIaOfficialsRequest;
import org.emat.dto.UpdateDisbursementNoteCapacityBuildingIaOfficialsStatusRequest;
import org.emat.entity.DisbursementNoteCapacityBuildingIaOfficials;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.enums.Status;
import org.emat.mapper.DisbursementNoteCapacityBuildingIaOfficialsMapper;
import org.emat.repository.DisbursementNoteCapacityBuildingIaOfficialsRepository;
import org.emat.service.DisbursementNoteCapacityBuildingIaOfficialsService;
import org.emat.validator.DisbursementNoteCapacityBuildingIaOfficialsValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DisbursementNoteCapacityBuildingIaOfficialsServiceImpl
        implements DisbursementNoteCapacityBuildingIaOfficialsService {

    private final DisbursementNoteCapacityBuildingIaOfficialsRepository repository;
    private final DisbursementNoteCapacityBuildingIaOfficialsMapper mapper;
    private final DisbursementNoteCapacityBuildingIaOfficialsValidator validator;

    @Override
    public DisbursementNoteCapacityBuildingIaOfficialsResponse create(
            CreateDisbursementNoteCapacityBuildingIaOfficialsRequest request) {
        log.info(
                "Creating Disbursement Note Capacity Building IA Officials for registration ID: {}",
                request.getRegistrationId());
        IndustryAssociationRegistration registration =
                validator.getRegistrationOrThrow(request.getRegistrationId());
        DisbursementNoteCapacityBuildingIaOfficials note = mapper.toEntity(request, registration);
        return mapper.toResponse(repository.save(note));
    }

    @Override
    @Transactional(readOnly = true)
    public DisbursementNoteCapacityBuildingIaOfficialsResponse getById(Long id) {
        log.debug("Fetching Disbursement Note Capacity Building IA Officials with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public DisbursementNoteCapacityBuildingIaOfficialsResponse getByRegistrationId(
            Long registrationId) {
        log.debug(
                "Fetching Disbursement Note Capacity Building IA Officials for registration ID: {}",
                registrationId);
        return mapper.toResponse(validator.getByRegistrationIdOrThrow(registrationId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DisbursementNoteCapacityBuildingIaOfficialsResponse> getAll() {
        log.debug("Fetching all active Disbursement Note Capacity Building IA Officials");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public DisbursementNoteCapacityBuildingIaOfficialsResponse update(
            Long id, UpdateDisbursementNoteCapacityBuildingIaOfficialsRequest request) {
        log.info("Updating Disbursement Note Capacity Building IA Officials with ID: {}", id);
        DisbursementNoteCapacityBuildingIaOfficials note = validator.getByIdOrThrow(id);

        if (request.getRegistrationId() != null
                && !request.getRegistrationId().equals(note.getRegistration().getId())) {
            note.setRegistration(validator.getRegistrationOrThrow(request.getRegistrationId()));
        }

        mapper.applyUpdateRequest(note, request);
        return mapper.toResponse(repository.save(note));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting Disbursement Note Capacity Building IA Officials with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }

    @Override
    public DisbursementNoteCapacityBuildingIaOfficialsResponse updateStatus(
            Long id, UpdateDisbursementNoteCapacityBuildingIaOfficialsStatusRequest request) {
        log.info(
                "Updating status for Disbursement Note Capacity Building IA Officials with ID: {}",
                id);
        if (request == null || request.getStatus() == null) {
            throw new IllegalArgumentException("Status must not be null");
        }
        DisbursementNoteCapacityBuildingIaOfficials note = validator.getByIdOrThrow(id);
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