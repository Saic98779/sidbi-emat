package org.emat.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateDia3CInfoSeriesRequest;
import org.emat.dto.Dia3CInfoSeriesResponse;
import org.emat.dto.UpdateDia3CInfoSeriesRequest;
import org.emat.entity.Dia3CInfoSeries;
import org.emat.mapper.Dia3CInfoSeriesMapper;
import org.emat.repository.Dia3CInfoSeriesRepository;
import org.emat.service.Dia3CInfoSeriesService;
import org.emat.validator.Dia3CInfoSeriesValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class Dia3CInfoSeriesServiceImpl implements Dia3CInfoSeriesService {

    private final Dia3CInfoSeriesRepository repository;
    private final Dia3CInfoSeriesMapper mapper;
    private final Dia3CInfoSeriesValidator validator;

    @Override
    public Dia3CInfoSeriesResponse create(CreateDia3CInfoSeriesRequest request) {
        log.info("Creating DIA 3C Info Series");
        Dia3CInfoSeries series = mapper.toEntity(request);
        return mapper.toResponse(repository.save(series));
    }

    @Override
    @Transactional(readOnly = true)
    public Dia3CInfoSeriesResponse getById(Long id) {
        log.debug("Fetching DIA 3C Info Series with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dia3CInfoSeriesResponse> getAll() {
        log.debug("Fetching all active DIA 3C Info Series");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public Dia3CInfoSeriesResponse update(Long id, UpdateDia3CInfoSeriesRequest request) {
        log.info("Updating DIA 3C Info Series with ID: {}", id);
        Dia3CInfoSeries series = validator.getByIdOrThrow(id);
        mapper.applyUpdateRequest(series, request);
        return mapper.toResponse(repository.save(series));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting DIA 3C Info Series with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }
}