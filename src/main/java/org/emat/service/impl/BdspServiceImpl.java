package org.emat.service.impl;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.emat.dto.BdspImportResult;
import org.emat.dto.BdspImportRowResult;
import org.emat.dto.BdspResponse;
import org.emat.dto.CreateBdspRequest;
import org.emat.dto.UpdateBdspRequest;
import org.emat.entity.Bdsp;
import org.emat.mapper.BdspMapper;
import org.emat.repository.BdspRepository;
import org.emat.service.BdspService;
import org.emat.validator.BdspValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BdspServiceImpl implements BdspService {

    private static final String[] COLUMN_HEADERS = {
        "Name of BDSP",
        "Rationale for onboarding BDSP",
        "Theme",
        "Area of Service/Expertise",
        "State",
        "District",
        "Contact",
        "Email",
        "KYC",
        "Requested by",
        "Request date"
    };

    private static final DateTimeFormatter[] DATE_FORMATS = {
        DateTimeFormatter.ISO_LOCAL_DATE,
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
        DateTimeFormatter.ofPattern("dd-MM-yyyy"),
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    };

    private final BdspRepository repository;
    private final BdspMapper mapper;
    private final BdspValidator validator;

    @Override
    public BdspResponse create(CreateBdspRequest request) {
        log.info("Creating BDSP");
        Bdsp bdsp = mapper.toEntity(request);
        return mapper.toResponse(repository.save(bdsp));
    }

    @Override
    @Transactional(readOnly = true)
    public BdspResponse getById(Long id) {
        log.debug("Fetching BDSP with ID: {}", id);
        return mapper.toResponse(validator.getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BdspResponse> getAll() {
        log.debug("Fetching all active BDSP");
        return repository.findAllByIsActiveTrue().stream().map(mapper::toResponse).toList();
    }

    @Override
    public BdspResponse update(Long id, UpdateBdspRequest request) {
        log.info("Updating BDSP with ID: {}", id);
        Bdsp bdsp = validator.getByIdOrThrow(id);
        mapper.applyUpdateRequest(bdsp, request);
        return mapper.toResponse(repository.save(bdsp));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting BDSP with ID: {}", id);
        repository.delete(validator.getByIdOrThrow(id));
    }

    @Override
    public BdspImportResult importFromExcel(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            log.warn("BDSP import rejected: empty file");
            throw new IllegalArgumentException("Uploaded file is empty");
        }
        String filename =
                file.getOriginalFilename() == null
                        ? ""
                        : file.getOriginalFilename().toLowerCase(Locale.ENGLISH);
        if (!filename.endsWith(".xlsx") && !filename.endsWith(".xls")) {
            log.warn("BDSP import rejected: unsupported file type {}", filename);
            throw new IllegalArgumentException("Only .xlsx or .xls files are supported");
        }

        log.info("Importing BDSP from Excel file: {}", file.getOriginalFilename());

        List<BdspImportRowResult> rowResults = new ArrayList<>();
        List<Bdsp> pending = new ArrayList<>();
        int totalRows = 0;

        try (InputStream is = file.getInputStream();
                Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new IllegalArgumentException("Workbook does not contain any sheet");
            }

            ColumnLayout layout = buildColumnLayout(sheet);

            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                int rowNumber = row.getRowNum() + 1;

                if (layout.hasHeader && row.getRowNum() == sheet.getFirstRowNum()) {
                    continue;
                }
                if (isRowEmpty(row)) {
                    continue;
                }

                totalRows++;
                BdspImportRowResult rowResult = processRow(row, layout, pending);
                rowResults.add(rowResult);
            }

            if (!pending.isEmpty()) {
                List<Bdsp> saved = repository.saveAll(pending);
                assignIds(rowResults, saved);
            }

            log.info("BDSP import completed: {} total, {} success", totalRows, rowResults.size());
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("BDSP Excel import failed", ex);
            throw new RuntimeException("Failed to parse Excel file: " + ex.getMessage(), ex);
        }

        long successCount = rowResults.stream().filter(BdspImportRowResult::isSuccess).count();
        long failureCount = rowResults.size() - successCount;

        return BdspImportResult.builder()
                .totalRows(totalRows)
                .successCount((int) successCount)
                .failureCount((int) failureCount)
                .rows(rowResults)
                .build();
    }

    private BdspImportRowResult processRow(Row row, ColumnLayout layout, List<Bdsp> pending) {
        BdspImportRowResult rowResult =
                BdspImportRowResult.builder()
                        .rowNumber(row.getRowNum() + 1)
                        .success(false)
                        .build();
        try {
            CreateBdspRequest request = buildRequest(row, layout);
            pending.add(mapper.toEntity(request));
            rowResult.setSuccess(true);
        } catch (Exception ex) {
            rowResult.setErrorMessage(
                    ex.getMessage() != null ? ex.getMessage() : ex.getClass().getSimpleName());
        }
        return rowResult;
    }

    private void assignIds(List<BdspImportRowResult> rowResults, List<Bdsp> saved) {
        int savedIndex = 0;
        for (BdspImportRowResult rowResult : rowResults) {
            if (rowResult.isSuccess() && savedIndex < saved.size()) {
                rowResult.setBdspId(saved.get(savedIndex).getId());
                savedIndex++;
            }
        }
    }

    private CreateBdspRequest buildRequest(Row row, ColumnLayout layout) {
        return CreateBdspRequest.builder()
                .nameOfBdsp(readString(row, layout, 0))
                .rationaleForOnboarding(readString(row, layout, 1))
                .theme(readString(row, layout, 2))
                .areaOfServiceExpertise(readString(row, layout, 3))
                .state(readString(row, layout, 4))
                .district(readString(row, layout, 5))
                .contact(readString(row, layout, 6))
                .email(readString(row, layout, 7))
                .kyc(readString(row, layout, 8))
                .requestedBy(readString(row, layout, 9))
                .requestDate(readDate(row, layout, 10))
                .build();
    }

    private ColumnLayout buildColumnLayout(Sheet sheet) {
        Row firstRow = sheet.getRow(sheet.getFirstRowNum());
        ColumnLayout layout = new ColumnLayout();
        if (firstRow != null && isHeaderRow(firstRow)) {
            layout.hasHeader = true;
            for (int i = 0; i < layout.indexes.length; i++) {
                layout.indexes[i] = -1;
            }
            Map<String, Integer> headerIndexMap = buildHeaderIndexMap();
            int lastCellNum = firstRow.getLastCellNum();
            for (int c = 0; c < lastCellNum; c++) {
                String header = readCellAsString(firstRow.getCell(c));
                if (header == null) {
                    continue;
                }
                Integer fieldIndex = headerIndexMap.get(normalize(header));
                if (fieldIndex != null && layout.indexes[fieldIndex] == -1) {
                    layout.indexes[fieldIndex] = c;
                }
            }
        }
        return layout;
    }

    private Map<String, Integer> buildHeaderIndexMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("nameofbdsp", 0);
        map.put("bdspname", 0);
        map.put("rationaleofonboardingbdsp", 1);
        map.put("rationaleforonboardingbdsp", 1);
        map.put("rationaleforonboarding", 1);
        map.put("rationaleofonboarding", 1);
        map.put("theme", 2);
        map.put("areaofserviceexpertise", 3);
        map.put("areaofserviceexpertises", 3);
        map.put("areaofexpertise", 3);
        map.put("state", 4);
        map.put("district", 5);
        map.put("contact", 6);
        map.put("contactnumber", 6);
        map.put("email", 7);
        map.put("emailid", 7);
        map.put("kyc", 8);
        map.put("requestedby", 9);
        map.put("requestdate", 10);
        return map;
    }

    private boolean isHeaderRow(Row row) {
        int lastCellNum = row.getLastCellNum();
        Map<String, Integer> indexMap = buildHeaderIndexMap();
        int matches = 0;
        for (int c = 0; c < lastCellNum; c++) {
            String header = readCellAsString(row.getCell(c));
            if (header != null && indexMap.containsKey(normalize(header))) {
                matches++;
            }
        }
        return matches >= 3;
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        int lastCellNum = row.getLastCellNum();
        for (int c = 0; c < lastCellNum; c++) {
            if (readCellAsString(row.getCell(c)) != null) {
                return false;
            }
        }
        return true;
    }

    private String readString(Row row, ColumnLayout layout, int fieldIndex) {
        if (layout.indexes[fieldIndex] < 0) {
            return null;
        }
        return readCellAsString(row.getCell(layout.indexes[fieldIndex]));
    }

    private LocalDate readDate(Row row, ColumnLayout layout, int fieldIndex) {
        if (layout.indexes[fieldIndex] < 0) {
            return null;
        }
        Cell cell = row.getCell(layout.indexes[fieldIndex]);
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        }
        String value = readCellAsString(cell);
        if (value == null) {
            return null;
        }
        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                return LocalDate.parse(value, formatter);
            } catch (DateTimeParseException ignored) {
                // try next pattern
            }
        }
        throw new IllegalArgumentException("Invalid date value in cell: " + value);
    }

    private String readCellAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                String value = cell.getStringCellValue();
                return value == null || value.isBlank() ? null : value.trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                }
                double numeric = cell.getNumericCellValue();
                if (numeric == Math.floor(numeric) && !Double.isInfinite(numeric)) {
                    return String.valueOf((long) numeric);
                }
                return String.valueOf(numeric);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return switch (cell.getCachedFormulaResultType()) {
                    case STRING -> {
                        String formulaValue = cell.getStringCellValue();
                        yield formulaValue == null || formulaValue.isBlank()
                                ? null
                                : formulaValue.trim();
                    }
                    case NUMERIC -> String.valueOf(cell.getNumericCellValue());
                    case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
                    default -> null;
                };
            default:
                return null;
        }
    }

    private String normalize(String value) {
        return value.toLowerCase(Locale.ENGLISH).replaceAll("[^a-z0-9]", "");
    }

    /** Holds resolved Excel column index for each BDSP field, plus header presence flag. */
    private static class ColumnLayout {
        private final int[] indexes = new int[COLUMN_HEADERS.length];
        private boolean hasHeader;

        private ColumnLayout() {
            for (int i = 0; i < indexes.length; i++) {
                indexes[i] = i;
            }
        }
    }
}