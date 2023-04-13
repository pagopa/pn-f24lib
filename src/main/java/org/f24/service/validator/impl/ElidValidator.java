package org.f24.service.validator.impl;

import org.f24.dto.form.F24Elid;
import org.f24.service.validator.Validator;

public class ElidValidator implements Validator {

    private F24Elid form;

    public ElidValidator(F24Elid form) {
        this.form = form;
    }

    @Override
    public void validate() {

    }

}
