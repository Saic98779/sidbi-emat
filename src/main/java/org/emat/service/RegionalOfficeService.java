package org.emat.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.RegionalOfficeResponse;
import org.emat.entity.RegionalOffice;
import org.emat.repository.RegionalOfficeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionalOfficeService {

    private final RegionalOfficeRepository repository;

    /**
     * Get all active Regional Offices.
     */
    @Transactional
    public List<RegionalOfficeResponse> getAllRegionalOffices() {

        return repository.findAllByIsActiveTrue()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Convert Entity to Response.
     */
    private RegionalOfficeResponse convertToResponse(RegionalOffice office) {

        return RegionalOfficeResponse.builder()
                .uuid(office.getUuid())
                .roId(office.getRoId())
                .roName(office.getRoName())
                .city(office.getCity())
                .district(office.getDistrict())
                .state(office.getState())
                .address(office.getAddress())
                .contactNo(office.getContactNo())
                .build();
    }
}