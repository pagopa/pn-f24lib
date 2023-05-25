package org.f24.service.validator.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.f24.dto.form.F24Simplified;
import org.f24.exception.ErrorEnum;
import org.f24.exception.ResourceException;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;

import org.f24.dto.component.PaymentReasonRecord;

public class SimplifiedValidator extends FormValidator {

    private F24Simplified form;

    public SimplifiedValidator(String schemaPath, F24Simplified form) {
        super(schemaPath, form);
        this.form = form;
    }

    private void validateDebitAndCreditFields() throws ResourceException {
        List<PaymentReasonRecord> paymentItemsList = this.form.getPaymentReasonSection().getReasonRecordList();

        for (PaymentReasonRecord paymentItem : paymentItemsList) {
            if (paymentItem.getDebitAmount() != null && !Objects.equals(paymentItem.getDebitAmount(), "0") && paymentItem.getCreditAmount() != null && !Objects.equals(paymentItem.getCreditAmount(), "0")) {
                throw new ResourceException(ErrorEnum.MOTIVE_RECORD.getMessage());
            }
        }
    }
    
    @Override
    public void validate() throws ProcessingException, IOException, ResourceException {
        super.validate();
        validateDebitAndCreditFields();
    }

}
