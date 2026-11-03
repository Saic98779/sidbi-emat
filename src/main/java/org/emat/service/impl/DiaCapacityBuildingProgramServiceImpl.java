package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateDiaCapacityBuildingProgramRequest;
import org.emat.dto.DiaCapacityBuildingProgramResponse;
import org.emat.dto.UpdateDiaCapacityBuildingProgramRequest;
import org.emat.entity.DiaCapacityBuildingProgram;
import org.emat.mapper.DiaCapacityBuildingProgramMapper;
import org.emat.repository.DiaCapacityBuildingProgramRepository;
import org.emat.service.DiaCapacityBuildingProgramService;
import org.emat.validator.DiaCapacityBuildingProgramValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DiaCapacityBuildingProgramServiceImpl implements DiaCapacityBuildingProgramService {

    private final DiaCapacityBuildingProgramRepository repository;
    private final DiaCapacityBuildingProgramMapper mapper;
    private final DiaCapacityBuildingProgramValidator validator;

    @Override
    public DiaCapacityBuildingProgramResponse create(
            CreateDiaCapacityBuildingProgramRequest request) {
        log.info("Creating DIA Capacity Building Program");
        DiaCapacityBuildingProgram program = mapper.toEntity(request);
        return mapper.toResponse(repository.save(program));
    }

    @Override
    @Transactional(readOnly = true)
    public DiaCapacityBuildingProgramResponse getById(Long id) {
        log.debug("Fetching DIA Capacity Building Program with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaCapacityBuildingProgramResponse> getAll() {
        log.debug("Fetching all active DIA Capacity Building Programs");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public DiaCapacityBuildingProgramResponse update(
            Long id, UpdateDiaCapacityBuildingProgramRequest request) {
        log.info("Updating DIA Capacity Building Program with ID: {}", id);
        DiaCapacityBuildingProgram program = validator.getByIdOrThrow(id);

        mapper.applyUpdateRequest(program, request);
        return mapper.toResponse(repository.save(program));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting DIA Capacity Building Program with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }
}
