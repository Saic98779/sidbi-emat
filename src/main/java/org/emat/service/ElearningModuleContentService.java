package org.emat.service;

import java.util.List;
import org.emat.dto.CreateElearningModuleContentRequest;
import org.emat.dto.ElearningModuleContentResponse;
import org.emat.dto.UpdateElearningModuleContentRequest;
import org.emat.dto.UpdateElearningModuleContentStatusRequest;

public interface ElearningModuleContentService {

    ElearningModuleContentResponse create(CreateElearningModuleContentRequest request);

    ElearningModuleContentResponse getById(Long id);

    List<ElearningModuleContentResponse> getAll();

    ElearningModuleContentResponse update(Long id, UpdateElearningModuleContentRequest request);

    ElearningModuleContentResponse updateStatus(
            Long id, UpdateElearningModuleContentStatusRequest request);

    void delete(Long id);
}