package org.emat.service;

import lombok.RequiredArgsConstructor;
import org.emat.dto.VendorDropdownDTO;
import org.emat.dto.VendorRequestDTO;
import org.emat.dto.VendorResponseDTO;
import org.emat.entity.Vendor;
import org.emat.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;

    /**
     * Create Vendor
     */
    public VendorResponseDTO createVendor(VendorRequestDTO request) {

        Vendor vendor = new Vendor();

        mapRequestToEntity(request, vendor);

        vendor.setCreatedDate(LocalDateTime.now());

        Vendor savedVendor = vendorRepository.save(vendor);

        return mapToResponse(savedVendor);
    }

    /**
     * Update Vendor
     */
    public VendorResponseDTO updateVendor(UUID uuid, VendorRequestDTO request) {

        Vendor vendor = vendorRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        mapRequestToEntity(request, vendor);

        vendor.setUpdatedDate(LocalDateTime.now());

        Vendor updatedVendor = vendorRepository.save(vendor);

        return mapToResponse(updatedVendor);
    }

    /**
     * Get Vendor By Id
     */
    public VendorResponseDTO getVendorById(UUID uuid) {

        Vendor vendor = vendorRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        return mapToResponse(vendor);
    }

    /**
     * Get All Vendors
     */
    public List<VendorResponseDTO> getAllVendors() {

        return vendorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Delete Vendor
     */
    public void deleteVendor(UUID uuid) {

        Vendor vendor = vendorRepository.findById(uuid)
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
                .map(v -> new VendorDropdownDTO(
                        v.getUuid(),
                        v.getVendorId(),
                        v.getVendorName()))
                .toList();
    }

    // ===========================
    // Private Mapper Methods
    // ===========================

    private void mapRequestToEntity(VendorRequestDTO request, Vendor vendor) {

        vendor.setVendorId(request.getVendorId());
        vendor.setVendorName(request.getVendorName());
        vendor.setCompanyName(request.getCompanyName());
        vendor.setContactPerson(request.getContactPerson());
        vendor.setEmail(request.getEmail());
        vendor.setMobileNo(request.getMobileNo());
        vendor.setGstNo(request.getGstNo());
        vendor.setPanNo(request.getPanNo());
        vendor.setAddress(request.getAddress());
        vendor.setDistrict(request.getDistrict());
        vendor.setState(request.getState());
        vendor.setPinCode(request.getPinCode());
        vendor.setBankName(request.getBankName());
        vendor.setAccountNumber(request.getAccountNumber());
        vendor.setIfscCode(request.getIfscCode());
        vendor.setBranchName(request.getBranchName());
        vendor.setActive(request.getActive());
    }

    private VendorResponseDTO mapToResponse(Vendor vendor) {

        VendorResponseDTO response = new VendorResponseDTO();

        response.setUuid(vendor.getUuid());
        response.setVendorId(vendor.getVendorId());
        response.setVendorName(vendor.getVendorName());
        response.setCompanyName(vendor.getCompanyName());
        response.setContactPerson(vendor.getContactPerson());
        response.setEmail(vendor.getEmail());
        response.setMobileNo(vendor.getMobileNo());
        response.setGstNo(vendor.getGstNo());
        response.setPanNo(vendor.getPanNo());
        response.setAddress(vendor.getAddress());
        response.setDistrict(vendor.getDistrict());
        response.setState(vendor.getState());
        response.setPinCode(vendor.getPinCode());
        response.setBankName(vendor.getBankName());
        response.setAccountNumber(vendor.getAccountNumber());
        response.setIfscCode(vendor.getIfscCode());
        response.setBranchName(vendor.getBranchName());
        response.setActive(vendor.getActive());
        response.setCreatedBy(vendor.getCreatedBy());
        response.setCreatedDate(vendor.getCreatedDate());
        response.setUpdatedBy(vendor.getUpdatedBy());
        response.setUpdatedDate(vendor.getUpdatedDate());

        return response;
    }
}