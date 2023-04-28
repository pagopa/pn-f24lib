package org.f24.service.pdf.impl;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import org.f24.dto.component.*;
import org.f24.dto.form.F24Simplified;
import org.f24.service.pdf.FieldEnum;
import org.f24.service.pdf.PDFCreator;
import org.f24.service.pdf.PDFFormManager;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class SimplifiedPDFCreator extends PDFFormManager implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24Semplificato.pdf";
    private static final int MOTIVE_RECORDS_NUMBER = 10;

    private F24Simplified form;

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
        PersonData personData = this.form.getContributor().getPersonData();
        if(personData != null && personData.getPersonalData() != null) {
            PersonalData personalData = personData.getPersonalData();
            setField(FieldEnum.CORPORATE_NAME.getName(), personalData.getSurname());
            setField(FieldEnum.NAME.getName(), personalData.getName());
            setField(FieldEnum.DATE_OF_BIRTH.getName(), personalData.getDateOfBirth().replace("-", ""));
            setField(FieldEnum.SEX.getName(), personalData.getSex());
            setField(FieldEnum.MUNICIPALITY_OF_BIRTH.getName(), personalData.getMunicipalityOfBirth());
            setField(FieldEnum.PROVINCE.getName(), personalData.getProvince());
        }
    }

    private void setCompanyData() throws Exception {
        CompanyData companyData = this.form.getContributor().getCompanyData();
        if(companyData != null) {
            setField(FieldEnum.CORPORATE_NAME.getName(), companyData.getName());
        }
    }

    private void setRegistryData() throws Exception {
        setPersonData();
        setCompanyData();
    }

    private void setContributor() throws Exception {
        Contributor contributor = this.form.getContributor();
        if(contributor != null) {
            setField(FieldEnum.TAX_CODE.getName(), contributor.getTaxCode());
            setField(FieldEnum.OFFICE_CODE.getName(), contributor.getOfficeCode());
            setField(FieldEnum.ACT_CODE.getName(), contributor.getActCode());
            setField(FieldEnum.RECEIVER_TAX_CODE.getName(), contributor.getReceiverTaxCode());
            setField(FieldEnum.ID_CODE.getName(), contributor.getIdCode());
            setRegistryData();
        }
    }

    private void setPaymentMotiveRecordCheckboxes(PaymentMotiveRecord paymentMotiveRecord, int index) throws Exception {
        if (paymentMotiveRecord.getActiveRepentance() == Boolean.TRUE) setField(FieldEnum.ACTIVE_REPENTANCE.getName() + index, "X");
        if (paymentMotiveRecord.getVariedBuildings() == Boolean.TRUE) setField(FieldEnum.VARIED_BUILDINGS.getName() + index, "X");
        if (paymentMotiveRecord.getAdvancePayment() == Boolean.TRUE) setField(FieldEnum.ADVANCE_PAYMENT.getName() + index, "X");
        if (paymentMotiveRecord.getBalance() == Boolean.TRUE) setField(FieldEnum.BALANCE.getName() + index, "X");
        if (paymentMotiveRecord.getNumberOfBuildings() != null) setField(FieldEnum.NUMBER_OF_BUILDINGS.getName() + index, paymentMotiveRecord.getNumberOfBuildings());
    }

    private void setPaymentMotiveRecordAmounts(PaymentMotiveRecord paymentMotiveRecord, int index) throws Exception {
        if(paymentMotiveRecord.getDeduction() != null) {
            setField(FieldEnum.DEDUCTION.getName() + index, getMoney(Integer.parseInt(paymentMotiveRecord.getDeduction())));
        }
        if(paymentMotiveRecord.getDebitAmount() != null) {
            setField(FieldEnum.DEBIT_AMOUNT.getName() + index, getMoney(Integer.parseInt(paymentMotiveRecord.getDebitAmount())));
        }
        if(paymentMotiveRecord.getCreditAmount() != null) {
            setField(FieldEnum.CREDIT_AMOUNT.getName() + index, getMoney(Integer.parseInt(paymentMotiveRecord.getCreditAmount())));
        }
    }

    private void setPaymentMotiveSection(int copyIndex) {
        try {
            setField(FieldEnum.OPERATION_ID.getName(), this.form.getPaymentMotiveSection().getOperationId());
            List<PaymentMotiveRecord> paymentMotiveRecordList = this.form.getPaymentMotiveSection().getMotiveRecordList();
            int limit = copyIndex * MOTIVE_RECORDS_NUMBER + MOTIVE_RECORDS_NUMBER;
            limit = Math.min(limit, paymentMotiveRecordList.size());
            paymentMotiveRecordList = paymentMotiveRecordList.subList(copyIndex * MOTIVE_RECORDS_NUMBER, limit);
            for (int index = 1; index <= paymentMotiveRecordList.size(); index++) {
                PaymentMotiveRecord paymentMotiveRecord = paymentMotiveRecordList.get(index - 1);
                setField(FieldEnum.SECTION.getName() + index, paymentMotiveRecord.getSection());
                setField(FieldEnum.TRIBUTE_CODE.getName() + index, paymentMotiveRecord.getTributeCode());
                setField(FieldEnum.INSTITUTION_CODE.getName() + index, paymentMotiveRecord.getInstitutionCode());
                setPaymentMotiveRecordCheckboxes(paymentMotiveRecord, index);
                setField(FieldEnum.MONTH.getName() + index, paymentMotiveRecord.getMonth());
                setField(FieldEnum.REPORTING_YEAR.getName() + index, paymentMotiveRecord.getReportingYear());
                setPaymentMotiveRecordAmounts(paymentMotiveRecord, index);
            }
            setField(FieldEnum.TOTAL_AMOUNT.getName(), getMoney(Integer.parseInt(this.form.getPaymentMotiveSection().getTotalAmount().toString())));
        } catch (Exception e) {
            //
        }
    }

    private void setPaymentDetails() throws Exception {
        PaymentDetails paymentDetails = this.form.getPaymentDetails();
        if(paymentDetails != null) {
            setField(FieldEnum.DATE_OF_PAYMENT.getName(), paymentDetails.getDateOfPayment().replace("-", ""));
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

            int motiveRecordsCount = this.form.getPaymentMotiveSection().getMotiveRecordList().size();
            if(this.form.getPaymentMotiveSection().getMotiveRecordList().size() > MOTIVE_RECORDS_NUMBER) {
                copy(((motiveRecordsCount + MOTIVE_RECORDS_NUMBER - 1) / MOTIVE_RECORDS_NUMBER) - 1);
            }

            for(int copyIndex = 0; copyIndex < getCopies().size(); copyIndex++) {
                setIndex(copyIndex);
                setHeader();
                setContributor();
                setPaymentDetails();
                setPaymentMotiveSection(copyIndex);
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
