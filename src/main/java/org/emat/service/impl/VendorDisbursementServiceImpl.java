package org.emat.service.impl;

import lombok.RequiredArgsConstructor;
import org.emat.dto.*;
import org.emat.entity.IndustryAssociationBseRecommendation;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.entity.VendorDisbursement;
import org.emat.entity.VendorDisbursementDetail;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.IndustryAssociationBseRecommendationRepository;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.emat.repository.VendorDisbursementRepository;
import org.emat.service.VendorDisbursementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendorDisbursementServiceImpl implements VendorDisbursementService {

    private final VendorDisbursementRepository vendorDisbursementRepository;
    private final IndustryAssociationRegistrationRepository registrationRepository;
    private final IndustryAssociationBseRecommendationRepository bseRecommendationRepository;

    @Override
    @Transactional
    public VendorDisbursementResponse create(CreateVendorDisbursementRequest request) {
        VendorDisbursement entity = new VendorDisbursement();
        mapParentFields(entity, request);
        entity.setDetails(mapCreateDetails(request.getDetails(), entity));
        return toResponse(vendorDisbursementRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public VendorDisbursementResponse getById(Long id) {
        return toResponse(findEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VendorDisbursementResponse> getAll() {
        return vendorDisbursementRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public VendorDisbursementResponse update(Long id, UpdateVendorDisbursementRequest request) {
        VendorDisbursement entity = findEntity(id);
        mapUpdateFields(entity, request);
        if (request.getDetails() != null) {
            entity.getDetails().clear();
            entity.getDetails().addAll(mapUpdateDetails(request.getDetails(), entity));
        }
        return toResponse(vendorDisbursementRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        vendorDisbursementRepository.delete(findEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getApprovedIndustryAssociationNames() {
        return registrationRepository.findAllByIsActiveTrueAndIsSidbeApprovedTrue().stream()
                .map(IndustryAssociationRegistration::getIndustryAssociationName)
                .filter(name -> name != null && !name.isBlank())
                .toList();
    }

    private VendorDisbursement findEntity(Long id) {
        return vendorDisbursementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("VendorDisbursement not found with id: " + id));
    }

    private void mapParentFields(VendorDisbursement entity, CreateVendorDisbursementRequest request) {
        entity.setManpowerAgencyName(request.getManpowerAgencyName());
        entity.setGstinOfAgency(request.getGstinOfAgency());
        entity.setReasonForNoGstin(request.getReasonForNoGstin());
        entity.setGstinOfSdbi(request.getGstinOfSdbi());
        entity.setSanctionedAmount(request.getSanctionedAmount());
        entity.setDisbursedTillDate(request.getDisbursedTillDate());
        entity.setDisbursementSoughtIn(request.getDisbursementSoughtIn());
        entity.setNatureOfPayment(request.getNatureOfPayment());
        entity.setInvoiceDate(request.getInvoiceDate());
        entity.setInvoiceNumber(request.getInvoiceNumber());
        entity.setDetailsOfItems(request.getDetailsOfItems());
        entity.setInvoiceValue(request.getInvoiceValue());
        entity.setGstAmount(request.getGstAmount());
        entity.setTotalAmount(request.getTotalAmount());
        entity.setTdsApplicable(request.getTdsApplicable());
        entity.setTdsNotApplicableReason(request.getTdsNotApplicableReason());
        entity.setRecommendedDisbursementAmount(request.getRecommendedDisbursementAmount());
        entity.setAccountCode(request.getAccountCode());
        entity.setComplianceTerms(request.getComplianceTerms());
        entity.setRecommendation(request.getRecommendation());
        entity.setStatus(request.getStatus());
        entity.setCreatedBy(request.getCreatedBy());
        entity.setVerifiedBy(request.getVerifiedBy());
        entity.setApprovedBy(request.getApprovedBy());
    }

    private void mapUpdateFields(VendorDisbursement entity, UpdateVendorDisbursementRequest request) {
        if (request.getManpowerAgencyName() != null) entity.setManpowerAgencyName(request.getManpowerAgencyName());
        if (request.getGstinOfAgency() != null) entity.setGstinOfAgency(request.getGstinOfAgency());
        if (request.getReasonForNoGstin() != null) entity.setReasonForNoGstin(request.getReasonForNoGstin());
        if (request.getGstinOfSdbi() != null) entity.setGstinOfSdbi(request.getGstinOfSdbi());
        if (request.getSanctionedAmount() != null) entity.setSanctionedAmount(request.getSanctionedAmount());
        if (request.getDisbursedTillDate() != null) entity.setDisbursedTillDate(request.getDisbursedTillDate());
        if (request.getDisbursementSoughtIn() != null) entity.setDisbursementSoughtIn(request.getDisbursementSoughtIn());
        if (request.getNatureOfPayment() != null) entity.setNatureOfPayment(request.getNatureOfPayment());
        if (request.getInvoiceDate() != null) entity.setInvoiceDate(request.getInvoiceDate());
        if (request.getInvoiceNumber() != null) entity.setInvoiceNumber(request.getInvoiceNumber());
        if (request.getInvoiceValue() != null) entity.setInvoiceValue(request.getInvoiceValue());
        if (request.getGstAmount() != null) entity.setGstAmount(request.getGstAmount());
        if (request.getTotalAmount() != null) entity.setTotalAmount(request.getTotalAmount());
        if (request.getTdsApplicable() != null) entity.setTdsApplicable(request.getTdsApplicable());
        if (request.getTdsNotApplicableReason() != null) entity.setTdsNotApplicableReason(request.getTdsNotApplicableReason());
        if (request.getRecommendedDisbursementAmount() != null) entity.setRecommendedDisbursementAmount(request.getRecommendedDisbursementAmount());
        if (request.getAccountCode() != null) entity.setAccountCode(request.getAccountCode());
        if (request.getComplianceTerms() != null) entity.setComplianceTerms(request.getComplianceTerms());
        if (request.getRecommendation() != null) entity.setRecommendation(request.getRecommendation());
        if (request.getStatus() != null) entity.setStatus(request.getStatus());
        if (request.getCreatedBy() != null) entity.setCreatedBy(request.getCreatedBy());
        if (request.getVerifiedBy() != null) entity.setVerifiedBy(request.getVerifiedBy());
        if (request.getApprovedBy() != null) entity.setApprovedBy(request.getApprovedBy());
    }

    private List<VendorDisbursementDetail> mapCreateDetails(List<CreateVendorDisbursementDetailRequest> requests,
                                                            VendorDisbursement parent) {
        if (requests == null) {
            return new ArrayList<>();
        }
        return requests.stream().map(r -> mapDetail(r, parent)).toList();
    }

    private List<VendorDisbursementDetail> mapUpdateDetails(List<UpdateVendorDisbursementDetailRequest> requests,
                                                            VendorDisbursement parent) {
        if (requests == null) {
            return new ArrayList<>();
        }
        return requests.stream().map(r -> {
            VendorDisbursementDetail detail = new VendorDisbursementDetail();
            detail.setVendorDisbursement(parent);
            detail.setIndustryRegistrationId(resolveIa(r.getIaId()));
            detail.setBse(resolveBse(r.getBseId()));
            detail.setSalaryMonth(r.getSalaryMonth());
            detail.setSalaryDays(r.getSalaryDays());
            detail.setPaidDays(r.getPaidDays());
            detail.setAdditionalAmount(r.getAdditionalAmount());
            detail.setAdditionalAmountReason(r.getAdditionalAmountReason());
            detail.setPaymentToBse(r.getPaymentToBse());
            detail.setGtAttendanceComments(r.getGtAttendanceComments());
            detail.setGtAdditionalComments(r.getGtAdditionalComments());
            return detail;
        }).toList();
    }

    private VendorDisbursementDetail mapDetail(CreateVendorDisbursementDetailRequest r, VendorDisbursement parent) {
        VendorDisbursementDetail detail = new VendorDisbursementDetail();
        detail.setVendorDisbursement(parent);
        detail.setIndustryRegistrationId(resolveIa(r.getIaId()));
        detail.setBse(resolveBse(r.getBseId()));
        detail.setSalaryMonth(r.getSalaryMonth());
        detail.setSalaryDays(r.getSalaryDays());
        detail.setPaidDays(r.getPaidDays());
        detail.setAdditionalAmount(r.getAdditionalAmount());
        detail.setAdditionalAmountReason(r.getAdditionalAmountReason());
        detail.setPaymentToBse(r.getPaymentToBse());
        detail.setGtAttendanceComments(r.getGtAttendanceComments());
        detail.setGtAdditionalComments(r.getGtAdditionalComments());
        return detail;
    }

    private IndustryAssociationRegistration resolveIa(String iaId) {
        if (iaId == null || iaId.isBlank()) {
            return null;
        }
        UUID uuid = UUID.fromString(iaId);
        return registrationRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("IndustryAssociationRegistration not found with UUID: " + iaId));
    }

    private IndustryAssociationBseRecommendation resolveBse(String bseId) {
        if (bseId == null || bseId.isBlank()) {
            return null;
        }
        UUID uuid = UUID.fromString(bseId);
        return bseRecommendationRepository.findByUuidAndIsActiveTrue(uuid)
                .orElseThrow(() -> new EntityNotFoundException("IndustryAssociationBseRecommendation not found with UUID: " + bseId));
    }

    private VendorDisbursementResponse toResponse(VendorDisbursement entity) {
        return VendorDisbursementResponse.builder()
                .id(entity.getId())
                .manpowerAgencyName(entity.getManpowerAgencyName())
                .gstinOfAgency(entity.getGstinOfAgency())
                .reasonForNoGstin(entity.getReasonForNoGstin())
                .gstinOfSdbi(entity.getGstinOfSdbi())
                .sanctionedAmount(entity.getSanctionedAmount())
                .disbursedTillDate(entity.getDisbursedTillDate())
                .disbursementSoughtIn(entity.getDisbursementSoughtIn())
                .natureOfPayment(entity.getNatureOfPayment())
                .invoiceDate(entity.getInvoiceDate())
                .invoiceNumber(entity.getInvoiceNumber())
                .invoiceValue(entity.getInvoiceValue())
                .detailsOfItems(entity.getDetailsOfItems())
                .gstAmount(entity.getGstAmount())
                .totalAmount(entity.getTotalAmount())
                .tdsApplicable(entity.getTdsApplicable())
                .tdsNotApplicableReason(entity.getTdsNotApplicableReason())
                .recommendedDisbursementAmount(entity.getRecommendedDisbursementAmount())
                .accountCode(entity.getAccountCode())
                .complianceTerms(entity.getComplianceTerms())
                .recommendation(entity.getRecommendation())
                .status(entity.getStatus())
                .createdBy(entity.getCreatedBy())
                .verifiedBy(entity.getVerifiedBy())
                .approvedBy(entity.getApprovedBy())
                .details(entity.getDetails() == null ? new ArrayList<>() : entity.getDetails().stream()
                        .map(d -> {
                            String iaId = null;
                            if (d.getIndustryRegistrationId() != null) {
                                iaId = d.getIndustryRegistrationId().getUuid().toString();
                            }
                            String bseId = null;
                            if (d.getBse() != null) {
                                bseId = d.getBse().getUuid().toString();
                            }
                            return VendorDisbursementDetailResponse.builder()
                                    .id(d.getId())
                                    .iaId(iaId)
                                    .bseId(bseId)
                                    .salaryMonth(d.getSalaryMonth())
                                    .salaryDays(d.getSalaryDays())
                                    .paidDays(d.getPaidDays())
                                    .additionalAmount(d.getAdditionalAmount())
                                    .additionalAmountReason(d.getAdditionalAmountReason())
                                    .paymentToBse(d.getPaymentToBse())
                                    .gtAttendanceComments(d.getGtAttendanceComments())
                                    .gtAdditionalComments(d.getGtAdditionalComments())
                                    .build();
                        })
                        .toList())
                .build();
    }
}

