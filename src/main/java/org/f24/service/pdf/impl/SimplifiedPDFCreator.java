package org.f24.service.pdf.impl;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import org.f24.dto.component.*;
import org.f24.dto.form.F24Simplified;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.util.CreatorHelper;
import org.f24.service.pdf.util.FieldEnum;
import org.f24.service.pdf.PDFCreator;
import org.f24.service.pdf.util.PDFFormManager;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class SimplifiedPDFCreator extends PDFFormManager implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24Semplificato.pdf";
    private static final int MOTIVE_RECORDS_NUMBER = 10;

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
        if(header != null) {
            setField(FieldEnum.DELEGATION.getName(), header.getDelegationTo());
            setField(FieldEnum.AGENCY.getName(), header.getAgency());
            setField(FieldEnum.AGENCY_PROVINCE.getName(), header.getProvince());
        }
    }

    private void setPersonData() throws ResourceException {
        PersonData personData = this.form.getTaxPayer().getPersonData();
        if(personData != null && personData.getPersonalData() != null) {
            PersonalData personalData = personData.getPersonalData();
            setField(FieldEnum.CORPORATE_NAME.getName(), personalData.getSurname());
            setField(FieldEnum.NAME.getName(), personalData.getName());
            setField(FieldEnum.BIRTH_DATE.getName(), personalData.getBirthDate().replace("-", ""));
            setField(FieldEnum.SEX.getName(), personalData.getSex());
            setField(FieldEnum.BIRTH_PLACE.getName(), personalData.getBirthPlace());
            setField(FieldEnum.BIRTH_PROVINCE.getName(), personalData.getBirthProvince());
        }
    }

    private void setCompanyData() throws ResourceException {
        CompanyData companyData = this.form.getTaxPayer().getCompanyData();
        if(companyData != null) {
            setField(FieldEnum.CORPORATE_NAME.getName(), companyData.getName());
        }
    }

    private void setRegistryData() throws ResourceException {
        setPersonData();
        setCompanyData();
    }

    private void setTaxPayer() throws ResourceException {
        TaxPayer taxPayer = this.form.getTaxPayer();
        if(taxPayer != null) {
            setField(FieldEnum.TAX_CODE.getName(), taxPayer.getTaxCode());
            setField(FieldEnum.OFFICE_CODE.getName(), taxPayer.getOfficeCode());
            setField(FieldEnum.DOCUMENT_CODE.getName(), taxPayer.getDocumentCode());
            setField(FieldEnum.RELATIVE_TAX_CODE.getName(), taxPayer.getRelativePersonTaxCode());
            setField(FieldEnum.ID_CODE.getName(), taxPayer.getIdCode());
            setRegistryData();
        }
    }

    private void setPaymentMotiveRecordCheckboxes(PaymentReasonRecord paymentReasonRecord, int index) throws ResourceException {
        if (paymentReasonRecord.getReconsideration() == Boolean.TRUE) setField(FieldEnum.RECONSIDERATION.getName() + index, "X");
        if (paymentReasonRecord.getPropertiesChanges() == Boolean.TRUE) setField(FieldEnum.PROPERTIES_CHANGED.getName() + index, "X");
        if (paymentReasonRecord.getAdvancePayment() == Boolean.TRUE) setField(FieldEnum.ADVANCE_PAYMENT.getName() + index, "X");
        if (paymentReasonRecord.getFullPayment() == Boolean.TRUE) setField(FieldEnum.FULL_PAYMENT.getName() + index, "X");
        if (paymentReasonRecord.getNumberOfProperties() != null) setField(FieldEnum.NUMBER_OF_PROPERTIES.getName() + index, paymentReasonRecord.getNumberOfProperties());
    }

    private void setPaymentMotiveRecordAmounts(PaymentReasonRecord paymentReasonRecord, int index) throws ResourceException {
        if(paymentReasonRecord.getDeduction() != null) {
            setField(FieldEnum.DEDUCTION.getName() + index, helper.getMoney(Integer.parseInt(paymentReasonRecord.getDeduction())));
        }
        if(paymentReasonRecord.getDebitAmount() != null) {
            setField(FieldEnum.DEBIT_AMOUNT.getName() + index, helper.getMoney(Integer.parseInt(paymentReasonRecord.getDebitAmount())));
        }
        if(paymentReasonRecord.getCreditAmount() != null) {
            setField(FieldEnum.CREDIT_AMOUNT.getName() + index, helper.getMoney(Integer.parseInt(paymentReasonRecord.getCreditAmount())));
        }
    }

    private void setPaymentReasonSection(int copyIndex) {
        try {
            setField(FieldEnum.OPERATION_ID.getName(), this.form.getPaymentReasonSection().getOperationId());
            List<PaymentReasonRecord> paymentReasonRecordList = this.form.getPaymentReasonSection().getReasonRecordList();
            int limit = copyIndex * MOTIVE_RECORDS_NUMBER + MOTIVE_RECORDS_NUMBER;
            limit = Math.min(limit, paymentReasonRecordList.size());
            paymentReasonRecordList = paymentReasonRecordList.subList(copyIndex * MOTIVE_RECORDS_NUMBER, limit);
            for (int index = 1; index <= paymentReasonRecordList.size(); index++) {
                PaymentReasonRecord paymentReasonRecord = paymentReasonRecordList.get(index - 1);
                setField(FieldEnum.SECTION.getName() + index, paymentReasonRecord.getSection());
                setField(FieldEnum.TAX_TYPE_CODE.getName() + index, paymentReasonRecord.getTaxTypeCode());
                setField(FieldEnum.INSTITUTION_CODE.getName() + index, paymentReasonRecord.getInstitutionCode());
                setPaymentMotiveRecordCheckboxes(paymentReasonRecord, index);
                setField(FieldEnum.MONTH.getName() + index, paymentReasonRecord.getMonth());
                setField(FieldEnum.YEAR.getName() + index, paymentReasonRecord.getYear());
                setPaymentMotiveRecordAmounts(paymentReasonRecord, index);
            }
            setField(FieldEnum.TOTAL_AMOUNT.getName(), helper.getMoney(Integer.parseInt(this.form.getPaymentReasonSection().getTotalAmount().toString())));
        } catch (Exception e) {
            //
        }
    }

    private void setPaymentDetails() throws ResourceException {
        PaymentDetails paymentDetails = this.form.getPaymentDetails();
        if(paymentDetails != null) {
            setField(FieldEnum.DATE_OF_PAYMENT.getName(), paymentDetails.getPaymentDate().replace("-", ""));
            setField(FieldEnum.COMPANY.getName(), paymentDetails.getCompany());
            setField(FieldEnum.CAB_CODE.getName(), paymentDetails.getCabCode());
            setField(FieldEnum.CHECK_NUMBER.getName(), paymentDetails.getCheckNumber());
            setField(FieldEnum.ABI_CODE.getName(), paymentDetails.getAbiCode());
            setField(FieldEnum.BANK.getName(), paymentDetails.isBank() ? "X" : "");
            setField(FieldEnum.CIRCULAR.getName(), !paymentDetails.isBank() ? "X" : "");
            setField(FieldEnum.IBAN_CODE.getName(), paymentDetails.getIbanCode());
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
            if(this.form.getPaymentReasonSection().getReasonRecordList().size() > MOTIVE_RECORDS_NUMBER) {
                copy(((motiveRecordsCount + MOTIVE_RECORDS_NUMBER - 1) / MOTIVE_RECORDS_NUMBER) - 1);
            }

            for(int copyIndex = 0; copyIndex < getCopies().size(); copyIndex++) {
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
