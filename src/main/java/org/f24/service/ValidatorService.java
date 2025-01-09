package org.f24.service;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.f24.dto.form.F24Form;
import org.f24.exception.ResourceException;
import org.f24.service.validator.Validator;
import org.f24.service.validator.ValidatorFactory;

import java.io.IOException;

public class ValidatorService {

    public void validatePDF(F24Form form) throws IOException, ProcessingException, ResourceException {
        Validator validator = ValidatorFactory.createValidator(form);
        validator.validate();
    }

    public void validatePdfWithoutTaxCode(F24Form form) throws IOException, ProcessingException, ResourceException {
        Validator validator = ValidatorFactory.createValidator(form);
        validator.validateWithoutTaxCode();
    }

}
