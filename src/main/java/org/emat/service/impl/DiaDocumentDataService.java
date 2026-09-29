package org.emat.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.emat.entity.BaseEntity;
import org.emat.entity.DisbursementNoteCapacityBuildingIa;
import org.emat.entity.DisbursementNoteCapacityBuildingIaOfficials;
import org.emat.entity.IndustryAssociationAppraisal;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.ActionPlanRepository;
import org.emat.repository.DisbursementCapexRepository;
import org.emat.repository.DisbursementNoteCapacityBuildingIaOfficialsRepository;
import org.emat.repository.DisbursementNoteCapacityBuildingIaRepository;
import org.emat.repository.MonthlySalaryDetailsRepository;
import org.emat.validator.IndustryAssociationAppraisalValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaDocumentDataService {
    private final IndustryAssociationAppraisalValidator validator;
    private final DisbursementCapexRepository capexRepository;
    private final DisbursementNoteCapacityBuildingIaRepository membersRepository;
    private final DisbursementNoteCapacityBuildingIaOfficialsRepository officialsRepository;
    private final ActionPlanRepository actionPlanRepository;
    private final MonthlySalaryDetailsRepository salaryRepository;
    private final DiaWordTemplate template;

    public byte[] docxByAppraisalId(Long appraisalId) {
        return docx(validator.getAppraisalOrThrow(appraisalId));
    }

    public byte[] docxByRegistrationId(Long registrationId) {
        return docx(validator.getAppraisalByRegistrationOrThrow(registrationId));
    }

    private byte[] docx(IndustryAssociationAppraisal appraisal) {
        var registration = appraisal.getRegistration();
        if (Boolean.FALSE.equals(appraisal.getIsActive()) || registration == null
                || Boolean.FALSE.equals(registration.getIsActive())) {
            throw new EntityNotFoundException("Active appraisal and IA registration are required for a DIA document");
        }
        Long registrationId = registration.getId();
        return template.render(appraisal,
                capexRepository.findByRegistrationId(registrationId)
                        .filter(item -> !Boolean.FALSE.equals(item.getIsActive())).orElse(null),
                active(membersRepository.findByRegistrationId(registrationId), DisbursementNoteCapacityBuildingIa::getId),
                active(officialsRepository.findByRegistrationId(registrationId), DisbursementNoteCapacityBuildingIaOfficials::getId),
                actionPlanRepository.findByRegistrationIdAndIsActiveTrueOrderByIdAsc(registrationId),
                salaryRepository.findForDiaDocument(registrationId));
    }

    private <T extends BaseEntity> List<T> active(List<T> values, Function<T, Long> id) {
        return values.stream().filter(item -> !Boolean.FALSE.equals(item.getIsActive()))
                .sorted(Comparator.comparing(id, Comparator.nullsLast(Comparator.naturalOrder()))).toList();
    }
}
