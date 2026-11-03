package org.emat.service;

import java.util.List;
import org.emat.dto.CreateDiaCreditApplicationRequest;
import org.emat.dto.DiaCreditApplicationResponse;
import org.emat.dto.UpdateDiaCreditApplicationRequest;

public interface DiaCreditApplicationService {

    DiaCreditApplicationResponse create(CreateDiaCreditApplicationRequest request);

    DiaCreditApplicationResponse getById(Long id);

    List<DiaCreditApplicationResponse> getAll();

    DiaCreditApplicationResponse update(Long id, UpdateDiaCreditApplicationRequest request);

    void delete(Long id);
}
