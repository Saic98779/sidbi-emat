package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.ApiResponse;
import org.emat.dto.BranchDropdownResponse;
import org.emat.service.BranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/dropdown")
    public ResponseEntity<ApiResponse<List<BranchDropdownResponse>>> getBranchDropdown(
            @RequestParam String state) {

        return ResponseEntity.ok(ApiResponse.success("Branch dropdown fetched successfully", branchService.getBranchDropdownByState(state)));
    }
}
