package org.emat.mapper;

import org.emat.dto.BseSalaryRequest;
import org.emat.dto.BseSalaryResponse;
import org.emat.dto.BseSalaryUpdateRequest;
import org.emat.dto.MonthlySalaryDetailsRequest;
import org.emat.dto.MonthlySalaryDetailsResponse;
import org.emat.dto.MonthlySalaryDetailsUpdateRequest;
import org.emat.entity.BseSalary;
import org.emat.entity.MonthlySalaryDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Component
public class BseSalaryMapper {

    public void applyCreateFields(BseSalary entity, BseSalaryRequest request) {
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

    public void applyUpdateFields(BseSalary entity, BseSalaryUpdateRequest request) {
        applyIfNotNull(request.getGstinOfAgency(), entity::setGstinOfAgency);
        applyIfNotNull(request.getReasonForNoGstin(), entity::setReasonForNoGstin);
        applyIfNotNull(request.getGstinOfSdbi(), entity::setGstinOfSdbi);
        applyIfNotNull(request.getSanctionedAmount(), entity::setSanctionedAmount);
        applyIfNotNull(request.getDisbursedTillDate(), entity::setDisbursedTillDate);
        applyIfNotNull(request.getDisbursementSoughtIn(), entity::setDisbursementSoughtIn);
        applyIfNotNull(request.getNatureOfPayment(), entity::setNatureOfPayment);
        applyIfNotNull(request.getInvoiceDate(), entity::setInvoiceDate);
        applyIfNotNull(request.getInvoiceNumber(), entity::setInvoiceNumber);
        applyIfNotNull(request.getInvoiceValue(), entity::setInvoiceValue);
        applyIfNotNull(request.getGstAmount(), entity::setGstAmount);
        applyIfNotNull(request.getTotalAmount(), entity::setTotalAmount);
        applyIfNotNull(request.getTdsApplicable(), entity::setTdsApplicable);
        applyIfNotNull(request.getTdsNotApplicableReason(), entity::setTdsNotApplicableReason);
        applyIfNotNull(request.getRecommendedDisbursementAmount(), entity::setRecommendedDisbursementAmount);
        applyIfNotNull(request.getAccountCode(), entity::setAccountCode);
        applyIfNotNull(request.getComplianceTerms(), entity::setComplianceTerms);
        applyIfNotNull(request.getRecommendation(), entity::setRecommendation);
        applyIfNotNull(request.getStatus(), entity::setStatus);
        applyIfNotNull(request.getCreatedBy(), entity::setCreatedBy);
        applyIfNotNull(request.getVerifiedBy(), entity::setVerifiedBy);
        applyIfNotNull(request.getApprovedBy(), entity::setApprovedBy);
    }

    public List<MonthlySalaryDetails> mapCreateDetails(List<MonthlySalaryDetailsRequest> requests, BseSalary parent) {
        if (requests == null) {
            return new ArrayList<>();
        }
        return requests.stream().map(r -> mapCreateDetail(r, parent)).toList();
    }

    public List<MonthlySalaryDetails> mapUpdateDetails(List<MonthlySalaryDetailsUpdateRequest> requests, BseSalary parent) {
        if (requests == null) {
            return new ArrayList<>();
        }
        return requests.stream().map(r -> {
            MonthlySalaryDetails detail = new MonthlySalaryDetails();
            detail.setBseSalary(parent);
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

    private MonthlySalaryDetails mapCreateDetail(MonthlySalaryDetailsRequest request, BseSalary parent) {
        MonthlySalaryDetails detail = new MonthlySalaryDetails();
        detail.setBseSalary(parent);
        detail.setSalaryMonth(request.getSalaryMonth());
        detail.setSalaryDays(request.getSalaryDays());
        detail.setPaidDays(request.getPaidDays());
        detail.setAdditionalAmount(request.getAdditionalAmount());
        detail.setAdditionalAmountReason(request.getAdditionalAmountReason());
        detail.setPaymentToBse(request.getPaymentToBse());
        detail.setGtAttendanceComments(request.getGtAttendanceComments());
        detail.setGtAdditionalComments(request.getGtAdditionalComments());
        return detail;
    }

    public BseSalaryResponse toResponse(BseSalary entity) {
        return BseSalaryResponse.builder()
                .id(entity.getId())
                .manpowerAgencyName(resolveManpowerAgencyName(entity))
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
                .monthlySalaryDetails(
                        entity.getMonthlySalaryDetails() == null
                                ? new ArrayList<>()
                                : entity.getMonthlySalaryDetails().stream()
                                .map(d -> MonthlySalaryDetailsResponse.builder()
                                        .id(d.getId())
                                        .bseName(null)
                                        .manpowerAgencyName(null)
                                        .salaryMonth(d.getSalaryMonth())
                                        .salaryDays(d.getSalaryDays())
                                        .paidDays(d.getPaidDays())
                                        .additionalAmount(d.getAdditionalAmount())
                                        .additionalAmountReason(d.getAdditionalAmountReason())
                                        .paymentToBse(d.getPaymentToBse())
                                        .gtAttendanceComments(d.getGtAttendanceComments())
                                        .gtAdditionalComments(d.getGtAdditionalComments())
                                        .build())
                                .toList())
                .build();
    }

    private String resolveManpowerAgencyName(BseSalary entity) {
        if (entity.getMonthlySalaryDetails() == null) {
            return null;
        }
        return entity.getMonthlySalaryDetails().stream()
                .map(MonthlySalaryDetails::getBseSalary)
                .filter(Objects::nonNull)
                .map(BseSalary::getMonthlySalaryDetails)
                .findFirst()
                .map(details -> details.stream()
                        .findFirst()
                        .map(MonthlySalaryDetails::getSalaryMonth)
                        .orElse(null))
                .orElse(null);
    }

    private <T> void applyIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}

