package org.f24.service.pdf.impl;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import org.f24.dto.component.*;
import org.f24.dto.form.F24Simplified;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.util.CreatorHelper;
import org.f24.service.pdf.PDFCreator;
import org.f24.service.pdf.util.PDFFormManager;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static org.f24.service.pdf.util.FieldEnum.*;

public class SimplifiedPDFCreator extends PDFFormManager implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24Semplificato.pdf";
    private static final int REASON_RECORDS_NUMBER = 10;

    private F24Simplified form;
    private CreatorHelper helper = new CreatorHelper();

    /**
     * Constructs Simplified PDF Creator.
     *
     * @param form F24Simplifiedcomponent (DTO for Simplified Form).
     */
    public SimplifiedPDFCreator(F24Simplified form) {
        this.form = form;
    }

    private void setHeader() throws ResourceException {
        Header header = this.form.getHeader();
        if (header != null) {
            setField(DELEGATION.getName(), header.getDelegationTo());
            setField(AGENCY.getName(), header.getAgency());
            setField(AGENCY_PROVINCE.getName(), header.getProvince());
        }
    }

    private void setPersonData() throws ResourceException {
        PersonData personData = this.form.getTaxPayer().getPersonData();
        if (personData != null && personData.getPersonalData() != null) {
            PersonalData personalData = personData.getPersonalData();
            setField(CORPORATE_NAME.getName(), personalData.getSurname());
            setField(NAME.getName(), personalData.getName());
            setField(BIRTH_DATE.getName(), personalData.getBirthDate().replace("-", ""));
            setField(SEX.getName(), personalData.getSex());
            setField(BIRTH_PLACE.getName(), personalData.getBirthPlace());
            setField(BIRTH_PROVINCE.getName(), personalData.getBirthProvince());
        }
    }

    private void setCompanyData() throws ResourceException {
        CompanyData companyData = this.form.getTaxPayer().getCompanyData();
        if (companyData != null) {
            setField(CORPORATE_NAME.getName(), companyData.getName());
        }
    }

    private void setRegistryData() throws ResourceException {
        setPersonData();
        setCompanyData();
    }

    private void setTaxPayer() throws ResourceException {
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
            setField(DEDUCTION.getName() + index, helper.getMoney(Integer.parseInt(paymentReasonRecord.getDeduction())));
        }
        if (paymentReasonRecord.getDebitAmount() != null) {
            setField(DEBIT_AMOUNT.getName() + index, helper.getMoney(Integer.parseInt(paymentReasonRecord.getDebitAmount())));
        }
        if (paymentReasonRecord.getCreditAmount() != null) {
            setField(CREDIT_AMOUNT.getName() + index, helper.getMoney(Integer.parseInt(paymentReasonRecord.getCreditAmount())));
        }
    }

    private void setPaymentReasonSection(int copyIndex) {
        try {
            setField(OPERATION_ID.getName(), this.form.getPaymentReasonSection().getOperationId());
            List<PaymentReasonRecord> paymentReasonRecordList = this.form.getPaymentReasonSection().getReasonRecordList();
            int limit = copyIndex * REASON_RECORDS_NUMBER + REASON_RECORDS_NUMBER;
            limit = Math.min(limit, paymentReasonRecordList.size());
            paymentReasonRecordList = paymentReasonRecordList.subList(copyIndex * REASON_RECORDS_NUMBER, limit);
            for (int index = 1; index <= paymentReasonRecordList.size(); index++) {
                PaymentReasonRecord paymentReasonRecord = paymentReasonRecordList.get(index - 1);
                setField(SECTION.getName() + index, paymentReasonRecord.getSection());
                setField(TAX_TYPE_CODE.getName() + index, paymentReasonRecord.getTaxTypeCode());
                setField(INSTITUTION_CODE.getName() + index, paymentReasonRecord.getInstitutionCode());
                setPaymentReasonRecordCheckboxes(paymentReasonRecord, index);
                setField(MONTH.getName() + index, paymentReasonRecord.getMonth());
                setField(YEAR.getName() + index, paymentReasonRecord.getYear());
                setPaymentReasonRecordAmounts(paymentReasonRecord, index);
            }
            setField(TOTAL_AMOUNT.getName(), helper.getMoney(Integer.parseInt(this.form.getPaymentReasonSection().getTotalAmount().toString())));
        } catch (Exception e) {
            //
        }
    }

    private void setPaymentDetails() throws ResourceException {
        PaymentDetails paymentDetails = this.form.getPaymentDetails();
        if (paymentDetails != null) {
            setField(DATE_OF_PAYMENT.getName(), paymentDetails.getPaymentDate().replace("-", ""));
            setField(COMPANY.getName(), paymentDetails.getCompany());
            setField(CAB_CODE.getName(), paymentDetails.getCabCode());
            setField(CHECK_NUMBER.getName(), paymentDetails.getCheckNumber());
            setField(ABI_CODE.getName(), paymentDetails.getAbiCode());
            setField(BANK.getName(), paymentDetails.isBank() ? "X" : "");
            setField(CIRCULAR.getName(), !paymentDetails.isBank() ? "X" : "");
            setField(IBAN_CODE.getName(), paymentDetails.getIbanCode());
        }
    }

    /**
     * Method which creates PDF Document for F24 Simplified Form.
     *
     * @return PDFDocument object with filled fields.
     */
    @Override
    public byte[] createPDF() {
        try {
            loadDoc(MODEL_NAME);

            int motiveRecordsCount = this.form.getPaymentReasonSection().getReasonRecordList().size();
            if (this.form.getPaymentReasonSection().getReasonRecordList().size() > REASON_RECORDS_NUMBER) {
                copy(((motiveRecordsCount + REASON_RECORDS_NUMBER - 1) / REASON_RECORDS_NUMBER) - 1);
            }

            for (int copyIndex = 0; copyIndex < getCopies().size(); copyIndex++) {
                setIndex(copyIndex);
                setHeader();
                setTaxPayer();
                setPaymentDetails();
                setPaymentReasonSection(copyIndex);
            }

            mergeCopies();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            getDoc().save(byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return ByteArrayBuilder.NO_BYTES;
        }
    }

}
