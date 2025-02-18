package org.f24.service.pdf.impl;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import org.f24.dto.component.PaymentReasonRecord;
import org.f24.dto.component.TaxPayer;
import org.f24.dto.form.F24Simplified;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.PDFCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.f24.service.pdf.util.FieldEnum.*;

public class SimplifiedPDFCreator extends FormPDFCreator implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24Semplificato.pdf";

    private final F24Simplified form;
    private final Logger logger = LoggerFactory.getLogger(SimplifiedPDFCreator.class.getName());

    /**
     * Constructs Simplified PDF Creator.
     *
     * @param form F24Simplifiedcomponent (DTO for Simplified Form).
     */
    public SimplifiedPDFCreator(F24Simplified form) {
        super(form);
        this.form = form;
    }

    @Override
    protected void setTaxPayer() throws ResourceException {
        TaxPayer taxPayer = this.form.getTaxPayer();
        if (taxPayer != null) {
            setField(TAX_CODE.getName(), taxPayer.getTaxCode());
            setField(OFFICE_CODE.getName(), taxPayer.getOfficeCode());
            setField(DOCUMENT_CODE.getName(), taxPayer.getDocumentCode());
            setField(RELATIVE_PERSON_TAX_CODE.getName(), taxPayer.getRelativePersonTaxCode());
            setField(ID_CODE.getName(), taxPayer.getIdCode());
            setRegistryData();
        }
    }

    private void setPaymentReasonRecordCheckboxes(PaymentReasonRecord paymentReasonRecord, int index) throws ResourceException {
        if (paymentReasonRecord.getReconsideration() == Boolean.TRUE) setField(RECONSIDERATION.getName() + index, "X");
        if (paymentReasonRecord.getPropertiesChanges() == Boolean.TRUE)
            setField(PROPERTIES_CHANGED.getName() + index, "X");
        if (paymentReasonRecord.getAdvancePayment() == Boolean.TRUE) setField(ADVANCE_PAYMENT.getName() + index, "X");
        if (paymentReasonRecord.getFullPayment() == Boolean.TRUE) setField(FULL_PAYMENT.getName() + index, "X");
        if (paymentReasonRecord.getNumberOfProperties() != null)
            setField(NUMBER_OF_PROPERTIES.getName() + index, paymentReasonRecord.getNumberOfProperties());
    }

    private void setPaymentReasonRecordAmounts(PaymentReasonRecord paymentReasonRecord, int index) throws ResourceException {
        if (paymentReasonRecord.getDeduction() != null) {
            setField(DEDUCTION.getName() + index, getMoney(Integer.parseInt(paymentReasonRecord.getDeduction())));
        }
        if (paymentReasonRecord.getDebitAmount() != null) {
            setField(DEBIT_AMOUNT.getName() + index, getMoney(Integer.parseInt(paymentReasonRecord.getDebitAmount())));
        }
        if (paymentReasonRecord.getCreditAmount() != null) {
            setField(CREDIT_AMOUNT.getName() + index, getMoney(Integer.parseInt(paymentReasonRecord.getCreditAmount())));
        }
    }

    private void setPaymentReasonSection(int copyIndex) {
        try {
            setField(OPERATION_ID.getName(), this.form.getPaymentReasonSection().getOperationId());
            List<PaymentReasonRecord> paymentReasonRecordList = this.form.getPaymentReasonSection().getReasonRecordList();
            int limit = copyIndex * REASON_RECORDS_NUMBER.getRecordsNum() + REASON_RECORDS_NUMBER.getRecordsNum();
            limit = Math.min(limit, paymentReasonRecordList.size());
            paymentReasonRecordList = paymentReasonRecordList.subList(copyIndex * REASON_RECORDS_NUMBER.getRecordsNum(), limit);
            for (int index = 1; index <= paymentReasonRecordList.size(); index++) {
                PaymentReasonRecord paymentReasonRecord = paymentReasonRecordList.get(index - 1);
                setField(SECTION.getName() + index, paymentReasonRecord.getSection());
                setField(TAX_TYPE_CODE.getName() + index, paymentReasonRecord.getTaxTypeCode());
                setField(MUNICIPALITY_CODE.getName() + index, paymentReasonRecord.getMunicipalityCode());
                setPaymentReasonRecordCheckboxes(paymentReasonRecord, index);
                setField(MONTH.getName() + index, paymentReasonRecord.getMonth());
                setField(YEAR.getName() + index, paymentReasonRecord.getYear());
                setPaymentReasonRecordAmounts(paymentReasonRecord, index);
            }
            setField(TOTAL_AMOUNT.getName(), getMoney(Integer.parseInt(this.form.getPaymentReasonSection().getTotalAmount().toString())));
        } catch (Exception e) {
            //
        }
    }

    @Override
    public int getPagesAmount() throws IOException {
        int totalPages = 0;
        loadDoc(MODEL_NAME);

        if(this.form.getPaymentReasonSection() != null) {
            int motiveRecordsCount = this.form.getPaymentReasonSection().getReasonRecordList().size();
            totalPages = getTotalPages(motiveRecordsCount, REASON_RECORDS_NUMBER.getRecordsNum(), totalPages);
        }
        copy(totalPages);

        return getCopies().size();
    }

    /**
     * Method which creates PDF Document for F24 Simplified Form.
     *
     * @return PDFDocument object with filled fields.
     */
    @Override
    public byte[] createPDF() {
        try {
            int copiesCount = getPagesAmount();

            for (int copyIndex = 0; copyIndex < copiesCount; copyIndex++) {
                setIndex(copyIndex);
                setHeader();
                setTaxPayer();
                setPaymentReasonSection(copyIndex);
                flat();
                updateCopy();
            }

            byte[] mergeCopies = mergeCopies();

            finalizeDoc();

            logger.info("simplified PDF is created");

            return mergeCopies;
        } catch (Exception e) {
            return ByteArrayBuilder.NO_BYTES;
        }
    }

}
