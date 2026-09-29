package org.emat.service.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.io.ByteArrayInputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.emat.entity.AnnexureV;
import org.emat.entity.AnnexureVI;
import org.emat.entity.DisbursementCapex;
import org.emat.entity.DisbursementNoteCapacityBuildingIa;
import org.emat.entity.DisbursementNoteCapacityBuildingIaOfficials;
import org.emat.entity.IndustryAssociationAppraisal;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.mapper.IndustryAssociationAppraisalMapper;
import org.emat.repository.DisbursementCapexRepository;
import org.emat.repository.DisbursementNoteCapacityBuildingIaOfficialsRepository;
import org.emat.repository.DisbursementNoteCapacityBuildingIaRepository;
import org.emat.repository.IndustryAssociationAppraisalRepository;
import org.emat.repository.ActionPlanRepository;
import org.emat.repository.MonthlySalaryDetailsRepository;
import org.emat.service.DocxToPdfRenderer;
import org.emat.service.StageService;
import org.emat.util.CommonUtil;
import org.emat.validator.IndustryAssociationAppraisalValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IndustryAssociationAppraisalServiceImplTest {

    @Mock private IndustryAssociationAppraisalRepository appraisalRepository;
    @Mock private DisbursementCapexRepository disbursementCapexRepository;
    @Mock private DisbursementNoteCapacityBuildingIaRepository disbursementIaRepository;
    @Mock
    private DisbursementNoteCapacityBuildingIaOfficialsRepository disbursementIaOfficialsRepository;
    @Mock private IndustryAssociationAppraisalMapper appraisalMapper;
    @Mock private IndustryAssociationAppraisalValidator appraisalValidator;
    @Mock private StageService stageService;
    @Mock private DocxToPdfRenderer docxToPdfRenderer;
    @Mock private CommonUtil commonUtil;
    @Mock private DiaDocumentDataService diaDocumentDataService;
    @Mock private ActionPlanRepository actionPlanRepository;
    @Mock private MonthlySalaryDetailsRepository salaryRepository;

    @InjectMocks private IndustryAssociationAppraisalServiceImpl service;

    @Test
    void generateDiaDocumentPdf_fillsOriginalWordAndReturnsPdfBytes() throws Exception {
        IndustryAssociationRegistration registration = IndustryAssociationRegistration.builder()
                .id(42L)
                .industryAssociationName("Test IA")
                .state("KA")
                .district("Bengaluru")
                .build();

        IndustryAssociationAppraisal appraisal = IndustryAssociationAppraisal.builder()
                .id(100L)
                .registration(registration)
                .cibilRanking("A")
                .budgetAllocated(new BigDecimal("123.45"))
                .dopDate(LocalDate.of(2026, 9, 27))
                .annexureVList(List.of(AnnexureV.builder().snNo(1).particulars("Training").build()))
                .annexureVIList(List.of(AnnexureVI.builder().section("A").indicativeItem("Projector").build()))
                .termsAndConditions(List.of("Condition 1"))
                .build();

        DisbursementCapex capex = DisbursementCapex.builder()
                .id(900L)
                .disbursementSought(new BigDecimal("5500"))
                .build();

        DisbursementNoteCapacityBuildingIa iaNote =
                DisbursementNoteCapacityBuildingIa.builder().id(901L).industryAssociationName("Test IA").build();

        DisbursementNoteCapacityBuildingIaOfficials officialNote =
                DisbursementNoteCapacityBuildingIaOfficials.builder()
                        .id(902L)
                        .eventManagementAgencyName("Agency A")
                        .build();

        byte[] expected = "pdf".getBytes();

        when(appraisalValidator.getAppraisalOrThrow(100L)).thenReturn(appraisal);
        when(disbursementCapexRepository.findByRegistrationId(42L)).thenReturn(Optional.of(capex));
        when(disbursementIaRepository.findByRegistrationId(42L)).thenReturn(List.of(iaNote));
        when(disbursementIaOfficialsRepository.findByRegistrationId(42L))
                .thenReturn(List.of(officialNote));
        var dataService = new DiaDocumentDataService(appraisalValidator, disbursementCapexRepository,
                disbursementIaRepository, disbursementIaOfficialsRepository,
                actionPlanRepository, salaryRepository, new DiaWordTemplate());
        byte[] renderedDocx = dataService.docxByAppraisalId(100L);
        when(diaDocumentDataService.docxByAppraisalId(100L)).thenReturn(renderedDocx);
        when(docxToPdfRenderer.render(any())).thenReturn(expected);

        byte[] result = service.generateDiaDocumentPdf(100L);

        assertArrayEquals(expected, result);

        ArgumentCaptor<byte[]> docxCaptor = ArgumentCaptor.forClass(byte[].class);
        verify(docxToPdfRenderer).render(docxCaptor.capture());
        try (var doc = new XWPFDocument(new ByteArrayInputStream(docxCaptor.getValue()));
                var extractor = new XWPFWordExtractor(doc)) {
            String text = extractor.getText();
            assertTrue(text.contains("Test IA"));
            assertTrue(text.contains("Annexure V"));
            assertTrue(text.contains("Annexure VI"));
            assertTrue(text.contains("Agency A"));
            assertTrue(text.contains("MEMORANDUM OF UNDERSTANDING"));
        }
    }
}


