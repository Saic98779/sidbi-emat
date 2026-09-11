package org.emat.service;

import java.util.List;
import org.emat.dto.AppraisalDropdownDto;
import org.emat.dto.SustainabilityMatrixRequest;
import org.emat.dto.SustainabilityMatrixResponse;
import org.emat.entity.SustainabilityMatrix;

public interface SustainabilityMatrixService {

    SustainabilityMatrixResponse create(SustainabilityMatrixRequest request);

    SustainabilityMatrixResponse update(Long id, SustainabilityMatrixRequest request);

    SustainabilityMatrixResponse getById(Long id);

    List<SustainabilityMatrixResponse> getByAppraisalId(Long appraisalId);

    List<SustainabilityMatrixResponse> getAll();

    void delete(Long id);

    SustainabilityMatrixResponse toResponse(SustainabilityMatrix entity);

    List<AppraisalDropdownDto> getAppraisalDropdown();
}
