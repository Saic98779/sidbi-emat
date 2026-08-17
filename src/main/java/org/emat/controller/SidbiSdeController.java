package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.SidbiSdeDropdownResponse;
import org.emat.service.EndpointRolePolicyService;
import org.emat.service.SidbiSdeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sidbi-sde")
@RequiredArgsConstructor
public class SidbiSdeController {

    private final SidbiSdeService sidbiSdeService;
    private final EndpointRolePolicyService endpointRolePolicyService;


    @GetMapping("/dropdown")
    @PreAuthorize("hasAnyRole(@endpointRolePolicyService.resolveRoles('sidbiSde'))")
    public ResponseEntity<List<SidbiSdeDropdownResponse>> getSdeDropdown(
            @RequestParam UUID branchUuid) {

        return ResponseEntity.ok(
                sidbiSdeService.getDropdownByBranch(branchUuid)
        );
    }
}
