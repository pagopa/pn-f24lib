package org.f24.service.validator.impl;

import org.f24.dto.form.F24Excise;

public class ExciseValidator extends FormValidator {

    private F24Excise form;

    public ExciseValidator(String schemaPath, F24Excise form) {
        super(schemaPath, form);
    }

}
