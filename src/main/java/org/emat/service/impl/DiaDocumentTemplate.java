package org.emat.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.emat.entity.*;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

/** Explicit document schema: never inspect fields or stringify JPA entities/proxies. */
@Component
public class DiaDocumentTemplate {
    private static final String TEMPLATE = "template/DIA_Document_Data_Bound.html";
    private static final Pattern TOKEN = Pattern.compile("\\{\\{([a-zA-Z0-9.]+)}}");
    private static final String MISSING = "Not recorded";

    public String render(
            IndustryAssociationAppraisal a, DisbursementCapex capex,
            List<DisbursementNoteCapacityBuildingIa> members,
            List<DisbursementNoteCapacityBuildingIaOfficials> officials,
            List<ActionPlan> plans, List<MonthlySalaryDetails> salaries) {
        IndustryAssociationRegistration r = Objects.requireNonNull(a.getRegistration());
        Map<String, String> values = new HashMap<>();
        values.put("ia.name", text(r.getIndustryAssociationName()));
        values.put("ia.address", text(r.getAddress()));
        values.put("appraisal.id", text(a.getId()));
        values.put("registration.id", text(r.getId()));
        values.put("annexureV.rows", softInterventions(a.getAnnexureVList()));
        values.put("annexureVI.rows", capexItems(a.getAnnexureVIList()));
        values.put("registration.rows", fields(r, null, """
                State|state
                Name of industry association|industryAssociationName
                Constitution of IA|constitutionType
                Other constitution details|constitutionOther
                Incorporation date|incorporationDate
                Incorporation certificate reference|incorporationCertificate
                Type of IA|iaType
                Proof of constitution reference|constitutionProof
                Address|address
                District|district
                PIN code|pincode
                Email|email
                PAN|panNo
                Assigned SDE|sde
                Basis of selection|selectionCriteria
                """));
        values.put("contacts.rows", fields(a, r, """
                Apex office holder name|apexHolderName
                Designation|apexHolderDesignation
                Contact number|apexHolderMobile
                Email ID|apexHolderEmail
                Address proof type|addressProofType
                Address proof reference|addressProof
                ID proof type|idProofType
                ID proof reference|idProof
                Nodal person name|nodalName
                Nodal designation|nodalDesignation
                Nodal contact number|nodalMobile
                Nodal email ID|nodalEmail
                """));
        values.put("diligence.rows", fields(a, null, """
                CIBIL report reference|cibilReportReferenceNo
                CIBIL report date|cibilReportDate
                CIBIL ranking|cibilRanking
                CIBIL remarks|cibilRemarks
                NGO DARPAN number|ngoDarpanNumber
                NGO DARPAN file reference|ngoDarpanFile
                NABARD blacklisted|nabardBlacklisted
                NABARD blacklist file reference|nabardBlacklistFile
                SMART report available|smartReportAvailable
                SMART report reference|smartReportReferenceNo
                SMART report date|smartReportDate
                SMART report remarks|smartReportRemarks
                Web search verified|webSearchVerified
                Web search document reference|webSearchDocument
                """));
        values.put("owners.rows", fields(a, null, """
                Office holder CIBIL reference|holderCibilReferenceNo
                Office holder CIBIL date|holderCibilDate
                Office holder CIBIL score|holderCibilScore
                Office holder CIBIL remarks|holderCibilRemarks
                Office holder CIBIL file reference|holderCibilFile
                Office holder SMART available|holderSmartAvailable
                Office holder SMART date|holderSmartDate
                Office holder SMART remarks|holderSmartRemarks
                Beneficial owner CIBIL reference|beneficialOwnerCibilReferenceNo
                Beneficial owner CIBIL date|beneficialOwnerCibilDate
                Beneficial owner CIBIL ranking|beneficialOwnerCibilRanking
                Beneficial owner CIBIL remarks|beneficialOwnerCibilRemarks
                Beneficial owner CIBIL file reference|beneficialOwnerCibilFile
                Beneficial owner SMART available|beneficialOwnerSmartAvailable
                Beneficial owner SMART date|beneficialOwnerSmartDate
                Beneficial owner SMART remarks|beneficialOwnerSmartRemarks
                """));
        values.put("infrastructure.rows", fields(a, r, """
                SIDBI branch|sidbiBranch
                Mapped to identified cluster|mappedWithCluster
                Cluster name|clusterName
                Mapped to important district|mappedWithImportantDistrict
                District MSME count|districtMsmeCount
                MSME count without traders|msmeCountWithoutTraders
                Active members above 200|activeMembersAbove200
                Active member count|activeMembersCount
                Justification for selection|justification
                Approval letter reference|approvalLetter
                Member directory available|memberDirectoryAvailable
                Building type|buildingType
                Declaration signed|declarationSigned
                Electricity bill reference|electricityBill
                Telephone bill reference|telephoneBill
                IT infrastructure available|itInfrastructureAvailable
                Infrastructure details|infrastructureType
                Secretariat staff available|secretariatStaffAvailable
                Website available|websiteAvailable
                Website URL|websiteUrl
                Paid services available|paidServicesAvailable
                Paid services details|paidServicesDetails
                Adverse remarks available|adverseRemarksAvailable
                Adverse remarks|adverseRemarks
                Web report reference|webReport
                """) + fields(r, null, "SIDBI branch name|sidbiBranchName\nSecretariat staff details|secretariatStaff"));
        values.put("readiness.rows", fields(a, null, """
                Major sources of income|majorSourcesOfIncome
                Activities last year|activitiesLastYear
                Formalization comments|formalizationComments
                Referral arrangement ready|referralArrangementReady
                Referral arrangement comments|referralArrangementComments
                BSE readiness|bseReadinessReady
                BSE readiness comments|bseReadinessComments
                Scope for financing|financingScope
                Financing scope (crore)|financingScopeCrore
                Project location|projectLocation
                """) + fields(a, r, "Willingness comments|willingnessComments\nWorked with SIDBI before|workedWithSidbiBefore"));
        values.put("grants.rows", fields(a, null, """
                Proposed salary grant (stored units)|grantProposedSalary
                Proposed CAPEX grant (stored units)|grantProposedCapex
                Proposed capacity-building grant (stored units)|grantProposedCapacityBuilding
                """) + fields(a, r, """
                Proposed grant details|grantDetails
                Envisaged output|envisagedOutput
                Envisaged outcome|envisagedOutcome
                """) + fields(r, null, "Proposed overall grant (stored units)|grantProposed\nEnvisaged impact|envisagedImpact"));
        values.put("budget.rows", fields(a, null, """
                Cluster expert comments|clusterExpertComments
                Financial year (stored date)|financialYear
                Budget allocated (stored units)|budgetAllocated
                Utilized amount (stored units)|utilizedAmount
                Available budget (stored units)|availableBudget
                DoP date (as recorded)|dopDate
                Recommendation|recommendation
                Recommendation remarks|recommendationRemarks
                SIDBI approval recorded|isSidbeApproved
                """));
        values.put("sectors.rows", sectors(a.getSectors()));
        values.put("terms.items", terms(a.getTermsAndConditions()));
        values.put("capex.rows", fields(capex, null, """
                GSTIN of IA|gstinIa
                GSTIN not applicable|gstinNotApplicable
                Reason for no GSTIN|gstinNotApplicableReason
                GSTIN of SIDBI|gstinSidbi
                Sanctioned amount (Rs.)|sanctionedAmount
                Disbursed till date (Rs.)|disbursedTillDate
                Disbursement sought (Rs.)|disbursementSought
                Invoice date|invoiceDate
                Invoice number|invoiceNumber
                Details of items|detailsOfItems
                Value of services/items (Rs.)|valueOfServiceItems
                IGST amount (Rs.)|igstAmount
                Total amount (Rs.)|totalAmount
                TDS applicable|tdsApplicable
                Reason for TDS not applicable|tdsNotApplicableReason
                Amount recommended (Rs.)|amountRecommendedForDisbursement
                Account code|accountCode
                GT CAPEX verification comments|gtCapexVerificationComments
                Pre-disbursement compliance|preDisbursementCompliance
                Recommendation|recommendation
                """));
        values.put("members.sections", records(members, n -> fields(n, null,
                "Industry association name|industryAssociationName\nGSTIN of IA|gstinOfIa\n" + CAPACITY_FIELDS)));
        values.put("officials.sections", records(officials, n -> fields(n, null,
                "Event management agency|eventManagementAgencyName\nGSTIN of agency|gstinOfAgency\n" + CAPACITY_FIELDS)));
        values.put("actionPlans.sections", records(plans, p -> fields(p, null,
                "State|state\nMaker status|makerStatus\nChecker status|checkerStatus\nApproved date|approvedDate\nRemarks|remark")
                + records(safe(p.getActivities()).stream().sorted(Comparator.comparing(
                        ActionPlanActivity::getActivityNo, Comparator.nullsLast(Comparator.naturalOrder()))).toList(), activity -> fields(activity, null, """
                        Activity number|activityNo
                        Name of activity|nameOfActivity
                        Planned month|monthToBeHeld
                        Technical service provider|technicalServiceProvider
                        Total cost (Rs.)|totalCost
                        SIDBI support (%)|percentSupportBySidbi
                        Support by others (%)|percentSupportByOthers
                        IA contribution (%)|percentContributionByIa
                        Expected participant members|expectedParticipantMembers
                        Expected participant non-members|expectedParticipantNonMembers
                        Expected output|expectedOutput
                        Expected outcome|expectedOutcome
                        Expected income-generating activity|expectedIncomeGeneratingActivity
                        """))));
        values.put("salaries.sections", records(salaries, s ->
                fields(s.getBse(), null, "BSE name|bseName")
                + fields(s.getBseSalary(), null, "Salary invoice reference|invoiceNumber\nInvoice date|invoiceDate\nSalary status|status")
                + fields(s, null, """
                        Salary month|salaryMonth
                        Monthly salary (Rs.)|monthlySalary
                        Salary days|salaryDays
                        Paid days|paidDays
                        Additional amount (Rs.)|additionalAmount
                        Reason for additional amount|additionalAmountReason
                        Payment to BSE (Rs.)|paymentToBse
                        GT attendance comments|gtAttendanceComments
                        GT additional payment comments|gtAdditionalComments
                        """)));
        return merge(values);
    }

