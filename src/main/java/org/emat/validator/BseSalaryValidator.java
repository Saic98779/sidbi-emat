package org.emat.validator;

import lombok.RequiredArgsConstructor;
import org.emat.entity.BseSalary;
import org.emat.exception.EntityNotFoundException;
import org.emat.repository.BseSalaryRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BseSalaryValidator {

    private static final String BSE_SALARY_NOT_FOUND_MESSAGE = "VendorDisbursement not found with id: ";

    private final BseSalaryRepository bseSalaryRepository;

    public BseSalary getByIdOrThrow(Long id) {
        return bseSalaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(BSE_SALARY_NOT_FOUND_MESSAGE + id));
    }
}

