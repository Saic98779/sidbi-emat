package org.emat.service;

import org.emat.dto.CreateVendorDisbursementSalaryRequest;
import org.emat.dto.UpdateVendorDisbursementSalaryRequest;
import org.emat.dto.VendorDisbursementSalaryResponse;

import java.util.List;

public interface VendorDisbursementService {
    VendorDisbursementSalaryResponse create(CreateVendorDisbursementSalaryRequest request);
    VendorDisbursementSalaryResponse getById(Long id);
    List<VendorDisbursementSalaryResponse> getAll();
    VendorDisbursementSalaryResponse update(Long id, UpdateVendorDisbursementSalaryRequest request);
    void delete(Long id);
    List<String> getApprovedIndustryAssociationNames();
}
