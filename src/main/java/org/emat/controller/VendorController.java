package org.emat.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.emat.dto.ApiResponse;
import org.emat.dto.IdRequest;
import org.emat.dto.VendorDropdownDTO;
import org.emat.dto.VendorRequestDTO;
import org.emat.dto.VendorResponseDTO;
import org.emat.service.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendor")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @PostMapping
    public ResponseEntity<ApiResponse<VendorResponseDTO>> createVendor(
            @RequestBody VendorRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "Vendor created successfully",
                                vendorService.createVendor(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VendorResponseDTO>> updateVendor(
            @PathVariable Long id, @RequestBody VendorRequestDTO request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Vendor updated successfully", vendorService.updateVendor(id, request)));
    }

    @PostMapping("/get")
    public ResponseEntity<ApiResponse<VendorResponseDTO>> getVendorById(
            @Valid @RequestBody IdRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Vendor fetched successfully",
                        vendorService.getVendorById(request.getId())));
    }

    @PostMapping("/user")
    public ResponseEntity<ApiResponse<VendorResponseDTO>> getVendorByUserId(
            @Valid @RequestBody IdRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Vendor fetched successfully",
                        vendorService.getVendorByUserId(request.getId())));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VendorResponseDTO>>> getAllVendors() {

        return ResponseEntity.ok(
                ApiResponse.success("Vendors fetched successfully", vendorService.getAllVendors()));
    }

    @GetMapping("/dropdown")
    public ResponseEntity<ApiResponse<List<VendorDropdownDTO>>> getVendorDropdown() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Vendor dropdown fetched successfully", vendorService.getVendorDropdown()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteVendor(@PathVariable Long id) {

        vendorService.deleteVendor(id);

        return ResponseEntity.ok(ApiResponse.success("Vendor deleted successfully", null));
    }
}
