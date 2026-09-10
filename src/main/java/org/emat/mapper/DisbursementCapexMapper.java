package org.emat.mapper;

import org.emat.dto.DisbursementCapexRequest;
import org.emat.dto.DisbursementCapexResponse;
import org.emat.entity.DisbursementCapex;
import org.emat.entity.IndustryAssociationRegistration;
import org.springframework.stereotype.Component;

@Component
public class DisbursementCapexMapper {

    public DisbursementCapex toEntity(
            DisbursementCapexRequest request,
            IndustryAssociationRegistration registration) {
        return DisbursementCapex.builder()
                .registration(registration)
                .gstinIa(request.getGstinIa())
                .gstinNotApplicable(request.getGstinNotApplicable())
                .gstinNotApplicableReason(request.getGstinNotApplicableReason())
                .gstinSidbi(request.getGstinSidbi())
                .sanctionedAmount(request.getSanctionedAmount())
                .disbursedTillDate(request.getDisbursedTillDate())
                .disbursementSought(request.getDisbursementSought())
                .invoiceDate(request.getInvoiceDate())
                .invoiceNumber(request.getInvoiceNumber())
                .detailsOfItems(request.getDetailsOfItems())
                .valueOfServiceItems(request.getValueOfServiceItems())
                .igstAmount(request.getIgstAmount())
                .totalAmount(request.getTotalAmount())
                .tdsApplicable(request.getTdsApplicable())
                .tdsNotApplicableReason(request.getTdsNotApplicableReason())
                .amountRecommendedForDisbursement(request.getAmountRecommendedForDisbursement())
                .accountCode(request.getAccountCode())
                .gtCapexVerificationComments(request.getGtCapexVerificationComments())
                .preDisbursementCompliance(request.getPreDisbursementCompliance())
                .recommendation(request.getRecommendation())
                .build();
    }

    public void updateEntityFromRequest(DisbursementCapex existing, DisbursementCapexRequest request) {
        existing.setGstinIa(request.getGstinIa());
        existing.setGstinNotApplicable(request.getGstinNotApplicable());
        existing.setGstinNotApplicableReason(request.getGstinNotApplicableReason());
        existing.setGstinSidbi(request.getGstinSidbi());
        existing.setSanctionedAmount(request.getSanctionedAmount());
        existing.setDisbursedTillDate(request.getDisbursedTillDate());
        existing.setDisbursementSought(request.getDisbursementSought());
        existing.setInvoiceDate(request.getInvoiceDate());
        existing.setInvoiceNumber(request.getInvoiceNumber());
        existing.setDetailsOfItems(request.getDetailsOfItems());
        existing.setValueOfServiceItems(request.getValueOfServiceItems());
        existing.setIgstAmount(request.getIgstAmount());
        existing.setTotalAmount(request.getTotalAmount());
        existing.setTdsApplicable(request.getTdsApplicable());
        existing.setTdsNotApplicableReason(request.getTdsNotApplicableReason());
        existing.setAmountRecommendedForDisbursement(request.getAmountRecommendedForDisbursement());
        existing.setAccountCode(request.getAccountCode());
        existing.setGtCapexVerificationComments(request.getGtCapexVerificationComments());
        existing.setPreDisbursementCompliance(request.getPreDisbursementCompliance());
        existing.setRecommendation(request.getRecommendation());
    }

    public DisbursementCapexResponse toResponse(DisbursementCapex expenditure) {
        return DisbursementCapexResponse.builder()
                .id(expenditure.getId())
                .registrationId(expenditure.getRegistration().getId())
                .industryAssociationName(expenditure.getRegistration().getIndustryAssociationName())
                .gstinIa(expenditure.getGstinIa())
                .gstinNotApplicable(expenditure.getGstinNotApplicable())
                .gstinNotApplicableReason(expenditure.getGstinNotApplicableReason())
                .gstinSidbi(expenditure.getGstinSidbi())
                .sanctionedAmount(expenditure.getSanctionedAmount())
                .disbursedTillDate(expenditure.getDisbursedTillDate())
                .disbursementSought(expenditure.getDisbursementSought())
                .invoiceDate(expenditure.getInvoiceDate())
                .invoiceNumber(expenditure.getInvoiceNumber())
                .detailsOfItems(expenditure.getDetailsOfItems())
                .valueOfServiceItems(expenditure.getValueOfServiceItems())
                .igstAmount(expenditure.getIgstAmount())
                .totalAmount(expenditure.getTotalAmount())
                .tdsApplicable(expenditure.getTdsApplicable())
                .tdsNotApplicableReason(expenditure.getTdsNotApplicableReason())
                .amountRecommendedForDisbursement(expenditure.getAmountRecommendedForDisbursement())
                .accountCode(expenditure.getAccountCode())
                .gtCapexVerificationComments(expenditure.getGtCapexVerificationComments())
                .preDisbursementCompliance(expenditure.getPreDisbursementCompliance())
                .recommendation(expenditure.getRecommendation())
                .build();
    }
}

