package org.f24.service;

import org.f24.dto.form.F24Form;
import org.f24.service.validator.Validator;
import org.f24.service.validator.ValidatorFactory;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {

    public void validatePDF(F24Form form) {
        Validator validator = ValidatorFactory.createValidator(form);
        validator.validate();
    }

}
