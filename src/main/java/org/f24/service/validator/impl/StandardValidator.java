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
        if(this.form.getTreasurySection() != null)
            validateDebitandCredit(this.form.getTreasurySection().getTaxList());
        if(this.form.getInpsSection() != null)
            validateDebitandCredit(this.form.getInpsSection().getInpsRecordList());
        if(this.form.getRegionSection() != null)
            validateDebitandCredit(this.form.getRegionSection().getRegionRecordList());
        if(this.form.getLocalTaxSection() != null)
            validateDebitandCredit(this.form.getLocalTaxSection().getLocalTaxRecordList());
        if(this.form.getSocialSecuritySection() != null)
            validateDebitandCredit(this.form.getSocialSecuritySection().getInailRecords());
        if(this.form.getSocialSecuritySection() != null)
            validateDebitandCredit(this.form.getSocialSecuritySection().getSocialSecurityRecordList());
    }


    @Override
    public void validate() throws ProcessingException, IOException, ResourceException {
        super.validate();
        validateSections();
    }

    @Override
    public void validateWithoutTaxCode() throws ProcessingException, IOException, ResourceException {
        super.validateWithoutTaxCode();
        validateSections();
    }
}
