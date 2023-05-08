package org.f24.service.validator.impl;

import java.io.IOException;
import java.util.List;

import org.f24.dto.component.Record;
import org.f24.dto.form.F24Standard;
import org.f24.exception.ErrorEnum;
import org.f24.exception.ResourceException;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;

public class StandardValidator extends FormValidator {

    private F24Standard form;

    public StandardValidator(String schemaPath, F24Standard form) {
        super(schemaPath, form);
        this.form = form;
    }

    private void validateSections() throws ResourceException {
        List<? extends Record> treasuryRecordList = this.form.getTreasurySection().getTaxList();
        validateDebitandCredit(treasuryRecordList);

        List<? extends Record> inpsRecordList = this.form.getInpsSection().getInpsRecordList();
        validateDebitandCredit(inpsRecordList);

        List<? extends Record> regionRecordList = this.form.getRegionSection().getRegionRecordList();
        validateDebitandCredit(regionRecordList);

        List<? extends Record> imuRecordList = this.form.getLocalTaxSection().getLocalTaxRecordList();
        validateDebitandCredit(imuRecordList);

        List<? extends Record> inailRecordList = this.form.getSocialSecuritySection().getInailRecords();
        validateDebitandCredit(inailRecordList);

        List<? extends Record> socSecurityRecordList = this.form.getSocialSecuritySection().getSocialSecurityRecordList();
        validateDebitandCredit(socSecurityRecordList);

    }

    private void validateDebitandCredit(List<? extends Record> targetRecordList) throws ResourceException {
        if (targetRecordList != null) {
            for (Record recordItem : targetRecordList) {
                if(recordItem.getDebitAmount() != null && recordItem.getCreditAmount() != null ) {
                    if (!recordItem.getDebitAmount().equals("0") && !recordItem.getCreditAmount().equals("0")) {
                        throw new ResourceException(ErrorEnum.MOTIVE_RECORD.getMessage()); // TODO comment for gen test
                    }
                }
            }
        }
    }

    @Override
    public void validate() throws ProcessingException, IOException, ResourceException {
        super.validate();
        validateSections();
    }

}
