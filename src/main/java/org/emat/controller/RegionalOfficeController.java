package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.ApiResponse;
import org.emat.dto.RegionalOfficeResponse;
import org.emat.service.RegionalOfficeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regional-office")
@RequiredArgsConstructor
public class RegionalOfficeController {

    private final RegionalOfficeService regionalOfficeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RegionalOfficeResponse>>> getRegionalOffice() {
        return ResponseEntity.ok(ApiResponse.success("Regional offices fetched successfully", regionalOfficeService.getAllRegionalOffices()));
    }
}
