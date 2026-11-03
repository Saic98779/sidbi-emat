package org.emat.mapper;

import org.emat.dto.CreateGrantDisbursementRequest;
import org.emat.dto.GrantDisbursementResponse;
import org.emat.dto.UpdateGrantDisbursementRequest;
import org.emat.entity.GrantDisbursement;
import org.springframework.stereotype.Component;

@Component
public class GrantDisbursementMapper {

    public GrantDisbursement toEntity(CreateGrantDisbursementRequest request) {
        return GrantDisbursement.builder()
                .grantAmount(request.getGrantAmount())
                .hardIntervention(request.getHardIntervention())
                .bseSalary(request.getBseSalary())
                .capacityBuildingTrainings(request.getCapacityBuildingTrainings())
                .seminarsWorkshops(request.getSeminarsWorkshops())
                .ratingAccreditations(request.getRatingAccreditations())
                .others(request.getOthers())
                .disbursementAmount(request.getDisbursementAmount())
                .disbursementDate(request.getDisbursementDate())
                .expectedDisbursementDate(request.getExpectedDisbursementDate())
                .isActive(true)
                .build();
    }

    public void applyUpdateRequest(
            GrantDisbursement disbursement, UpdateGrantDisbursementRequest request) {
        if (request.getGrantAmount() != null) disbursement.setGrantAmount(request.getGrantAmount());
        if (request.getHardIntervention() != null)
            disbursement.setHardIntervention(request.getHardIntervention());
        if (request.getBseSalary() != null) disbursement.setBseSalary(request.getBseSalary());
        if (request.getCapacityBuildingTrainings() != null)
            disbursement.setCapacityBuildingTrainings(request.getCapacityBuildingTrainings());
        if (request.getSeminarsWorkshops() != null)
            disbursement.setSeminarsWorkshops(request.getSeminarsWorkshops());
        if (request.getRatingAccreditations() != null)
            disbursement.setRatingAccreditations(request.getRatingAccreditations());
        if (request.getOthers() != null) disbursement.setOthers(request.getOthers());
        if (request.getDisbursementAmount() != null)
            disbursement.setDisbursementAmount(request.getDisbursementAmount());
        if (request.getDisbursementDate() != null)
            disbursement.setDisbursementDate(request.getDisbursementDate());
        if (request.getExpectedDisbursementDate() != null)
            disbursement.setExpectedDisbursementDate(request.getExpectedDisbursementDate());
    }

    public GrantDisbursementResponse toResponse(GrantDisbursement disbursement) {
        return GrantDisbursementResponse.builder()
                .id(disbursement.getId())
                .grantAmount(disbursement.getGrantAmount())
                .hardIntervention(disbursement.getHardIntervention())
                .bseSalary(disbursement.getBseSalary())
                .capacityBuildingTrainings(disbursement.getCapacityBuildingTrainings())
                .seminarsWorkshops(disbursement.getSeminarsWorkshops())
                .ratingAccreditations(disbursement.getRatingAccreditations())
                .others(disbursement.getOthers())
                .disbursementAmount(disbursement.getDisbursementAmount())
                .disbursementDate(disbursement.getDisbursementDate())
                .expectedDisbursementDate(disbursement.getExpectedDisbursementDate())
                .createdAt(disbursement.getCreatedAt())
                .updatedAt(disbursement.getUpdatedAt())
                .createdBy(disbursement.getCreatedBy())
                .updatedBy(disbursement.getUpdatedBy())
                .isActive(disbursement.getIsActive())
                .build();
    }
}
