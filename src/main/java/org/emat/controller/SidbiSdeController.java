package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.SidbiSdeDropdownResponse;
import org.emat.service.SidbiSdeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
