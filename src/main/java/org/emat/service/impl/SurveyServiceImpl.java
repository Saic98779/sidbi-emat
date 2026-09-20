package org.emat.service.impl;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateSurveyRequest;
import org.emat.dto.SurveyResponse;
import org.emat.dto.UpdateSurveyRequest;
import org.emat.dto.UpdateSurveyStatusRequest;
import org.emat.entity.Survey;
import org.emat.enums.Status;
import org.emat.mapper.SurveyMapper;
import org.emat.repository.SurveyRepository;
import org.emat.service.SurveyService;
import org.emat.validator.SurveyValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository repository;
    private final SurveyMapper mapper;
    private final SurveyValidator validator;

    @Override
    public SurveyResponse create(CreateSurveyRequest request) {
        log.info("Creating Survey");
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Override
    @Transactional(readOnly = true)
    public SurveyResponse getById(Long id) {
        log.debug("Fetching Survey with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SurveyResponse> getAll() {
        log.debug("Fetching all active Surveys");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public SurveyResponse update(Long id, UpdateSurveyRequest request) {
        log.info("Updating Survey with ID: {}", id);
        Survey survey = validator.getByIdOrThrow(id);
        mapper.applyUpdateRequest(survey, request);
        if (request.getSurveyQuestionnaires() != null) {
            survey.getSurveyQuestionnaires().clear();
            survey.getSurveyQuestionnaires()
                    .addAll(
                            mapper.mapUpdateQuestionnaires(
                                    request.getSurveyQuestionnaires(), survey));
        }
        return mapper.toResponse(repository.save(survey));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting Survey with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }

    @Override
    public SurveyResponse updateStatus(Long id, UpdateSurveyStatusRequest request) {
        log.info("Updating status for Survey with ID: {}", id);
        if (request == null || request.getStatus() == null) {
            throw new IllegalArgumentException("Status must not be null");
        }
        Survey survey = validator.getByIdOrThrow(id);
        survey.setStatus(request.getStatus());
        if (request.getStatus() == Status.APPROVED) {
            survey.setApprovedDate(LocalDate.now());
        } else {
            survey.setApprovedDate(null);
        }
        return mapper.toResponse(repository.save(survey));
    }
}
