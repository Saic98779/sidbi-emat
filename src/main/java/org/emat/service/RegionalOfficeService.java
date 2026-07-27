package org.emat.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CreateRegionalOfficeRequest;
import org.emat.dto.RegionalOfficeResponse;
import org.emat.dto.UpdateRegionalOfficeRequest;
import org.emat.entity.RegionalOffice;
import org.emat.repository.RegionalOfficeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionalOfficeService {

    private final RegionalOfficeRepository repository;

    /**
     * Create Regional Office.
     */
    @Transactional
    public RegionalOfficeResponse createRegionalOffice(CreateRegionalOfficeRequest request) {

        if (repository.existsByRoId(request.getRoId())) {
            throw new IllegalArgumentException("Regional Office already exists with RO ID : " + request.getRoId());
        }

        RegionalOffice office = RegionalOffice.builder()
                .roId(request.getRoId())
                .roName(request.getRoName())
                .city(request.getCity())
                .district(request.getDistrict())
                .state(request.getState())
                .address(request.getAddress())
                .contactNo(request.getContactNo())
                .build();

        repository.save(office);

        log.info("Regional Office created successfully : {}", office.getUuid());

        return convertToResponse(office);
    }

    /**
     * Get Regional Office by UUID.
     */
    @Transactional
    public RegionalOfficeResponse getRegionalOffice(String uuid) {

        RegionalOffice office = repository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() ->
                        new EntityNotFoundException("Regional Office not found : " + uuid));

        return convertToResponse(office);
    }

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
     * Search Regional Offices.
     */
    @Transactional
    public List<RegionalOfficeResponse> search(String state,
                                               String district) {

        return repository
                .findAllByIsActiveTrueAndStateAndDistrict(state, district)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update Regional Office.
     */
    @Transactional
    public RegionalOfficeResponse updateRegionalOffice(String uuid,
                                                       UpdateRegionalOfficeRequest request) {

        RegionalOffice office = repository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() ->
                        new EntityNotFoundException("Regional Office not found : " + uuid));

        office.setRoName(request.getRoName());
        office.setCity(request.getCity());
        office.setDistrict(request.getDistrict());
        office.setState(request.getState());
        office.setAddress(request.getAddress());
        office.setContactNo(request.getContactNo());

        repository.save(office);

        return convertToResponse(office);
    }

    /**
     * Soft delete.
     */
    @Transactional
    public void deleteRegionalOffice(String uuid) {

        RegionalOffice office = repository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() ->
                        new EntityNotFoundException("Regional Office not found : " + uuid));

        office.setIsActive(false);

        repository.save(office);
    }

    /**
     * Permanent delete.
     */
    @Transactional
    public void permanentlyDeleteRegionalOffice(String uuid) {

        RegionalOffice office = repository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() ->
                        new EntityNotFoundException("Regional Office not found : " + uuid));

        repository.delete(office);
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