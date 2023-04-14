package org.f24.service.validator.impl;

import org.f24.dto.form.F24Standard;

public class StandardValidator extends FormValidator {

    private F24Standard form;

    public StandardValidator(String schemaPath, F24Standard form ) {
        super(schemaPath, form);
    }

}
