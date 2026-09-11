package org.emat.service;

import java.util.List;
import org.emat.dto.EligibilityMatrixDto;
import org.emat.dto.RegistrationDropdownDto;

public interface EligibilityMatrixService {

    EligibilityMatrixDto create(EligibilityMatrixDto request);

    EligibilityMatrixDto getById(Long id);

    List<EligibilityMatrixDto> getAll();

    EligibilityMatrixDto getByRegistrationId(Long registrationId);

    EligibilityMatrixDto update(Long id, EligibilityMatrixDto request);

    void delete(Long id);

    List<RegistrationDropdownDto> getRegistrationDropdown();
}
