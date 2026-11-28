package org.emat.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CapacityBuildingResponse;
import org.emat.repository.CapacityBuildingRepository;
import org.emat.service.CapacityBuildingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CapacityBuildingServiceImpl implements CapacityBuildingService {

    private static final Set<String> SOURCES = Set.of("EVENT", "WEBINAR");

    private final CapacityBuildingRepository repository;

    @Override
    public List<CapacityBuildingResponse> getAll(
            LocalDate fromDate, LocalDate toDate, String source) {
        if (fromDate != null && toDate != null && fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException("fromDate must be on or before toDate");
        }
        String normalizedSource = null;
        if (source != null && !source.isBlank()) {
            normalizedSource = source.trim().toUpperCase(Locale.ROOT);
            if (!SOURCES.contains(normalizedSource)) {
                throw new IllegalArgumentException("source must be EVENT or WEBINAR");
            }
        }
        log.debug(
                "Fetching capacity building rows: fromDate={}, toDate={}, source={}",
                fromDate,
                toDate,
                normalizedSource);
        return repository.findAll(fromDate, toDate, normalizedSource);
    }
}
