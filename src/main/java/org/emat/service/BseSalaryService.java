package org.emat.service;

import java.util.List;
import org.emat.dto.BseSalaryRequest;
import org.emat.dto.BseSalaryResponse;
import org.emat.dto.BseSalaryUpdateRequest;

public interface BseSalaryService {

    BseSalaryResponse create(BseSalaryRequest request);

    BseSalaryResponse getById(Long id);

    List<BseSalaryResponse> getAll();

    BseSalaryResponse update(Long id, BseSalaryUpdateRequest request);

    void delete(Long id);

    List<String> getApprovedIndustryAssociationNames();
}
