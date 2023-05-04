package org.f24.service.pdf.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.f24.dto.component.*;
import org.f24.dto.component.Record;
import org.f24.dto.form.F24Standard;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.PDFCreator;
import org.f24.service.pdf.PDFFormManager;
import org.f24.service.pdf.CreatorHelper;
import org.f24.service.pdf.FieldEnum;

public class StandardPDFCreator extends PDFFormManager implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24IMU2013.pdf";
    private static final int TAX_RECORDS_NUMBER = 6;
    private static final int UNIV_RECORDS_NUMBER = 4;
    private static final int INAIL_RECORDS_NUMBER = 3;
    private static final int SOC_RECORDS_NUMBER = 2;

    private CreatorHelper helper = new CreatorHelper();
    private Logger logger = Logger.getLogger(StandardPDFCreator.class.getName());
    private F24Standard form;
    private int totalBalance = 0;

    /**
     * Constructs Standard PDF Creator.
     *
     * @param form F24Standard component (DTO for Standard Form).
     */
    public StandardPDFCreator(F24Standard form) {
        this.form = form;
        logger.setLevel(Level.WARNING);
    }

    private void setHeader() throws Exception {
        Header header = this.form.getHeader();

        if (header != null) {
            setField(FieldEnum.ATTORNEY.getName(), header.getDelegationTo());
            setField(FieldEnum.AGENCY.getName(), header.getAgency());
            setField(FieldEnum.AGENCY_PROVINCE.getName(), header.getProvince());
        }
    }

    private void setPersonData() throws Exception {
        PersonalData personalData = this.form.getTaxPayer().getPersonData().getPersonalData();

        if (personalData != null) {
            setField(FieldEnum.CORPORATE_NAME.getName(), personalData.getSurname());
            setField(FieldEnum.NAME.getName(), personalData.getName());
            setField(FieldEnum.DATE_OF_BIRTH.getName(), personalData.getBirthdate().replace("-", ""));
            setField(FieldEnum.SEX.getName(), personalData.getSex());
            setField(FieldEnum.MUNICIPALITY_OF_BIRTH.getName(), personalData.getBirthMunicipality());
            setField(FieldEnum.PROVINCE.getName(), personalData.getProvince());
        }
    }

    private void setTaxResidenceData() throws Exception {
        TaxAddress taxResidenceData = this.form.getTaxPayer().getPersonData().getTaxAddress();

        if (taxResidenceData != null) {
            setField(FieldEnum.ADDRESS.getName(), taxResidenceData.getAddress());
            setField(FieldEnum.MUNICIPALITY.getName(), taxResidenceData.getMunicipality());
            setField(FieldEnum.TAX_PROVINCE.getName(), taxResidenceData.getProvince());
        }
    }

    private void setContributor() throws Exception {
        TaxPayer contributor = this.form.getTaxPayer();

        if (contributor != null) {
            setField(FieldEnum.TAX_CODE.getName(), contributor.getTaxCode());
            setField(FieldEnum.OTHER_TAX_CODE.getName(), contributor.getOtherTaxCode());
            setField(FieldEnum.ID_CODE.getName(), contributor.getIdCode());

            if (contributor.isNotCalendarYear())
                setField(FieldEnum.CALENDAR_YEAR.getName(), "X");

            setPersonData();
            setTaxResidenceData();
        }
    }

    private void setInpsSection(String sectionId, int copyIndex) throws Exception {
        InpsSection inpsSection = this.form.getInpsSection();
        List<InpsRecord> inpsRecordList = inpsSection.getInpsRecordList();

        if (inpsRecordList != null) {
            inpsRecordList = helper.paginateList(copyIndex, UNIV_RECORDS_NUMBER, inpsRecordList);

            int index = 1;
            for (InpsRecord record : inpsRecordList) {
                setField(FieldEnum.LOCATION_CODE.getName() + sectionId + index, record.getOfficeCode());
                setField(FieldEnum.CONTRIBUTION_REASON.getName() + sectionId + index, record.getContributionReason());
                setField(FieldEnum.INPS_CODE.getName() + sectionId + index, record.getInpsCode());

                setMultiDate(FieldEnum.START_DATE.getName(), sectionId, index, record.getPeriod().getStartDate());
                setMultiDate(FieldEnum.END_DATE.getName(), sectionId, index, record.getPeriod().getEndDate());

                setSectionRecordAmounts(sectionId, index, record);

                index++;
            }

            setSectionTotals(sectionId, index, inpsRecordList);
        }
    }

    private void setImuSection(String sectionId, int copyIndex) throws Exception {
        ImuSection imuSection = this.form.getImuSection();
        List<ImuRecord> imuRecordList = imuSection.getImuRecordList();

        if (imuRecordList != null) {
            imuRecordList = helper.paginateList(copyIndex, UNIV_RECORDS_NUMBER, imuRecordList);

            int index = 1;
            for (ImuRecord record : imuRecordList) {
                setField(FieldEnum.YEAR.getName() + sectionId + index, record.getYear());
                setField(FieldEnum.INSTALLMENT.getName() + sectionId + index, record.getInstallment());
                setField(FieldEnum.TRIBUTE_CODE.getName() + sectionId + index, record.getTributeCode());
                setField(FieldEnum.MUNICIPALITY_CODE.getName() + sectionId + index, record.getMunicipalityCode());

                if (record.getRepentance() != null) {
                    setField(FieldEnum.REPENTANCE.getName() + index, "X");
                }
                if (record.getChangedBuildings() != null) {
                    setField(FieldEnum.CHANGED_BUILDINGS.getName() + index, "X");
                }
                if (record.getAdvancePayment() != null) {
                    setField(FieldEnum.ADVANCE_PAYMENT.getName() + index, "X");
                }
                if (record.getPayment() != null) {
                    setField(FieldEnum.PAYMENT.getName() + index, "X");
                }
                if (record.getNumberOfBuildings() != null) {
                    setField(FieldEnum.NUMBER_OF_BUILDINGS.getName() + index, record.getNumberOfBuildings());
                }

                setSectionRecordAmounts(sectionId, index, record);

                index++;
            }

            setSectionTotals(sectionId, index, imuRecordList);

            Double parsedDeduction = Double.parseDouble(imuSection.getDeduction());
            setMultiField(FieldEnum.DEDUCTION.getName(), parsedDeduction);
        }
    }

    private void setTreasurySection(String sectionId, int copyIndex) throws Exception {
        TreasurySection treasurySection = this.form.getTreasurySection();
        List<Tax> taxList = treasurySection.getTaxList();

        if (taxList != null) {
            taxList = helper.paginateList(copyIndex, TAX_RECORDS_NUMBER, taxList);

            int index = 1;
            for (Tax record : taxList) {
                setField(FieldEnum.TRIBUTE_CODE.getName() + sectionId + index, record.getTributeCode());
                setField(FieldEnum.INSTALLMENT.getName() + sectionId + index, record.getInstallment());
                setField(FieldEnum.YEAR.getName() + sectionId + index, record.getYear());

                setSectionRecordAmounts(sectionId, index, record);

                index++;
            }

            setField(FieldEnum.OFFICE_CODE.getName(), treasurySection.getOfficeCode());
            setField(FieldEnum.ACT_CODE.getName(), treasurySection.getActCode());

            setSectionTotals(sectionId, index, taxList);
        }
    }

    private void setSocialSecurity(String sectionId, int copyIndex) throws Exception {
        SocialSecuritySection socSecurity = this.form.getSecuritySection();
        List<SocialSecurityRecord> socSecurityList = socSecurity.getSocialSecurityRecordList();

        if (socSecurityList != null) {
            socSecurityList = helper.paginateList(copyIndex, SOC_RECORDS_NUMBER, socSecurityList);

            int index = 1;
            for (SocialSecurityRecord record : socSecurityList) {
                setField(FieldEnum.INSTITUTION_CODE.getName() + sectionId, record.getInstitutionCode());
                setField(FieldEnum.LOCATION_CODE.getName() + sectionId + index, record.getOfficeCode());
                setField(FieldEnum.CONTRIBUTION_REASON.getName() + sectionId + index, record.getContributionReason());
                setField(FieldEnum.POSITION_CODE.getName() + sectionId + index, record.getPositionCode());

                setMultiDate(FieldEnum.START_DATE.getName(), sectionId, index, record.getPeriod().getStartDate());
                setMultiDate(FieldEnum.END_DATE.getName(), sectionId, index, record.getPeriod().getEndDate());

                setSectionRecordAmounts(sectionId, index, record);

                index++;
            }

            setSectionTotals(sectionId, index, socSecurityList);
        }
    }

    private void setInail(String sectionId, int copyIndex) throws Exception {
        SocialSecuritySection socSecurity = this.form.getSecuritySection();
        List<InailRecord> inailRecordList = socSecurity.getInailRecords();

        if (inailRecordList != null) {
            inailRecordList = helper.paginateList(copyIndex, INAIL_RECORDS_NUMBER, inailRecordList);

            int index = 1;
            for (InailRecord record : inailRecordList) {
                setField(FieldEnum.LOCATION_CODE.getName() + sectionId + index, record.getOfficeCode());
                setField(FieldEnum.COMPANY_CODE.getName() + sectionId + index, record.getCompanyCode());
                setField(FieldEnum.CONTROL_CODE.getName() + sectionId + index, record.getControlCode());
                setField(FieldEnum.REFERENCE_NUMBER.getName() + sectionId + index, record.getReferenceNumber());
                setField(FieldEnum.REASON.getName() + sectionId + index, record.getReason());

                setSectionRecordAmounts(sectionId, index, record);

                index++;
            }

            setSectionTotals(sectionId, index, inailRecordList);
        }
    }

    private void setPaymentDetails() throws Exception {
        PaymentDetails paymentDetails = this.form.getPaymentDetails();

        setField(FieldEnum.DATE_OF_PAYMENT.getName(), paymentDetails.getPaymentDate().replaceAll("-", ""));
        setField(FieldEnum.COMPANY.getName(), paymentDetails.getCompany());
        setField(FieldEnum.CAB_CODE.getName(), paymentDetails.getCabCode());
        setField(FieldEnum.CHECK_NUMBER.getName(), paymentDetails.getCheckNumber());
        setField(FieldEnum.ABI_CODE.getName(), paymentDetails.getAbiCode());

        if (paymentDetails.isBank()) {
            setField(FieldEnum.BANK.getName(), "X");
        } else {
            setField(FieldEnum.CIRCULAR.getName(), "X");
        }
    }

    private void setRegionSection(String sectionId, int copyIndex) throws Exception {
        RegionSection regionSection = this.form.getRegionSection();
        List<RegionRecord> regionRecordsList = regionSection.getRegionRecordList();

        if (regionRecordsList != null) {
            regionRecordsList = helper.paginateList(copyIndex, UNIV_RECORDS_NUMBER, regionRecordsList);

            int index = 1;
            for (RegionRecord record : regionRecordsList) {
                setField(FieldEnum.YEAR.getName() + sectionId + index, record.getYear());
                setField(FieldEnum.INSTALLMENT.getName() + sectionId + index, record.getInstallment());
                setField(FieldEnum.TRIBUTE_CODE.getName() + sectionId + index, record.getTributeCode());
                setField(FieldEnum.REGION_CODE.getName() + sectionId + index, record.getRegionCode());

                setSectionRecordAmounts(sectionId, index, record);

                index++;
            }

            setSectionTotals(sectionId, index, regionRecordsList);
        }
    }

    private void setSectionTotals(String sectionId, int index,
            List<? extends Record> recordList) throws NumberFormatException, ResourceException {

        if (helper.getTotalAmount(recordList) != null) {
            Integer total = helper.getTotalAmount(recordList);
            totalBalance += total;
            String parsedTotal = helper.getMoney(total);
            setField(FieldEnum.TOTAL_AMOUNT.getName() + sectionId, parsedTotal);
        }

        if (helper.getCreditTotal(recordList) != null) {
            Integer creditTotal = helper.getCreditTotal(recordList);
            String parsedTotal = helper.getMoney(creditTotal);
            setField(FieldEnum.TOTAL_CREDIT.getName() + sectionId, parsedTotal);
        }

        if (helper.getDebitTotal(recordList) != null) {
            Integer debitTotal = helper.getDebitTotal(recordList);
            String parsedTotal = helper.getMoney(debitTotal);
            setField(FieldEnum.TOTAL_DEBIT.getName() + sectionId, parsedTotal);
        }
    }

    private void setSectionRecordAmounts(String sectionId, int index, Record sourceRecord)
            throws ResourceException {
        if (sourceRecord.getCreditAmount() != null) {
            String recordCredit = sourceRecord.getCreditAmount();
            String parsedCredit = helper.getMoney(Integer.parseInt(recordCredit));
            setField(FieldEnum.CREDIT_AMOUNT.getName() + sectionId + index, parsedCredit);
        }

        if (sourceRecord.getDebitAmount() != null) {
            String recordDebit = sourceRecord.getDebitAmount();
            String parsedDebit = helper.getMoney(Integer.parseInt(recordDebit));
            setField(FieldEnum.DEBIT_AMOUNT.getName() + sectionId + index, parsedDebit);
        }

        if (sourceRecord.getDeduction() != null) {
            String parseDeduction = helper.getMoney(Integer.parseInt(sourceRecord.getDeduction()));
            setField(FieldEnum.DEDUCTION.getName() + sectionId + index, parseDeduction);
        }

    }

    private void setMultiField(String fieldName, Double sourceRecord) throws Exception {
        String[] splittedCreditAmount = splitField(sourceRecord);
        setField(fieldName + "Int", splittedCreditAmount[0]);
        setField(fieldName + "Dec", splittedCreditAmount[1]);
    }

    private void setMultiDate(String fieldName, String sectionId, int index, String date) throws ResourceException {
        String monthPart = date.substring(0, 2);
        String yearPart = date.substring(2, date.length() - 1);

        setField(fieldName + "Month" + sectionId + index, monthPart);
        setField(fieldName + "Year" + sectionId + index, yearPart);
    }

    private String[] splitField(double input) {
        input = Math.round(input * 100.0) / 100.0;
        int integerPart = (int) input;
        double decimalPart = input - integerPart;
        return new String[] { Integer.toString(integerPart), String.format(Locale.ROOT, "%.2f", decimalPart).split("\\.")[1] };
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

            // TODO Test with multiple records count
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
                // setPaymentDetails();
                setField(FieldEnum.IBAN_CODE.getName(), this.form.getIbanCode());
                setField(FieldEnum.TOTAL_AMOUNT.getName(), helper.getMoney(totalBalance));
            }

            mergeCopies();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            getDoc().save(byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return null;
        }
    }

}
