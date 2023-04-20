package org.f24.service.pdf.impl;

import java.util.Calendar;
import java.util.List;

import org.f24.dto.component.Contributor;
import org.f24.dto.component.Header;
import org.f24.dto.component.ImuRecord;
import org.f24.dto.component.ImuSection;
import org.f24.dto.component.InpsRecord;
import org.f24.dto.component.PersonalData;
import org.f24.dto.component.RegionRecord;
import org.f24.dto.component.RegionSection;
import org.f24.dto.component.Tax;
import org.f24.dto.component.TaxResidence;
import org.f24.dto.component.TreasurySection;
import org.f24.dto.form.F24Standard;
import org.f24.service.pdf.PDFCreator;
import org.f24.service.pdf.PDFFormManager;

public class StandardPDFCreator extends PDFFormManager implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "";
    private static final int UNIV_RECORDS_NUMBER = 4;
    private static final int TAX_RECORDS_NUMBER = 6;

    private static final String REGION_SECTION = "region";
    private static final String INPS_SECTION = "inps";
    private static final String HEADER_SECTION = "header";
    private static final String PERSONAL_SECTION = "personalData";
    private static final String CONTRIBUTOR_SECTION = "contributor";
    private static final String TAX_SECTION = "taxResidence";
    private static final String IMU_SECTION = "taxResidence";
    private static final String TREASURY_SECTION = "treasury";

    private F24Standard form;

    /**
     * Constructs Standard PDF Creator.
     *
     * @param form F24Standard component (DTO for Standard Form).
     */
    public StandardPDFCreator(F24Standard form) {
        this.form = form;
    }

    private void setHeader() throws Exception {
        String sectionName = HEADER_SECTION;

        Header header = this.form.getHeader();
        if (header == null)
            return;

        setField(sectionName + "delegation", header.getDelegationTo());
        setField(sectionName + "agency", header.getAgency());
        setField(sectionName + "province", header.getProvince());
    }

    private void setPersonData() throws Exception {
        String sectionName = PERSONAL_SECTION;
        PersonalData personalData = this.form.getContributor().getPersonData().getPersonalData();

        if (personalData == null)
            return;

        setField(sectionName + "surname", personalData.getSurname());
        setField(sectionName + "name", personalData.getName());
        setField(sectionName + "dateOfBirth", personalData.getDateOfBirth());
        setField(sectionName + "sex", personalData.getSex());
        setField(sectionName + "municipalityOfBirth", personalData.getMunicipalityOfBirth());
        setField(sectionName + "province", personalData.getProvince());
    }

    private void setTaxResidenceData() throws Exception {
        String sectionName = TAX_SECTION;
        TaxResidence taxResidenceData = this.form.getContributor().getPersonData().getTaxResidence();

        if (taxResidenceData == null)
            return;

        setField(sectionName + "address", taxResidenceData.getAddress());
        setField(sectionName + "municipality", taxResidenceData.getMunicipality());
        setField(sectionName + "province", taxResidenceData.getProvince());
    }

    private void setContributor() throws Exception {
        String sectionName = CONTRIBUTOR_SECTION;
        Contributor contributor = this.form.getContributor();
        String currentYear = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

        if (contributor == null)
            return;

        setField(sectionName + "taxCode", contributor.getTaxCode());
        setField(sectionName + "receiverTaxCode", contributor.getReceiverTaxCode());
        setField(sectionName + "idCode", contributor.getIdCode());

        if (contributor.isIfCalendarYear())
            setField(sectionName + "calendarYear", currentYear);

        setPersonData();
        setTaxResidenceData();
    }

    private void setInpsSection(int copyIndex) throws Exception {
        String sectionName = INPS_SECTION;
        List<InpsRecord> inpsRecordList = this.form.getInpsSection().getInpsRecordList();

        if (inpsRecordList == null)
            return;

        inpsRecordList = paginateList(copyIndex, UNIV_RECORDS_NUMBER, inpsRecordList);

        int index = 1;
        for (InpsRecord record : inpsRecordList) {
            setField(sectionName + "locationCode" + index, record.getLocationCode());
            setField(sectionName + "contributionReason" + index, record.getContributionReason());
            setField(sectionName + "inpsCode" + index, record.getInpsCode());
            setField(sectionName + "startDate" + index, record.getReportingPeriod().getStartDate());
            setField(sectionName + "endDate" + index, record.getReportingPeriod().getEndDate());

            if (record.getCreditAmount() != null)
                setMultiField(sectionName, "creditAmount", index, Double.parseDouble(record.getCreditAmount()));

            if (record.getDebitAmount() != null)
                setMultiField(sectionName, "debitAmount", index, Double.parseDouble(record.getDebitAmount()));

            index++;
        }

    }

    private void setImuSection(int copyIndex) throws Exception {
        String sectionName = IMU_SECTION;
        ImuSection imuSection = this.form.getImuSection();
        List<ImuRecord> imuRecordList = imuSection.getImuRecordList();

        if (imuRecordList == null)
            return;

        imuRecordList = paginateList(copyIndex, UNIV_RECORDS_NUMBER, imuRecordList);

        int index = 1;
        for (ImuRecord record : imuRecordList) {
            setField(sectionName + "reportingYear" + index, record.getReportingYear());
            setField(sectionName + "installment" + index, record.getInstallment());
            setField(sectionName + "tributeCode" + index, record.getTributeCode());
            setField(sectionName + "tributeCode" + index, record.getMunicipalityCode());

            if (record.getActiveRepentance() != null)
                setField(sectionName + "ravv" + index, "X");
            if (record.getVariedBuildings() != null)
                setField(sectionName + "building" + index, "X");
            if (record.getAdvancePayment() != null)
                setField(sectionName + "acc" + index, "X");
            if (record.getBalance() != null)
                setField(sectionName + "balance" + index, "X");
            if (record.getNumberOfBuildings() != null)
                setField(sectionName + "numberOfBuildings" + index, record.getNumberOfBuildings());

            if (record.getCreditAmount() != null)
                setMultiField(sectionName, "creditAmount", index, Double.parseDouble(record.getCreditAmount()));

            if (record.getDebitAmount() != null)
                setMultiField(sectionName, "debitAmount", index, Double.parseDouble(record.getDebitAmount()));

            index++;

        }
    }

    private void setTreasurySection(int copyIndex) throws Exception {
        String sectionName = TREASURY_SECTION;
        TreasurySection treasurySection = this.form.getTreasurySection();
        List<Tax> taxList = treasurySection.getTaxList();

        if (taxList == null)
            return;

        taxList = paginateList(copyIndex, TAX_RECORDS_NUMBER, taxList);

        int index = 1;
        for (Tax record : taxList) {
            setField(sectionName + "tributeCode" + index, record.getTributeCode());
            setField(sectionName + "installment" + index, record.getInstallment());
            setField(sectionName + "reportingYear" + index, record.getReportingYear());

            if (record.getCreditAmount() != null)
                setMultiField(sectionName, "creditAmount", index, Double.parseDouble(record.getCreditAmount()));

            if (record.getDebitAmount() != null)
                setMultiField(sectionName, "debitAmount", index, Double.parseDouble(record.getDebitAmount()));

            index++;
        }

    }

    private void setRegionSection(int copyIndex) throws Exception {
        String sectionName = REGION_SECTION;
        RegionSection regionSection = this.form.getRegionSection();
        List<RegionRecord> regionRecordsList = regionSection.getRegionRecordList();

        if (regionRecordsList == null)
            return;

        regionRecordsList = paginateList(copyIndex, UNIV_RECORDS_NUMBER, regionRecordsList);

        int index = 1;
        for (RegionRecord record : regionRecordsList) {
            setField(sectionName + "reportingYear" + index, record.getReportingYear());
            setField(sectionName + "installment" + index, record.getInstallment());
            setField(sectionName + "tributeCode" + index, record.getTributeCode());
            setField(sectionName + "regionCode" + index, record.getRegionCode());

            if (record.getCreditAmount() != null)
                setMultiField(sectionName, "creditAmount", index, Double.parseDouble(record.getCreditAmount()));

            if (record.getDebitAmount() != null)
                setMultiField(sectionName, "debitAmount", index, Double.parseDouble(record.getDebitAmount()));

            index++;
        }
    }

    private void setMultiField(String sectionName, String fieldName, int index, Double dataSource) throws Exception {
        String[] splittedCreditAmount = splitMoney(dataSource);
        setField(sectionName + fieldName + "Int" + index, splittedCreditAmount[0]);
        setField(sectionName + fieldName + "Dec" + index, splittedCreditAmount[1]);
    }

    private <T> List<T> paginateList(int copyIndex, int recordsNumber, List<T> list) {
        int limit = copyIndex * (recordsNumber * 2);
        limit = Math.min(limit, list.size());

        return list = list.subList(copyIndex * recordsNumber, limit);
    }

    private String[] splitMoney(double input) {
        input = Math.round(input * 100.0) / 100.0;
        int integerPart = (int) input;
        double decimalPart = input - integerPart;
        return new String[] { Integer.toString(integerPart), String.format("%.2f", decimalPart).split(",")[1] };
    }

    /**
     * Method which creates PDF Document for F24 Standard Form.
     *
     * @return PDFDocument object with filled fields.
     */
    @Override
    public byte[] createPDF() {
        return null;
    }

}
