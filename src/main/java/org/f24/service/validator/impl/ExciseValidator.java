package org.f24.service.validator.impl;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.f24.dto.form.F24Excise;
import org.f24.exception.ResourceException;

import java.io.IOException;

public class ExciseValidator extends FormValidator {

    private F24Excise form;

    public ExciseValidator(String schemaPath, F24Excise form) {
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
    }

    @Override
    public void validate() throws ProcessingException, IOException, ResourceException {
        super.validate();
        validateSections();
    }

}
