package org.f24.service.validator.impl;

import java.io.IOException;
import java.util.List;

import org.f24.dto.form.F24Simplified;
import org.f24.exception.ResourceException;
import org.f24.service.validator.ErrorEnum;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;

import org.f24.dto.component.PaymentMotiveRecord;

public class SimplifiedValidator extends FormValidator {

    private F24Simplified form;
    private String schemaPath;

    public SimplifiedValidator(String schemaPath, F24Simplified form) {
        super(schemaPath, form);
        this.schemaPath = schemaPath;
        this.form = form;
    }

    private void validateDebitAndCreditFields() throws ResourceException {
        List<PaymentMotiveRecord> paymentItemsList = this.form.getPaymentMotiveSection().getMotiveRecordList();

        for (PaymentMotiveRecord paymentItem : paymentItemsList) {
            if (paymentItem.getDebitAmount() != "0" && paymentItem.getCreditAmount() != "0") {
                throw new ResourceException(ErrorEnum.MOTIVE_RECORD.getMessage());
            }
        };
    }
    
    @Override
    public void validate() throws ProcessingException, IOException, ResourceException {
        super.validate();
        validateDebitAndCreditFields();
    }

}
