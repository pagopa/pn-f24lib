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
        validateDebitandCredit(this.form.getTreasurySection().getTaxList());
        validateDebitandCredit(this.form.getInpsSection().getInpsRecordList());
        validateDebitandCredit(this.form.getRegionSection().getRegionRecordList());
        validateDebitandCredit(this.form.getLocalTaxSection().getLocalTaxRecordList());
    }

    @Override
    public void validate() throws ProcessingException, IOException, ResourceException {
        super.validate();
        validateSections();
    }

}
