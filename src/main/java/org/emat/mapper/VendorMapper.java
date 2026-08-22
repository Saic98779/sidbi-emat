package org.emat.mapper;

import org.emat.dto.VendorDropdownDTO;
import org.emat.dto.VendorRequestDTO;
import org.emat.dto.VendorResponseDTO;
import org.emat.entity.Vendor;
import org.springframework.stereotype.Component;

@Component
public class VendorMapper {

    public void updateEntityFromRequest(VendorRequestDTO request, Vendor vendor) {
        vendor.setVendorId(request.getVendorId());
        vendor.setVendorName(request.getVendorName());
        vendor.setCompanyName(request.getCompanyName());
        vendor.setSpocName(request.getSpocName());
        vendor.setSpocMobileNo(request.getSpocMobileNo());
        vendor.setEmail(request.getEmail());
        vendor.setContactNo(request.getMobileNo());
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

    public VendorResponseDTO toResponse(Vendor vendor) {
        VendorResponseDTO response = new VendorResponseDTO();

        response.setId(vendor.getId());
        response.setVendorId(vendor.getVendorId());
        response.setVendorName(vendor.getVendorName());
        response.setCompanyName(vendor.getCompanyName());
        response.setSpocName(vendor.getSpocName());
        response.setEmail(vendor.getEmail());
        response.setMobileNo(vendor.getContactNo());
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

    public VendorDropdownDTO toDropdown(Vendor vendor) {
        return new VendorDropdownDTO(vendor.getId(), vendor.getVendorId(), vendor.getVendorName());
    }
}

