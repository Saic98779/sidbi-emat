package org.emat.mapper;

import org.emat.dto.CreateDiaCreditApplicationRequest;
import org.emat.dto.DiaCreditApplicationResponse;
import org.emat.dto.UpdateDiaCreditApplicationRequest;
import org.emat.entity.DiaCreditApplication;
import org.springframework.stereotype.Component;

@Component
public class DiaCreditApplicationMapper {

    public DiaCreditApplication toEntity(CreateDiaCreditApplicationRequest request) {
        return DiaCreditApplication.builder()
                .roName(request.getRoName())
                .applicantName(request.getApplicantName())
                .applicantPan(request.getApplicantPan())
                .loanAmountApplied(request.getLoanAmountApplied())
                .mobileNo(request.getMobileNo())
                .email(request.getEmail())
                .officeAddress(request.getOfficeAddress())
                .pinCode(request.getPinCode())
                .industryAssociationName(request.getIndustryAssociationName())
                .industryAssociationLoginId(request.getIndustryAssociationLoginId())
                .acknowledgementNo(request.getAcknowledgementNo())
                .memberType(request.getMemberType())
                .status(request.getStatus())
                .loanSanctioned(request.getLoanSanctioned())
                .sanctionDate(request.getSanctionDate())
                .amountSanctioned(request.getAmountSanctioned())
                .amountDisbursed(request.getAmountDisbursed())
                .isActive(true)
                .build();
    }

    public void applyUpdateRequest(
            DiaCreditApplication application, UpdateDiaCreditApplicationRequest request) {
        if (request.getRoName() != null) application.setRoName(request.getRoName());
        if (request.getApplicantName() != null)
            application.setApplicantName(request.getApplicantName());
        if (request.getApplicantPan() != null)
            application.setApplicantPan(request.getApplicantPan());
        if (request.getLoanAmountApplied() != null)
            application.setLoanAmountApplied(request.getLoanAmountApplied());
        if (request.getMobileNo() != null) application.setMobileNo(request.getMobileNo());
        if (request.getEmail() != null) application.setEmail(request.getEmail());
        if (request.getOfficeAddress() != null)
            application.setOfficeAddress(request.getOfficeAddress());
        if (request.getPinCode() != null) application.setPinCode(request.getPinCode());
        if (request.getIndustryAssociationName() != null)
            application.setIndustryAssociationName(request.getIndustryAssociationName());
        if (request.getIndustryAssociationLoginId() != null)
            application.setIndustryAssociationLoginId(request.getIndustryAssociationLoginId());
        if (request.getAcknowledgementNo() != null)
            application.setAcknowledgementNo(request.getAcknowledgementNo());
        if (request.getMemberType() != null) application.setMemberType(request.getMemberType());
        if (request.getStatus() != null) application.setStatus(request.getStatus());
        if (request.getLoanSanctioned() != null)
            application.setLoanSanctioned(request.getLoanSanctioned());
        if (request.getSanctionDate() != null)
            application.setSanctionDate(request.getSanctionDate());
        if (request.getAmountSanctioned() != null)
            application.setAmountSanctioned(request.getAmountSanctioned());
        if (request.getAmountDisbursed() != null)
            application.setAmountDisbursed(request.getAmountDisbursed());
    }

    public DiaCreditApplicationResponse toResponse(DiaCreditApplication application) {
        return DiaCreditApplicationResponse.builder()
                .id(application.getId())
                .roName(application.getRoName())
                .applicantName(application.getApplicantName())
                .applicantPan(application.getApplicantPan())
                .loanAmountApplied(application.getLoanAmountApplied())
                .mobileNo(application.getMobileNo())
                .email(application.getEmail())
                .officeAddress(application.getOfficeAddress())
                .pinCode(application.getPinCode())
                .industryAssociationName(application.getIndustryAssociationName())
                .industryAssociationLoginId(application.getIndustryAssociationLoginId())
                .acknowledgementNo(application.getAcknowledgementNo())
                .memberType(application.getMemberType())
                .status(application.getStatus())
                .loanSanctioned(application.getLoanSanctioned())
                .sanctionDate(application.getSanctionDate())
                .amountSanctioned(application.getAmountSanctioned())
                .amountDisbursed(application.getAmountDisbursed())
                .createdAt(application.getCreatedAt())
                .updatedAt(application.getUpdatedAt())
                .createdBy(application.getCreatedBy())
                .updatedBy(application.getUpdatedBy())
                .isActive(application.getIsActive())
                .build();
    }
}
