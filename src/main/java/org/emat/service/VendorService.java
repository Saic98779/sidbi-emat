package org.emat.service;

import lombok.RequiredArgsConstructor;
import org.emat.dto.VendorDropdownDTO;
import org.emat.dto.VendorRequestDTO;
import org.emat.dto.VendorResponseDTO;
import org.emat.entity.Vendor;
import org.emat.mapper.VendorMapper;
import org.emat.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    /**
     * Create Vendor
     */
    public VendorResponseDTO createVendor(VendorRequestDTO request) {

        Vendor vendor = new Vendor();

        vendorMapper.updateEntityFromRequest(request, vendor);

        vendor.setCreatedDate(LocalDateTime.now());

        Vendor savedVendor = vendorRepository.save(vendor);

        return vendorMapper.toResponse(savedVendor);
    }

    /**
     * Update Vendor
     */
    public VendorResponseDTO updateVendor(Long id, VendorRequestDTO request) {

        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        vendorMapper.updateEntityFromRequest(request, vendor);

        vendor.setUpdatedDate(LocalDateTime.now());

        Vendor updatedVendor = vendorRepository.save(vendor);

        return vendorMapper.toResponse(updatedVendor);
    }

    /**
     * Get Vendor By Id
     */
    public VendorResponseDTO getVendorById(Long id) {

        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        return vendorMapper.toResponse(vendor);
    }

    public VendorResponseDTO getVendorByUserId(Long userId) {

        Vendor vendor = vendorRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found for user: " + userId));

        return vendorMapper.toResponse(vendor);
    }
    /**
     * Get All Vendors
     */
    public List<VendorResponseDTO> getAllVendors() {

        return vendorRepository.findAll()
                .stream()
                .map(vendorMapper::toResponse)
                .toList();
    }

    /**
     * Delete Vendor
     */
    public void deleteVendor(Long id) {

        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        vendorRepository.delete(vendor);
    }

    /**
     * Vendor Dropdown
     */
    public List<VendorDropdownDTO> getVendorDropdown() {

        return vendorRepository.findAll()
                .stream()
                .filter(v -> Boolean.TRUE.equals(v.getActive()))
                .map(vendorMapper::toDropdown)
                .toList();
    }
}