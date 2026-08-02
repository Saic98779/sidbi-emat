package org.emat.controller;

import lombok.RequiredArgsConstructor;
import org.emat.dto.RegionalOfficeRequest;
import org.emat.dto.RegionalOfficeResponse;
import org.emat.dto.UpdateRegionalOfficeRequest;
import org.emat.service.RegionalOfficeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regional-office")
@RequiredArgsConstructor
public class RegionalOfficeController {

    private final RegionalOfficeService regionalOfficeService;

    @GetMapping
    public ResponseEntity<List<RegionalOfficeResponse>> getRegionalOffice() {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(regionalOfficeService.getAllRegionalOffices());}
}
