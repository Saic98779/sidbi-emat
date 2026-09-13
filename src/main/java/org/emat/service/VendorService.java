package org.emat.service;

import java.util.List;
import org.emat.dto.VendorDropdownDTO;
import org.emat.dto.VendorRequestDTO;
import org.emat.dto.VendorResponseDTO;

public interface VendorService {

    /** Create Vendor */
    VendorResponseDTO createVendor(VendorRequestDTO request);

    /** Update Vendor */
    VendorResponseDTO updateVendor(Long id, VendorRequestDTO request);

    /** Get Vendor By Id */
    VendorResponseDTO getVendorById(Long id);

    VendorResponseDTO getVendorByUserId(Long userId);

    /** Get All Vendors */
    List<VendorResponseDTO> getAllVendors();

    /** Delete Vendor */
    void deleteVendor(Long id);

    /** Vendor Dropdown */
    List<VendorDropdownDTO> getVendorDropdown();
}
