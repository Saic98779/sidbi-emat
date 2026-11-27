package org.emat.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.emat.dto.ParticipantImportRow;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Parses, normalizes and validates participant rows from an Excel workbook (.xlsx / .xls), and
 * builds the matching import template. Shared by the DIA Exposure Visit Participant and DIA MSME
 * Participant imports.
 *
 * <p>The first sheet is read. If the first row looks like a header row, columns are located by
 * header name (order-independent); otherwise the default column order is assumed:
 *
 * <ol>
 *   <li>MSME Name
 *   <li>Designation
 *   <li>Gender
 *   <li>Father/Husband Name
 *   <li>Date of Birth
 *   <li>Email
 *   <li>Contact No
 *   <li>PAN No
 *   <li>Udyam Aadhar No
 * </ol>
 */
@Component
@Slf4j
public class ParticipantExcelParser {

    private static final int FIELD_COUNT = 9;
    private static final int MSME_NAME = 0;
    private static final int DESIGNATION = 1;
    private static final int GENDER = 2;
    private static final int FATHER_HUSBAND_NAME = 3;
    private static final int DATE_OF_BIRTH = 4;
    private static final int EMAIL = 5;
    private static final int CONTACT_NO = 6;
    private static final int PAN_NO = 7;
    private static final int UDYAM_AADHAR_NO = 8;

    /** Template header labels, in default column order. Each normalizes to a known header key. */
    private static final String[] TEMPLATE_HEADERS = {
        "MSME Name",
        "Designation",
        "Gender",
        "Father/Husband Name",
        "Date of Birth",
        "Email",
        "Contact No",
        "PAN No",
        "Udyam Aadhar No"
    };

    private static final Map<String, Integer> HEADER_INDEX_MAP = buildHeaderIndexMap();

    private static final List<DateTimeFormatter> DATE_FORMATS =
            List.of(
                    DateTimeFormatter.ISO_LOCAL_DATE,
                    DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                    DateTimeFormatter.ofPattern("dd.MM.yyyy"),
                    DateTimeFormatter.ofPattern("d-M-yyyy"),
                    DateTimeFormatter.ofPattern("d/M/yyyy"));

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PAN_PATTERN = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");
    private static final Pattern CONTACT_PATTERN = Pattern.compile("^\\+?[0-9]{10,15}$");

    /**
     * A parsed Excel row. {@code error} is set when the row could not be parsed or failed
     * validation; {@code row} is {@code null} only when parsing itself failed.
     */
    public record ParsedRow(int rowNumber, ParticipantImportRow row, String error) {

        public boolean hasError() {
            return error != null;
        }
    }

