package org.emat.service.impl;

import lombok.RequiredArgsConstructor;
import org.emat.dto.*;
import org.emat.entity.IndustryAssociationBseRecommendation;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.entity.User;
import org.emat.entity.VendorDisbursementDetail;
import org.emat.entity.VendorDisbursementSalary;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.IndustryAssociationBseRecommendationRepository;
import org.emat.repository.IndustryAssociationRegistrationRepository;
import org.emat.repository.UserRepository;
import org.emat.repository.VendorDisbursementRepository;
import org.emat.service.VendorDisbursementService;
import org.emat.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendorDisbursementServiceImpl implements VendorDisbursementService {

    private final VendorDisbursementRepository vendorDisbursementRepository;
    private final IndustryAssociationRegistrationRepository registrationRepository;
    private final IndustryAssociationBseRecommendationRepository bseRecommendationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public VendorDisbursementSalaryResponse create(CreateVendorDisbursementSalaryRequest request) {
        if (request.getRegistrationUuid() == null || request.getRegistrationUuid().isBlank()) {
            throw new IllegalArgumentException("registrationUuid is required");
        }

        UUID registrationUuid = UuidUtil.toUuid(request.getRegistrationUuid());
        IndustryAssociationRegistration registration = registrationRepository
                .findByUuid(registrationUuid)
                .orElseThrow(() -> new EntityNotFoundException("Registration not found with UUID: " + request.getRegistrationUuid()));

        VendorDisbursementSalary entity = new VendorDisbursementSalary();
        mapParentFields(entity, request);
        entity.setDetails(mapCreateDetails(request.getDetails(), entity));
        entity.setRegistration(registration);
        return toResponse(vendorDisbursementRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public VendorDisbursementSalaryResponse getById(Long id) {
        return toResponse(findEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VendorDisbursementSalaryResponse> getAll() {
        return vendorDisbursementRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public VendorDisbursementSalaryResponse update(Long id, UpdateVendorDisbursementSalaryRequest request) {
        VendorDisbursementSalary entity = findEntity(id);

        if (request.getRegistrationUuid() != null) {
            IndustryAssociationRegistration registration = registrationRepository
                    .findByUuid(request.getRegistrationUuid())
                    .orElseThrow(() -> new EntityNotFoundException("Registration not found with UUID: " + request.getRegistrationUuid()));
            entity.setRegistration(registration);
        }

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

    private VendorDisbursementSalary findEntity(Long id) {
        return vendorDisbursementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("VendorDisbursement not found with id: " + id));
    }

    private void mapParentFields(VendorDisbursementSalary entity, CreateVendorDisbursementSalaryRequest request) {
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
        entity.setCreatedBy(resolveUsername(request.getCreatedBy()));
        entity.setVerifiedBy(resolveUsername(request.getVerifiedBy()));
        entity.setApprovedBy(resolveUsername(request.getApprovedBy()));
    }

    private void mapUpdateFields(VendorDisbursementSalary entity, UpdateVendorDisbursementSalaryRequest request) {
        setIfNotNull(request.getGstinOfAgency(), entity::setGstinOfAgency);
        setIfNotNull(request.getReasonForNoGstin(), entity::setReasonForNoGstin);
        setIfNotNull(request.getGstinOfSdbi(), entity::setGstinOfSdbi);
        setIfNotNull(request.getSanctionedAmount(), entity::setSanctionedAmount);
        setIfNotNull(request.getDisbursedTillDate(), entity::setDisbursedTillDate);
        setIfNotNull(request.getDisbursementSoughtIn(), entity::setDisbursementSoughtIn);
        setIfNotNull(request.getNatureOfPayment(), entity::setNatureOfPayment);
        setIfNotNull(request.getInvoiceDate(), entity::setInvoiceDate);
        setIfNotNull(request.getInvoiceNumber(), entity::setInvoiceNumber);
        setIfNotNull(request.getDetailsOfItems(), entity::setDetailsOfItems);
        setIfNotNull(request.getInvoiceValue(), entity::setInvoiceValue);
        setIfNotNull(request.getGstAmount(), entity::setGstAmount);
        setIfNotNull(request.getTotalAmount(), entity::setTotalAmount);
        setIfNotNull(request.getTdsApplicable(), entity::setTdsApplicable);
        setIfNotNull(request.getTdsNotApplicableReason(), entity::setTdsNotApplicableReason);
        setIfNotNull(request.getRecommendedDisbursementAmount(), entity::setRecommendedDisbursementAmount);
        setIfNotNull(request.getAccountCode(), entity::setAccountCode);
        setIfNotNull(request.getComplianceTerms(), entity::setComplianceTerms);
        setIfNotNull(request.getRecommendation(), entity::setRecommendation);
        setIfNotNull(request.getStatus(), entity::setStatus);
        setIfNotNull(request.getCreatedBy(), value -> entity.setCreatedBy(resolveUsername(value)));
        setIfNotNull(request.getVerifiedBy(), value -> entity.setVerifiedBy(resolveUsername(value)));
        setIfNotNull(request.getApprovedBy(), value -> entity.setApprovedBy(resolveUsername(value)));
    }

    private <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

    private List<VendorDisbursementDetail> mapCreateDetails(List<CreateVendorDisbursementDetailRequest> requests, VendorDisbursementSalary parent) {
        if (requests == null) {
            return new ArrayList<>();
        }
        return requests.stream().map(r -> mapDetail(r, parent)).collect(Collectors.toCollection(ArrayList::new));
    }

    private List<VendorDisbursementDetail> mapUpdateDetails(List<UpdateVendorDisbursementDetailRequest> requests, VendorDisbursementSalary parent) {
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

    private VendorDisbursementDetail mapDetail(CreateVendorDisbursementDetailRequest r, VendorDisbursementSalary parent) {
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
            throw new IllegalArgumentException("iaId is required");
        }
        UUID uuid = parseUuidOrThrow(iaId, "iaId");
        return registrationRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("IndustryAssociationRegistration not found with UUID: " + iaId));
    }

    private IndustryAssociationBseRecommendation resolveBse(String bseId) {
        if (bseId == null || bseId.isBlank()) {
            throw new IllegalArgumentException("bseId is required");
        }
        UUID uuid = parseUuidOrThrow(bseId, "bseId");
        return bseRecommendationRepository.findByUuidAndIsActiveTrue(uuid)
                .orElseThrow(() -> new EntityNotFoundException("IndustryAssociationBseRecommendation not found with UUID: " + bseId));
    }

    private UUID parseUuidOrThrow(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(fieldName + " must be a valid UUID");
        }
    }

    private String resolveUsername(String username) {
        if (username == null || username.isBlank()) {
            return null;
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
        return user.getUsername();
    }

    private VendorDisbursementSalaryResponse toResponse(VendorDisbursementSalary entity) {
        return VendorDisbursementSalaryResponse.builder()
                .id(entity.getId())
                .manpowerAgencyName(entity.getRegistration().getIndustryAssociationName())
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

