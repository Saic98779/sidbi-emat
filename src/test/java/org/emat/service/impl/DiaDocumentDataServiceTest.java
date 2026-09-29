package org.emat.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import org.emat.entity.DisbursementNoteCapacityBuildingIa;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.*;
import org.emat.validator.IndustryAssociationAppraisalValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DiaDocumentDataServiceTest {
    @Mock IndustryAssociationAppraisalValidator validator;
    @Mock DisbursementCapexRepository capexRepository;
    @Mock DisbursementNoteCapacityBuildingIaRepository membersRepository;
    @Mock DisbursementNoteCapacityBuildingIaOfficialsRepository officialsRepository;
    @Mock ActionPlanRepository actionPlanRepository;
    @Mock MonthlySalaryDetailsRepository salaryRepository;
    @Mock DiaWordTemplate template;
    @InjectMocks DiaDocumentDataService service;

    @Test
    void registrationRouteResolvesAppraisalAndFetchesOnlyItsRegistrationData() {
        var appraisal = DiaDocumentTemplateTest.fixture();
        when(validator.getAppraisalByRegistrationOrThrow(42L)).thenReturn(appraisal);
        when(capexRepository.findByRegistrationId(42L)).thenReturn(Optional.empty());
        when(membersRepository.findByRegistrationId(42L)).thenReturn(List.of(
                DisbursementNoteCapacityBuildingIa.builder().id(5L).isActive(false).build()));
        when(template.render(eq(appraisal), isNull(), eq(List.of()), eq(List.of()), eq(List.of()), eq(List.of())))
                .thenReturn(new byte[] {1, 2});
        assertArrayEquals(new byte[] {1, 2}, service.docxByRegistrationId(42L));
        verify(validator, never()).getAppraisalOrThrow(anyLong());
        verify(actionPlanRepository).findByRegistrationIdAndIsActiveTrueOrderByIdAsc(42L);
        verify(salaryRepository).findForDiaDocument(42L);
        verify(officialsRepository).findByRegistrationId(42L);
    }

    @Test
    void appraisalRouteDoesNotInterpretItsIdAsARegistrationId() {
        var appraisal = DiaDocumentTemplateTest.fixture();
        when(validator.getAppraisalOrThrow(221L)).thenReturn(appraisal);
        service.docxByAppraisalId(221L);
        verify(capexRepository).findByRegistrationId(42L);
        verify(validator, never()).getAppraisalByRegistrationOrThrow(anyLong());
    }

    @Test
    void inactiveAppraisalCannotBeExported() {
        var appraisal = DiaDocumentTemplateTest.fixture();
        appraisal.setIsActive(false);
        when(validator.getAppraisalOrThrow(221L)).thenReturn(appraisal);
        assertThrows(EntityNotFoundException.class, () -> service.docxByAppraisalId(221L));
        verifyNoInteractions(capexRepository, template);
    }

    @Test
    void registrationWithoutAppraisalDoesNotFallBackToAnotherAppraisal() {
        when(validator.getAppraisalByRegistrationOrThrow(42L)).thenThrow(new EntityNotFoundException("No appraisal"));
        assertThrows(EntityNotFoundException.class, () -> service.docxByRegistrationId(42L));
        verifyNoInteractions(capexRepository, template);
    }
}