    /**
     * Reads all non-empty data rows from the first sheet. Rows whose cells cannot be parsed are
     * returned with an error instead of aborting the whole import.
     *
     * @throws IllegalArgumentException if the workbook cannot be read or has no sheet
     */
    public List<ParsedRow> parse(MultipartFile file) {
        List<ParsedRow> rows = new ArrayList<>();
        try (InputStream is = file.getInputStream();
                Workbook workbook = WorkbookFactory.create(is)) {
            if (workbook.getNumberOfSheets() == 0) {
                throw new IllegalArgumentException("Workbook does not contain any sheet");
            }
            Sheet sheet = workbook.getSheetAt(0);
            int[] layout = new int[FIELD_COUNT];
            boolean hasHeader = buildColumnLayout(sheet, layout);

            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (hasHeader && row.getRowNum() == sheet.getFirstRowNum()) {
                    continue;
                }
                if (isRowEmpty(row)) {
                    continue;
                }
                rows.add(parseRow(row, layout));
            }
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Participant Excel parsing failed", ex);
            throw new IllegalArgumentException(
                    "Failed to parse Excel file: " + ex.getMessage(), ex);
        }
        return rows;
    }

    /**
     * Builds the import template (.xlsx): a bold header row matching the columns this parser
     * reads, with the Date of Birth column formatted as dd-MM-yyyy and the identifier columns
     * formatted as text so leading zeros are kept.
     */
    public byte[] buildTemplate() {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Participants");

            Font boldFont = workbook.createFont();
            boldFont.setBold(true);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(boldFont);

            DataFormat dataFormat = workbook.createDataFormat();
            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(dataFormat.getFormat("dd-mm-yyyy"));
            CellStyle textStyle = workbook.createCellStyle();
            textStyle.setDataFormat(dataFormat.getFormat("@"));

            Row header = sheet.createRow(0);
            for (int i = 0; i < TEMPLATE_HEADERS.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(TEMPLATE_HEADERS[i]);
                cell.setCellStyle(headerStyle);
                sheet.setColumnWidth(i, 22 * 256);
            }
            sheet.setDefaultColumnStyle(DATE_OF_BIRTH, dateStyle);
            sheet.setDefaultColumnStyle(CONTACT_NO, textStyle);
            sheet.setDefaultColumnStyle(PAN_NO, textStyle);
            sheet.setDefaultColumnStyle(UDYAM_AADHAR_NO, textStyle);
            sheet.createFreezePane(0, 1);

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException ex) {
            log.error("Failed to build participant import template", ex);
            throw new IllegalStateException("Failed to build import template", ex);
        }
    }

    /** Validates the import file is present, non-empty and an .xlsx / .xls workbook. */
    public void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }
        String filename =
                file.getOriginalFilename() == null
                        ? ""
                        : file.getOriginalFilename().toLowerCase(Locale.ENGLISH);
        if (!filename.endsWith(".xlsx") && !filename.endsWith(".xls")) {
            throw new IllegalArgumentException("Only .xlsx or .xls files are supported");
        }
    }

    private ParsedRow parseRow(Row row, int[] layout) {
        int rowNumber = row.getRowNum() + 1;
        ParticipantImportRow parsed;
        try {
            parsed =
                    new ParticipantImportRow(
                            readString(row, layout, MSME_NAME),
                            readString(row, layout, DESIGNATION),
                            readString(row, layout, GENDER),
                            readString(row, layout, FATHER_HUSBAND_NAME),
                            readDate(row, layout, DATE_OF_BIRTH),
                            normalizeEmail(readString(row, layout, EMAIL)),
                            normalizeContact(readString(row, layout, CONTACT_NO)),
                            normalizeCode(readString(row, layout, PAN_NO)),
                            normalizeCode(readString(row, layout, UDYAM_AADHAR_NO)));
        } catch (Exception ex) {
            String message =
                    ex.getMessage() != null ? ex.getMessage() : ex.getClass().getSimpleName();
            return new ParsedRow(rowNumber, null, message);
        }
        return new ParsedRow(rowNumber, parsed, validateRow(parsed));
    }

    /** Returns an error message, or {@code null} when the row is valid. */
    private String validateRow(ParticipantImportRow row) {
        if (row.msmeName() == null) {
            return "MSME Name is required";
        }
        if (row.panNo() == null
                && row.udyamAadharNo() == null
                && row.email() == null
                && row.contactNo() == null) {
            return "At least one identifier (PAN No, Udyam Aadhar No, Email or Contact No) is"
                    + " required";
        }
        if (row.panNo() != null && !PAN_PATTERN.matcher(row.panNo()).matches()) {
            return "Invalid PAN No '" + row.panNo() + "'";
        }
        if (row.email() != null && !EMAIL_PATTERN.matcher(row.email()).matches()) {
            return "Invalid Email format";
        }
        if (row.contactNo() != null && !CONTACT_PATTERN.matcher(row.contactNo()).matches()) {
            return "Invalid Contact No: expected 10-15 digits";
        }
        return null;
    }

    private static String normalizeCode(String value) {
        return value == null ? null : value.replaceAll("\\s", "").toUpperCase(Locale.ENGLISH);
    }

    private static String normalizeEmail(String value) {
        return value == null ? null : value.trim().toLowerCase(Locale.ENGLISH);
    }

    private static String normalizeContact(String value) {
        return value == null ? null : value.replaceAll("[\\s()-]", "");
    }

    /** Resolves column indexes; returns {@code true} if the first row is a header row. */
    private boolean buildColumnLayout(Sheet sheet, int[] layout) {
        for (int i = 0; i < layout.length; i++) {
            layout[i] = i;
        }
        Row firstRow = sheet.getRow(sheet.getFirstRowNum());
        if (firstRow == null || !isHeaderRow(firstRow)) {
            return false;
        }
        for (int i = 0; i < layout.length; i++) {
            layout[i] = -1;
        }
        for (int c = 0; c < firstRow.getLastCellNum(); c++) {
            String header = readCellAsString(firstRow.getCell(c));
            if (header == null) {
                continue;
            }
            Integer fieldIndex = HEADER_INDEX_MAP.get(normalize(header));
            if (fieldIndex != null && layout[fieldIndex] == -1) {
                layout[fieldIndex] = c;
            }
        }
        return true;
    }

    private static Map<String, Integer> buildHeaderIndexMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("msmename", MSME_NAME);
        map.put("nameofmsme", MSME_NAME);
        map.put("participantname", MSME_NAME);
        map.put("name", MSME_NAME);
        map.put("designation", DESIGNATION);
        map.put("gender", GENDER);
        map.put("fatherhusbandname", FATHER_HUSBAND_NAME);
        map.put("fathersname", FATHER_HUSBAND_NAME);
        map.put("fathername", FATHER_HUSBAND_NAME);
        map.put("husbandname", FATHER_HUSBAND_NAME);
        map.put("fathershusbandsname", FATHER_HUSBAND_NAME);
        map.put("dateofbirth", DATE_OF_BIRTH);
        map.put("dob", DATE_OF_BIRTH);
        map.put("email", EMAIL);
        map.put("emailid", EMAIL);
        map.put("emailaddress", EMAIL);
        map.put("contactno", CONTACT_NO);
        map.put("contactnumber", CONTACT_NO);
        map.put("contact", CONTACT_NO);
        map.put("mobileno", CONTACT_NO);
        map.put("mobilenumber", CONTACT_NO);
        map.put("panno", PAN_NO);
        map.put("pan", PAN_NO);
        map.put("pannumber", PAN_NO);
        map.put("udyamaadharno", UDYAM_AADHAR_NO);
        map.put("udyamaadhaarno", UDYAM_AADHAR_NO);
        map.put("udyamaadharnumber", UDYAM_AADHAR_NO);
        map.put("udyamaadhaarnumber", UDYAM_AADHAR_NO);
        map.put("udyamno", UDYAM_AADHAR_NO);
        map.put("udyamregistrationno", UDYAM_AADHAR_NO);
        map.put("udyamregistrationnumber", UDYAM_AADHAR_NO);
        return map;
    }

    private boolean isHeaderRow(Row row) {
        int matches = 0;
        for (int c = 0; c < row.getLastCellNum(); c++) {
            String header = readCellAsString(row.getCell(c));
            if (header != null && HEADER_INDEX_MAP.containsKey(normalize(header))) {
                matches++;
            }
        }
        return matches >= 3;
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (int c = 0; c < row.getLastCellNum(); c++) {
            if (readCellAsString(row.getCell(c)) != null) {
                return false;
            }
        }
        return true;
    }

    private String readString(Row row, int[] layout, int fieldIndex) {
        if (layout[fieldIndex] < 0) {
            return null;
        }
        return readCellAsString(row.getCell(layout[fieldIndex]));
    }

    private LocalDate readDate(Row row, int[] layout, int fieldIndex) {
        if (layout[fieldIndex] < 0) {
            return null;
        }
        Cell cell = row.getCell(layout[fieldIndex]);
        if (cell == null) {
            return null;
        }
        boolean numeric =
                cell.getCellType() == CellType.NUMERIC
                        || (cell.getCellType() == CellType.FORMULA
                                && cell.getCachedFormulaResultType() == CellType.NUMERIC);
        if (numeric && DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        }
        String value = readCellAsString(cell);
        if (value == null) {
            return null;
        }
        for (DateTimeFormatter format : DATE_FORMATS) {
            try {
                return LocalDate.parse(value, format);
            } catch (DateTimeParseException ignored) {
                // try next format
            }
        }
        throw new IllegalArgumentException(
                "Invalid Date of Birth '" + value + "'. Expected format dd-MM-yyyy or yyyy-MM-dd");
    }

    private String readCellAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType type = cell.getCellType();
        if (type == CellType.FORMULA) {
            type = cell.getCachedFormulaResultType();
        }
        switch (type) {
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
            default:
                return null;
        }
    }

    private static String normalize(String value) {
        return value.toLowerCase(Locale.ENGLISH).replaceAll("[^a-z0-9]", "");
    }
}
