package org.emat.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.AnnexureVResponse;
import org.emat.dto.AnnexureVIResponse;
import org.emat.dto.ApiResponse;
import org.emat.dto.ApprovalRequest;
import org.emat.dto.CreateIndustryAssociationAppraisalRequest;
import org.emat.dto.IndustryAssociationAppraisalResponse;
import org.emat.dto.IndustryAssociationRegistrationResponse;
import org.emat.dto.UpdateIndustryAssociationAppraisalRequest;
import org.emat.service.IndustryAssociationAppraisalService;
import org.emat.service.IndustryAssociationRegistrationService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/industry-association-appraisals")
@RequiredArgsConstructor
@Slf4j
public class IndustryAssociationAppraisalController {

    private final IndustryAssociationAppraisalService service;
    private final IndustryAssociationRegistrationService registrationService;

    @PostMapping
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalCreate'))")
    public ResponseEntity<ApiResponse<IndustryAssociationAppraisalResponse>> createAppraisal(
            @RequestBody CreateIndustryAssociationAppraisalRequest request) {
        log.info("Received request to create new Industry Association Appraisal");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.created(
                                "Appraisal created successfully",
                                service.createAppraisal(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalRead'))")
    public ResponseEntity<ApiResponse<IndustryAssociationAppraisalResponse>> getAppraisalById(
            @PathVariable("id") Long id) {
        log.info("Received request to fetch appraisal with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appraisal fetched successfully", service.getAppraisalById(id)));
    }

    @GetMapping("/registration/{registrationId}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalRead'))")
    public ResponseEntity<ApiResponse<IndustryAssociationAppraisalResponse>>
            getAppraisalByRegistrationUId(@PathVariable("registrationId") Long registrationId) {
        log.info("Received request to fetch appraisal for registration ID: {}", registrationId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appraisal fetched successfully",
                        service.getAppraisalByRegistrationId(registrationId)));
    }

    @GetMapping
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalRead'))")
    public ResponseEntity<ApiResponse<List<IndustryAssociationAppraisalResponse>>>
            getAllAppraisals() {
        log.info("Received request to fetch all appraisals");
        return ResponseEntity.ok(
                ApiResponse.success("Appraisals fetched successfully", service.getAllAppraisals()));
    }

    @PutMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalUpdate'))")
    public ResponseEntity<ApiResponse<IndustryAssociationAppraisalResponse>> updateAppraisal(
            @PathVariable("id") Long id,
            @RequestBody UpdateIndustryAssociationAppraisalRequest request) {
        log.info("Received request to update appraisal with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appraisal updated successfully", service.updateAppraisal(id, request)));
    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalApprove'))")
    public ResponseEntity<ApiResponse<IndustryAssociationAppraisalResponse>> approveBySidbe(
            @PathVariable("id") Long id,
            @RequestBody ApprovalRequest approvalRequest,
            Authentication authentication) {
        log.info("Received SIDBE approval request for appraisal with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appraisal approved successfully",
                        service.approveBySidbe(id, approvalRequest, authentication.getName())));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalDelete'))")
    public ResponseEntity<ApiResponse<Void>> deleteAppraisal(@PathVariable("id") Long id) {
        log.info("Received request to permanently delete appraisal with ID: {}", id);
        service.permanentlyDeleteAppraisal(id);
        return ResponseEntity.ok(ApiResponse.success("Appraisal deleted successfully", null));
    }

    @GetMapping("/registration/{registrationId}/dia-report")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalRead'))")
    public ResponseEntity<String> generateDiaReportHtml(@PathVariable Long registrationId)
            throws IOException {
        IndustryAssociationAppraisalResponse appraisal =
                service.getAppraisalByRegistrationId(registrationId);
        IndustryAssociationRegistrationResponse registration =
                registrationService.getRegistrationById(registrationId);
        String template = loadDiaReportTemplate();

        String html = replaceBetweenMarkers(
                template,
                "ANNEXURE_V_ROWS",
                "END_ANNEXURE_V_ROWS",
                buildAnnexureVRows(appraisal.getAnnexureVList()));
        html = replaceBetweenMarkers(
                html,
                "ANNEXURE_VI_ROWS",
                "END_ANNEXURE_VI_ROWS",
                buildAnnexureVIRows(appraisal.getAnnexureVIList()));
        html = populateAppraisalFormatPlaceholders(html, appraisal, registration);
        html = populateSanctionTermsPlaceholders(html, appraisal, registration);

        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(html);
    }

    @GetMapping("/{id}/appraisal-format")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalRead'))")
    public ResponseEntity<String> generateAppraisalFormatHtml(@PathVariable Long id) {
        IndustryAssociationAppraisalResponse appraisal = service.getAppraisalByRegistrationId(id);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(wrapAppraisalFormatDocument(buildAppraisalFormatHtml(appraisal)));
    }

    @Operation(
            summary = "Search Industry Association Appraisals",
            description =
                    "Retrieves Industry Association Appraisals filtered by state, district, and"
                            + " SIDBI approval status.")
    @GetMapping("/search")
    @PreAuthorize(
            "hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalSearch'))")
    public ResponseEntity<ApiResponse<List<IndustryAssociationAppraisalResponse>>> getAppraisals(
            @RequestParam String state,
            @RequestParam String district,
            @RequestParam Boolean isSidbeApproved) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appraisals fetched successfully",
                        service.getAppraisals(state, district, isSidbeApproved)));
    }

    private String loadDiaReportTemplate() throws IOException {
        ClassPathResource resource = new ClassPathResource("template/DIA-report.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    private String replaceBetweenMarkers(
            String template, String startMarker, String endMarker, String replacement) {
        String startToken = "<!-- " + startMarker + " -->";
        String endToken = "<!-- " + endMarker + " -->";
        int startIndex = template.indexOf(startToken);
        int endIndex = template.indexOf(endToken);

        if (startIndex < 0 || endIndex < 0 || endIndex <= startIndex) {
            return template;
        }

        return template.substring(0, startIndex)
                + replacement
                + template.substring(endIndex + endToken.length());
    }

    private String buildAnnexureVRows(List<AnnexureVResponse> annexureVList) {
        if (annexureVList == null || annexureVList.isEmpty()) {
            return "<tr style=\"\"><td colspan=\"4\" style=\"border-top:0.5pt solid #000;border-bottom:0.5pt solid #000;border-left:0.5pt solid #000;border-right:0.5pt solid #000;vertical-align:middle;padding:0pt 5.4pt 0pt 5.4pt\"><p style=\"text-align:justify;margin-top:0pt;margin-bottom:0pt;line-height:1.345;font-size:12pt;font-family:'Aptos', 'Calibri', 'Carlito', 'Arial', sans-serif\"><span style=\"font-family:'Arial', 'Liberation Sans', 'Helvetica', sans-serif;font-size:10pt\">No soft intervention data available.</span></p></td></tr>";
        }

        StringBuilder html = new StringBuilder();
        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal totalSidbiSupport = BigDecimal.ZERO;

        for (AnnexureVResponse item : annexureVList) {
            if (item.getTotalCost() != null) {
                totalCost = totalCost.add(item.getTotalCost());
            }
            if (item.getSidbiSupport() != null) {
                totalSidbiSupport = totalSidbiSupport.add(item.getSidbiSupport());
            }

            html.append("<tr style=\"\">")
                    .append("<td style=\"border-top:0.5pt solid #000;border-bottom:0.5pt solid #000;border-left:0.5pt solid #000;border-right:0.5pt solid #000;vertical-align:top;padding:0pt 5.4pt 0pt 5.4pt\"><p style=\"text-align:justify;margin-top:0pt;margin-bottom:0pt;line-height:1.345;font-size:12pt;font-family:'Aptos', 'Calibri', 'Carlito', 'Arial', sans-serif\"><span style=\"font-family:'Arial', 'Liberation Sans', 'Helvetica', sans-serif;font-size:10pt\">")
                    .append(escapeHtml(item.getSnNo() == null ? "" : item.getSnNo().toString()))
                    .append("</span></p></td>")
                    .append("<td style=\"border-top:0.5pt solid #000;border-bottom:0.5pt solid #000;border-left:0.5pt solid #000;border-right:0.5pt solid #000;vertical-align:middle;padding:0pt 5.4pt 0pt 5.4pt\"><p style=\"text-align:justify;margin-top:0pt;margin-bottom:0pt;line-height:1.345;font-size:12pt;font-family:'Aptos', 'Calibri', 'Carlito', 'Arial', sans-serif\"><span style=\"font-family:'Arial', 'Liberation Sans', 'Helvetica', sans-serif;font-size:10pt\">")
                    .append(escapeHtml(item.getParticulars()))
                    .append("</span></p></td>")
                    .append("<td style=\"border-top:0.5pt solid #000;border-bottom:0.5pt solid #000;border-left:0.5pt solid #000;border-right:0.5pt solid #000;vertical-align:middle;padding:0pt 5.4pt 0pt 5.4pt\"><p style=\"text-align:justify;margin-top:0pt;margin-bottom:0pt;line-height:1.345;font-size:12pt;font-family:'Aptos', 'Calibri', 'Carlito', 'Arial', sans-serif\"><span style=\"font-family:'Arial', 'Liberation Sans', 'Helvetica', sans-serif;font-size:10pt\">")
                    .append(escapeHtml(formatAmount(item.getTotalCost())))
                    .append("</span></p></td>")
                    .append("<td style=\"border-top:0.5pt solid #000;border-bottom:0.5pt solid #000;border-left:0.5pt solid #000;border-right:0.5pt solid #000;vertical-align:middle;padding:0pt 5.4pt 0pt 5.4pt\"><p style=\"text-align:justify;margin-top:0pt;margin-bottom:0pt;line-height:1.345;font-size:12pt;font-family:'Aptos', 'Calibri', 'Carlito', 'Arial', sans-serif\"><span style=\"font-family:'Arial', 'Liberation Sans', 'Helvetica', sans-serif;font-size:10pt\">")
                    .append(escapeHtml(formatAmount(item.getSidbiSupport())))
                    .append("</span></p></td>")
                    .append("</tr>");
        }

        html.append("<tr style=\"\">")
                .append("<td style=\"border-top:0.5pt solid #000;border-bottom:0.5pt solid #000;border-left:0.5pt solid #000;border-right:0.5pt solid #000;vertical-align:top;padding:0pt 5.4pt 0pt 5.4pt\"><p style=\"text-align:justify;margin-top:0pt;margin-bottom:0pt;line-height:1.345;font-size:12pt;font-family:'Aptos', 'Calibri', 'Carlito', 'Arial', sans-serif\">&nbsp;</p></td>")
                .append("<td style=\"border-top:0.5pt solid #000;border-bottom:0.5pt solid #000;border-left:0.5pt solid #000;border-right:0.5pt solid #000;vertical-align:middle;padding:0pt 5.4pt 0pt 5.4pt\"><p style=\"text-align:justify;margin-top:0pt;margin-bottom:0pt;line-height:1.345;font-size:12pt;font-family:'Aptos', 'Calibri', 'Carlito', 'Arial', sans-serif\"><span style=\"font-family:'Arial', 'Liberation Sans', 'Helvetica', sans-serif;font-size:10pt;font-weight:bold\">Total</span></p></td>")
                .append("<td style=\"border-top:0.5pt solid #000;border-bottom:0.5pt solid #000;border-left:0.5pt solid #000;border-right:0.5pt solid #000;vertical-align:middle;padding:0pt 5.4pt 0pt 5.4pt\"><p style=\"text-align:justify;margin-top:0pt;margin-bottom:0pt;line-height:1.345;font-size:12pt;font-family:'Aptos', 'Calibri', 'Carlito', 'Arial', sans-serif\"><span style=\"font-family:'Arial', 'Liberation Sans', 'Helvetica', sans-serif;font-size:10pt;font-weight:bold\">")
                .append(escapeHtml(formatAmount(totalCost)))
                .append("</span></p></td>")
                .append("<td style=\"border-top:0.5pt solid #000;border-bottom:0.5pt solid #000;border-left:0.5pt solid #000;border-right:0.5pt solid #000;vertical-align:middle;padding:0pt 5.4pt 0pt 5.4pt\"><p style=\"text-align:justify;margin-top:0pt;margin-bottom:0pt;line-height:1.345;font-size:12pt;font-family:'Aptos', 'Calibri', 'Carlito', 'Arial', sans-serif\"><span style=\"font-family:'Arial', 'Liberation Sans', 'Helvetica', sans-serif;font-size:10pt;font-weight:bold\">")
                .append(escapeHtml(formatAmount(totalSidbiSupport)))
                .append("</span></p></td>")
                .append("</tr>");

        return html.toString();
    }

    private String buildAnnexureVIRows(List<AnnexureVIResponse> annexureVIList) {
        if (annexureVIList == null || annexureVIList.isEmpty()) {
            return "<tr style=\"\"><td colspan=\"4\" style=\"border-top:0.5pt solid #000;border-bottom:0.5pt solid #000;border-left:0.5pt solid #000;border-right:0.5pt solid #000;vertical-align:middle;padding:0pt 5.4pt 0pt 5.4pt\"><p style=\"text-align:justify;margin-top:0pt;margin-bottom:0pt;line-height:1.345;font-size:12pt;font-family:'Aptos', 'Calibri', 'Carlito', 'Arial', sans-serif\"><span style=\"font-family:'Arial', 'Liberation Sans', 'Helvetica', sans-serif;font-size:10pt\">No CAPEX data available.</span></p></td></tr>";
        }

        StringBuilder html = new StringBuilder();
        for (AnnexureVIResponse item : annexureVIList) {
            html.append("<tr style=\"\">")
                    .append("<td style=\"border-top:0.5pt solid #000;border-bottom:0.5pt solid #000;border-left:0.5pt solid #000;border-right:0.5pt solid #000;vertical-align:top;padding:0pt 5.4pt 0pt 5.4pt\"><p style=\"text-align:justify;margin-top:0pt;margin-bottom:0pt;line-height:1.345;font-size:12pt;font-family:'Aptos', 'Calibri', 'Carlito', 'Arial', sans-serif\"><span style=\"font-family:'Arial', 'Liberation Sans', 'Helvetica', sans-serif;font-size:10pt\">")
                    .append(escapeHtml(item.getIndicativeItem()))
                    .append("</span></p></td>")
                    .append("<td style=\"border-top:0.5pt solid #000;border-bottom:0.5pt solid #000;border-left:0.5pt solid #000;border-right:0.5pt solid #000;vertical-align:top;padding:0pt 5.4pt 0pt 5.4pt\"><p style=\"text-align:center;margin-top:0pt;margin-bottom:0pt;line-height:1.345;font-size:12pt;font-family:'Aptos', 'Calibri', 'Carlito', 'Arial', sans-serif\"><span style=\"font-family:'Arial', 'Liberation Sans', 'Helvetica', sans-serif;font-size:10pt\">")
                    .append(escapeHtml(item.getNumbers() == null ? "" : item.getNumbers().toString()))
                    .append("</span></p></td>")
                    .append("<td style=\"border-top:0.5pt solid #000;border-bottom:0.5pt solid #000;border-left:0.5pt solid #000;border-right:0.5pt solid #000;vertical-align:top;padding:0pt 5.4pt 0pt 5.4pt\"><p style=\"text-align:justify;margin-top:0pt;margin-bottom:0pt;line-height:1.345;font-size:12pt;font-family:'Aptos', 'Calibri', 'Carlito', 'Arial', sans-serif\"><span style=\"font-family:'Arial', 'Liberation Sans', 'Helvetica', sans-serif;font-size:10pt\">")
                    .append(escapeHtml(item.getMake()))
                    .append("</span></p></td>")
                    .append("<td style=\"border-top:0.5pt solid #000;border-bottom:0.5pt solid #000;border-left:0.5pt solid #000;border-right:0.5pt solid #000;vertical-align:top;padding:0pt 5.4pt 0pt 5.4pt\"><p style=\"text-align:justify;margin-top:0pt;margin-bottom:0pt;line-height:1.345;font-size:12pt;font-family:'Aptos', 'Calibri', 'Carlito', 'Arial', sans-serif\"><span style=\"font-family:'Arial', 'Liberation Sans', 'Helvetica', sans-serif;font-size:10pt\">")
                    .append(escapeHtml(formatAmount(item.getMaximumCost())))
                    .append("</span></p></td>")
                    .append("</tr>");
        }

        html.append("<tr style=\"\">")
                .append("<td colspan=\"4\" style=\"border-top:0.5pt solid #000;border-bottom:0.5pt solid #000;border-left:0.5pt solid #000;border-right:0.5pt solid #000;vertical-align:top;padding:0pt 5.4pt 0pt 5.4pt\">")
                .append("<p style=\"text-align:justify;margin-top:0pt;margin-bottom:0pt;line-height:1.345;font-size:12pt;font-family:'Aptos', 'Calibri', 'Carlito', 'Arial', sans-serif\">")
                .append("<span style=\"font-family:'Arial', 'Liberation Sans', 'Helvetica', sans-serif;font-size:10pt\">*</span>")
                .append("<span style=\"font-family:'Arial', 'Liberation Sans', 'Helvetica', sans-serif;font-size:10pt;font-style:italic\">")
                .append(" The above items are indicative in nature. Based on the specific requirements of the IA as identified by PMA/SDE, need based changes in interventions / costs may be considered within the overall budget envisaged for such soft interventions per IAs.")
                .append("</span></p></td></tr>");

        return html.toString();
    }

    private String buildAppraisalFormatHtml(IndustryAssociationAppraisalResponse appraisal) {
        List<FieldValue> fields = extractFieldValues(appraisal);

        StringBuilder html = new StringBuilder();
        html.append("<div style=\"width: 760px; min-height: 1100px; background: #fff; box-sizing: border-box; padding: 28px 24px 20px; margin: 0 auto; border: 1px solid #000; page-break-inside: avoid;\">")
                .append("<div style=\"text-align: right; font-weight: 700; font-size: 16px; margin-bottom: 12px;\">Annexure VIII</div>")
                .append("<div style=\"text-align: center; font-weight: 700; font-size: 18px; margin: 10px 0 18px;\">Appraisal Format</div>")
                .append("<table style=\"border-collapse: collapse; width: 100%; font-size: 13px; table-layout: fixed;\">");

        for (FieldValue field : fields) {
            html.append("<tr>")
                    .append("<th style=\"border:1px solid #000; padding:8px 10px; text-align:left; vertical-align:top; width: 40%; background:#f7f7f7;\">")
                    .append(escapeHtml(field.label()))
                    .append("</th>")
                    .append("<td style=\"border:1px solid #000; padding:8px 10px; vertical-align:top; width: 60%; word-break: break-word;\">")
                    .append(field.value().isEmpty() ? "<span style='color:#666;font-style:italic;'>N/A</span>" : escapeHtml(field.value()))
                    .append("</td>")
                    .append("</tr>");
        }

        html.append("</table></div>");
        return html.toString();
    }

    private String populateAppraisalFormatPlaceholders(
            String html,
            IndustryAssociationAppraisalResponse appraisal,
            IndustryAssociationRegistrationResponse registration) {
        html = putPlaceholderAfterLabel(html, "State", 1, "{{APPRAISAL_STATE}}");
        html = putPlaceholderAfterLabel(
                html,
                "Name of Industry Association (IA)",
                1,
                "{{APPRAISAL_IA_NAME}}");
        html = putPlaceholderAfterLabel(
                html, "Constitution of IA", 1, "{{APPRAISAL_CONSTITUTION}}");
        html = putPlaceholderAfterLabel(
                html, "Year of Incorporation", 1, "{{APPRAISAL_YEAR_OF_INCORP}}");
        html = putPlaceholderAfterLabel(
                html,
                "Type of IA (For Profit /Not for Profit)",
                1,
                "{{APPRAISAL_IA_TYPE}}");
        html = putPlaceholderAfterLabel(
                html, "Proof of Constitution", 1, "{{APPRAISAL_PROOF_OF_CONSTITUTION}}");
        html = putPlaceholderAfterLabel(html, "Address of IA", 1, "{{APPRAISAL_ADDRESS}}");
        html = putPlaceholderAfterLabel(html, "District", 1, "{{APPRAISAL_DISTRICT}}");
        html = putPlaceholderAfterLabel(html, "Pin code", 1, "{{APPRAISAL_PIN_CODE}}");
        html = putPlaceholderAfterLabel(html, "Name", 1, "{{APPRAISAL_APEX_NAME}}");
        html = putPlaceholderAfterLabel(
                html, "Designation", 1, "{{APPRAISAL_APEX_DESIGNATION}}");
        html = putPlaceholderAfterLabel(
                html, "Contact Number", 1, "{{APPRAISAL_APEX_CONTACT}}");
        html = putPlaceholderAfterLabel(html, "Email ID", 1, "{{APPRAISAL_APEX_EMAIL}}");
        html = putPlaceholderAfterLabel(html, "Name", 2, "{{APPRAISAL_NODAL_NAME}}");
        html = putPlaceholderAfterLabel(
                html, "Designation", 2, "{{APPRAISAL_NODAL_DESIGNATION}}");
        html = putPlaceholderAfterLabel(
                html, "Contact Number", 2, "{{APPRAISAL_NODAL_CONTACT}}");
        html = putPlaceholderAfterLabel(html, "Email ID", 2, "{{APPRAISAL_NODAL_EMAIL}}");
        html = putPlaceholderAfterLabel(html, "CIBIL", 1, "{{APPRAISAL_CIBIL}}");
        html = putPlaceholderAfterLabel(
                html, "NABARD Blacklist", 1, "{{APPRAISAL_NABARD_BLACKLIST}}");
        html = putPlaceholderAfterLabel(
                html,
                "Due Diligence report from SMART",
                1,
                "{{APPRAISAL_SMART_DUE_DILIGENCE}}");
        html = putPlaceholderAfterLabel(
                html, "Web search result", 1, "{{APPRAISAL_WEB_SEARCH_RESULT}}");

        return html.replace("{{APPRAISAL_STATE}}", safeValue(registration.getState()))
                .replace(
                        "{{APPRAISAL_IA_NAME}}", safeValue(registration.getIndustryAssociationName()))
                .replace("{{APPRAISAL_CONSTITUTION}}", safeValue(registration.getConstitutionType()))
                .replace(
                        "{{APPRAISAL_YEAR_OF_INCORP}}",
                        safeValue(formatDate(registration.getIncorporationDate())))
                .replace("{{APPRAISAL_IA_TYPE}}", safeValue(registration.getIaType()))
                .replace(
                        "{{APPRAISAL_PROOF_OF_CONSTITUTION}}",
                        safeValue(registration.getConstitutionProof()))
                .replace("{{APPRAISAL_ADDRESS}}", safeValue(registration.getAddress()))
                .replace("{{APPRAISAL_DISTRICT}}", safeValue(registration.getDistrict()))
                .replace("{{APPRAISAL_PIN_CODE}}", safeValue(registration.getPincode()))
                .replace("{{APPRAISAL_APEX_NAME}}", safeValue(registration.getApexHolderName()))
                .replace(
                        "{{APPRAISAL_APEX_DESIGNATION}}",
                        safeValue(registration.getApexHolderDesignation()))
                .replace("{{APPRAISAL_APEX_CONTACT}}", safeValue(registration.getApexHolderMobile()))
                .replace("{{APPRAISAL_APEX_EMAIL}}", safeValue(registration.getApexHolderEmail()))
                .replace("{{APPRAISAL_NODAL_NAME}}", safeValue(registration.getNodalName()))
                .replace(
                        "{{APPRAISAL_NODAL_DESIGNATION}}",
                        safeValue(registration.getNodalDesignation()))
                .replace("{{APPRAISAL_NODAL_CONTACT}}", safeValue(registration.getNodalMobile()))
                .replace("{{APPRAISAL_NODAL_EMAIL}}", safeValue(registration.getNodalEmail()))
                .replace(
                        "{{APPRAISAL_CIBIL}}",
                        safeValue(
                                joinNonBlank(
                                        appraisal.getCibilReportReferenceNo(),
                                        formatDate(appraisal.getCibilReportDate()),
                                        appraisal.getCibilRanking())))
                .replace(
                        "{{APPRAISAL_NABARD_BLACKLIST}}",
                        safeValue(Boolean.TRUE.equals(appraisal.getNabardBlacklisted()) ? "Yes" : "No"))
                .replace(
                        "{{APPRAISAL_SMART_DUE_DILIGENCE}}",
                        safeValue(
                                joinNonBlank(
                                        appraisal.getSmartReportReferenceNo(),
                                        formatDate(appraisal.getSmartReportDate()),
                                        appraisal.getSmartReportRemarks())))
                .replace(
                        "{{APPRAISAL_WEB_SEARCH_RESULT}}",
                        safeValue(appraisal.getWebReport()));
    }

    private String populateSanctionTermsPlaceholders(
            String html,
            IndustryAssociationAppraisalResponse appraisal,
            IndustryAssociationRegistrationResponse registration) {
        String iaName =
                registration != null && registration.getIndustryAssociationName() != null
                        ? registration.getIndustryAssociationName().trim()
                        : (appraisal != null ? safeValue(appraisal.getRegistrationName()) : "");
        String sanctionAmount = formatCurrency(appraisal != null ? appraisal.getBudgetAllocated() : null);
        BigDecimal softTotal = getAnnexureVTotal(appraisal);
        BigDecimal hardTotal = getAnnexureVITotal(appraisal);

        String purpose =
                "For Capacity Building of IA viz., "
                        + iaName
                        + ". The objective of the DIA programme is to focus on the promotion of growth, sustainability and prosperity of IA for its members and stakeholders, to provide business support services and be a representative voice for its members.";
        String scope =
                appraisal != null && appraisal.getProjectLocation() != null && !appraisal.getProjectLocation().isBlank()
                        ? appraisal.getProjectLocation()
                        : (registration != null ? safeValue(registration.getAddress()) : "");

        return html.replace("{{SANCTION_IA_NAME}}", safeValue(iaName))
                .replace("{{SANCTION_SCHEME}}", "Development of Industry Association (DIA)")
                .replace("{{SANCTION_AMOUNT}}", safeValue(sanctionAmount))
                .replace("{{SANCTION_PURPOSE}}", safeValue(purpose))
                .replace("{{SANCTION_SCOPE}}", safeValue(scope))
                .replace(
                        "{{SANCTION_END_HR}}",
                        "Human Resource Support in the form of Engagement of Business Support Executive (BSE) within a budget of ₹"
                                + safeValue(formatCurrency(appraisal != null ? appraisal.getBudgetAllocated() : null))
                                + " towards HR Cost.")
                .replace(
                        "{{SANCTION_END_SOFT}}",
                        "Capacity building of IA members, staff and officials through activities as indicated in Annexure V, within a budget of ₹"
                                + safeValue(formatCurrency(softTotal))
                                + ".")
                .replace(
                        "{{SANCTION_END_HARD}}",
                        "Purchase of CAPEX items as indicated in Annexure VI within a budget of ₹"
                                + safeValue(formatCurrency(hardTotal))
                                + ".");
    }

    private BigDecimal getAnnexureVTotal(IndustryAssociationAppraisalResponse appraisal) {
        if (appraisal == null || appraisal.getAnnexureVList() == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = BigDecimal.ZERO;
        for (AnnexureVResponse item : appraisal.getAnnexureVList()) {
            if (item != null && item.getTotalCost() != null) {
                total = total.add(item.getTotalCost());
            }
        }
        return total;
    }

    private BigDecimal getAnnexureVITotal(IndustryAssociationAppraisalResponse appraisal) {
        if (appraisal == null || appraisal.getAnnexureVIList() == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = BigDecimal.ZERO;
        for (AnnexureVIResponse item : appraisal.getAnnexureVIList()) {
            if (item != null && item.getMaximumCost() != null) {
                total = total.add(item.getMaximumCost());
            }
        }
        return total;
    }

    private String formatCurrency(BigDecimal value) {
        if (value == null) {
            return "0";
        }
        return value.stripTrailingZeros().toPlainString();
    }

    private String putPlaceholderAfterLabel(
            String html, String label, int occurrence, String placeholder) {
            int fromIndex = 0;
            int labelIndex = -1;
            for (int i = 0; i < occurrence; i++) {
                labelIndex = html.indexOf(label, fromIndex);
                if (labelIndex < 0) {
                    return html;
                }
                fromIndex = labelIndex + label.length();
            }

            int labelCellEnd = html.indexOf("</td>", labelIndex);
            if (labelCellEnd < 0) {
                return html;
            }
            int valueCellStart = html.indexOf("<td", labelCellEnd);
            if (valueCellStart < 0) {
                return html;
            }
            int valueCellEnd = html.indexOf("</td>", valueCellStart);
            if (valueCellEnd < 0) {
                return html;
            }

            String valueCell = html.substring(valueCellStart, valueCellEnd + 5);
            String updatedCell =
                    valueCell.replaceFirst(
                            "(?s)<span[^>]*>.*?</span>",
                            "<span style=\"font-family:'Arial', 'Liberation Sans', 'Helvetica', sans-serif;font-size:10pt\">"
                                    + placeholder
                                    + "</span>");
            if (updatedCell.equals(valueCell)) {
                updatedCell =
                        valueCell.replaceFirst(
                                "(?s)(<p[^>]*>).*?(</p>)", "$1" + placeholder + "$2");
            }

            return html.substring(0, valueCellStart) + updatedCell + html.substring(valueCellEnd + 5);
        }

        private String safeValue(String value) {
            if (value == null || value.isBlank()) {
                return "";
            }
            return escapeHtml(value.trim());
        }

        private String formatDate(java.time.LocalDate date) {
            return date == null ? "" : date.toString();
        }

        private String joinNonBlank(String... values) {
            StringBuilder output = new StringBuilder();
            for (String value : values) {
                if (value == null || value.isBlank()) {
                    continue;
                }
                if (!output.isEmpty()) {
                    output.append(' ');
                }
                output.append(value.trim());
            }
            return output.toString();
        }

    private String wrapAppraisalFormatDocument(String bodyHtml) {
        return "<!DOCTYPE html><html lang=\"en\"><head>"
                + "<meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Appraisal Format</title>"
                + "<style>body{font-family:Arial,sans-serif;background:#f2f2f2;margin:0;padding:24px;color:#000;} @media print{body{background:#fff;padding:0;}}</style>"
                + "</head><body>"
                + bodyHtml
                + "</body></html>";
    }

    private List<FieldValue> extractFieldValues(IndustryAssociationAppraisalResponse appraisal) {
        List<FieldValue> values = new ArrayList<>();
        Field[] fields = IndustryAssociationAppraisalResponse.class.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                continue;
            }

            String name = field.getName();
            Object value = readFieldValue(appraisal, field);
            if (value == null) {
                values.add(new FieldValue(toDisplayLabel(name), ""));
                continue;
            }

            values.add(new FieldValue(toDisplayLabel(name), formatFieldValue(value)));
        }

        values.sort(Comparator.comparing(FieldValue::label));
        return values;
    }

    private String toDisplayLabel(String name) {
        StringBuilder label = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (i > 0 && Character.isUpperCase(ch)) {
                label.append(' ');
            }
            label.append(ch);
        }
        return label.toString();
    }

    private Object readFieldValue(IndustryAssociationAppraisalResponse appraisal, Field field) {
        try {
            field.setAccessible(true);
            return field.get(appraisal);
        } catch (IllegalAccessException ignored) {
            return null;
        }
    }

    private String formatFieldValue(Object value) {
        if (value instanceof Iterable<?> iterable) {
            List<String> parts = new ArrayList<>();
            for (Object item : iterable) {
                parts.add(item == null ? "" : String.valueOf(item));
            }
            return String.join(", ", parts);
        }
        if (value instanceof java.time.LocalDate localDate) {
            return localDate.toString();
        }
        if (value instanceof java.time.LocalDateTime localDateTime) {
            return localDateTime.toString();
        }
        if (value instanceof BigDecimal bigDecimal) {
            return bigDecimal.stripTrailingZeros().toPlainString();
        }
        if (value instanceof Boolean bool) {
            return bool ? "Yes" : "No";
        }
        return String.valueOf(value);
    }

    private String formatAmount(BigDecimal amount) {
        if (amount == null) {
            return "0";
        }
        return amount.stripTrailingZeros().toPlainString();
    }

    private String escapeHtml(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private record FieldValue(String label, String value) {}
}
