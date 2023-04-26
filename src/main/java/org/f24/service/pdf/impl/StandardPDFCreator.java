package org.f24.service.pdf.impl;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.List;

import org.f24.dto.component.Contributor;
import org.f24.dto.component.Header;
import org.f24.dto.component.ImuRecord;
import org.f24.dto.component.ImuSection;
import org.f24.dto.component.InailRecord;
import org.f24.dto.component.InpsRecord;
import org.f24.dto.component.InpsSection;
import org.f24.dto.component.PaymentDetails;
import org.f24.dto.component.PersonalData;
import org.f24.dto.component.RegionRecord;
import org.f24.dto.component.RegionSection;
import org.f24.dto.component.SocialSecurityRecord;
import org.f24.dto.component.SocialSecuritySection;
import org.f24.dto.component.Tax;
import org.f24.dto.component.TaxResidence;
import org.f24.dto.component.TreasurySection;
import org.f24.dto.form.F24Standard;
import org.f24.service.pdf.PDFCreator;
import org.f24.service.pdf.PDFFormManager;
import org.f24.service.pdf.FieldEnum;

public class StandardPDFCreator extends PDFFormManager implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24IMU2013.pdf";
    private static final int TAX_RECORDS_NUMBER = 6;
    private static final int UNIV_RECORDS_NUMBER = 4;
    private static final int INAIL_RECORDS_NUMBER = 3;
    private static final int SOC_RECORDS_NUMBER = 2;

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
        String sectionName = FieldEnum.HEADER.name();

        Header header = this.form.getHeader();
        if (header == null)
            return;

        setField(sectionName + FieldEnum.DELEGATION.name(), header.getDelegationTo());
        setField(sectionName + FieldEnum.AGENCY.name(), header.getAgency());
        setField(sectionName + FieldEnum.PROVINCE.name(), header.getProvince());
    }

    private void setPersonData() throws Exception {
        String sectionName = FieldEnum.PERSONAL_DATA.name();
        PersonalData personalData = this.form.getContributor().getPersonData().getPersonalData();

        if (personalData == null)
            return;

        setField(sectionName + FieldEnum.SURNAME.name(), personalData.getSurname());
        setField(sectionName + FieldEnum.NAME.name(), personalData.getName());
        setField(sectionName + FieldEnum.DATE_OF_BIRTH.name(), personalData.getDateOfBirth());
        setField(sectionName + FieldEnum.SEX.name(), personalData.getSex());
        setField(sectionName + FieldEnum.MUNICIPALITY_OF_BIRTH.name(), personalData.getMunicipalityOfBirth());
        setField(sectionName + FieldEnum.PROVINCE.name(), personalData.getProvince());
    }

    private void setTaxResidenceData() throws Exception {
        String sectionName = FieldEnum.TAX_RESIDENCE.name();
        TaxResidence taxResidenceData = this.form.getContributor().getPersonData().getTaxResidence();

        if (taxResidenceData == null)
            return;

        setField(sectionName + FieldEnum.ADDRESS.name(), taxResidenceData.getAddress());
        setField(sectionName + FieldEnum.MUNICIPALITY.name(), taxResidenceData.getMunicipality());
        setField(sectionName + FieldEnum.PROVINCE.name(), taxResidenceData.getProvince());
    }

    private void setContributor() throws Exception {
        String sectionName = FieldEnum.CONTRIBUTOR.name();
        Contributor contributor = this.form.getContributor();
        String currentYear = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

        if (contributor == null)
            return;

        setField(sectionName + FieldEnum.TAX_CODE.name(), contributor.getTaxCode());
        setField(sectionName + FieldEnum.RECEIVER_TAX_CODE.name(), contributor.getReceiverTaxCode());
        setField(sectionName + FieldEnum.ID_CODE.name(), contributor.getIdCode());

        if (contributor.isIfCalendarYear())
            setField(sectionName + FieldEnum.CALENDAR_YEAR.name(), currentYear);

        setPersonData();
        setTaxResidenceData();
    }

    private void setInpsSection(int copyIndex) throws Exception {
        String sectionName = FieldEnum.INPS.name();
        InpsSection inpsSection = this.form.getInpsSection();
        List<InpsRecord> inpsRecordList = inpsSection.getInpsRecordList();

        if (inpsRecordList == null)
            return;

        inpsRecordList = paginateList(copyIndex, UNIV_RECORDS_NUMBER, inpsRecordList);

        int index = 1;
        for (InpsRecord record : inpsRecordList) {
            setField(sectionName + FieldEnum.LOCATION_CODE.name() + index, record.getLocationCode());
            setField(sectionName + FieldEnum.CONTRIBUTION_REASON.name() + index, record.getContributionReason());
            setField(sectionName + FieldEnum.INPS_CODE.name() + index, record.getInpsCode());
            setField(sectionName + FieldEnum.START_DATE.name() + index, record.getReportingPeriod().getStartDate());
            setField(sectionName + FieldEnum.END_DATE.name() + index, record.getReportingPeriod().getEndDate());

            if (record.getCreditAmount() != null)
                setMultiField(sectionName, FieldEnum.CREDIT_AMOUNT.name(), index, Double.parseDouble(record.getCreditAmount()));

            if (record.getDebitAmount() != null)
                setMultiField(sectionName, FieldEnum.DEBIT_AMOUNT.name(), index, Double.parseDouble(record.getDebitAmount()));

            index++;
        }

        Double parsedRecord = Double.parseDouble(inpsSection.getTotalAmount(inpsRecordList).toString());
        setMultiField(sectionName, FieldEnum.BALANCE.name(), parsedRecord);

        Double parsedDebit = Double.parseDouble(inpsSection.getDebitTotal(inpsRecordList).toString());
        setMultiField(sectionName, FieldEnum.TOTAL_DEBIT.name(), parsedDebit);

        Double parsedCredit = Double.parseDouble(inpsSection.getCreditTotal(inpsRecordList).toString());
        setMultiField(sectionName, FieldEnum.TOTAL_CREDIT.name(), parsedCredit);
    }

    private void setImuSection(int copyIndex) throws Exception {
        String sectionName = FieldEnum.IMU.name();
        ImuSection imuSection = this.form.getImuSection();
        List<ImuRecord> imuRecordList = imuSection.getImuRecordList();

        if (imuRecordList == null)
            return;

        imuRecordList = paginateList(copyIndex, UNIV_RECORDS_NUMBER, imuRecordList);

        int index = 1;
        for (ImuRecord record : imuRecordList) {
            setField(sectionName + FieldEnum.REPORTING_YEAR.name() + index, record.getReportingYear());
            setField(sectionName + FieldEnum.INSTALLMENT.name() + index, record.getInstallment());
            setField(sectionName + FieldEnum.TRIBUTE_CODE.name() + index, record.getTributeCode());
            setField(sectionName + FieldEnum.MUNICIPALITY_CODE.name() + index, record.getMunicipalityCode());

            if (record.getActiveRepentance() != null)
                setField(sectionName + FieldEnum.ACTIVE_REPENTANCE.name() + index, "X");
            if (record.getVariedBuildings() != null)
                setField(sectionName + FieldEnum.VARIED_BUILDINGS.name() + index, "X");
            if (record.getAdvancePayment() != null)
                setField(sectionName + FieldEnum.ADVANCE_PAYMENT.name() + index, "X");
            if (record.getBalance() != null)
                setField(sectionName + FieldEnum.BALANCE.name() + index, "X");
            if (record.getNumberOfBuildings() != null)
                setField(sectionName + FieldEnum.NUMBER_OF_BUILDINGS.name() + index, record.getNumberOfBuildings());

            if (record.getCreditAmount() != null)
                setMultiField(sectionName, FieldEnum.CREDIT_AMOUNT.name(), index, Double.parseDouble(record.getCreditAmount()));

            if (record.getDebitAmount() != null)
                setMultiField(sectionName, FieldEnum.DEBIT_AMOUNT.name(), index, Double.parseDouble(record.getDebitAmount()));

            index++;
        }

        Double parsedRecord = Double.parseDouble(imuSection.getTotalAmount(imuRecordList).toString());
        setMultiField(sectionName, FieldEnum.BALANCE.name(), parsedRecord);

        Double parsedDebit = Double.parseDouble(imuSection.getDebitTotal(imuRecordList).toString());
        setMultiField(sectionName, FieldEnum.TOTAL_DEBIT.name(), parsedDebit);

        Double parsedCredit = Double.parseDouble(imuSection.getCreditTotal(imuRecordList).toString());
        setMultiField(sectionName, FieldEnum.TOTAL_CREDIT.name(), parsedCredit);
    }

    private void setTreasurySection(int copyIndex) throws Exception {
        String sectionName = FieldEnum.TREASURY.name();
        TreasurySection treasurySection = this.form.getTreasurySection();
        List<Tax> taxList = treasurySection.getTaxList();

        if (taxList == null)
            return;

        taxList = paginateList(copyIndex, TAX_RECORDS_NUMBER, taxList);

        int index = 1;
        for (Tax record : taxList) {
            setField(sectionName + FieldEnum.TRIBUTE_CODE.name() + index, record.getTributeCode());
            setField(sectionName + FieldEnum.INSTALLMENT.name() + index, record.getInstallment());
            setField(sectionName + FieldEnum.REPORTING_YEAR.name() + index, record.getReportingYear());

            if (record.getCreditAmount() != null)
                setMultiField(sectionName, FieldEnum.CREDIT_AMOUNT.name(), index, Double.parseDouble(record.getCreditAmount()));

            if (record.getDebitAmount() != null)
                setMultiField(sectionName, FieldEnum.DEBIT_AMOUNT.name(), index, Double.parseDouble(record.getDebitAmount()));

            index++;
        }

        Double parsedRecord = Double.parseDouble(treasurySection.getTotalAmount(taxList).toString());
        setMultiField(sectionName, FieldEnum.BALANCE.name(), parsedRecord);

        Double parsedDebit = Double.parseDouble(treasurySection.getDebitTotal(taxList).toString());
        setMultiField(sectionName, FieldEnum.TOTAL_DEBIT.name(), parsedDebit);

        Double parsedCredit = Double.parseDouble(treasurySection.getCreditTotal(taxList).toString());
        setMultiField(sectionName, FieldEnum.TOTAL_CREDIT.name(), parsedCredit);
    }

    private void setSocialSecurity(int copyIndex) throws Exception {
        String sectionName = FieldEnum.SOCIAL_SECURITY.name();
        SocialSecuritySection socSecurity = this.form.getSecuritySection();
        List<SocialSecurityRecord> socSecurityList = socSecurity.getSocialSecurityRecordList();

        if (socSecurityList == null)
            return;

        socSecurityList = paginateList(copyIndex, SOC_RECORDS_NUMBER, socSecurityList);

        int index = 1;
        for (SocialSecurityRecord record : socSecurityList) {
            setField(sectionName + FieldEnum.CONTRIBUTION_REASON.name() + index, record.getContributionReason());
            setField(sectionName + FieldEnum.POSITION_CODE.name() + index, record.getPositionCode());
            setField(sectionName + FieldEnum.LOCATION_CODE.name() + index, record.getLocationCode());
            setField(sectionName + FieldEnum.INSTITUTION_CODE.name() + index, record.getInstitutionCode());
            setField(sectionName + FieldEnum.START_DATE.name() + index, record.getPeriod().getStartDate());
            setField(sectionName + FieldEnum.END_DATE.name() + index, record.getPeriod().getEndDate());

            if (record.getCreditAmount() != null)
                setMultiField(sectionName, FieldEnum.CREDIT_AMOUNT.name(), index, Double.parseDouble(record.getCreditAmount()));

            if (record.getDebitAmount() != null)
                setMultiField(sectionName, FieldEnum.DEBIT_AMOUNT.name(), index, Double.parseDouble(record.getDebitAmount()));

            index++;
        }

        Double parsedRecord = Double.parseDouble(socSecurity.getTotalAmount(socSecurityList).toString());
        setMultiField(sectionName, FieldEnum.BALANCE.name(), parsedRecord);

        Double parsedDebit = Double.parseDouble(socSecurity.getDebitTotal(socSecurityList).toString());
        setMultiField(sectionName, FieldEnum.TOTAL_DEBIT.name(), parsedDebit);

        Double parsedCredit = Double.parseDouble(socSecurity.getCreditTotal(socSecurityList).toString());
        setMultiField(sectionName, FieldEnum.TOTAL_CREDIT.name(), parsedCredit);
    }

    private void setInail(int copyIndex) throws Exception {
        String sectionName = FieldEnum.INAIL.name();
        SocialSecuritySection socSecurity = this.form.getSecuritySection();
        List<InailRecord> inailRecordList = socSecurity.getInailRecords();

        if (inailRecordList == null)
            return;

        inailRecordList = paginateList(copyIndex, INAIL_RECORDS_NUMBER, inailRecordList);

        int index = 1;
        for (InailRecord record : inailRecordList) {
            setField(sectionName + FieldEnum.LOCATION_CODE.name() + index, record.getLocationCode());
            setField(sectionName + FieldEnum.COMPANY_CODE.name() + index, record.getCompanyCode());
            setField(sectionName + FieldEnum.BANK_ACCOUNT.name() + index, record.getBankAccount());
            setField(sectionName + FieldEnum.REFERENCE_NUMBER.name() + index, record.getReferenceNumber());
            setField(sectionName + FieldEnum.REASON.name() + index, record.getReason());

            if (record.getCreditAmount() != null)
                setMultiField(sectionName, FieldEnum.CREDIT_AMOUNT.name(), index, Double.parseDouble(record.getCreditAmount()));

            if (record.getDebitAmount() != null)
                setMultiField(sectionName, FieldEnum.DEBIT_AMOUNT.name(), index, Double.parseDouble(record.getDebitAmount()));

            index++;
        }

        Double parsedRecord = Double.parseDouble(socSecurity.getTotalAmount(inailRecordList).toString());
        setMultiField(sectionName, FieldEnum.BALANCE.name(), parsedRecord);

        Double parsedDebit = Double.parseDouble(socSecurity.getDebitTotal(inailRecordList).toString());
        setMultiField(sectionName, FieldEnum.TOTAL_DEBIT.name(), parsedDebit);

        Double parsedCredit = Double.parseDouble(socSecurity.getCreditTotal(inailRecordList).toString());
        setMultiField(sectionName, FieldEnum.TOTAL_CREDIT.name(), parsedCredit);
    }

    private void setPaymentDetails() throws Exception {
        String sectionName = FieldEnum.PAYMENT_DETAILS.name();
        PaymentDetails paymentDetails = this.form.getPaymentDetails();

        setField(sectionName + FieldEnum.DATE_OF_PAYMENT.name(), paymentDetails.getDateOfPayment().replaceAll("-", ""));
        setField(sectionName + FieldEnum.COMPANY.name(), paymentDetails.getCompany());
        setField(sectionName + FieldEnum.CAB_CODE.name(), paymentDetails.getCabCode());
        setField(sectionName + FieldEnum.CHECK_NUMBER.name(), paymentDetails.getCheckNumber());
        setField(sectionName + FieldEnum.ABI_CODE.name(), paymentDetails.getAbiCode());

        if (paymentDetails.isBank()) {
            setField(sectionName + FieldEnum.BANK.name() + "1", "X");
        } else {
            setField(sectionName + FieldEnum.BANK.name() + "2", "X");
        }
    }

    private void setRegionSection(int copyIndex) throws Exception {
        String sectionName = FieldEnum.REGION_CODE.name();
        RegionSection regionSection = this.form.getRegionSection();
        List<RegionRecord> regionRecordsList = regionSection.getRegionRecordList();

        if (regionRecordsList == null)
            return;

        regionRecordsList = paginateList(copyIndex, UNIV_RECORDS_NUMBER, regionRecordsList);

        int index = 1;
        for (RegionRecord record : regionRecordsList) {
            setField(sectionName + FieldEnum.REPORTING_YEAR.name() + index, record.getReportingYear());
            setField(sectionName + FieldEnum.INSTALLMENT.name() + index, record.getInstallment());
            setField(sectionName + FieldEnum.TRIBUTE_CODE.name() + index, record.getTributeCode());
            setField(sectionName + FieldEnum.REGION_CODE.name() + index, record.getRegionCode());

            if (record.getCreditAmount() != null)
                setMultiField(sectionName, FieldEnum.CREDIT_AMOUNT.name(), index, Double.parseDouble(record.getCreditAmount()));

            if (record.getDebitAmount() != null)
                setMultiField(sectionName, FieldEnum.DEBIT_AMOUNT.name(), index, Double.parseDouble(record.getDebitAmount()));

            index++;
        }

        Double parsedRecord = Double.parseDouble(regionSection.getTotalAmount(regionRecordsList).toString());
        setMultiField(sectionName, FieldEnum.BALANCE.name(), parsedRecord);

        Double parsedDebit = Double.parseDouble(regionSection.getDebitTotal(regionRecordsList).toString());
        setMultiField(sectionName, FieldEnum.TOTAL_DEBIT.name(), parsedDebit);

        Double parsedCredit = Double.parseDouble(regionSection.getCreditTotal(regionRecordsList).toString());
        setMultiField(sectionName, FieldEnum.TOTAL_CREDIT.name(), parsedCredit);

    }

    private void setMultiField(String sectionName, String fieldName, int index, Double record) throws Exception {
        String[] splittedCreditAmount = splitMoney(record);
        setField(sectionName + fieldName + "Int" + index, splittedCreditAmount[0]);
        setField(sectionName + fieldName + "Dec" + index, splittedCreditAmount[1]);
    }

    private void setMultiField(String sectionName, String fieldName, Double record) throws Exception {
        String[] splittedCreditAmount = splitMoney(record);
        setField(sectionName + fieldName + "Int", splittedCreditAmount[0]);
        setField(sectionName + fieldName + "Dec", splittedCreditAmount[1]);
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
        try {
            loadDoc(MODEL_NAME);
            int totalPages = 0;

            int inpsRecordsCount = this.form.getInpsSection().getInpsRecordList().size();
            int imuRecordsCount = this.form.getImuSection().getImuRecordList().size();
            int regionRecordsCount = this.form.getRegionSection().getRegionRecordList().size();
            int treasutyRecordsCount = this.form.getTreasurySection().getTaxList().size();
            int inailRecordsCount = this.form.getSecuritySection().getInailRecords().size();
            int socSecurityRecordsCount = this.form.getSecuritySection().getSocialSecurityRecordList().size();

            if (treasutyRecordsCount > TAX_RECORDS_NUMBER) {
                int pagesCount = ((treasutyRecordsCount + TAX_RECORDS_NUMBER - 1) / TAX_RECORDS_NUMBER) - 1;
                totalPages = totalPages < pagesCount ? pagesCount : totalPages;
            }

            if (inpsRecordsCount > UNIV_RECORDS_NUMBER) {
                int pagesCount = ((inpsRecordsCount + UNIV_RECORDS_NUMBER - 1) / UNIV_RECORDS_NUMBER) - 1;
                totalPages = totalPages < pagesCount ? pagesCount : totalPages;
            }

            if (regionRecordsCount > UNIV_RECORDS_NUMBER) {
                int pagesCount = ((regionRecordsCount + UNIV_RECORDS_NUMBER - 1) / UNIV_RECORDS_NUMBER) - 1;
                totalPages = totalPages < pagesCount ? pagesCount : totalPages;
            }

            if (imuRecordsCount > UNIV_RECORDS_NUMBER) {
                int pagesCount = ((imuRecordsCount + UNIV_RECORDS_NUMBER - 1) / UNIV_RECORDS_NUMBER) - 1;
                totalPages = totalPages < pagesCount ? pagesCount : totalPages;
            }

            if (inailRecordsCount > INAIL_RECORDS_NUMBER) {
                int pagesCount = ((inailRecordsCount + INAIL_RECORDS_NUMBER - 1) / INAIL_RECORDS_NUMBER) - 1;
                totalPages = totalPages < pagesCount ? pagesCount : totalPages;
            }

            if (socSecurityRecordsCount > SOC_RECORDS_NUMBER) {
                int pagesCount = ((socSecurityRecordsCount + SOC_RECORDS_NUMBER - 1) / SOC_RECORDS_NUMBER) - 1;
                totalPages = totalPages < pagesCount ? pagesCount : totalPages;
            }

            copy(totalPages);

            for (int copyIndex = 0; copyIndex < getCopies().size(); copyIndex++) {
                setIndex(copyIndex);
                setHeader();
                setContributor();
                setTreasurySection(copyIndex);
                setInpsSection(copyIndex);
                setRegionSection(copyIndex);
                setImuSection(copyIndex);
                setInail(copyIndex);
                setSocialSecurity(copyIndex);
                setPaymentDetails();
            }

            mergeCopies();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            getDoc().save(byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
