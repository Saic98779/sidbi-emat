//package org.emat.service.impl;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Objects;
//import java.util.function.BiConsumer;
//import java.util.function.Function;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//import org.apache.poi.xwpf.usermodel.*;
//import org.apache.xmlbeans.XmlCursor;
//import org.emat.entity.*;
//import org.springframework.beans.PropertyAccessorFactory;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Component;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
//
///** Binds known locations in the supplied Word document; it does not recreate its layout. */
//@Component
//public class DiaWordTemplate {
//    public static final String RESOURCE = "template/DIA_Guidelines_Original.docx";
//    private static final String MISSING = "Not recorded";
//    private static final Pattern BLANK = Pattern.compile("_{2,}|[.…]{3,}");
//
//    public byte[] render(IndustryAssociationAppraisal a, DisbursementCapex capex,
//            List<DisbursementNoteCapacityBuildingIa> members,
//            List<DisbursementNoteCapacityBuildingIaOfficials> officials,
//            List<ActionPlan> plans, List<MonthlySalaryDetails> salaries) {
//        try (var input = new ClassPathResource(RESOURCE).getInputStream();
//                var doc = new XWPFDocument(input); var output = new ByteArrayOutputStream()) {
//            List<XWPFTable> tables = new ArrayList<>(doc.getTables());
//            List<XWPFParagraph> paragraphs = new ArrayList<>(doc.getParagraphs());
//            validate(tables, paragraphs);
//            var r = Objects.requireNonNull(a.getRegistration(), "IA registration required");
//            bindSoft(tables.get(0), a.getAnnexureVList());
//            bindCapex(tables.get(1), a.getAnnexureVIList());
//            bindAppraisal(tables.get(3), a, r);
//            bindLegal(tables, paragraphs, a, r);
//            List<ActionPlanActivity> activities = active(plans).stream()
//                    .flatMap(p -> active(p.getActivities()).stream())
//                    .sorted(Comparator.comparing(ActionPlanActivity::getId, Comparator.nullsLast(Comparator.naturalOrder())))
//                    .toList();
//            repeat(doc, tables.get(7), activities, (table, activity) -> bindActivity(table, activity, r));
//            repeat(doc, tables.get(8), active(salaries), (table, salary) -> bindSalary(table, salary, r));
//            bindCapexNote(tables.get(9), capex, r);
//            repeat(doc, tables.get(10), active(members), (table, note) -> bindCapacity(table, note, r, false));
//            repeat(doc, tables.get(11), active(officials), (table, note) -> bindCapacity(table, note, r, true));
//            doc.getProperties().getCoreProperties().setTitle("DIA draft — appraisal " + a.getId() + " / IA " + r.getId());
//            doc.write(output);
//            return output.toByteArray();
//        } catch (IOException e) {
//            throw new IllegalStateException("Cannot fill the original DIA Word template", e);
//        }
//    }
//
//    private void validate(List<XWPFTable> tables, List<XWPFParagraph> paragraphs) {
//        if (tables.size() != 12 || paragraphs.size() != 316
//                || !tables.get(0).getRow(0).getCell(1).getText().contains("Particulars")
//                || !tables.get(3).getRow(73).getCell(1).getText().contains("Recommendations")
//                || !tables.get(11).getRow(0).getCell(0).getText().contains("IA officials")) {
//            throw new IllegalStateException("DIA Word template structure changed; review the cell bindings before exporting");
//        }
//    }
//
//    private void bindSoft(XWPFTable table, List<AnnexureV> values) {
//        List<AnnexureV> rows = active(values).stream().sorted(Comparator.comparing(
//                AnnexureV::getSnNo, Comparator.nullsLast(Comparator.naturalOrder()))).toList();
//        // Retain the original eleven data slots; add identically styled rows only if necessary.
//        while (table.getNumberOfRows() - 2 < rows.size()) {
//            table.addRow(new XWPFTableRow((org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow)
//                    table.getRow(1).getCtRow().copy(), table), table.getNumberOfRows() - 1);
//        }
//        for (int i = 1; i < table.getNumberOfRows() - 1; i++) {
//            var row = table.getRow(i);
//            for (var c : row.getTableCells()) set(c, "");
//            if (i <= rows.size()) {
//                var item = rows.get(i - 1);
//                set(row.getCell(0), format(item.getSnNo()));
//                set(row.getCell(1), format(item.getParticulars()));
//                set(row.getCell(2), format(item.getTotalCost()));
//                set(row.getCell(3), format(item.getSidbiSupport()));
//            }
//        }
//        if (rows.isEmpty()) set(table.getRow(1).getCell(1), "No Annexure V records saved");
//        var total = table.getRow(table.getNumberOfRows() - 1);
//        set(total.getCell(2), total(rows, AnnexureV::getTotalCost));
//        set(total.getCell(3), total(rows, AnnexureV::getSidbiSupport));
//    }
//
//    private <T> String total(List<T> rows, Function<T, BigDecimal> value) {
//        if (rows.isEmpty()) return MISSING;
//        if (rows.stream().anyMatch(row -> value.apply(row) == null)) return "Incomplete";
//        return format(rows.stream().map(value).reduce(BigDecimal.ZERO, BigDecimal::add));
//    }
//
//    private void bindCapex(XWPFTable table, List<AnnexureVI> values) {
//        List<AnnexureVI> rows = active(values).stream().sorted(Comparator.comparing(
//                AnnexureVI::getId, Comparator.nullsLast(Comparator.naturalOrder()))).toList();
//        if (!rows.isEmpty()) {
//            String sections = rows.stream().map(item -> join(item.getSection(), item.getSectionNote()))
//                    .filter(s -> !s.equals(MISSING)).distinct().collect(Collectors.joining("\n"));
//            if (!sections.isBlank()) set(table.getRow(0).getCell(0), sections);
//        }
//        while (table.getNumberOfRows() - 3 < rows.size()) {
//            table.addRow(new XWPFTableRow((org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow)
//                    table.getRow(2).getCtRow().copy(), table), table.getNumberOfRows() - 1);
//        }
//        for (int i = 2; i < table.getNumberOfRows() - 1; i++) {
//            var row = table.getRow(i);
//            for (var cell : row.getTableCells()) {
//                // The original sample furniture shares a vertically merged Make cell.
//                if (cell.getCTTc().isSetTcPr() && cell.getCTTc().getTcPr().isSetVMerge())
//                    cell.getCTTc().getTcPr().unsetVMerge();
//                set(cell, "");
//            }
//            if (i - 2 < rows.size()) {
//                var item = rows.get(i - 2);
//                set(row.getCell(0), format(item.getIndicativeItem()));
//                set(row.getCell(1), format(item.getNumbers()));
//                set(row.getCell(2), format(item.getMake()));
//                set(row.getCell(3), format(item.getMaximumCost()) + " " + format(item.getMaximumCostUnit()));
//            }
//        }
//        if (rows.isEmpty()) set(table.getRow(2).getCell(0), "No Annexure VI records saved");
//    }
//
//    private void bindAppraisal(XWPFTable table, IndustryAssociationAppraisal a, IndustryAssociationRegistration r) {
//        bind(table, r, null, "2:state,3:industryAssociationName,4:constitutionType,6:iaType,7:constitutionProof,8:address,9:district,10:pincode,47:secretariatStaff,61:selectionCriteria,66:envisagedImpact");
//        cell(table, 5, r.getIncorporationDate() == null ? null : r.getIncorporationDate().getYear());
//        bind(table, a, r, "12:apexHolderName,13:apexHolderDesignation,14:apexHolderMobile,15:apexHolderEmail,17:nodalName,18:nodalDesignation,19:nodalMobile,20:nodalEmail,35:mappedWithCluster,36:clusterName,37:mappedWithImportantDistrict,38:msmeCountWithoutTraders,40:activeMembersCount,41:justification,42:buildingType,43:infrastructureType,44:itInfrastructureAvailable,45:infrastructureType,46:secretariatStaffAvailable,49:paidServicesAvailable,50:paidServicesDetails,64:envisagedOutput,65:envisagedOutcome");
//        bind(table, a, null, "51:majorSourcesOfIncome,52:activitiesLastYear,54:formalizationComments,55:referralArrangementComments,56:bseReadinessComments,59:financingScope,60:projectLocation,67:clusterExpertComments,69:budgetAllocated,70:utilizedAmount,71:availableBudget");
//        cell(table, 22, detail(a, "cibilReportReferenceNo,cibilReportDate,cibilRanking,cibilRemarks"));
//        cell(table, 24, a.getNabardBlacklisted());
//        cell(table, 25, detail(a, "smartReportReferenceNo,smartReportDate,smartReportRemarks"));
//        cell(table, 26, join(a.getWebSearchVerified(), a.getWebSearchDocument()));
//        cell(table, 28, detail(a, "holderCibilReferenceNo,holderCibilDate,holderCibilScore,holderCibilRemarks"));
//        cell(table, 29, detail(a, "holderSmartAvailable,holderSmartDate,holderSmartRemarks"));
//        cell(table, 31, detail(a, "beneficialOwnerCibilReferenceNo,beneficialOwnerCibilDate,beneficialOwnerCibilRanking,beneficialOwnerCibilRemarks"));
//        cell(table, 32, detail(a, "beneficialOwnerSmartAvailable,beneficialOwnerSmartDate,beneficialOwnerSmartRemarks"));
//        cell(table, 33, join(shared(a, r, "sidbiBranch"), r.getSidbiBranchName()));
//        cell(table, 48, join(shared(a, r, "websiteAvailable"), shared(a, r, "websiteUrl")));
//        cell(table, 57, active(a.getSectors()).stream().map(s -> format(s.getSector())).collect(Collectors.joining("\n")));
//        cell(table, 58, active(a.getSectors()).stream().map(s -> format(s.getSector()) + ": " + format(s.getSectorKeyProblems())).collect(Collectors.joining("\n")));
//        cell(table, 62, r.getGrantProposed());
//        cell(table, 63, "Salary: " + format(a.getGrantProposedSalary()) + "\nCapacity building: "
//                + format(a.getGrantProposedCapacityBuilding()) + "\nCAPEX: " + format(a.getGrantProposedCapex())
//                + "\n" + format(shared(a, r, "grantDetails")));
//        // These form fields explicitly use lakhs. Values are copied without unverified conversion.
//        if (a.getFinancialYear() != null) replace(table.getRow(69).getCell(1).getParagraphs(), "202__", Integer.toString(a.getFinancialYear().getYear()));
//        replaceBlanks(table.getRow(72).getCell(1).getParagraphs(), List.of(format(a.getDopDate())));
//        cell(table, 72, a.getDopDate());
//        cell(table, 73, join(a.getRecommendation(), a.getRecommendationRemarks()));
//        if (a.getTermsAndConditions() != null && !a.getTermsAndConditions().isEmpty())
//            cell(table, 68, String.join("\n", a.getTermsAndConditions()));
//    }
//
//    private void bindLegal(List<XWPFTable> tables, List<XWPFParagraph> p,
//            IndustryAssociationAppraisal a, IndustryAssociationRegistration r) {
//        for (int index : List.of(4, 5)) {
//            XWPFTable t = tables.get(index);
//            set(t.getRow(0).getCell(1), format(r.getIndustryAssociationName()));
//            set(t.getRow(3).getCell(1), MISSING); // No persisted final grant sanction amount.
//            replace(t.getRow(4).getCell(1).getParagraphs(), "__<<Name of IA>>____________________", format(r.getIndustryAssociationName()));
//            set(t.getRow(5).getCell(1), format(shared(a, r, "grantDetails")));
//            replaceBlanks(t.getRow(index == 4 ? 6 : 7).getCell(1).getParagraphs(), List.of(MISSING, MISSING, MISSING));
//            set(t.getRow(index == 4 ? 10 : 11).getCell(1), MISSING); // Disbursement pattern is not a model field.
//        }
//        if (a.getTermsAndConditions() != null && !a.getTermsAndConditions().isEmpty())
//            set(tables.get(4).getRow(11).getCell(1), String.join("\n", a.getTermsAndConditions()));
//        else set(tables.get(4).getRow(11).getCell(1), MISSING);
//        replaceBlanks(List.of(p.get(59)), List.of(MISSING, MISSING));
//        set(p.get(64), format(r.getIndustryAssociationName()));
//        set(p.get(65), format(r.getAddress()));
//        set(p.get(66), join(r.getDistrict(), r.getState(), r.getPincode()));
//        replaceBlanks(List.of(p.get(73)), List.of(MISSING)); // A proposal submission date is not createdAt/DoP.
//        replaceBlanks(List.of(p.get(75)), List.of(MISSING, MISSING, format(a.getProjectLocation())));
//        replaceBlanks(List.of(p.get(90)), List.of(MISSING, MISSING));
//        replace(List.of(p.get(92)), "<<IA Name>>", format(r.getIndustryAssociationName()));
//        replaceBlanks(List.of(p.get(95), p.get(96)), List.of(MISSING, MISSING));
//        replaceBlanks(List.of(p.get(99)), List.of(format(r.getIndustryAssociationName())));
//        replaceBlanks(List.of(p.get(131)), List.of(MISSING, MISSING, MISSING));
//        // Populate identification only; do not fabricate constitution-specific legal representations.
//        replace(List.of(p.get(135)), "[ Details of the IA to be inserted based on the constitution of IA (as per enclosed Annexure)",
//                format(r.getIndustryAssociationName()) + ", " + format(r.getConstitutionType()) + ", " + format(r.getAddress()) + ".");
//        replaceBlanks(List.of(p.get(143)), List.of(format(r.getIndustryAssociationName()), format(r.getIaType()),
//                format(r.getDistrict()), format(shared(a, r, "activeMembersCount")), format(a.getActivitiesLastYear())));
//        replaceBlanks(List.of(p.get(145)), List.of(format(a.getProjectLocation())));
//        replaceBlanks(List.of(p.get(146)), List.of(format(a.getActivitiesLastYear()), format(r.getAddress())));
//        replaceBlanks(List.of(p.get(189)), List.of(MISSING));
//        replaceBlanks(List.of(p.get(239)), List.of(format(r.getAddress())));
//        replaceBlanks(List.of(p.get(240)), List.of(format(r.getEmail()), format(r.getEmail())));
//        replaceBlanks(List.of(p.get(242)), List.of(format(shared(a, r, "sidbiBranch")), MISSING));
//        replaceBlanks(tables.get(6).getRow(0).getCell(0).getParagraphs(), List.of(format(r.getIndustryAssociationName())));
//        // Signatories and witnesses remain for actual execution, not inferred from contact/approver names.
//    }
//
//    private void bindActivity(XWPFTable t, ActionPlanActivity activity, IndustryAssociationRegistration r) {
//        cell(t, 2, r.getState()); cell(t, 3, r.getIndustryAssociationName());
//        bind(t, activity, null, "5:nameOfActivity,7:technicalServiceProvider,8:totalCost,9:percentSupportBySidbi,10:percentSupportByOthers,11:percentContributionByIa");
//        for (String mapping : List.of("6:monthToBeHeld", "12:expectedParticipantMembers", "13:expectedParticipantNonMembers",
//                "14:expectedOutput", "15:expectedOutcome", "16:expectedIncomeGeneratingActivity")) {
//            String[] parts = mapping.split(":");
//            Object value = value(activity, parts[1]);
//            cell(t, Integer.parseInt(parts[0]), missing(value) ? MISSING : "Planned / expected: " + format(value));
//        }
//    }
//
//    private void bindSalary(XWPFTable t, MonthlySalaryDetails s, IndustryAssociationRegistration r) {
//        BseSalary invoice = s == null ? null : s.getBseSalary();
//        cell(t, 2, null); // Salary has no mapped manpower agency relationship.
//        bind(t, invoice, null, "3:gstinOfAgency,4:reasonForNoGstin,5:gstinOfSdbi,9:natureOfPayment,10:invoiceDate,11:invoiceNumber,15:tdsApplicable,16:tdsNotApplicableReason,18:accountCode,19:complianceTerms,20:recommendation");
//        for (String mapping : List.of("6:sanctionedAmount", "7:disbursedTillDate", "8:disbursementSoughtIn",
//                "12:invoiceValue", "13:gstAmount", "14:totalAmount", "17:recommendedDisbursementAmount")) {
//            String[] parts = mapping.split(":"); Object amount = value(invoice, parts[1]);
//            cell(t, Integer.parseInt(parts[0]), missing(amount) ? MISSING : "Invoice-wide: " + format(amount));
//        }
//        cell(t, 24, r.getIndustryAssociationName());
//        cell(t, 25, s == null || s.getBse() == null ? null : s.getBse().getBseName());
//        bind(t, s, null, "26:salaryMonth,27:monthlySalary,28:paidDays,29:additionalAmount,30:additionalAmountReason,31:paymentToBse,32:gtAttendanceComments,33:gtAdditionalComments");
//    }
//
//    private void bindCapexNote(XWPFTable t, DisbursementCapex note, IndustryAssociationRegistration r) {
//        cell(t, 2, r.getIndustryAssociationName());
//        bind(t, note, null, "3:gstinIa,4:gstinNotApplicableReason,5:gstinSidbi,6:sanctionedAmount,7:disbursedTillDate,8:disbursementSought,10:invoiceDate,11:invoiceNumber,12:valueOfServiceItems,13:igstAmount,14:totalAmount,15:tdsApplicable,16:tdsNotApplicableReason,17:amountRecommendedForDisbursement,18:accountCode,19:gtCapexVerificationComments,20:preDisbursementCompliance,21:recommendation");
//        if (note == null) cell(t, 9, null);
//        else replaceBlanks(t.getRow(9).getCell(2).getParagraphs(), List.of(format(note.getSanctionedAmount()), MISSING,
//                format(note.getDisbursedTillDate()), MISSING, format(note.getDisbursementSought()), format(note.getDetailsOfItems())));
//    }
//
//    private void bindCapacity(XWPFTable t, Object note, IndustryAssociationRegistration r, boolean officials) {
//        cell(t, 2, officials ? value(note, "eventManagementAgencyName") : r.getIndustryAssociationName());
//        cell(t, 3, value(note, officials ? "gstinOfAgency" : "gstinOfIa"));
//        bind(t, note, null, "4:gstinNotApplicableReason,5:gstinOfSidbi,6:sanctionedAmount,7:disbursedTillDate,8:disbursementSought,9:natureOfPayment,10:invoiceDate,11:invoiceNumber,12:valueOfServiceItemsSupplied,13:igstAt18Percent,14:totalAmount,15:tdsApplicable,16:tdsNotApplicableReason,17:amountRecommendedForDisbursement,18:accountCodeForPayment,19:gtCommentsOnEventOutcomeImpact,20:compliancePreDisbursementTerms,21:recommendation");
//    }
//
//    /** Copies the original form for additional records rather than changing its columns. */
//    private <T> void repeat(XWPFDocument doc, XWPFTable original, List<T> items, BiConsumer<XWPFTable, T> bind) {
//        CTTbl prototype = (CTTbl) original.getCTTbl().copy();
//        bind.accept(original, items.isEmpty() ? null : items.get(0));
//        for (int i = items.size() - 1; i >= 1; i--) {
//            try (XmlCursor cursor = original.getCTTbl().newCursor()) {
//                cursor.toEndToken(); cursor.toNextToken();
//                XWPFParagraph separator = doc.insertNewParagraph(cursor);
//                separator.setPageBreak(true);
//                try (XmlCursor after = separator.getCTP().newCursor()) {
//                    after.toEndToken(); after.toNextToken();
//                    XWPFTable inserted = doc.insertNewTbl(after);
//                    inserted.getCTTbl().set(prototype);
//                    bind.accept(new XWPFTable(inserted.getCTTbl(), doc), items.get(i));
//                }
//            }
//        }
//    }
//
//    private void bind(XWPFTable t, Object primary, Object fallback, String mappings) {
//        for (String mapping : mappings.split(",")) {
//            String[] parts = mapping.split(":");
//            cell(t, Integer.parseInt(parts[0]), shared(primary, fallback, parts[1]));
//        }
//    }
//
//    private Object shared(Object primary, Object fallback, String property) {
//        Object v = value(primary, property);
//        return missing(v) ? value(fallback, property) : v;
//    }
//
//    private Object value(Object bean, String property) {
//        return bean == null ? null : PropertyAccessorFactory.forBeanPropertyAccess(bean).getPropertyValue(property);
//    }
//
//    private String detail(Object bean, String properties) {
//        List<Object> values = new ArrayList<>();
//        for (String p : properties.split(",")) values.add(value(bean, p));
//        return join(values.toArray());
//    }
//
//    private String join(Object... values) {
//        String result = java.util.Arrays.stream(values).filter(v -> !missing(v)).map(this::format).collect(Collectors.joining("; "));
//        return result.isBlank() ? MISSING : result;
//    }
//
//    private void cell(XWPFTable table, int row, Object value) {
//        set(table.getRow(row).getCell(2), format(value));
//    }
//
//    /** Preserve paragraph/run properties, table geometry and unused paragraphs in a value cell. */
//    private void set(XWPFTableCell cell, String value) {
//        for (XWPFParagraph p : cell.getParagraphs()) set(p, "");
//        XWPFParagraph p = cell.getParagraphs().isEmpty() ? cell.addParagraph() : cell.getParagraphs().get(0);
//        set(p, value);
//    }
//
//    private void set(XWPFParagraph paragraph, String text) {
//        var runs = paragraph.getRuns();
//        XWPFRun first = runs.isEmpty() ? paragraph.createRun() : runs.get(0);
//        for (XWPFRun run : runs) {
//            for (CTText t : run.getCTR().getTList()) t.setStringValue("");
//        }
//        // Newlines in stored values become Word line breaks, not XML markup.
//        String[] lines = text.split("\\R", -1);
//        first.setText(lines[0], 0);
//        for (int i = 1; i < lines.length; i++) { first.addBreak(); first.setText(lines[i]); }
//    }
//
//    /** Replace text spanning Word runs without flattening their fonts, emphasis or surrounding text. */
//    private void replace(List<XWPFParagraph> paragraphs, String token, String value) {
//        for (XWPFParagraph p : paragraphs) replaceMatches(p, Pattern.compile(Pattern.quote(token)), match -> value);
//    }
//
//    private void replaceBlanks(List<XWPFParagraph> paragraphs, List<String> values) {
//        int[] index = {0};
//        for (XWPFParagraph p : paragraphs) replaceMatches(p, BLANK,
//                match -> index[0] < values.size() ? values.get(index[0]++) : MISSING);
//    }
//
//    private void replaceMatches(XWPFParagraph p, Pattern pattern, Function<String, String> replacement) {
//        List<CTText> nodes = p.getRuns().stream().flatMap(run -> run.getCTR().getTList().stream()).toList();
//        String original = nodes.stream().map(CTText::getStringValue).collect(Collectors.joining());
//        var matcher = pattern.matcher(original);
//        record Edit(int start, int end, String value) {}
//        List<Edit> edits = new ArrayList<>();
//        while (matcher.find()) edits.add(new Edit(matcher.start(), matcher.end(), replacement.apply(matcher.group()).replaceAll("\\R", "; ")));
//        int[] starts = new int[nodes.size()];
//        for (int i = 1; i < nodes.size(); i++) starts[i] = starts[i - 1] + nodes.get(i - 1).getStringValue().length();
//        for (int e = edits.size() - 1; e >= 0; e--) {
//            Edit edit = edits.get(e);
//            for (int i = nodes.size() - 1; i >= 0; i--) {
//                int end = i + 1 < nodes.size() ? starts[i + 1] : original.length();
//                if (end <= edit.start() || starts[i] >= edit.end()) continue;
//                String text = nodes.get(i).getStringValue();
//                int from = Math.max(0, edit.start() - starts[i]);
//                int to = Math.min(end, edit.end()) - starts[i];
//                nodes.get(i).setStringValue(text.substring(0, from)
//                        + (edit.start() >= starts[i] ? edit.value() : "") + text.substring(to));
//            }
//        }
//    }
//
//    private static boolean missing(Object value) {
//        return value == null || value instanceof String s && s.isBlank();
//    }
//
//    private String format(Object value) {
//        if (missing(value)) return MISSING;
//        if (value instanceof Boolean b) return b ? "Yes" : "No";
//        if (value instanceof BigDecimal d) return d.setScale(2, RoundingMode.HALF_UP).toPlainString();
//        if (value instanceof LocalDate d) return d.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//        if (value instanceof String || value instanceof Number || value instanceof Enum<?>) return value.toString();
//        throw new IllegalArgumentException("Non-scalar DIA document value");
//    }
//
//    private static <T> List<T> active(List<T> values) {
//        return values == null ? List.of() : values.stream().filter(Objects::nonNull)
//                .filter(v -> !(v instanceof BaseEntity b) || !Boolean.FALSE.equals(b.getIsActive())).toList();
//    }
//}
