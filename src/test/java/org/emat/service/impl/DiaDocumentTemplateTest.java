//package org.emat.service.impl;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//import org.emat.entity.*;
//import org.junit.jupiter.api.Test;
//
//class DiaDocumentTemplateTest {
//    private final DiaDocumentTemplate template = new DiaDocumentTemplate();
//
//    static IndustryAssociationAppraisal fixture() {
//        var registration = IndustryAssociationRegistration.builder().id(42L)
//                .industryAssociationName("DEMO IA — fixture data only").state("Karnataka")
//                .district("Bengaluru").address("Demo address")
//                .nodalName("Registration nodal contact").websiteAvailable(true)
//                .activeMembersCount(300).envisagedImpact("More MSMEs served").build();
//        return IndustryAssociationAppraisal.builder().id(221L).registration(registration)
//                .apexHolderName("Appraisal office holder").websiteAvailable(false).activeMembersCount(0)
//                .cibilRanking("A").cibilReportDate(LocalDate.of(2026, 9, 27))
//                .sectors(List.of(new IndustryAssociationAppraisal.SectorDetail("Textiles", "Credit access and skilling")))
//                .termsAndConditions(List.of("Verify bills before reimbursement"))
//                .annexureVList(List.of(
//                        AnnexureV.builder().id(2L).snNo(2).particulars("Skilling workshop")
//                                .totalCost(new BigDecimal("20000.50")).sidbiSupport(new BigDecimal("10000.25")).build(),
//                        AnnexureV.builder().id(1L).snNo(1).particulars("Member directory")
//                                .totalCost(new BigDecimal("10000.50")).sidbiSupport(new BigDecimal("5000.25")).build()))
//                .annexureVIList(List.of(AnnexureVI.builder().id(3L).section("A. Hard interventions")
//                        .sectionNote("Approved equipment list").indicativeItem("Laptop").numbers(2)
//                        .make("Demo make").maximumCost(new BigDecimal("50000")).maximumCostUnit("each").build()))
//                .build();
//    }
//
//    private String render(IndustryAssociationAppraisal a) {
//        return template.render(a, null, List.of(), List.of(), List.of(), List.of());
//    }
//
//    @Test
//    void populatesAnnexuresAndSectorsWithoutScannedImagesOrProxyInternals() {
//        String html = render(fixture());
//        for (String value : List.of("Member directory", "Skilling workshop", "30001.00", "15000.50",
//                "Laptop", "Demo make", "50000.00", "each", "Approved equipment list",
//                "Textiles", "Credit access and skilling", "27-09-2026")) assertTrue(html.contains(value), value);
//        assertTrue(html.indexOf("Member directory") < html.indexOf("Skilling workshop"));
//        assertFalse(html.contains("data:image"));
//        assertFalse(html.contains("{{"));
//        assertFalse(html.contains("hibernate"));
//        assertFalse(html.contains("SectorDetail"));
//    }
//
//    @Test
//    void fallsBackOnlyForMissingProfileValuesAndPreservesFalseAndZero() {
//        var appraisal = fixture();
//        appraisal.setNodalName(" ");
//        String html = render(appraisal);
//        assertTrue(html.contains("Registration nodal contact"));
//        assertTrue(html.contains("Website available</th><td>No</td>"));
//        assertTrue(html.contains("Active member count</th><td>0</td>"));
//        assertTrue(html.contains("More MSMEs served"));
//        assertTrue(html.contains("Appraisal office holder"));
//    }
//
//    @Test
//    void getterBackedRegistrationWorksWhenBackingFieldsAreEmpty() {
//        var registration = mock(IndustryAssociationRegistration.class);
//        when(registration.getId()).thenReturn(42L);
//        when(registration.getIndustryAssociationName()).thenReturn("Getter-backed IA");
//        when(registration.getState()).thenReturn("Getter-backed state");
//        when(registration.getNodalName()).thenReturn("Getter-backed nodal contact");
//        var appraisal = IndustryAssociationAppraisal.builder().id(221L).registration(registration).build();
//        String html = render(appraisal);
//        assertTrue(html.contains("Getter-backed IA"));
//        assertTrue(html.contains("Getter-backed state"));
//        assertTrue(html.contains("Getter-backed nodal contact"));
//        verify(registration).getState();
//    }
//
//    @Test
//    void escapesDataWithoutRecursivelyInterpretingItAsTemplateMarkup() {
//        var appraisal = fixture();
//        appraisal.setRecommendationRemarks("<script>alert(1)</script> & {{ia.name}}");
//        String html = render(appraisal);
//        assertTrue(html.contains("&lt;script&gt;alert(1)&lt;/script&gt; &amp; {{ia.name}}"));
//        assertFalse(html.contains("<script>"));
//    }
//
//    @Test
//    void missingAnnexuresAreExplicitAndIncompleteTotalsAreNotInvented() {
//        var appraisal = fixture();
//        appraisal.setAnnexureVList(null);
//        appraisal.setAnnexureVIList(List.of());
//        String html = render(appraisal);
//        assertTrue(html.contains("No Annexure V items recorded for this appraisal."));
//        assertTrue(html.contains("No Annexure VI items recorded for this appraisal."));
//        appraisal.setAnnexureVList(List.of(AnnexureV.builder().snNo(1).particulars("Uncosted activity").build()));
//        assertTrue(render(appraisal).contains("Incomplete — amount missing"));
//    }
//
//    @Test
//    void allRemainingSectionSchemasResolveRealEntityGetters() {
//        var activity = ActionPlanActivity.builder().nameOfActivity("Planned digital training")
//                .expectedOutput("Expected directory").build();
//        var plan = ActionPlan.builder().activities(List.of(activity)).build();
//        var salary = new MonthlySalaryDetails();
//        salary.setBse(IndustryAssociationBseRecommendation.builder().bseName("Demo BSE").build());
//        salary.setBseSalary(new BseSalary());
//        salary.setPaymentToBse(new BigDecimal("12500"));
//        String html = template.render(fixture(), new DisbursementCapex(),
//                List.of(DisbursementNoteCapacityBuildingIa.builder().natureOfPayment("Member training").build()),
//                List.of(DisbursementNoteCapacityBuildingIaOfficials.builder().eventManagementAgencyName("Demo agency").build()),
//                List.of(plan), List.of(salary));
//        for (String value : List.of("Planned digital training", "Expected directory", "Demo BSE", "12500.00",
//                "Member training", "Demo agency")) assertTrue(html.contains(value), value);
//    }
//
//    @Test
//    void omitsExplicitlyInactiveAnnexureRows() {
//        var appraisal = fixture();
//        appraisal.setAnnexureVList(List.of(AnnexureV.builder().particulars("DELETED ITEM").isActive(false).build()));
//        assertFalse(render(appraisal).contains("DELETED ITEM"));
//    }
//}
