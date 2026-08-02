package org.emat.service;

import org.emat.dto.CreateVendorDisbursementRequest;
import org.emat.dto.UpdateVendorDisbursementRequest;
import org.emat.dto.VendorDisbursementResponse;

import java.util.List;

public interface VendorDisbursementService {
    VendorDisbursementResponse create(CreateVendorDisbursementRequest request);
    VendorDisbursementResponse getById(Long id);
    List<VendorDisbursementResponse> getAll();
    VendorDisbursementResponse update(Long id, UpdateVendorDisbursementRequest request);
    void delete(Long id);
}
