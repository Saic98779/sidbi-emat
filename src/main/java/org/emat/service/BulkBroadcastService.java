package org.emat.service;

import java.util.List;
import org.emat.dto.BulkBroadcastResponse;
import org.emat.dto.CreateBulkBroadcastRequest;
import org.emat.dto.UpdateBulkBroadcastRequest;
import org.emat.dto.UpdateBulkBroadcastStatusRequest;

public interface BulkBroadcastService {

    BulkBroadcastResponse create(CreateBulkBroadcastRequest request);

    BulkBroadcastResponse getById(Long id);

    List<BulkBroadcastResponse> getAll();

    BulkBroadcastResponse update(Long id, UpdateBulkBroadcastRequest request);

    BulkBroadcastResponse updateStatus(Long id, UpdateBulkBroadcastStatusRequest request);

    void delete(Long id);
}