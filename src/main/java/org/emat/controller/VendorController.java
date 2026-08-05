package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.VendorDropdownDTO;
import org.emat.dto.VendorRequestDTO;
import org.emat.dto.VendorResponseDTO;
import org.emat.service.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vendor")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    /**
     * Create Vendor
     */
    @PostMapping
    public ResponseEntity<VendorResponseDTO> createVendor(
            @RequestBody VendorRequestDTO request) {

        return new ResponseEntity<>(
                vendorService.createVendor(request),
                HttpStatus.CREATED);
    }

    /**
     * Update Vendor
     */
    @PutMapping("/{uuid}")
    public ResponseEntity<VendorResponseDTO> updateVendor(
            @PathVariable UUID uuid,
            @RequestBody VendorRequestDTO request) {

        return ResponseEntity.ok(
                vendorService.updateVendor(uuid, request));
    }

    /**
     * Get Vendor By UUID
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<VendorResponseDTO> getVendorById(
            @PathVariable UUID uuid) {

        return ResponseEntity.ok(
                vendorService.getVendorById(uuid));
    }

    /**
     * Get All Vendors
     */
    @GetMapping
    public ResponseEntity<List<VendorResponseDTO>> getAllVendors() {

        return ResponseEntity.ok(
                vendorService.getAllVendors());
    }

    /**
     * Vendor Dropdown
     */
    @GetMapping("/dropdown")
    public ResponseEntity<List<VendorDropdownDTO>> getVendorDropdown() {

        return ResponseEntity.ok(
                vendorService.getVendorDropdown());
    }

    /**
     * Delete Vendor
     */
    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteVendor(
            @PathVariable UUID uuid) {

        vendorService.deleteVendor(uuid);

        return ResponseEntity.ok("Vendor deleted successfully.");
    }
}