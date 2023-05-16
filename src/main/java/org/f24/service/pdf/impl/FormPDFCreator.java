package org.f24.service.pdf.impl;

import org.f24.dto.component.*;
import org.f24.dto.component.Record;
import org.f24.dto.form.F24Form;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.util.CreatorHelper;
import org.f24.service.pdf.util.PDFFormManager;

import java.util.List;
import java.util.Locale;

import static org.f24.service.pdf.util.FieldEnum.*;

public class FormPDFCreator extends PDFFormManager {

    private F24Form form;
    private CreatorHelper helper = new CreatorHelper();

    public FormPDFCreator(F24Form form) {
        this.form = form;
    }

    protected void setHeader() throws ResourceException {
        Header header = this.form.getHeader();

        if (header != null) {
            setField(DELEGATION.getName(), header.getDelegationTo());
            setField(AGENCY.getName(), header.getAgency());
            setField(AGENCY_PROVINCE.getName(), header.getProvince());
        }
    }

    protected void setRegistryData() throws ResourceException {
        setPersonData();
        setCompanyData();
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

    protected void setTaxResidenceData() throws ResourceException {
        TaxAddress taxResidenceData = this.form.getTaxPayer().getPersonData().getTaxAddress();

        if (taxResidenceData != null) {
            setField(ADDRESS.getName(), taxResidenceData.getAddress());
            setField(MUNICIPALITY.getName(), taxResidenceData.getMunicipality());
            setField(TAX_PROVINCE.getName(), taxResidenceData.getProvince());
        }
    }

    protected void setSectionRecordAmount(String sectionId, int index, Record sourceRecord)
            throws ResourceException {
        if (sourceRecord.getCreditAmount() != null) {
            String recordCredit = sourceRecord.getCreditAmount();
            String parsedCredit = helper.getMoney(Integer.parseInt(recordCredit));
            setField(CREDIT_AMOUNT.getName() + sectionId + index, parsedCredit);
        }

        if (sourceRecord.getDebitAmount() != null) {
            String recordDebit = sourceRecord.getDebitAmount();
            String parsedDebit = helper.getMoney(Integer.parseInt(recordDebit));
            setField(DEBIT_AMOUNT.getName() + sectionId + index, parsedDebit);
        }

        if (sourceRecord.getDeduction() != null) {
            String parseDeduction = helper.getMoney(Integer.parseInt(sourceRecord.getDeduction()));
            setField(DEDUCTION.getName() + sectionId + index, parseDeduction);
        }

    }

    protected int setSectionTotal(String sectionId, List<? extends Record> recordList, int totalBalance) throws NumberFormatException, ResourceException {
        Integer creditTotal = 0;
        Integer debitTotal = 0;

        if (helper.getCreditTotal(recordList) != 0) {
            creditTotal = helper.getCreditTotal(recordList);
            String parsedTotal = helper.getMoney(creditTotal);
            setField(TOTAL_CREDIT.getName() + sectionId, parsedTotal);
        }

        if (helper.getDebitTotal(recordList) != 0) {
            debitTotal = helper.getDebitTotal(recordList);
            String parsedTotal = helper.getMoney(debitTotal);
            setField(TOTAL_DEBIT.getName() + sectionId, parsedTotal);
        }
        totalBalance = debitTotal - creditTotal;
        if (totalBalance != 0) {
            if (totalBalance < 0)
                setField(BALANCE_SIGN.getName() + sectionId, "-");
            String parsedTotal = helper.getMoney(totalBalance);
            setField(TOTAL_AMOUNT.getName() + sectionId, parsedTotal);
        }

        return totalBalance;
    }

    protected void setMultiField(String fieldName, Double sourceRecord) throws ResourceException {
        String[] splittedAmount = splitField(sourceRecord);
        setField(fieldName + "Int", splittedAmount[0]);
        setField(fieldName + "Dec", splittedAmount[1]);
    }

    protected void setMultiDate(String fieldName, String sectionId, int index, String date) throws ResourceException {
        String monthPart = date.substring(0, 2);
        String yearPart = date.substring(2);

        setField(fieldName + "Month" + sectionId + index, monthPart);
        setField(fieldName + "Year" + sectionId + index, yearPart);
    }

    private String[] splitField(double input) {
        input = Math.round(input * 100.0) / 100.0;
        int integerPart = (int) input;
        double decimalPart = input - integerPart;
        return new String[]{Integer.toString(integerPart), String.format(Locale.ROOT, "%.2f", decimalPart).split("\\.")[1]};
    }

    protected void setPaymentDetails() throws ResourceException {
        PaymentDetails paymentDetails = this.form.getPaymentDetails();
        if (paymentDetails != null) {
            setField(DATE_OF_PAYMENT.getName(), paymentDetails.getPaymentDate().replace("-", ""));
            setField(COMPANY.getName(), paymentDetails.getCompany());
            setField(CAB_CODE.getName(), paymentDetails.getCabCode());
            setField(CHECK_NUMBER.getName(), paymentDetails.getCheckNumber());
            setField(ABI_CODE.getName(), paymentDetails.getAbiCode());
            setField(BANK.getName(), paymentDetails.isBank() ? "X" : "");
            setField(CIRCULAR.getName(), !paymentDetails.isBank() ? "X" : "");
        }
    }

}
