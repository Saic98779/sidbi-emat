package org.emat.mapper;

import org.emat.dto.CreateDisbursementNoteCapacityBuildingIaRequest;
import org.emat.dto.DisbursementNoteCapacityBuildingIaResponse;
import org.emat.dto.UpdateDisbursementNoteCapacityBuildingIaRequest;
import org.emat.entity.DisbursementNoteCapacityBuildingIa;
import org.emat.entity.IndustryAssociationRegistration;
import org.springframework.stereotype.Component;

@Component
public class DisbursementNoteCapacityBuildingIaMapper {

    public DisbursementNoteCapacityBuildingIa toEntity(
            CreateDisbursementNoteCapacityBuildingIaRequest request,
            IndustryAssociationRegistration registration) {
        return DisbursementNoteCapacityBuildingIa.builder()
                .registration(registration)
                .industryAssociationName(request.getIndustryAssociationName())
                .gstinOfIa(request.getGstinOfIa())
                .gstinNotApplicableReason(request.getGstinNotApplicableReason())
                .gstinOfSidbi(request.getGstinOfSidbi())
                .sanctionedAmount(request.getSanctionedAmount())
                .disbursedTillDate(request.getDisbursedTillDate())
                .disbursementSought(request.getDisbursementSought())
                .natureOfPayment(request.getNatureOfPayment())
                .invoiceDate(request.getInvoiceDate())
                .invoiceNumber(request.getInvoiceNumber())
                .valueOfServiceItemsSupplied(request.getValueOfServiceItemsSupplied())
                .igstAt18Percent(request.getIgstAt18Percent())
                .totalAmount(request.getTotalAmount())
                .tdsApplicable(request.getTdsApplicable())
                .tdsNotApplicableReason(request.getTdsNotApplicableReason())
                .amountRecommendedForDisbursement(request.getAmountRecommendedForDisbursement())
                .accountCodeForPayment(request.getAccountCodeForPayment())
                .gtCommentsOnEventOutcomeImpact(request.getGtCommentsOnEventOutcomeImpact())
                .compliancePreDisbursementTerms(request.getCompliancePreDisbursementTerms())
                .recommendation(request.getRecommendation())
                .isActive(true)
                .build();
    }

    public void applyUpdateRequest(
            DisbursementNoteCapacityBuildingIa note,
            UpdateDisbursementNoteCapacityBuildingIaRequest request) {
        if (request.getIndustryAssociationName() != null)
            note.setIndustryAssociationName(request.getIndustryAssociationName());
        if (request.getGstinOfIa() != null) note.setGstinOfIa(request.getGstinOfIa());
        if (request.getGstinNotApplicableReason() != null)
            note.setGstinNotApplicableReason(request.getGstinNotApplicableReason());
        if (request.getGstinOfSidbi() != null) note.setGstinOfSidbi(request.getGstinOfSidbi());
        if (request.getSanctionedAmount() != null)
            note.setSanctionedAmount(request.getSanctionedAmount());
        if (request.getDisbursedTillDate() != null)
            note.setDisbursedTillDate(request.getDisbursedTillDate());
        if (request.getDisbursementSought() != null)
            note.setDisbursementSought(request.getDisbursementSought());
        if (request.getNatureOfPayment() != null)
            note.setNatureOfPayment(request.getNatureOfPayment());
        if (request.getInvoiceDate() != null) note.setInvoiceDate(request.getInvoiceDate());
        if (request.getInvoiceNumber() != null) note.setInvoiceNumber(request.getInvoiceNumber());
        if (request.getValueOfServiceItemsSupplied() != null)
            note.setValueOfServiceItemsSupplied(request.getValueOfServiceItemsSupplied());
        if (request.getIgstAt18Percent() != null)
            note.setIgstAt18Percent(request.getIgstAt18Percent());
        if (request.getTotalAmount() != null) note.setTotalAmount(request.getTotalAmount());
        if (request.getTdsApplicable() != null) note.setTdsApplicable(request.getTdsApplicable());
        if (request.getTdsNotApplicableReason() != null)
            note.setTdsNotApplicableReason(request.getTdsNotApplicableReason());
        if (request.getAmountRecommendedForDisbursement() != null)
            note.setAmountRecommendedForDisbursement(
                    request.getAmountRecommendedForDisbursement());
        if (request.getAccountCodeForPayment() != null)
            note.setAccountCodeForPayment(request.getAccountCodeForPayment());
        if (request.getGtCommentsOnEventOutcomeImpact() != null)
            note.setGtCommentsOnEventOutcomeImpact(
                    request.getGtCommentsOnEventOutcomeImpact());
        if (request.getCompliancePreDisbursementTerms() != null)
            note.setCompliancePreDisbursementTerms(
                    request.getCompliancePreDisbursementTerms());
        if (request.getRecommendation() != null)
            note.setRecommendation(request.getRecommendation());
    }

    public DisbursementNoteCapacityBuildingIaResponse toResponse(
            DisbursementNoteCapacityBuildingIa note) {
        return DisbursementNoteCapacityBuildingIaResponse.builder()
                .id(note.getId())
                .registrationId(note.getRegistration() != null ? note.getRegistration().getId() : null)
                .registrationName(
                        note.getRegistration() != null
                                ? note.getRegistration().getIndustryAssociationName()
                                : null)
                .industryAssociationName(note.getIndustryAssociationName())
                .gstinOfIa(note.getGstinOfIa())
                .gstinNotApplicableReason(note.getGstinNotApplicableReason())
                .gstinOfSidbi(note.getGstinOfSidbi())
                .sanctionedAmount(note.getSanctionedAmount())
                .disbursedTillDate(note.getDisbursedTillDate())
                .disbursementSought(note.getDisbursementSought())
                .natureOfPayment(note.getNatureOfPayment())
                .invoiceDate(note.getInvoiceDate())
                .invoiceNumber(note.getInvoiceNumber())
                .valueOfServiceItemsSupplied(note.getValueOfServiceItemsSupplied())
                .igstAt18Percent(note.getIgstAt18Percent())
                .totalAmount(note.getTotalAmount())
                .tdsApplicable(note.getTdsApplicable())
                .tdsNotApplicableReason(note.getTdsNotApplicableReason())
                .amountRecommendedForDisbursement(note.getAmountRecommendedForDisbursement())
                .accountCodeForPayment(note.getAccountCodeForPayment())
                .gtCommentsOnEventOutcomeImpact(note.getGtCommentsOnEventOutcomeImpact())
                .compliancePreDisbursementTerms(note.getCompliancePreDisbursementTerms())
                .recommendation(note.getRecommendation())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .createdBy(note.getCreatedBy())
                .updatedBy(note.getUpdatedBy())
                .isActive(note.getIsActive())
                .build();
    }
}