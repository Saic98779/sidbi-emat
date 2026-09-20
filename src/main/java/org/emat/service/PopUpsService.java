package org.emat.service;

import java.util.List;
import org.emat.dto.CreatePopUpsRequest;
import org.emat.dto.PopUpsResponse;
import org.emat.dto.UpdatePopUpsRequest;
import org.emat.dto.UpdatePopUpsStatusRequest;

public interface PopUpsService {

    PopUpsResponse create(CreatePopUpsRequest request);

    PopUpsResponse getById(Long id);

    List<PopUpsResponse> getAll();

    PopUpsResponse update(Long id, UpdatePopUpsRequest request);

    PopUpsResponse updateStatus(Long id, UpdatePopUpsStatusRequest request);

    void delete(Long id);
}