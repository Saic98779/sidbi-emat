package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.DisbursementCapexRequest;
import org.emat.dto.DisbursementCapexResponse;
import org.emat.service.DisbursementCapexService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/disbursement-capex")
@RequiredArgsConstructor
public class DisbursementCapexController {

    private final DisbursementCapexService vendorExpenditureService;

    @PostMapping
    public ResponseEntity<DisbursementCapexResponse> create(
            @RequestBody DisbursementCapexRequest request) {

        DisbursementCapexResponse response =
                vendorExpenditureService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<DisbursementCapexResponse> getById(
            @PathVariable UUID uuid) {

        return ResponseEntity.ok(
                vendorExpenditureService.getById(uuid)
        );
    }

    @GetMapping("/registration/{registrationUuid}")
    public ResponseEntity<DisbursementCapexResponse> getByRegistrationUuid(
            @PathVariable UUID registrationUuid) {

        return ResponseEntity.ok(
                vendorExpenditureService
                        .getByRegistrationUuid(registrationUuid)
        );
    }

    @GetMapping
    public ResponseEntity<List<DisbursementCapexResponse>> getAll() {

        return ResponseEntity.ok(
                vendorExpenditureService.getAll()
        );
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<DisbursementCapexResponse> update(
            @PathVariable UUID uuid,
            @RequestBody DisbursementCapexRequest request) {

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