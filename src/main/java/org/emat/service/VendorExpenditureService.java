package org.emat.service;

import lombok.RequiredArgsConstructor;
import org.emat.dto.CreateVendorExpenditureRequest;
import org.emat.dto.VendorExpenditureResponse;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.entity.VendorExpenditure;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.emat.repository.VendorExpenditureRepository;
import org.emat.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorExpenditureService {

    private final VendorExpenditureRepository vendorExpenditureRepository;
    private final IndustryAssociationRegistrationRepository registrationRepository;

    public VendorExpenditureResponse create(CreateVendorExpenditureRequest request) {

        UUID registrationUuid = UuidUtil.toUuid(request.getRegistrationUuid());

        IndustryAssociationRegistration registration = registrationRepository
                .findByUuid(registrationUuid)
                .orElseThrow(() -> new EntityNotFoundException("REGISTRATION_NOT_FOUND_MESSAGE" + request.getRegistrationUuid()));

        VendorExpenditure expenditure = VendorExpenditure.builder()
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

        VendorExpenditure saved =
                vendorExpenditureRepository.save(expenditure);

        return mapToResponse(saved);
    }

    public VendorExpenditureResponse getById(UUID uuid) {

        VendorExpenditure expenditure =
                vendorExpenditureRepository.findById(uuid)
                        .orElseThrow(() ->
                                new RuntimeException("Vendor Expenditure not found"));

        return mapToResponse(expenditure);
    }

    public VendorExpenditureResponse getByRegistrationUuid(UUID registrationUuid) {

        VendorExpenditure expenditure =
                vendorExpenditureRepository
                        .findByRegistrationUuid(registrationUuid)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vendor Expenditure not found for registration"));

        return mapToResponse(expenditure);
    }

    public List<VendorExpenditureResponse> getAll() {

        return vendorExpenditureRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public VendorExpenditureResponse update(
            UUID uuid,
            CreateVendorExpenditureRequest request) {

        VendorExpenditure existing =
                vendorExpenditureRepository.findById(uuid)
                        .orElseThrow(() ->
                                new RuntimeException("Vendor Expenditure not found"));

        if (request.getRegistrationUuid() != null &&
                !request.getRegistrationUuid()
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

        VendorExpenditure updated =
                vendorExpenditureRepository.save(existing);

        return mapToResponse(updated);
    }

    public void delete(UUID uuid) {

        VendorExpenditure expenditure =
                vendorExpenditureRepository.findById(uuid)
                        .orElseThrow(() ->
                                new RuntimeException("Vendor Expenditure not found"));

        vendorExpenditureRepository.delete(expenditure);
    }

    private VendorExpenditureResponse mapToResponse(
            VendorExpenditure expenditure) {

        return VendorExpenditureResponse.builder()
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
            VendorExpenditure expenditure) {

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