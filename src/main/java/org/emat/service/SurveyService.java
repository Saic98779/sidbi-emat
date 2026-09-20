package org.emat.service;

import java.util.List;
import org.emat.dto.CreateSurveyRequest;
import org.emat.dto.SurveyResponse;
import org.emat.dto.UpdateSurveyRequest;
import org.emat.dto.UpdateSurveyStatusRequest;

public interface SurveyService {

    SurveyResponse create(CreateSurveyRequest request);

    SurveyResponse getById(Long id);

    List<SurveyResponse> getAll();

    SurveyResponse update(Long id, UpdateSurveyRequest request);

    SurveyResponse updateStatus(Long id, UpdateSurveyStatusRequest request);

    void delete(Long id);
}
