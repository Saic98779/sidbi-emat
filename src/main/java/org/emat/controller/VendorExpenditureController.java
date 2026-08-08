package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.CreateVendorExpenditureRequest;
import org.emat.dto.VendorExpenditureResponse;
import org.emat.service.VendorExpenditureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vendor-expenditure")
@RequiredArgsConstructor
public class VendorExpenditureController {

    private final VendorExpenditureService vendorExpenditureService;

    @PostMapping
    public ResponseEntity<VendorExpenditureResponse> create(
            @RequestBody CreateVendorExpenditureRequest request) {

        VendorExpenditureResponse response =
                vendorExpenditureService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<VendorExpenditureResponse> getById(
            @PathVariable UUID uuid) {

        return ResponseEntity.ok(
                vendorExpenditureService.getById(uuid)
        );
    }

    @GetMapping("/registration/{registrationUuid}")
    public ResponseEntity<VendorExpenditureResponse> getByRegistrationUuid(
            @PathVariable UUID registrationUuid) {

        return ResponseEntity.ok(
                vendorExpenditureService
                        .getByRegistrationUuid(registrationUuid)
        );
    }

    @GetMapping
    public ResponseEntity<List<VendorExpenditureResponse>> getAll() {

        return ResponseEntity.ok(
                vendorExpenditureService.getAll()
        );
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<VendorExpenditureResponse> update(
            @PathVariable UUID uuid,
            @RequestBody CreateVendorExpenditureRequest request) {

        return ResponseEntity.ok(
                vendorExpenditureService.update(uuid, request)
        );
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID uuid) {

        vendorExpenditureService.delete(uuid);

        return ResponseEntity.noContent().build();
    }
}