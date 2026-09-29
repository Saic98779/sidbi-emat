package org.emat.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.emat.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.core.io.ClassPathResource;

class DiaWordTemplateTest {
    private final DiaWordTemplate template = new DiaWordTemplate();

    private byte[] filled() {
        return template.render(DiaDocumentTemplateTest.fixture(), null, List.of(), List.of(), List.of(), List.of());
    }

    @Test
    void originalTableGeometrySectionsStylesAndLegalClausesArePreserved() throws Exception {
        try (var in = new ClassPathResource(DiaWordTemplate.RESOURCE).getInputStream();
                var original = new XWPFDocument(in);
                var result = new XWPFDocument(new ByteArrayInputStream(filled()))) {
            assertEquals(original.getTables().size(), result.getTables().size());
            assertEquals(original.getParagraphs().size(), result.getParagraphs().size());
            assertEquals(original.getDocument().getBody().getSectPr().xmlText(), result.getDocument().getBody().getSectPr().xmlText());
            assertEquals(original.getStyle().xmlText(), result.getStyle().xmlText());
            for (int i = 0; i < original.getTables().size(); i++) {
                assertEquals(original.getTables().get(i).getCTTbl().getTblPr().xmlText(),
                        result.getTables().get(i).getCTTbl().getTblPr().xmlText(), "Table properties " + i);
                assertEquals(original.getTables().get(i).getCTTbl().getTblGrid().xmlText(),
                        result.getTables().get(i).getCTTbl().getTblGrid().xmlText(), "Table widths " + i);
            }
            for (int p : List.of(77, 79, 138, 144, 147, 150, 153, 163, 170, 172, 195, 207, 212, 228, 234, 244, 249, 256)) {
                assertEquals(original.getParagraphs().get(p).getCTP().xmlText(), result.getParagraphs().get(p).getCTP().xmlText(), "Legal paragraph " + p);
            }
            assertEquals(original.getTables().get(2).getCTTbl().xmlText(), result.getTables().get(2).getCTTbl().xmlText(), "SOP unchanged");
            assertEquals(original.getTables().get(4).getRow(9).getCtRow().xmlText(),
                    result.getTables().get(4).getRow(9).getCtRow().xmlText(), "Pre-disbursement legal terms unchanged");
        }
    }

    @Test
    void annexuresContainSavedValuesAndNotTheSamplePrices() throws Exception {
        try (var doc = new XWPFDocument(new ByteArrayInputStream(filled()))) {
            var soft = doc.getTables().get(0);
            assertEquals("Member directory", soft.getRow(1).getCell(1).getText());
            assertEquals("30001.00", soft.getRow(12).getCell(2).getText());
            assertEquals("15000.50", soft.getRow(12).getCell(3).getText());
            assertFalse(soft.getText().contains("855000"));
            var capex = doc.getTables().get(1);
            assertEquals("Laptop", capex.getRow(2).getCell(0).getText());
            assertEquals("Demo make", capex.getRow(2).getCell(2).getText());
            assertEquals("50000.00 each", capex.getRow(2).getCell(3).getText());
            assertFalse(capex.getText().contains("Photocopy"));
            assertEquals("No", doc.getTables().get(3).getRow(48).getCell(2).getText());
            assertEquals("0", doc.getTables().get(3).getRow(40).getCell(2).getText());
            assertTrue(doc.getTables().get(3).getRow(58).getCell(2).getText().contains("Credit access"));
            assertTrue(doc.getParagraphs().get(143).getText().contains("DEMO IA"));
        }
    }

    @Test
    void missingRecordsAreNotReplacedWithSampleDataAndGettersAreUsed() throws Exception {
        var r = mock(IndustryAssociationRegistration.class);
        when(r.getIndustryAssociationName()).thenReturn("Getter-only IA");
        when(r.getState()).thenReturn("Getter-only state");
        var a = IndustryAssociationAppraisal.builder().id(221L).registration(r).build();
        try (var doc = new XWPFDocument(new ByteArrayInputStream(template.render(a, null, List.of(), List.of(), List.of(), List.of())))) {
            assertEquals("Getter-only state", doc.getTables().get(3).getRow(2).getCell(2).getText());
            assertEquals("Getter-only IA", doc.getTables().get(3).getRow(3).getCell(2).getText());
            assertTrue(doc.getTables().get(0).getText().contains("No Annexure V records saved"));
            assertTrue(doc.getTables().get(1).getText().contains("No Annexure VI records saved"));
            assertEquals("Not recorded", doc.getTables().get(4).getRow(3).getCell(1).getText());
        }
    }

    @Test
    void extraItemsAndNotesUseCopiesOfTheOriginalRowsAndForms() throws Exception {
        var a = DiaDocumentTemplateTest.fixture();
        var soft = new ArrayList<AnnexureV>();
        for (int i = 1; i <= 20; i++) soft.add(AnnexureV.builder().snNo(i).particulars("Activity " + i).totalCost(BigDecimal.TEN).sidbiSupport(BigDecimal.ONE).build());
        a.setAnnexureVList(soft);
        var notes = List.of(DisbursementNoteCapacityBuildingIa.builder().invoiceNumber("INV-FIRST").build(),
                DisbursementNoteCapacityBuildingIa.builder().invoiceNumber("INV-SECOND").build(),
                DisbursementNoteCapacityBuildingIa.builder().invoiceNumber("INV-THIRD").build());
        byte[] bytes = template.render(a, null, notes, List.of(), List.of(), List.of());
        try (var doc = new XWPFDocument(new ByteArrayInputStream(bytes)); var extractor = new XWPFWordExtractor(doc)) {
            assertEquals(14, doc.getTables().size());
            String text = extractor.getText();
            assertTrue(text.contains("Activity 20"));
            assertTrue(text.indexOf("INV-FIRST") < text.indexOf("INV-SECOND"));
            assertTrue(text.indexOf("INV-SECOND") < text.indexOf("INV-THIRD"));
            assertTrue(text.contains("Scope of Engagement of IAs with SIDBI under DIA"));
        }
    }

    @Test
    void persistedStringsArePlainWordTextNotXmlOrHtml() throws Exception {
        var a = DiaDocumentTemplateTest.fixture();
        a.setRecommendationRemarks("<script> & literal {{ia.name}} <w:t>");
        try (var doc = new XWPFDocument(new ByteArrayInputStream(template.render(a, null, List.of(), List.of(), List.of(), List.of())))) {
            assertTrue(doc.getTables().get(3).getRow(73).getCell(2).getText().contains("<script> & literal {{ia.name}} <w:t>"));
        }
    }

    @Test
    @EnabledIfSystemProperty(named = "dia.word.smoke", matches = "true")
    void exportsOriginalAndFilledWordFilesToPdf() throws Exception {
        Path output = Path.of("target", "dia-word-preview");
        Files.createDirectories(output);
        byte[] docx = filled();
        Files.write(output.resolve("dia-original-format-sample.docx"), docx);
        var renderer = new LibreOfficeDocxToPdfRenderer("");
        byte[] pdf = renderer.render(docx);
        assertTrue(pdf.length > 1000);
        Files.write(output.resolve("dia-original-format-sample.pdf"), pdf);
        try (var original = new ClassPathResource(DiaWordTemplate.RESOURCE).getInputStream()) {
            Files.write(output.resolve("original-reference.pdf"), renderer.render(original.readAllBytes()));
        }
    }
}
