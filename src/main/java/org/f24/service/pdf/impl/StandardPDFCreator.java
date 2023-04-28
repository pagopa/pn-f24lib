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
import org.f24.exception.ResourceException;
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
        Header header = this.form.getHeader();
        if (header == null)
            return;

        setField(FieldEnum.ATTORNEY.getName(), header.getDelegationTo());
        setField(FieldEnum.AGENCY.getName(), header.getAgency());
        setField(FieldEnum.AGENCY_PROVINCE.getName(), header.getProvince());
    }

    private void setPersonData() throws Exception {
        PersonalData personalData = this.form.getContributor().getPersonData().getPersonalData();

        if (personalData == null)
            return;

        setField(FieldEnum.CORPORATE_NAME.getName(), personalData.getSurname());
        setField(FieldEnum.NAME.getName(), personalData.getName());
        setField(FieldEnum.DATE_OF_BIRTH.getName(), personalData.getDateOfBirth());
        setField(FieldEnum.SEX.getName(), personalData.getSex());
        setField(FieldEnum.MUNICIPALITY_OF_BIRTH.getName(), personalData.getMunicipalityOfBirth());
        setField(FieldEnum.PROVINCE.getName(), personalData.getProvince());
    }

    private void setTaxResidenceData() throws Exception {
        TaxResidence taxResidenceData = this.form.getContributor().getPersonData().getTaxResidence();

        if (taxResidenceData == null)
            return;

        setField(FieldEnum.ADDRESS.getName(), taxResidenceData.getAddress());
        setField(FieldEnum.MUNICIPALITY.getName(), taxResidenceData.getMunicipality());
        setField(FieldEnum.PROVINCE.getName(), taxResidenceData.getProvince());
    }

    private void setContributor() throws Exception {
        Contributor contributor = this.form.getContributor();
        String currentYear = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

        if (contributor == null)
            return;

        setField(FieldEnum.TAX_CODE.getName(), contributor.getTaxCode());
        setField(FieldEnum.RECEIVER_TAX_CODE.getName(), contributor.getReceiverTaxCode());
        setField(FieldEnum.ID_CODE.getName(), contributor.getIdCode());

        if (contributor.isIfCalendarYear())
            setField(FieldEnum.CALENDAR_YEAR.getName(), currentYear);

        setPersonData();
        setTaxResidenceData();
    }

    private void setInpsSection(String sectionId, int copyIndex) throws Exception {
        InpsSection inpsSection = this.form.getInpsSection();
        List<InpsRecord> inpsRecordList = inpsSection.getInpsRecordList();

        if (inpsRecordList == null)
            return;

        inpsRecordList = paginateList(copyIndex, UNIV_RECORDS_NUMBER, inpsRecordList);

        int index = 1;
        for (InpsRecord record : inpsRecordList) {
            setField(FieldEnum.LOCATION_CODE.getName() + sectionId + index, record.getLocationCode());
            setField(FieldEnum.CONTRIBUTION_REASON.getName() + sectionId + index, record.getContributionReason());
            setField(FieldEnum.INPS_CODE.getName() + sectionId + index, record.getInpsCode());

            setMultiDate(FieldEnum.START_DATE.getName(), sectionId, index, record.getReportingPeriod().getStartDate());
            setMultiDate(FieldEnum.END_DATE.getName(), sectionId, index, record.getReportingPeriod().getEndDate());

            if (record.getCreditAmount() != null)
                setMultiField(sectionId, FieldEnum.CREDIT_AMOUNT.getName(), index,
                        Double.parseDouble(record.getCreditAmount()));

            if (record.getDebitAmount() != null)
                setMultiField(sectionId, FieldEnum.DEBIT_AMOUNT.getName(), index,
                        Double.parseDouble(record.getDebitAmount()));

            index++;
        }

        Double parsedRecord = Double.parseDouble(inpsSection.getTotalAmount(inpsRecordList).toString());
        setMultiField(sectionId, FieldEnum.BALANCE.getName(), parsedRecord);

        Double parsedDebit = Double.parseDouble(inpsSection.getDebitTotal(inpsRecordList).toString());
        setMultiField(sectionId, FieldEnum.TOTAL_DEBIT.getName(), parsedDebit);

        Double parsedCredit = Double.parseDouble(inpsSection.getCreditTotal(inpsRecordList).toString());
        setMultiField(sectionId, FieldEnum.TOTAL_CREDIT.getName(), parsedCredit);
    }

    private void setImuSection(String sectionId, int copyIndex) throws Exception {
        ImuSection imuSection = this.form.getImuSection();
        List<ImuRecord> imuRecordList = imuSection.getImuRecordList();

        if (imuRecordList == null)
            return;

        imuRecordList = paginateList(copyIndex, UNIV_RECORDS_NUMBER, imuRecordList);

        int index = 1;
        for (ImuRecord record : imuRecordList) {
            setField(FieldEnum.REPORTING_YEAR.getName() + sectionId + index, record.getReportingYear());
            setField(FieldEnum.INSTALLMENT.getName() + sectionId + index, record.getInstallment());
            setField(FieldEnum.TRIBUTE_CODE.getName() + sectionId + index, record.getTributeCode());
            setField(FieldEnum.MUNICIPALITY_CODE.getName() + sectionId + index, record.getMunicipalityCode());

            if (record.getActiveRepentance() != null)
                setField(FieldEnum.ACTIVE_REPETANCE.getName() + index, "X");
            if (record.getVariedBuildings() != null)
                setField(FieldEnum.VARIED_BUILDINGS.getName() + index, "X");
            if (record.getAdvancePayment() != null)
                setField(FieldEnum.ADVANCE_PAYMENT.getName() + index, "X");
            if (record.getBalance() != null)
                setField(FieldEnum.BALANCE.getName() + index, "X");
            if (record.getNumberOfBuildings() != null)
                setField(FieldEnum.NUMBER_OF_BUILDINGS.getName() + index, record.getNumberOfBuildings());

            if (record.getCreditAmount() != null)
                setMultiField(sectionId, FieldEnum.CREDIT_AMOUNT.getName(), index,
                        Double.parseDouble(record.getCreditAmount()));

            if (record.getDebitAmount() != null)
                setMultiField(sectionId, FieldEnum.DEBIT_AMOUNT.getName(), index,
                        Double.parseDouble(record.getDebitAmount()));

            index++;
        }

        Double parsedRecord = Double.parseDouble(imuSection.getTotalAmount(imuRecordList).toString());
        setMultiField(sectionId, FieldEnum.BALANCE.getName(), parsedRecord);

        Double parsedDeduction = Double.parseDouble(imuSection.getDeduction().toString());
        setMultiField(FieldEnum.DEDUCTION.getName(), parsedDeduction);

        Double parsedDebit = Double.parseDouble(imuSection.getDebitTotal(imuRecordList).toString());
        setMultiField(sectionId, FieldEnum.TOTAL_DEBIT.getName(), parsedDebit);

        Double parsedCredit = Double.parseDouble(imuSection.getCreditTotal(imuRecordList).toString());
        setMultiField(sectionId, FieldEnum.TOTAL_CREDIT.getName(), parsedCredit);
    }

    private void setTreasurySection(String sectionId, int copyIndex) throws Exception {
        TreasurySection treasurySection = this.form.getTreasurySection();
        List<Tax> taxList = treasurySection.getTaxList();

        if (taxList == null)
            return;

        taxList = paginateList(copyIndex, TAX_RECORDS_NUMBER, taxList);

        int index = 1;
        for (Tax record : taxList) {
            setField(FieldEnum.TRIBUTE_CODE.getName() + sectionId + index, record.getTributeCode());
            setField(FieldEnum.INSTALLMENT.getName() + sectionId + index, record.getInstallment());
            setField(FieldEnum.REPORTING_YEAR.getName() + sectionId + index, record.getReportingYear());

            if (record.getCreditAmount() != null)
                setMultiField(sectionId, FieldEnum.CREDIT_AMOUNT.getName(), index,
                        Double.parseDouble(record.getCreditAmount()));

            if (record.getDebitAmount() != null)
                setMultiField(sectionId, FieldEnum.DEBIT_AMOUNT.getName(), index,
                        Double.parseDouble(record.getDebitAmount()));

            index++;
        }

        setField(FieldEnum.OFFICE_CODE.getName(), treasurySection.getOfficeCode());
        setField(FieldEnum.DEED_CODE.getName(), treasurySection.getActCode());

        Double parsedRecord = Double.parseDouble(treasurySection.getTotalAmount(taxList).toString());
        setMultiField(sectionId, FieldEnum.BALANCE.getName(), parsedRecord);

        Double parsedDebit = Double.parseDouble(treasurySection.getDebitTotal(taxList).toString());
        setMultiField(sectionId, FieldEnum.TOTAL_DEBIT.getName(), parsedDebit);

        Double parsedCredit = Double.parseDouble(treasurySection.getCreditTotal(taxList).toString());
        setMultiField(sectionId, FieldEnum.TOTAL_CREDIT.getName(), parsedCredit);
    }

    private void setSocialSecurity(String sectionId, int copyIndex) throws Exception {
        SocialSecuritySection socSecurity = this.form.getSecuritySection();
        List<SocialSecurityRecord> socSecurityList = socSecurity.getSocialSecurityRecordList();

        if (socSecurityList == null)
            return;

        socSecurityList = paginateList(copyIndex, SOC_RECORDS_NUMBER, socSecurityList);

        int index = 1;
        for (SocialSecurityRecord record : socSecurityList) {
            setField(FieldEnum.INSTITUTION_CODE.getName() + sectionId, record.getInstitutionCode());
            setField(FieldEnum.LOCATION_CODE.getName() + sectionId + index, record.getLocationCode());
            setField(FieldEnum.CONTRIBUTION_REASON.getName() + sectionId + index, record.getContributionReason());
            setField(FieldEnum.POSITION_CODE.getName() + sectionId + index, record.getPositionCode());

            setMultiDate(FieldEnum.START_DATE.getName(), sectionId, index, record.getPeriod().getStartDate());
            setMultiDate(FieldEnum.END_DATE.getName(), sectionId, index, record.getPeriod().getEndDate());

            if (record.getCreditAmount() != null)
                setMultiField(sectionId, FieldEnum.CREDIT_AMOUNT.getName(), index,
                        Double.parseDouble(record.getCreditAmount()));

            if (record.getDebitAmount() != null)
                setMultiField(sectionId, FieldEnum.DEBIT_AMOUNT.getName(), index,
                        Double.parseDouble(record.getDebitAmount()));

            index++;
        }

        Double parsedRecord = Double.parseDouble(socSecurity.getTotalAmount(socSecurityList).toString());
        setMultiField(sectionId, FieldEnum.BALANCE.getName(), parsedRecord);

        Double parsedDebit = Double.parseDouble(socSecurity.getDebitTotal(socSecurityList).toString());
        setMultiField(sectionId, FieldEnum.TOTAL_DEBIT.getName(), parsedDebit);

        Double parsedCredit = Double.parseDouble(socSecurity.getCreditTotal(socSecurityList).toString());
        setMultiField(sectionId, FieldEnum.TOTAL_CREDIT.getName(), parsedCredit);
    }

    private void setInail(String sectionId, int copyIndex) throws Exception {
        SocialSecuritySection socSecurity = this.form.getSecuritySection();
        List<InailRecord> inailRecordList = socSecurity.getInailRecords();

        if (inailRecordList == null)
            return;

        inailRecordList = paginateList(copyIndex, INAIL_RECORDS_NUMBER, inailRecordList);

        int index = 1;
        for (InailRecord record : inailRecordList) {
            setField(FieldEnum.LOCATION_CODE.getName() + sectionId + index, record.getLocationCode());
            setField(FieldEnum.COMPANY_CODE.getName() + sectionId + index, record.getCompanyCode());
            setField(FieldEnum.CONTROL_CODE.getName() + sectionId + index, record.getBankAccount());
            setField(FieldEnum.REFERENCE_NUMBER.getName() + sectionId + index, record.getReferenceNumber());
            setField(FieldEnum.REASON.getName() + sectionId + index, record.getReason());

            if (record.getCreditAmount() != null)
                setMultiField(sectionId, FieldEnum.CREDIT_AMOUNT.getName(), index,
                        Double.parseDouble(record.getCreditAmount()));

            if (record.getDebitAmount() != null)
                setMultiField(sectionId, FieldEnum.DEBIT_AMOUNT.getName(), index,
                        Double.parseDouble(record.getDebitAmount()));

            index++;
        }

        Double parsedRecord = Double.parseDouble(socSecurity.getTotalAmount(inailRecordList).toString());
        setMultiField(sectionId, FieldEnum.BALANCE.getName(), parsedRecord);

        Double parsedDebit = Double.parseDouble(socSecurity.getDebitTotal(inailRecordList).toString());
        setMultiField(sectionId, FieldEnum.TOTAL_DEBIT.getName(), parsedDebit);

        Double parsedCredit = Double.parseDouble(socSecurity.getCreditTotal(inailRecordList).toString());
        setMultiField(sectionId, FieldEnum.TOTAL_CREDIT.getName(), parsedCredit);
    }

    private void setPaymentDetails() throws Exception {
        PaymentDetails paymentDetails = this.form.getPaymentDetails();

        setField(FieldEnum.DATE_OF_PAYMENT.getName(), paymentDetails.getDateOfPayment().replaceAll("-", ""));
        setField(FieldEnum.COMPANY.getName(), paymentDetails.getCompany());
        setField(FieldEnum.CAB_CODE.getName(), paymentDetails.getCabCode());
        setField(FieldEnum.CHECK_NUMBER.getName(), paymentDetails.getCheckNumber());
        setField(FieldEnum.ABI_CODE.getName(), paymentDetails.getAbiCode());

        if (paymentDetails.isBank()) {
            setField(FieldEnum.IS_BANK.getName(), "X");
        } else {
            setField(FieldEnum.IS_CIRCULAR.getName(), "X");
        }
    }

    private void setRegionSection(String sectionId, int copyIndex) throws Exception {
        RegionSection regionSection = this.form.getRegionSection();
        List<RegionRecord> regionRecordsList = regionSection.getRegionRecordList();

        if (regionRecordsList == null)
            return;

        regionRecordsList = paginateList(copyIndex, UNIV_RECORDS_NUMBER, regionRecordsList);

        int index = 1;
        for (RegionRecord record : regionRecordsList) {
            setField(FieldEnum.REPORTING_YEAR.getName() + sectionId + index, record.getReportingYear());
            setField(FieldEnum.INSTALLMENT.getName() + sectionId + index, record.getInstallment());
            setField(FieldEnum.TRIBUTE_CODE.getName() + sectionId + index, record.getTributeCode());
            setField(FieldEnum.REGION_CODE.getName() + sectionId + index, record.getRegionCode());

            if (record.getCreditAmount() != null)
                setMultiField(sectionId, FieldEnum.CREDIT_AMOUNT.getName(), index,
                        Double.parseDouble(record.getCreditAmount()));

            if (record.getDebitAmount() != null)
                setMultiField(sectionId, FieldEnum.DEBIT_AMOUNT.getName(), index,
                        Double.parseDouble(record.getDebitAmount()));

            index++;
        }

        Double parsedRecord = Double.parseDouble(regionSection.getTotalAmount(regionRecordsList).toString());
        setMultiField(sectionId, FieldEnum.BALANCE.getName(), parsedRecord);

        Double parsedDebit = Double.parseDouble(regionSection.getDebitTotal(regionRecordsList).toString());
        setMultiField(sectionId, FieldEnum.TOTAL_DEBIT.getName(), parsedDebit);

        Double parsedCredit = Double.parseDouble(regionSection.getCreditTotal(regionRecordsList).toString());
        setMultiField(sectionId, FieldEnum.TOTAL_CREDIT.getName(), parsedCredit);

    }

    private void setMultiField(String sectionId, String fieldName, int index, Double record) throws Exception {
        String[] splittedCreditAmount = splitField(record);
        setField(fieldName + "Int" + sectionId + index, splittedCreditAmount[0]);
        setField(fieldName + "Dec" + sectionId + index, splittedCreditAmount[1]);
    }

    private void setMultiField(String sectionId, String fieldName, Double record) throws Exception {
        String[] splittedCreditAmount = splitField(record);
        setField(fieldName + "Int" + sectionId, splittedCreditAmount[0]);
        setField(fieldName + "Dec" + sectionId, splittedCreditAmount[1]);
    }

    private void setMultiField(String fieldName, Double record) throws Exception {
        String[] splittedCreditAmount = splitField(record);
        setField(fieldName + "Int", splittedCreditAmount[0]);
        setField(fieldName + "Dec", splittedCreditAmount[1]);
    }

    private void setMultiDate(String fieldName, String sectionId, int index, String date) throws ResourceException {
        String monthPart = date.substring(0, 2);
        String yearPart = date.substring(2, date.length() - 1);

        setField(fieldName + "Month" + sectionId + index, monthPart);
        setField(fieldName + "Year" + sectionId + index, yearPart);
    }

    private <T> List<T> paginateList(int copyIndex, int maxRecordsNumber, List<T> targetList) {
        int limit = copyIndex * maxRecordsNumber + maxRecordsNumber;
        limit = Math.min(limit, targetList.size());

        return targetList = targetList.subList(copyIndex * maxRecordsNumber, limit);
    }

    private String[] splitField(double input) {
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

            //TODO Test with multiple records count
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

            int copiesCount = getCopies().size();

            for (int copyIndex = 0; copyIndex < copiesCount; copyIndex++) {
                setIndex(copyIndex);
                setHeader();
                setContributor();
                setTreasurySection("1", copyIndex);
                setInpsSection("2", copyIndex);
                setRegionSection("3", copyIndex);
                setImuSection("4", copyIndex);
                setInail("5", copyIndex);
                setSocialSecurity("6", copyIndex);
                setPaymentDetails();
                setField(FieldEnum.IBAN_CODE.getName(), this.form.getIbanCode());
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
