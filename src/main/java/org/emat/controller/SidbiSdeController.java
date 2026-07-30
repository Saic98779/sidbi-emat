package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.SidbiSdeDropdownResponse;
import org.emat.dto.SidbiSdeRequest;
import org.emat.dto.SidbiSdeResponse;
import org.emat.service.SidbiSdeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sidbi-sde")
@RequiredArgsConstructor
public class SidbiSdeController {

    private final SidbiSdeService sidbiSdeService;

    @GetMapping("/dropdown")
    public ResponseEntity<List<SidbiSdeDropdownResponse>> getSdeDropdown(
            @RequestParam UUID branchUuid) {

        return ResponseEntity.ok(
                sidbiSdeService.getDropdownByBranch(branchUuid)
        );
    }
    @PostMapping
    public ResponseEntity<SidbiSdeResponse> createSidbiSde(
            @RequestBody SidbiSdeRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sidbiSdeService.createSidbiSde(request));
    }
}
