package org.f24.service.validator.impl;

import org.f24.dto.form.F24Simplified;

public class SimplifiedValidator extends FormValidator {

    private F24Simplified form;

    public SimplifiedValidator(String schemaPath, F24Simplified form) {
        super(schemaPath, form);
    }

}
