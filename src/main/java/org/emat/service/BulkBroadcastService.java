package org.emat.service;

import java.util.List;
import org.emat.dto.BulkBroadcastResponse;
import org.emat.dto.CreateBulkBroadcastRequest;
import org.emat.dto.UpdateBulkBroadcastRequest;

public interface BulkBroadcastService {

    BulkBroadcastResponse create(CreateBulkBroadcastRequest request);

    BulkBroadcastResponse getById(Long id);

    List<BulkBroadcastResponse> getAll();

    BulkBroadcastResponse update(Long id, UpdateBulkBroadcastRequest request);

    void delete(Long id);
}