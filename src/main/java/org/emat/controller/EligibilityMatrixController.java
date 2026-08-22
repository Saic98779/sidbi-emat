package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.EligibilityMatrixDto;
import org.emat.dto.RegistrationDropdownDto;
import org.emat.service.EligibilityMatrixService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/eligibility-matrix")
@RequiredArgsConstructor
public class EligibilityMatrixController {

    private final EligibilityMatrixService eligibilityMatrixService;

    @PostMapping
    public ResponseEntity<EligibilityMatrixDto> create(@RequestBody EligibilityMatrixDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eligibilityMatrixService.create(request));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<EligibilityMatrixDto> getById(@PathVariable UUID uuid) {

        return ResponseEntity.ok(
                eligibilityMatrixService.getById(uuid)
        );
    }

    @GetMapping
    public ResponseEntity<List<EligibilityMatrixDto>> getAll() {

        return ResponseEntity.ok(
                eligibilityMatrixService.getAll()
        );
    }

    @GetMapping("/registration/{registrationUuid}")
    public ResponseEntity<EligibilityMatrixDto>
    getByRegistrationUuid(
            @PathVariable String registrationUuid) {

        return ResponseEntity.ok(
                eligibilityMatrixService
                        .getByRegistrationUuid(registrationUuid)
        );
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<EligibilityMatrixDto> update(@PathVariable UUID uuid, @RequestBody EligibilityMatrixDto request) {

        return ResponseEntity.ok(
                eligibilityMatrixService.update(uuid, request)
        );
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {

        eligibilityMatrixService.delete(uuid);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/registration-dropdown")
    public ResponseEntity<List<RegistrationDropdownDto>> getRegistrationDropdown() {

        return ResponseEntity.ok(
                eligibilityMatrixService.getRegistrationDropdown()
        );
    }
}