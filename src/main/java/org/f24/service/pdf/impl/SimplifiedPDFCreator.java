package org.f24.service.pdf.impl;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import org.f24.dto.component.*;
import org.f24.dto.form.F24Simplified;
import org.f24.service.pdf.CreatorHelper;
import org.f24.service.pdf.FieldEnum;
import org.f24.service.pdf.PDFCreator;
import org.f24.service.pdf.PDFFormManager;

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

    private void setHeader() throws Exception {
        Header header = this.form.getHeader();
        if(header != null) {
            setField(FieldEnum.DELEGATION.getName(), header.getDelegationTo());
            setField(FieldEnum.AGENCY.getName(), header.getAgency());
            setField(FieldEnum.AGENCY_PROVINCE.getName(), header.getProvince());
        }
    }

    private void setPersonData() throws Exception {
        PersonData personData = this.form.getTaxPayer().getPersonData();
        if(personData != null && personData.getPersonalData() != null) {
            PersonalData personalData = personData.getPersonalData();
            setField(FieldEnum.CORPORATE_NAME.getName(), personalData.getSurname());
            setField(FieldEnum.NAME.getName(), personalData.getName());
            setField(FieldEnum.DATE_OF_BIRTH.getName(), personalData.getBirthdate().replace("-", ""));
            setField(FieldEnum.SEX.getName(), personalData.getSex());
            setField(FieldEnum.MUNICIPALITY_OF_BIRTH.getName(), personalData.getBirthMunicipality());
            setField(FieldEnum.PROVINCE.getName(), personalData.getProvince());
        }
    }

    private void setCompanyData() throws Exception {
        CompanyData companyData = this.form.getTaxPayer().getCompanyData();
        if(companyData != null) {
            setField(FieldEnum.CORPORATE_NAME.getName(), companyData.getName());
        }
    }

    private void setRegistryData() throws Exception {
        setPersonData();
        setCompanyData();
    }

    private void setTaxPayer() throws Exception {
        TaxPayer taxPayer = this.form.getTaxPayer();
        if(taxPayer != null) {
            setField(FieldEnum.TAX_CODE.getName(), taxPayer.getTaxCode());
            setField(FieldEnum.OFFICE_CODE.getName(), taxPayer.getOfficeCode());
            setField(FieldEnum.ACT_CODE.getName(), taxPayer.getActCode());
            setField(FieldEnum.OTHER_TAX_CODE.getName(), taxPayer.getOtherTaxCode());
            setField(FieldEnum.ID_CODE.getName(), taxPayer.getIdCode());
            setRegistryData();
        }
    }

    private void setPaymentReasonRecordCheckboxes(PaymentReasonRecord paymentReasonRecord, int index) throws Exception {
        if (paymentReasonRecord.getRepentance() == Boolean.TRUE) setField(FieldEnum.REPENTANCE.getName() + index, "X");
        if (paymentReasonRecord.getChangedBuildings() == Boolean.TRUE) setField(FieldEnum.CHANGED_BUILDINGS.getName() + index, "X");
        if (paymentReasonRecord.getAdvancePayment() == Boolean.TRUE) setField(FieldEnum.ADVANCE_PAYMENT.getName() + index, "X");
        if (paymentReasonRecord.getPayment() == Boolean.TRUE) setField(FieldEnum.PAYMENT.getName() + index, "X");
        if (paymentReasonRecord.getNumberOfBuildings() != null) setField(FieldEnum.NUMBER_OF_BUILDINGS.getName() + index, paymentReasonRecord.getNumberOfBuildings());
    }

    private void setPaymentReasonRecordAmounts(PaymentReasonRecord paymentReasonRecord, int index) throws Exception {
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
                setField(FieldEnum.TRIBUTE_CODE.getName() + index, paymentReasonRecord.getTributeCode());
                setField(FieldEnum.INSTITUTION_CODE.getName() + index, paymentReasonRecord.getInstitutionCode());
                setPaymentReasonRecordCheckboxes(paymentReasonRecord, index);
                setField(FieldEnum.MONTH.getName() + index, paymentReasonRecord.getMonth());
                setField(FieldEnum.YEAR.getName() + index, paymentReasonRecord.getYear());
                setPaymentReasonRecordAmounts(paymentReasonRecord, index);
            }
            setField(FieldEnum.TOTAL_AMOUNT.getName(), helper.getMoney(Integer.parseInt(this.form.getPaymentReasonSection().getTotalAmount().toString())));
        } catch (Exception e) {
            //
        }
    }

    private void setPaymentDetails() throws Exception {
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
