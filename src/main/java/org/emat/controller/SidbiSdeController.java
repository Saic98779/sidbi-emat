package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.ApiResponse;
import org.emat.dto.SidbiSdeDropdownResponse;
import org.emat.service.SidbiSdeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sidbi-sde")
@RequiredArgsConstructor
public class SidbiSdeController {

    private final SidbiSdeService sidbiSdeService;

    @GetMapping("/dropdown")
    public ResponseEntity<ApiResponse<List<SidbiSdeDropdownResponse>>> getSdeDropdown(@RequestParam("branchId") Long branchId) {
        return ResponseEntity.ok(ApiResponse.success("SDE dropdown fetched successfully", sidbiSdeService.getDropdownByBranch(branchId)));
    }
}
