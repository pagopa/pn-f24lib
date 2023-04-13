package org.f24.service.validator.impl;

import org.f24.dto.form.F24Simplified;
import org.f24.service.validator.Validator;

public class SimplifiedValidator implements Validator {

    private F24Simplified form;

    public SimplifiedValidator(F24Simplified form) {
        this.form = form;
    }

    @Override
    public void validate() {

    }
}
