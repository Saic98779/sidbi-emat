package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.BranchDropdownResponse;
import org.emat.dto.BranchRequest;
import org.emat.dto.BranchResponse;
import org.emat.service.BranchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/dropdown")
    public ResponseEntity<List<BranchDropdownResponse>> getBranchDropdown(
            @RequestParam String state) {

        return ResponseEntity.ok(branchService.getBranchDropdownByState(state));
    }
    /**
     * Create Branch
     */
    @PostMapping
    public ResponseEntity<BranchResponse> createBranch(
            @RequestBody BranchRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(branchService.createBranch(request));
    }
}
