package org.emat.mapper;

import org.emat.dto.CreateIndustryAssociationRegistrationRequest;
import org.emat.dto.IndustryAssociationRegistrationResponse;
import org.emat.dto.SecretariatStaffDto;
import org.emat.dto.UpdateIndustryAssociationRegistrationRequest;
import org.emat.entity.IndustryAssociationRegistration;
import org.emat.entity.SecretariatStaff;
import org.emat.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IndustryAssociationRegistrationMapper {

    public IndustryAssociationRegistration toEntity(
            CreateIndustryAssociationRegistrationRequest request,
            boolean isSidbiSdeCaller,
            User sidbiApprover) {

        return IndustryAssociationRegistration.builder()
                .state(request.getState())
                .panNo(request.getPanNo())
                .email(request.getEmail())
                .industryAssociationName(request.getIndustryAssociationName())
                .constitutionType(request.getConstitutionType())
                .constitutionOther(request.getConstitutionOther())
                .incorporationDate(request.getIncorporationDate())
                .incorporationCertificate(request.getIncorporationCertificate())
                .iaType(request.getIaType())
                .constitutionProof(request.getConstitutionProof())
                .district(request.getDistrict())
                .pincode(request.getPincode())
                .apexHolderName(request.getApexHolderName())
                .apexHolderDesignation(request.getApexHolderDesignation())
                .apexHolderMobile(request.getApexHolderMobile())
                .apexHolderEmail(request.getApexHolderEmail())
                .addressProofType(request.getAddressProofType())
                .addressProof(request.getAddressProof())
                .idProofType(request.getIdProofType())
                .idProof(request.getIdProof())
                .nodalName(request.getNodalName())
                .nodalDesignation(request.getNodalDesignation())
                .nodalMobile(request.getNodalMobile())
                .nodalEmail(request.getNodalEmail())
                .sidbiBranch(request.getSidbiBranch())
                .sidbiBranchName(request.getSidbiBranchName())
                .mappedWithCluster(request.getMappedWithCluster())
                .clusterName(request.getClusterName())
                .mappedWithImportantDistrict(request.getMappedWithImportantDistrict())
                .districtMsmeCount(request.getDistrictMsmeCount())
                .activeMembersAbove200(request.getActiveMembersAbove200())
                .activeMembersCount(request.getActiveMembersCount())
                .justification(request.getJustification())
                .approvalLetter(request.getApprovalLetter())
                .msmeCountWithoutTraders(request.getMsmeCountWithoutTraders())
                .memberDirectoryAvailable(request.getMemberDirectoryAvailable())
                .buildingType(request.getBuildingType())
                .declarationSigned(request.getDeclarationSigned())
                .electricityBill(request.getElectricityBill())
                .telephoneBill(request.getTelephoneBill())
                .itInfrastructureAvailable(request.getItInfrastructureAvailable())
                .infrastructureType(request.getInfrastructureType())
                .secretariatStaffAvailable(request.getSecretariatStaffAvailable())
                .websiteAvailable(request.getWebsiteAvailable())
                .websiteUrl(request.getWebsiteUrl())
                .paidServicesAvailable(request.getPaidServicesAvailable())
                .paidServicesDetails(request.getPaidServicesDetails())
                .secretariatStaff(toSecretariatStaff(request.getSecretariatStaff()))
                .adverseRemarksAvailable(request.getAdverseRemarksAvailable())
                .adverseRemarks(request.getAdverseRemarks())
                .webReport(request.getWebReport())
                .selectionCriteria(request.getSelectionCriteria())
                .willingnessComments(request.getWillingnessComments())
                .workedWithSidbiBefore(request.getWorkedWithSidbiBefore())
                .grantProposed(request.getGrantProposed())
                .grantDetails(request.getGrantDetails())
                .envisagedOutput(request.getEnvisagedOutput())
                .envisagedOutcome(request.getEnvisagedOutcome())
                .envisagedImpact(request.getEnvisagedImpact())
                .sde(request.getSde())
                .isSidbeApproved(Boolean.TRUE.equals(request.getIsSidbeApproved()) || isSidbiSdeCaller)
                .sidbeApprovedByUser(sidbiApprover)
                .createdBy(request.getCreatedBy())
                .build();
    }

    public void applyUpdateRequest(
            IndustryAssociationRegistration registration,
            UpdateIndustryAssociationRegistrationRequest request) {

        if (request.getState() != null) registration.setState(request.getState());
        if (request.getIsEligibleMatricsAdded() != null) registration.setIsEligibleMatricsAdded(request.getIsEligibleMatricsAdded());
        if (request.getEmail() != null) registration.setEmail(request.getEmail());
        if (request.getPanNo() != null) registration.setPanNo(request.getPanNo());
        if (request.getIndustryAssociationName() != null) registration.setIndustryAssociationName(request.getIndustryAssociationName());
        if (request.getConstitutionType() != null) registration.setConstitutionType(request.getConstitutionType());
        if (request.getConstitutionOther() != null) registration.setConstitutionOther(request.getConstitutionOther());
        if (request.getIncorporationDate() != null) registration.setIncorporationDate(request.getIncorporationDate());
        if (request.getIncorporationCertificate() != null) registration.setIncorporationCertificate(request.getIncorporationCertificate());
        if (request.getIaType() != null) registration.setIaType(request.getIaType());
        if (request.getConstitutionProof() != null) registration.setConstitutionProof(request.getConstitutionProof());
        if (request.getDistrict() != null) registration.setDistrict(request.getDistrict());
        if (request.getPincode() != null) registration.setPincode(request.getPincode());
        if (request.getApexHolderName() != null) registration.setApexHolderName(request.getApexHolderName());
        if (request.getApexHolderDesignation() != null) registration.setApexHolderDesignation(request.getApexHolderDesignation());
        if (request.getApexHolderMobile() != null) registration.setApexHolderMobile(request.getApexHolderMobile());
        if (request.getApexHolderEmail() != null) registration.setApexHolderEmail(request.getApexHolderEmail());
        if (request.getAddressProofType() != null) registration.setAddressProofType(request.getAddressProofType());
        if (request.getAddressProof() != null) registration.setAddressProof(request.getAddressProof());
        if (request.getIdProofType() != null) registration.setIdProofType(request.getIdProofType());
        if (request.getIdProof() != null) registration.setIdProof(request.getIdProof());
        if (request.getNodalName() != null) registration.setNodalName(request.getNodalName());
        if (request.getNodalDesignation() != null) registration.setNodalDesignation(request.getNodalDesignation());
        if (request.getNodalMobile() != null) registration.setNodalMobile(request.getNodalMobile());
        if (request.getNodalEmail() != null) registration.setNodalEmail(request.getNodalEmail());
        if (request.getSidbiBranch() != null) registration.setSidbiBranch(request.getSidbiBranch());
        if (request.getSidbiBranchName() != null) registration.setSidbiBranchName(request.getSidbiBranchName());
        if (request.getMappedWithCluster() != null) registration.setMappedWithCluster(request.getMappedWithCluster());
        if (request.getClusterName() != null) registration.setClusterName(request.getClusterName());
        if (request.getMappedWithImportantDistrict() != null) registration.setMappedWithImportantDistrict(request.getMappedWithImportantDistrict());
        if (request.getDistrictMsmeCount() != null) registration.setDistrictMsmeCount(request.getDistrictMsmeCount());
        if (request.getActiveMembersAbove200() != null) registration.setActiveMembersAbove200(request.getActiveMembersAbove200());
        if (request.getActiveMembersCount() != null) registration.setActiveMembersCount(request.getActiveMembersCount());
        if (request.getJustification() != null) registration.setJustification(request.getJustification());
        if (request.getApprovalLetter() != null) registration.setApprovalLetter(request.getApprovalLetter());
        if (request.getMsmeCountWithoutTraders() != null) registration.setMsmeCountWithoutTraders(request.getMsmeCountWithoutTraders());
        if (request.getMemberDirectoryAvailable() != null) registration.setMemberDirectoryAvailable(request.getMemberDirectoryAvailable());
        if (request.getBuildingType() != null) registration.setBuildingType(request.getBuildingType());
        if (request.getDeclarationSigned() != null) registration.setDeclarationSigned(request.getDeclarationSigned());
        if (request.getElectricityBill() != null) registration.setElectricityBill(request.getElectricityBill());
        if (request.getTelephoneBill() != null) registration.setTelephoneBill(request.getTelephoneBill());
        if (request.getItInfrastructureAvailable() != null) registration.setItInfrastructureAvailable(request.getItInfrastructureAvailable());
        if (request.getInfrastructureType() != null) registration.setInfrastructureType(request.getInfrastructureType());
        if (request.getSecretariatStaffAvailable() != null) registration.setSecretariatStaffAvailable(request.getSecretariatStaffAvailable());
        if (request.getWebsiteAvailable() != null) registration.setWebsiteAvailable(request.getWebsiteAvailable());
        if (request.getWebsiteUrl() != null) registration.setWebsiteUrl(request.getWebsiteUrl());
        if (request.getPaidServicesAvailable() != null) registration.setPaidServicesAvailable(request.getPaidServicesAvailable());
        if (request.getPaidServicesDetails() != null) registration.setPaidServicesDetails(request.getPaidServicesDetails());
        if (request.getSecretariatStaff() != null) registration.setSecretariatStaff(toSecretariatStaff(request.getSecretariatStaff()));
        if (request.getAdverseRemarksAvailable() != null) registration.setAdverseRemarksAvailable(request.getAdverseRemarksAvailable());
        if (request.getAdverseRemarks() != null) registration.setAdverseRemarks(request.getAdverseRemarks());
        if (request.getWebReport() != null) registration.setWebReport(request.getWebReport());
        if (request.getSelectionCriteria() != null) registration.setSelectionCriteria(request.getSelectionCriteria());
        if (request.getWillingnessComments() != null) registration.setWillingnessComments(request.getWillingnessComments());
        if (request.getWorkedWithSidbiBefore() != null) registration.setWorkedWithSidbiBefore(request.getWorkedWithSidbiBefore());
        if (request.getGrantProposed() != null) registration.setGrantProposed(request.getGrantProposed());
        if (request.getGrantDetails() != null) registration.setGrantDetails(request.getGrantDetails());
        if (request.getEnvisagedOutput() != null) registration.setEnvisagedOutput(request.getEnvisagedOutput());
        if (request.getEnvisagedOutcome() != null) registration.setEnvisagedOutcome(request.getEnvisagedOutcome());
        if (request.getEnvisagedImpact() != null) registration.setEnvisagedImpact(request.getEnvisagedImpact());
        if (request.getSde() != null) registration.setSde(request.getSde());
        if (request.getIsActive() != null) registration.setIsActive(request.getIsActive());
        if (request.getUpdatedBy() != null) registration.setUpdatedBy(request.getUpdatedBy());
    }

    public IndustryAssociationRegistrationResponse toResponse(IndustryAssociationRegistration registration) {
        return IndustryAssociationRegistrationResponse.builder()
                .id(registration.getId())
                .state(registration.getState())
                .isEligibleMatricsAdded(registration.getIsEligibleMatricsAdded())
                .email(registration.getEmail())
                .panNo(registration.getPanNo())
                .industryAssociationName(registration.getIndustryAssociationName())
                .constitutionType(registration.getConstitutionType())
                .constitutionOther(registration.getConstitutionOther())
                .incorporationDate(registration.getIncorporationDate())
                .incorporationCertificate(registration.getIncorporationCertificate())
                .iaType(registration.getIaType())
                .constitutionProof(registration.getConstitutionProof())
                .district(registration.getDistrict())
                .pincode(registration.getPincode())
                .apexHolderName(registration.getApexHolderName())
                .apexHolderDesignation(registration.getApexHolderDesignation())
                .apexHolderMobile(registration.getApexHolderMobile())
                .apexHolderEmail(registration.getApexHolderEmail())
                .addressProofType(registration.getAddressProofType())
                .addressProof(registration.getAddressProof())
                .idProofType(registration.getIdProofType())
                .idProof(registration.getIdProof())
                .nodalName(registration.getNodalName())
                .nodalDesignation(registration.getNodalDesignation())
                .nodalMobile(registration.getNodalMobile())
                .nodalEmail(registration.getNodalEmail())
                .sidbiBranch(registration.getSidbiBranch())
                .sidbiBranchName(registration.getSidbiBranchName())
                .mappedWithCluster(registration.getMappedWithCluster())
                .clusterName(registration.getClusterName())
                .mappedWithImportantDistrict(registration.getMappedWithImportantDistrict())
                .districtMsmeCount(registration.getDistrictMsmeCount())
                .activeMembersAbove200(registration.getActiveMembersAbove200())
                .activeMembersCount(registration.getActiveMembersCount())
                .justification(registration.getJustification())
                .approvalLetter(registration.getApprovalLetter())
                .msmeCountWithoutTraders(registration.getMsmeCountWithoutTraders())
                .memberDirectoryAvailable(registration.getMemberDirectoryAvailable())
                .buildingType(registration.getBuildingType())
                .declarationSigned(registration.getDeclarationSigned())
                .electricityBill(registration.getElectricityBill())
                .telephoneBill(registration.getTelephoneBill())
                .itInfrastructureAvailable(registration.getItInfrastructureAvailable())
                .infrastructureType(registration.getInfrastructureType())
                .secretariatStaffAvailable(registration.getSecretariatStaffAvailable())
                .websiteAvailable(registration.getWebsiteAvailable())
                .websiteUrl(registration.getWebsiteUrl())
                .paidServicesAvailable(registration.getPaidServicesAvailable())
                .paidServicesDetails(registration.getPaidServicesDetails())
                .secretariatStaff(toSecretariatStaffDtos(registration.getSecretariatStaff()))
                .adverseRemarksAvailable(registration.getAdverseRemarksAvailable())
                .adverseRemarks(registration.getAdverseRemarks())
                .webReport(registration.getWebReport())
                .selectionCriteria(registration.getSelectionCriteria())
                .willingnessComments(registration.getWillingnessComments())
                .workedWithSidbiBefore(registration.getWorkedWithSidbiBefore())
                .grantProposed(registration.getGrantProposed())
                .grantDetails(registration.getGrantDetails())
                .envisagedOutput(registration.getEnvisagedOutput())
                .envisagedOutcome(registration.getEnvisagedOutcome())
                .envisagedImpact(registration.getEnvisagedImpact())
                .sde(registration.getSde())
                .isSidbeApproved(registration.getIsSidbeApproved())
                .sidbeApprovedByUserId(registration.getSidbeApprovedByUser() != null
                        ? registration.getSidbeApprovedByUser().getId() : null)
                .sidbeApprovedByUsername(registration.getSidbeApprovedByUser() != null
                        ? registration.getSidbeApprovedByUser().getUsername() : null)
                .isActive(registration.getIsActive())
                .createdAt(registration.getCreatedAt())
                .updatedAt(registration.getUpdatedAt())
                .createdBy(registration.getCreatedBy())
                .updatedBy(registration.getUpdatedBy())
                .build();
    }

    private List<SecretariatStaff> toSecretariatStaff(List<SecretariatStaffDto> staffDtos) {
        if (staffDtos == null) {
            return null;
        }
        return staffDtos.stream()
                .map(d -> new SecretariatStaff(d.getName(), d.getContact(), d.getEmail()))
                .toList();
    }

    private List<SecretariatStaffDto> toSecretariatStaffDtos(List<SecretariatStaff> staff) {
        if (staff == null) {
            return null;
        }
        return staff.stream()
                .map(s -> new SecretariatStaffDto(s.getName(), s.getContact(), s.getEmail()))
                .toList();
    }
}

