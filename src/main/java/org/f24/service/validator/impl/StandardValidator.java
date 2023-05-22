package org.f24.service.validator.impl;

import java.io.IOException;

import org.f24.dto.form.F24Standard;
import org.f24.exception.ResourceException;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;

public class StandardValidator extends FormValidator {

    private F24Standard form;

    public StandardValidator(String schemaPath, F24Standard form) {
        super(schemaPath, form);
        this.form = form;
    }

    private void validateSections() throws ResourceException {
        validateDebitandCredit(this.form.getTreasurySection().getTaxList());
        validateDebitandCredit(this.form.getInpsSection().getInpsRecordList());
        validateDebitandCredit(this.form.getRegionSection().getRegionRecordList());
        validateDebitandCredit(this.form.getLocalTaxSection().getLocalTaxRecordList());
        validateDebitandCredit(this.form.getSocialSecuritySection().getInailRecords());
        validateDebitandCredit(this.form.getSocialSecuritySection().getSocialSecurityRecordList());
    }


    @Override
    public void validate() throws ProcessingException, IOException, ResourceException {
        super.validate();
        validateSections();
    }

}
