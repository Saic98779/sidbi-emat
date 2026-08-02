package org.emat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateVendorDisbursementRequest;
import org.emat.dto.UpdateVendorDisbursementRequest;
import org.emat.dto.VendorDisbursementResponse;
import org.emat.service.VendorDisbursementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor-disbursements")
@RequiredArgsConstructor
@Slf4j
public class VendorDisbursementController {

    private final VendorDisbursementService vendorDisbursementService;

    @PostMapping
    public ResponseEntity<VendorDisbursementResponse> create(@RequestBody CreateVendorDisbursementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vendorDisbursementService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorDisbursementResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vendorDisbursementService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<VendorDisbursementResponse>> getAll() {
        return ResponseEntity.ok(vendorDisbursementService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendorDisbursementResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateVendorDisbursementRequest request) {
        return ResponseEntity.ok(vendorDisbursementService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vendorDisbursementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
