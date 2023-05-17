package org.f24.service.pdf.impl;

import org.f24.dto.component.*;
import org.f24.dto.component.Record;
import org.f24.dto.form.F24Form;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.util.PDFFormManager;

import java.util.List;

import static org.f24.service.pdf.util.FieldEnum.*;

public class FormPDFCreator extends PDFFormManager {

    private F24Form form;
    protected int totalBalance = 0;

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
            String parsedCredit = getMoney(Integer.parseInt(recordCredit));
            setField(CREDIT_AMOUNT.getName() + sectionId + index, parsedCredit);
        }

        if (sourceRecord.getDebitAmount() != null) {
            String recordDebit = sourceRecord.getDebitAmount();
            String parsedDebit = getMoney(Integer.parseInt(recordDebit));
            setField(DEBIT_AMOUNT.getName() + sectionId + index, parsedDebit);
        }

        if (sourceRecord.getDeduction() != null) {
            String parseDeduction = getMoney(Integer.parseInt(sourceRecord.getDeduction()));
            setField(DEDUCTION.getName() + sectionId + index, parseDeduction);
        }

    }

    protected int setSectionTotal(String sectionId, List<? extends Record> recordList, int totalBalance)
            throws NumberFormatException, ResourceException {
        Integer creditTotal = 0;
        Integer debitTotal = 0;
        int sectionTotalBalance = totalBalance;

        if (getCreditTotal(recordList) != 0) {
            creditTotal = getCreditTotal(recordList);
            String parsedTotal = getMoney(creditTotal);
            setField(TOTAL_CREDIT.getName() + sectionId, parsedTotal);
        }

        if (getDebitTotal(recordList) != 0) {
            debitTotal = getDebitTotal(recordList);
            String parsedTotal = getMoney(debitTotal);
            setField(TOTAL_DEBIT.getName() + sectionId, parsedTotal);
        }

        sectionTotalBalance = debitTotal - creditTotal;
        
        if (sectionTotalBalance != 0) {
            if (sectionTotalBalance < 0)
                setField(BALANCE_SIGN.getName() + sectionId, "-");
            String parsedTotal = getMoney(sectionTotalBalance);
            setField(TOTAL_AMOUNT.getName() + sectionId, parsedTotal);
        }

        return sectionTotalBalance;
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

    protected void setTreasurySection(String sectionId, int copyIndex) throws ResourceException {
        TreasurySection treasurySection = this.form.getTreasurySection();

        if (!treasurySection.getTaxList().isEmpty()) {
            List<Tax> taxList = paginateList(copyIndex, TAX_RECORDS_NUMBER.getRecordsNum(),
                    treasurySection.getTaxList());

            if (!taxList.isEmpty()) {
                for (int index = 1; index <= taxList.size(); index++) {
                    Tax taxRecord = taxList.get(index - 1);
                    setField(TAX_TYPE_CODE.getName() + sectionId + index, taxRecord.getTaxTypeCode());
                    setField(INSTALLMENT.getName() + sectionId + index, taxRecord.getInstallment());
                    setField(YEAR.getName() + sectionId + index, taxRecord.getYear());
                    setSectionRecordAmount(sectionId, index, taxRecord);
                }
                setField(OFFICE_CODE.getName(), treasurySection.getOfficeCode());
                setField(DOCUMENT_CODE.getName(), treasurySection.getDocumentCode());

                totalBalance += setSectionTotal(sectionId, taxList, totalBalance);
            }
        }
    }

    protected void setInpsSection(String sectionId, int copyIndex) throws ResourceException {
        InpsSection inpsSection = this.form.getInpsSection();

        if (!inpsSection.getInpsRecordList().isEmpty()) {
            List<InpsRecord> inpsRecordList = paginateList(copyIndex, UNIV_RECORDS_NUMBER.getRecordsNum(),
                    inpsSection.getInpsRecordList());

            if (!inpsRecordList.isEmpty()) {
                for (int index = 1; index <= inpsRecordList.size(); index++) {
                    InpsRecord inpsRecord = inpsRecordList.get(index - 1);
                    setField(OFFICE_CODE.getName() + sectionId + index, inpsRecord.getOfficeCode());
                    setField(CONTRIBUTION_REASON.getName() + sectionId + index, inpsRecord.getContributionReason());
                    setField(INPS_CODE.getName() + sectionId + index, inpsRecord.getInpsCode());
                    setMultiDate(START_DATE.getName(), sectionId, index, inpsRecord.getPeriod().getStartDate());
                    setMultiDate(END_DATE.getName(), sectionId, index, inpsRecord.getPeriod().getEndDate());

                    setSectionRecordAmount(sectionId, index, inpsRecord);

                }
                totalBalance += setSectionTotal(sectionId, inpsRecordList, totalBalance);
            }
        }
    }

    protected void setRegionSection(String sectionId, int copyIndex) throws ResourceException {
        RegionSection regionSection = this.form.getRegionSection();

        if (!regionSection.getRegionRecordList().isEmpty()) {
            List<RegionRecord> regionRecordsList = paginateList(copyIndex, UNIV_RECORDS_NUMBER.getRecordsNum(),
                    regionSection.getRegionRecordList());

            if (!regionRecordsList.isEmpty()) {
                for (int index = 1; index <= regionRecordsList.size(); index++) {
                    RegionRecord regionRecord = regionRecordsList.get(index - 1);
                    setField(YEAR.getName() + sectionId + index, regionRecord.getYear());
                    setField(INSTALLMENT.getName() + sectionId + index, regionRecord.getInstallment());
                    setField(TAX_TYPE_CODE.getName() + sectionId + index, regionRecord.getTaxTypeCode());
                    setField(REGION_CODE.getName() + sectionId + index, regionRecord.getRegionCode());

                    setSectionRecordAmount(sectionId, index, regionRecord);
                }
                totalBalance += setSectionTotal(sectionId, regionRecordsList, totalBalance);
            }
        }
    }

    protected void setLocalTaxSection(String sectionId, int copyIndex) throws ResourceException {
        LocalTaxSection localTaxSection = this.form.getLocalTaxSection();

        if (!localTaxSection.getLocalTaxRecordList().isEmpty()) {
            List<LocalTaxRecord> localTaxRecordList = paginateList(copyIndex, UNIV_RECORDS_NUMBER.getRecordsNum(),
                    localTaxSection.getLocalTaxRecordList());

            if (!localTaxRecordList.isEmpty()) {
                for (int index = 1; index <= localTaxRecordList.size(); index++) {
                    LocalTaxRecord taxRecord = localTaxRecordList.get(index - 1);
                    setField(YEAR.getName() + sectionId + index, taxRecord.getYear());
                    setField(INSTALLMENT.getName() + sectionId + index, taxRecord.getInstallment());
                    setField(TAX_TYPE_CODE.getName() + sectionId + index, taxRecord.getTaxTypeCode());
                    setField(MUNICIPALITY_CODE.getName() + sectionId + index, taxRecord.getMunicipalityCode());

                    setLocalTaxSectionChecks(taxRecord, index);
                    setSectionRecordAmount(sectionId, index, taxRecord);
                }
                if (!localTaxSection.getOperationId().isEmpty()) {
                    setField(OPERATION_ID.getName(), localTaxSection.getOperationId());
                }
                totalBalance += setSectionTotal(sectionId, localTaxRecordList, totalBalance);

                Double parsedDeduction = Double.parseDouble(localTaxSection.getDeduction());
                setMultiField(DEDUCTION.getName(), parsedDeduction);
            }
        }
    }

    protected void setLocalTaxSectionChecks(LocalTaxRecord taxRecord, int index) throws ResourceException {
        if (taxRecord.getReconsideration() != null && taxRecord.getReconsideration()) {
            setField(RECONSIDERATION.getName() + index, "X");
        }
        if (taxRecord.getPropertiesChanges() != null && taxRecord.getPropertiesChanges()) {
            setField(PROPERTIES_CHANGED.getName() + index, "X");
        }
        if (taxRecord.getAdvancePayment() != null && taxRecord.getAdvancePayment()) {
            setField(ADVANCE_PAYMENT.getName() + index, "X");
        }
        if (taxRecord.getFullPayment() != null && taxRecord.getFullPayment()) {
            setField(FULL_PAYMENT.getName() + index, "X");
        }
        if (taxRecord.getNumberOfProperties() != null) {
            setField(NUMBER_OF_PROPERTIES.getName() + index, taxRecord.getNumberOfProperties());
        }
    }

}
