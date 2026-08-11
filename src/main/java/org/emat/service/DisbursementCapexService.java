package org.emat.service;

import lombok.RequiredArgsConstructor;
import org.emat.dto.DisbursementCapexRequest;
import org.emat.dto.DisbursementCapexResponse;
import org.emat.entity.DisbursementCapex;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.entity.DisbursementCapex;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.emat.repository.DisbursementCapexRepository;
import org.emat.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DisbursementCapexService {

    private final DisbursementCapexRepository vendorExpenditureRepository;
    private final IndustryAssociationRegistrationRepository registrationRepository;

    public DisbursementCapexResponse create(DisbursementCapexRequest request) {

        UUID registrationUuid = UuidUtil.toUuid(request.getRegistrationUuid());

        IndustryAssociationRegistration registration = registrationRepository
                .findByUuid(registrationUuid)
                .orElseThrow(() -> new EntityNotFoundException("REGISTRATION_NOT_FOUND_MESSAGE" + request.getRegistrationUuid()));

        DisbursementCapex expenditure = DisbursementCapex.builder()
                .registration(registration)
                .gstinIa(request.getGstinIa())
                .gstinNotApplicable(request.getGstinNotApplicable())
                .gstinNotApplicableReason(request.getGstinNotApplicableReason())
                .gstinSidbi(request.getGstinSidbi())
                .sanctionedAmount(request.getSanctionedAmount())
                .disbursedTillDate(request.getDisbursedTillDate())
                .disbursementSought(request.getDisbursementSought())
                .natureOfPayment(request.getNatureOfPayment())
                .invoiceDate(request.getInvoiceDate())
                .invoiceNumber(request.getInvoiceNumber())
                .detailsOfItems(request.getDetailsOfItems())
                .valueOfServiceItems(request.getValueOfServiceItems())
                .igstAmount(request.getIgstAmount())
                .totalAmount(request.getTotalAmount())
                .tdsApplicable(request.getTdsApplicable())
                .tdsNotApplicableReason(request.getTdsNotApplicableReason())
                .amountRecommendedForDisbursement(
                        request.getAmountRecommendedForDisbursement())
                .accountCode(request.getAccountCode())
                .gtCapexVerificationComments(
                        request.getGtCapexVerificationComments())
                .preDisbursementCompliance(
                        request.getPreDisbursementCompliance())
                .recommendation(request.getRecommendation())
                .build();

        DisbursementCapex saved = vendorExpenditureRepository.save(expenditure);

        return mapToResponse(saved);
    }

    public DisbursementCapexResponse getById(UUID uuid) {

        DisbursementCapex expenditure =
                vendorExpenditureRepository.findById(uuid)
                        .orElseThrow(() ->
                                new RuntimeException("Vendor Expenditure not found"));

        return mapToResponse(expenditure);
    }

    public DisbursementCapexResponse getByRegistrationUuid(UUID registrationUuid) {

        DisbursementCapex expenditure =
                vendorExpenditureRepository
                        .findByRegistrationUuid(registrationUuid)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vendor Expenditure not found for registration"));

        return mapToResponse(expenditure);
    }

    public List<DisbursementCapexResponse> getAll() {

        return vendorExpenditureRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public DisbursementCapexResponse update(
            UUID uuid,
            DisbursementCapexRequest request) {

        DisbursementCapex existing = vendorExpenditureRepository.findById(uuid)
                        .orElseThrow(() -> new RuntimeException("Vendor Expenditure not found"));

        if (request.getRegistrationUuid() != null && !request.getRegistrationUuid()
                        .equals(existing.getRegistration().getUuid())) {

            UUID registrationUuid = UuidUtil.toUuid(request.getRegistrationUuid());

            IndustryAssociationRegistration registration = registrationRepository
                    .findByUuid(registrationUuid)
                    .orElseThrow(() -> new EntityNotFoundException("REGISTRATION_NOT_FOUND_MESSAGE" + request.getRegistrationUuid()));


            existing.setRegistration(registration);
        }

        existing.setGstinIa(request.getGstinIa());
        existing.setGstinNotApplicable(request.getGstinNotApplicable());
        existing.setGstinNotApplicableReason(
                request.getGstinNotApplicableReason());
        existing.setGstinSidbi(request.getGstinSidbi());
        existing.setSanctionedAmount(request.getSanctionedAmount());
        existing.setDisbursedTillDate(request.getDisbursedTillDate());
        existing.setDisbursementSought(request.getDisbursementSought());
        existing.setNatureOfPayment(request.getNatureOfPayment());
        existing.setInvoiceDate(request.getInvoiceDate());
        existing.setInvoiceNumber(request.getInvoiceNumber());
        existing.setDetailsOfItems(request.getDetailsOfItems());
        existing.setValueOfServiceItems(request.getValueOfServiceItems());
        existing.setIgstAmount(request.getIgstAmount());
        existing.setTotalAmount(request.getTotalAmount());
        existing.setTdsApplicable(request.getTdsApplicable());
        existing.setTdsNotApplicableReason(
                request.getTdsNotApplicableReason());
        existing.setAmountRecommendedForDisbursement(
                request.getAmountRecommendedForDisbursement());
        existing.setAccountCode(request.getAccountCode());
        existing.setGtCapexVerificationComments(
                request.getGtCapexVerificationComments());
        existing.setPreDisbursementCompliance(
                request.getPreDisbursementCompliance());
        existing.setRecommendation(request.getRecommendation());

        DisbursementCapex updated =
                vendorExpenditureRepository.save(existing);

        return mapToResponse(updated);
    }

    public void delete(UUID uuid) {

        DisbursementCapex expenditure =
                vendorExpenditureRepository.findById(uuid)
                        .orElseThrow(() ->
                                new RuntimeException("Vendor Expenditure not found"));

        vendorExpenditureRepository.delete(expenditure);
    }

    private DisbursementCapexResponse mapToResponse(
            DisbursementCapex expenditure) {

        return DisbursementCapexResponse.builder()
                .uuid(expenditure.getUuid())
                .registrationUuid(
                        expenditure.getRegistration().getUuid())
                .industryAssociationName(
                        getIndustryAssociationName(expenditure))
                .gstinIa(expenditure.getGstinIa())
                .gstinNotApplicable(
                        expenditure.getGstinNotApplicable())
                .gstinNotApplicableReason(
                        expenditure.getGstinNotApplicableReason())
                .gstinSidbi(expenditure.getGstinSidbi())
                .sanctionedAmount(expenditure.getSanctionedAmount())
                .disbursedTillDate(expenditure.getDisbursedTillDate())
                .disbursementSought(expenditure.getDisbursementSought())
                .natureOfPayment(expenditure.getNatureOfPayment())
                .invoiceDate(expenditure.getInvoiceDate())
                .invoiceNumber(expenditure.getInvoiceNumber())
                .detailsOfItems(expenditure.getDetailsOfItems())
                .valueOfServiceItems(
                        expenditure.getValueOfServiceItems())
                .igstAmount(expenditure.getIgstAmount())
                .totalAmount(expenditure.getTotalAmount())
                .tdsApplicable(expenditure.getTdsApplicable())
                .tdsNotApplicableReason(
                        expenditure.getTdsNotApplicableReason())
                .amountRecommendedForDisbursement(
                        expenditure.getAmountRecommendedForDisbursement())
                .accountCode(expenditure.getAccountCode())
                .gtCapexVerificationComments(
                        expenditure.getGtCapexVerificationComments())
                .preDisbursementCompliance(
                        expenditure.getPreDisbursementCompliance())
                .recommendation(expenditure.getRecommendation())
                .build();
    }

    private String getIndustryAssociationName(
            DisbursementCapex expenditure) {

        IndustryAssociationRegistration registration =
                expenditure.getRegistration();

        /*
         * Replace this with the actual field/path in
         * IndustryAssociationRegistration that contains
         * the Industry Association Name.
         */
        return registration.getIndustryAssociationName();
    }
}