    private static final String CAPACITY_FIELDS = """
            Reason for GSTIN not applicable|gstinNotApplicableReason
            GSTIN of SIDBI|gstinOfSidbi
            Sanctioned amount (Rs.)|sanctionedAmount
            Disbursed till date (Rs.)|disbursedTillDate
            Disbursement sought (Rs.)|disbursementSought
            Nature of payment|natureOfPayment
            Invoice date|invoiceDate
            Invoice number|invoiceNumber
            Value of services/items (Rs.)|valueOfServiceItemsSupplied
            IGST at 18 percent (Rs.)|igstAt18Percent
            Total amount (Rs.)|totalAmount
            TDS applicable|tdsApplicable
            Reason for TDS not applicable|tdsNotApplicableReason
            Amount recommended (Rs.)|amountRecommendedForDisbursement
            Account code|accountCodeForPayment
            GT event outcome and impact comments|gtCommentsOnEventOutcomeImpact
            Pre-disbursement compliance|compliancePreDisbursementTerms
            Recommendation|recommendation
            Status|status
            Remark|remark
            Approved date|approvedDate
            """;

    private String fields(Object primary, Object fallback, String schema) {
        if (primary == null && fallback == null) return empty();
        BeanWrapper source = primary == null ? null : PropertyAccessorFactory.forBeanPropertyAccess(primary);
        BeanWrapper backup = fallback == null ? null : PropertyAccessorFactory.forBeanPropertyAccess(fallback);
        StringBuilder html = new StringBuilder("<table class=\"fields\"><tbody>");
        schema.lines().filter(line -> !line.isBlank()).forEach(line -> {
            String[] field = line.split("\\|", 2);
            Object value = source == null ? null : source.getPropertyValue(field[1]);
            if (missing(value) && backup != null) value = backup.getPropertyValue(field[1]);
            html.append("<tr><th scope=\"row\">").append(text(field[0]))
                    .append("</th><td>").append(text(value)).append("</td></tr>");
        });
        return html.append("</tbody></table>").toString();
    }

