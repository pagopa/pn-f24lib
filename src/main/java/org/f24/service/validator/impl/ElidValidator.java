package org.f24.service.validator.impl;

import org.f24.dto.form.F24Elid;

public class ElidValidator extends FormValidator {

    private F24Elid form;

    public ElidValidator(String schemaPath,  F24Elid form) {
        super(schemaPath, form);
    }

}
