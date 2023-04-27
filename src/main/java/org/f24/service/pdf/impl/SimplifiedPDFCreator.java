package org.f24.service.pdf.impl;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import org.f24.dto.component.*;
import org.f24.dto.form.F24Simplified;
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
            setField("attorney", header.getDelegationTo());
            setField("agency", header.getAgency());
            setField("agencyProvince", header.getProvince());
        }
    }

    private void setPersonData() throws Exception {
        PersonData personData = this.form.getContributor().getPersonData();
        if(personData != null && personData.getPersonalData() != null) {
            PersonalData personalData = personData.getPersonalData();
            setField("corporateName", personalData.getSurname());
            setField("name", personalData.getName());
            setField("dateOfBirth", personalData.getDateOfBirth().replace("-", ""));
            setField("sex", personalData.getSex());
            setField("municipalityOfBirth", personalData.getMunicipalityOfBirth());
            setField("province", personalData.getProvince());
        }
    }

    private void setCompanyData() throws Exception {
        CompanyData companyData = this.form.getContributor().getCompanyData();
        if(companyData != null) {
            setField("corporateName", companyData.getName());
        }
    }

    private void setRegistryData() throws Exception {
        setPersonData();
        setCompanyData();
    }

    private void setContributor() throws Exception {
        Contributor contributor = this.form.getContributor();
        if(contributor != null) {
            setField("taxCode", contributor.getTaxCode());
            setField("officeCode", contributor.getOfficeCode());
            setField("deedCode", contributor.getActCode());
            setField("receiverTaxCode", contributor.getReceiverTaxCode());
            setField("idCode", contributor.getIdCode());
            setRegistryData();
        }
    }

    private String[] splitMoney(double input) {
        input = Math.round(input*100.0)/100.0;
        int integerPart = (int) input;
        double decimalPart = input - integerPart;
        return new String[] { Integer.toString(integerPart), String.format("%.2f", decimalPart).split(",")[1] };
    }

    private void setPaymentMotiveSection(int copyIndex) {
        try {
            setField("operationId", this.form.getPaymentMotiveSection().getOperationId());
            List<PaymentMotiveRecord> paymentMotiveRecordList = this.form.getPaymentMotiveSection().getMotiveRecordList();
            int limit = copyIndex * MOTIVE_RECORDS_NUMBER + MOTIVE_RECORDS_NUMBER;
            limit = Math.min(limit, paymentMotiveRecordList.size());
            paymentMotiveRecordList = paymentMotiveRecordList.subList(copyIndex * MOTIVE_RECORDS_NUMBER, limit);
            for (int index = 1; index <= paymentMotiveRecordList.size(); index++) {
                PaymentMotiveRecord paymentMotiveRecord = paymentMotiveRecordList.get(index - 1);
                setField("section" + index, paymentMotiveRecord.getSection());
                setField("tributeCode" + index, paymentMotiveRecord.getTributeCode());
                setField("institutionCode" + index, paymentMotiveRecord.getInstitutionCode());
                if (paymentMotiveRecord.getActiveRepentance() == Boolean.TRUE) setField("ravv" + index, "X");
                if (paymentMotiveRecord.getVariedBuildings() == Boolean.TRUE) setField("building" + index, "X");
                if (paymentMotiveRecord.getAdvancePayment() == Boolean.TRUE) setField("acc" + index, "X");
                if (paymentMotiveRecord.getBalance() == Boolean.TRUE) setField("balance" + index, "X");
                if (paymentMotiveRecord.getNumberOfBuildings() != null) setField("numberOfBuildings" + index, paymentMotiveRecord.getNumberOfBuildings());
                setField("month" + index, paymentMotiveRecord.getMonth());
                setField("reportingYear" + index, paymentMotiveRecord.getReportingYear());
                if(paymentMotiveRecord.getDeduction() != null) {
                    String[] splittedDeduction = splitMoney(Double.parseDouble(paymentMotiveRecord.getDeduction()));
                    setField("deductionInt" + index, splittedDeduction[0]);
                    setField("deductionDec" + index, splittedDeduction[1]);
                }
                if(paymentMotiveRecord.getDebitAmount() != null) {
                    String[] splittedDebitAmount = splitMoney(Double.parseDouble(paymentMotiveRecord.getDebitAmount()));
                    setField("debitAmountInt" + index, splittedDebitAmount[0]);
                    setField("debitAmountDec" + index, splittedDebitAmount[1]);
                }
                if(paymentMotiveRecord.getCreditAmount() != null) {
                    String[] splittedCreditAmount = splitMoney(Double.parseDouble(paymentMotiveRecord.getCreditAmount()));
                    setField("creditAmountInt" + index, splittedCreditAmount[0]);
                    setField("creditAmountDec" + index, splittedCreditAmount[1]);
                }
            }
            String[] splittedTotalAmount = splitMoney(Double.parseDouble(this.form.getPaymentMotiveSection().getTotalAmount().toString()));
            setField("totalAmountInt", splittedTotalAmount[0]);
            setField("totalAmountDec", splittedTotalAmount[1]);
        } catch (Exception e) {
            //
        }
    }

    private void setPaymentDetails() throws Exception {
        PaymentDetails paymentDetails = this.form.getPaymentDetails();
        if(paymentDetails != null) {
            setField("dateOfPayment", paymentDetails.getDateOfPayment().replace("-", ""));
            setField("company", paymentDetails.getCompany());
            setField("cabCode", paymentDetails.getCabCode());
            setField("checkNumber", paymentDetails.getCheckNumber());
            setField("abiCode", paymentDetails.getAbiCode());
            setField("isBank", paymentDetails.isBank() ? "X" : "");
            setField("isCircular", !paymentDetails.isBank() ? "X" : "");
            setField("ibanCode", paymentDetails.getIbanCode());
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
            return ByteArrayBuilder.NO_BYTES;
        }
    }

}
