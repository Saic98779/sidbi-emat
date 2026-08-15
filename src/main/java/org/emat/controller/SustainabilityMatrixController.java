package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.SustainabilityMatrixRequest;
import org.emat.dto.SustainabilityMatrixResponse;
import org.emat.entity.SustainabilityMatrix;
import org.emat.service.SustainabilityMatrixService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sustainability-matrix")
@RequiredArgsConstructor
public class SustainabilityMatrixController {

    private final SustainabilityMatrixService service;

    @PostMapping
    public ResponseEntity<SustainabilityMatrixResponse> create(
            @RequestBody SustainabilityMatrixRequest request
    ) {

        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<SustainabilityMatrixResponse> update(
            @PathVariable UUID uuid,
            @RequestBody SustainabilityMatrixRequest request
    ) {

        return ResponseEntity.ok(service.update(uuid, request));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<SustainabilityMatrixResponse> getById(
            @PathVariable UUID uuid
    ) {

        return ResponseEntity.ok(service.getById(uuid));
    }

    @GetMapping("/appraisal/{appraisalUuid}")
    public ResponseEntity<List<SustainabilityMatrixResponse>> getByAppraisalUuid(
            @PathVariable UUID appraisalUuid
    ) {

        return ResponseEntity.ok(
                service.getByAppraisalUuid(appraisalUuid)
        );
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID uuid
    ) {

        service.delete(uuid);

        return ResponseEntity.noContent().build();
    }
}