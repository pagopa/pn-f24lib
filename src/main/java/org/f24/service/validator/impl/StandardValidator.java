package org.f24.service.validator.impl;

import org.f24.dto.form.F24Standard;
import org.f24.service.validator.Validator;

public class StandardValidator implements Validator {

    private F24Standard form;

    public StandardValidator(F24Standard form) {
        this.form = form;
    }

    @Override
    public void validate() {

    }
}