    private String softInterventions(List<AnnexureV> entries) {
        List<AnnexureV> rows = safe(entries).stream()
                .sorted(Comparator.comparing(AnnexureV::getSnNo, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(AnnexureV::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
        if (rows.isEmpty()) return emptyRow(4, "No Annexure V items recorded for this appraisal.");
        StringBuilder html = new StringBuilder();
        rows.forEach(row -> html.append(row(row.getSnNo(), row.getParticulars(), row.getTotalCost(), row.getSidbiSupport())));
        html.append("<tr class=\"subtotal\"><td colspan=\"2\">Total (complete columns only)</td><td class=\"number\">")
                .append(total(rows, AnnexureV::getTotalCost)).append("</td><td class=\"number\">")
                .append(total(rows, AnnexureV::getSidbiSupport)).append("</td></tr>");
        return html.toString();
    }

    private <T> String total(List<T> rows, Function<T, BigDecimal> amount) {
        if (rows.stream().anyMatch(item -> amount.apply(item) == null)) return "Incomplete — amount missing";
        return text(rows.stream().map(amount).reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private String capexItems(List<AnnexureVI> entries) {
        List<AnnexureVI> rows = safe(entries).stream()
                .sorted(Comparator.comparing(AnnexureVI::getSection, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(AnnexureVI::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
        if (rows.isEmpty()) return emptyRow(6, "No Annexure VI items recorded for this appraisal.");
        StringBuilder html = new StringBuilder();
        rows.forEach(item -> html.append(row(
                (missing(item.getSection()) ? MISSING : item.getSection())
                        + (missing(item.getSectionNote()) ? "" : "\n" + item.getSectionNote()),
                item.getIndicativeItem(), item.getNumbers(), item.getMake(), item.getMaximumCost(), item.getMaximumCostUnit())));
        return html.toString();
    }

    private String sectors(List<IndustryAssociationAppraisal.SectorDetail> sectors) {
        if (safe(sectors).isEmpty()) return emptyRow(2, "No sectors recorded.");
        StringBuilder html = new StringBuilder();
        safe(sectors).forEach(s -> html.append(row(s.getSector(), s.getSectorKeyProblems())));
        return html.toString();
    }

    private String terms(List<String> terms) {
        if (safe(terms).isEmpty()) return empty();
        StringBuilder html = new StringBuilder("<ol>");
        safe(terms).forEach(term -> html.append("<li>").append(text(term)).append("</li>"));
        return html.append("</ol>").toString();
    }

    private <T> String records(List<T> entries, Function<T, String> render) {
        if (safe(entries).isEmpty()) return empty();
        StringBuilder html = new StringBuilder();
        int index = 1;
        for (T entry : safe(entries)) {
            html.append("<h3>Record ").append(index++).append("</h3>").append(render.apply(entry));
        }
        return html.toString();
    }

    private static <T> List<T> safe(List<T> entries) {
        return entries == null ? List.of() : entries.stream().filter(Objects::nonNull)
                .filter(item -> !(item instanceof BaseEntity base) || !Boolean.FALSE.equals(base.getIsActive())).toList();
    }

    private String row(Object... cells) {
        StringBuilder html = new StringBuilder("<tr>");
        for (Object cell : cells) html.append(cell instanceof Number ? "<td class=\"number\">" : "<td>").append(text(cell)).append("</td>");
        return html.append("</tr>").toString();
    }

    private String emptyRow(int columns, String message) {
        return "<tr><td class=\"empty\" colspan=\"" + columns + "\">" + text(message) + "</td></tr>";
    }

    private String empty() { return "<p class=\"empty\">No records saved for this IA / appraisal.</p>"; }

    private static boolean missing(Object value) {
        return value == null || value instanceof String s && s.isBlank();
    }

    private String text(Object value) {
        if (missing(value)) return MISSING;
        String result;
        if (value instanceof Boolean b) result = b ? "Yes" : "No";
        else if (value instanceof BigDecimal d) result = d.setScale(2, RoundingMode.HALF_UP).toPlainString();
        else if (value instanceof LocalDate d) result = d.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        else if (value instanceof LocalDateTime d) result = d.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        else if (value instanceof String || value instanceof Number || value instanceof Enum<?>) result = value.toString();
        else throw new IllegalArgumentException("Non-scalar value in DIA field mapping");
        return HtmlUtils.htmlEscape(result);
    }

    private String merge(Map<String, String> values) {
        try (var input = new ClassPathResource(TEMPLATE).getInputStream()) {
            Matcher matcher = TOKEN.matcher(new String(input.readAllBytes(), StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();
            while (matcher.find()) {
                String value = values.get(matcher.group(1));
                if (value == null) throw new IllegalStateException("Unmapped DIA placeholder: " + matcher.group(1));
                matcher.appendReplacement(result, Matcher.quoteReplacement(value));
            }
            matcher.appendTail(result);
            return result.toString();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load DIA data-bound template", e);
        }
    }
}

