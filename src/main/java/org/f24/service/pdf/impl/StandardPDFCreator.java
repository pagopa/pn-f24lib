package org.f24.service.pdf.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import org.f24.dto.component.*;
import org.f24.dto.component.Record;
import org.f24.dto.form.F24Standard;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.PDFCreator;
import org.f24.service.pdf.util.PDFFormManager;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;

import org.f24.service.pdf.util.CreatorHelper;
import org.f24.service.pdf.util.FieldEnum;

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
    }

    private void setHeader() throws ResourceException {
        Header header = this.form.getHeader();

        if (header != null) {
            setField(FieldEnum.ATTORNEY.getName(), header.getDelegationTo());
            setField(FieldEnum.AGENCY.getName(), header.getAgency());
            setField(FieldEnum.AGENCY_PROVINCE.getName(), header.getProvince());
        }
    }

    private void setPersonData() throws ResourceException {
        PersonalData personalData = this.form.getTaxPayer().getPersonData().getPersonalData();

        if (personalData != null) {
            setField(FieldEnum.CORPORATE_NAME.getName(), personalData.getSurname());
            setField(FieldEnum.NAME.getName(), personalData.getName());
            setField(FieldEnum.BIRTH_DATE.getName(), personalData.getBirthDate().replace("-", ""));
            setField(FieldEnum.SEX.getName(), personalData.getSex());
            setField(FieldEnum.BIRTH_PLACE.getName(), personalData.getBirthPlace());
            setField(FieldEnum.BIRTH_PROVINCE.getName(), personalData.getBirthProvince());
        }
    }

    private void setTaxResidenceData() throws ResourceException {
        TaxAddress taxResidenceData = this.form.getTaxPayer().getPersonData().getTaxAddress();

        if (taxResidenceData != null) {
            setField(FieldEnum.ADDRESS.getName(), taxResidenceData.getAddress());
            setField(FieldEnum.MUNICIPALITY.getName(), taxResidenceData.getMunicipality());
            setField(FieldEnum.TAX_PROVINCE.getName(), taxResidenceData.getProvince());
        }
    }

    private void setTaxPayer() throws ResourceException {
        TaxPayer taxPayer = this.form.getTaxPayer();

        if (taxPayer != null) {
            setField(FieldEnum.TAX_CODE.getName(), taxPayer.getTaxCode());
            setField(FieldEnum.RELATIVE_TAX_CODE.getName(), taxPayer.getRelativePersonTaxCode());
            setField(FieldEnum.ID_CODE.getName(), taxPayer.getIdCode());

            if (taxPayer.getIsNotTaxYear())
                setField(FieldEnum.IS_NOT_TAX_YEAR.getName(), "X");

            setPersonData();
            setCompanyData();
            setTaxResidenceData();
        }
    }

    private void setCompanyData() throws ResourceException {
        CompanyData companyData = this.form.getTaxPayer().getCompanyData();
        if(companyData != null) {
            setField(FieldEnum.CORPORATE_NAME.getName(), companyData.getName());
        }
    }

    private void setInpsSection(String sectionId, int copyIndex) throws ResourceException {
        InpsSection inpsSection = this.form.getInpsSection();
        List<InpsRecord> inpsRecordList = inpsSection.getInpsRecordList();

        if (inpsRecordList != null) {
            inpsRecordList = helper.paginateList(copyIndex, UNIV_RECORDS_NUMBER, inpsRecordList);

            int index = 1;
            for (InpsRecord inpsRecord : inpsRecordList) {
                setField(FieldEnum.OFFICE_CODE.getName() + sectionId + index, inpsRecord.getOfficeCode());
                setField(FieldEnum.CONTRIBUTION_REASON.getName() + sectionId + index, inpsRecord.getContributionReason());
                setField(FieldEnum.INPS_CODE.getName() + sectionId + index, inpsRecord.getInpsCode());

                setMultiDate(FieldEnum.START_DATE.getName(), sectionId, index, inpsRecord.getPeriod().getStartDate());
                setMultiDate(FieldEnum.END_DATE.getName(), sectionId, index, inpsRecord.getPeriod().getEndDate());

                setSectionRecordAmounts(sectionId, index, inpsRecord);

                index++;
            }

            setSectionTotals(sectionId, inpsRecordList);
        }
    }

    private void setLocalTaxSection(String sectionId, int copyIndex) throws ResourceException {
        LocalTaxSection localTaxSection = this.form.getLocalTaxSection();
        List<LocalTaxRecord> localTaxRecordList = localTaxSection.getLocalTaxRecordList();

        if (localTaxRecordList != null) {
            localTaxRecordList = helper.paginateList(copyIndex, UNIV_RECORDS_NUMBER, localTaxRecordList);

            int index = 1;
            for (LocalTaxRecord localTaxRecord : localTaxRecordList) {
                setField(FieldEnum.YEAR.getName() + sectionId + index, localTaxRecord.getYear());
                setField(FieldEnum.INSTALLMENT.getName() + sectionId + index, localTaxRecord.getInstallment());
                setField(FieldEnum.TAX_TYPE_CODE.getName() + sectionId + index, localTaxRecord.getTaxTypeCode());
                setField(FieldEnum.MUNICIPALITY_CODE.getName() + sectionId + index, localTaxRecord.getMunicipalityCode());

                if (localTaxRecord.getReconsideration() != null) {
                    setField(FieldEnum.RECONSIDERATION.getName() + index, "X");
                }
                if (localTaxRecord.getPropertiesChanges() != null) {
                    setField(FieldEnum.PROPERTIES_CHANGED.getName() + index, "X");
                }
                if (localTaxRecord.getAdvancePayment() != null) {
                    setField(FieldEnum.ADVANCE_PAYMENT.getName() + index, "X");
                }
                if (localTaxRecord.getFullPayment() != null) {
                    setField(FieldEnum.FULL_PAYMENT.getName() + index, "X");
                }
                if (localTaxRecord.getNumberOfProperties() != null) {
                    setField(FieldEnum.NUMBER_OF_PROPERTIES.getName() + index, localTaxRecord.getNumberOfProperties());
                }

                setSectionRecordAmounts(sectionId, index, localTaxRecord);

                index++;
            }

            setSectionTotals(sectionId, localTaxRecordList);

            Double parsedDeduction = Double.parseDouble(localTaxSection.getDeduction());
            setMultiField(FieldEnum.DEDUCTION.getName(), parsedDeduction);
        }
    }

    private void setTreasurySection(String sectionId, int copyIndex) throws ResourceException {
        TreasurySection treasurySection = this.form.getTreasurySection();
        List<Tax> taxList = treasurySection.getTaxList();

        if (taxList != null) {
            taxList = helper.paginateList(copyIndex, TAX_RECORDS_NUMBER, taxList);

            int index = 1;
            for (Tax taxRecord : taxList) {
                setField(FieldEnum.TAX_TYPE_CODE.getName() + sectionId + index, taxRecord.getTaxTypeCode());
                setField(FieldEnum.INSTALLMENT.getName() + sectionId + index, taxRecord.getInstallment());
                setField(FieldEnum.YEAR.getName() + sectionId + index, taxRecord.getYear());

                setSectionRecordAmounts(sectionId, index, taxRecord);

                index++;
            }

            setField(FieldEnum.OFFICE_CODE_TREASURY.getName(), treasurySection.getOfficeCode());
            setField(FieldEnum.DOCUMENT_CODE.getName(), treasurySection.getDocumentCode());

            setSectionTotals(sectionId, taxList);
        }
    }

    private void setSocialSecurity(String sectionId, int copyIndex) throws ResourceException {
        SocialSecuritySection socSecurity = this.form.getSocialSecuritySection();
        List<SocialSecurityRecord> socSecurityList = socSecurity.getSocialSecurityRecordList();

        if (socSecurityList != null) {
            socSecurityList = helper.paginateList(copyIndex, SOC_RECORDS_NUMBER, socSecurityList);

            int index = 1;
            for (SocialSecurityRecord socSecRecord : socSecurityList) {
                setField(FieldEnum.INSTITUTION_CODE.getName() + sectionId, socSecRecord.getInstitutionCode());
                setField(FieldEnum.OFFICE_CODE.getName() + sectionId + index, socSecRecord.getOfficeCode());
                setField(FieldEnum.CONTRIBUTION_REASON.getName() + sectionId + index, socSecRecord.getContributionReason());
                setField(FieldEnum.POSITION_CODE.getName() + sectionId + index, socSecRecord.getPositionCode());

                setMultiDate(FieldEnum.START_DATE.getName(), sectionId, index, socSecRecord.getPeriod().getStartDate());
                setMultiDate(FieldEnum.END_DATE.getName(), sectionId, index, socSecRecord.getPeriod().getEndDate());

                setSectionRecordAmounts(sectionId, index, socSecRecord);

                index++;
            }

            setSectionTotals(sectionId, socSecurityList);
        }
    }

    private void setInail(String sectionId, int copyIndex) throws ResourceException {
        SocialSecuritySection socSecurity = this.form.getSocialSecuritySection();
        List<InailRecord> inailRecordList = socSecurity.getInailRecords();

        if (inailRecordList != null) {
            inailRecordList = helper.paginateList(copyIndex, INAIL_RECORDS_NUMBER, inailRecordList);

            int index = 1;
            for (InailRecord inailRecord : inailRecordList) {
                setField(FieldEnum.OFFICE_CODE.getName() + sectionId + index, inailRecord.getOfficeCode());
                setField(FieldEnum.COMPANY_CODE.getName() + sectionId + index, inailRecord.getCompanyCode());
                setField(FieldEnum.CONTROL_CODE.getName() + sectionId + index, inailRecord.getControlCode());
                setField(FieldEnum.REFERENCE_NUMBER.getName() + sectionId + index, inailRecord.getReferenceNumber());
                setField(FieldEnum.REASON.getName() + sectionId + index, inailRecord.getReason());

                setSectionRecordAmounts(sectionId, index, inailRecord);

                index++;
            }

            setSectionTotals(sectionId, inailRecordList);
        }
    }

    private void setPaymentDetails() throws ResourceException {
        PaymentDetails paymentDetails = this.form.getPaymentDetails();

        setField(FieldEnum.DATE_OF_PAYMENT.getName(), paymentDetails.getPaymentDate().replace("-", ""));
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

    private void setRegionSection(String sectionId, int copyIndex) throws ResourceException {
        RegionSection regionSection = this.form.getRegionSection();
        List<RegionRecord> regionRecordsList = regionSection.getRegionRecordList();

        if (regionRecordsList != null) {
            regionRecordsList = helper.paginateList(copyIndex, UNIV_RECORDS_NUMBER, regionRecordsList);

            int index = 1;
            for (RegionRecord regionRecord : regionRecordsList) {
                setField(FieldEnum.YEAR.getName() + sectionId + index, regionRecord.getYear());
                setField(FieldEnum.INSTALLMENT.getName() + sectionId + index, regionRecord.getInstallment());
                setField(FieldEnum.TAX_TYPE_CODE.getName() + sectionId + index, regionRecord.getTaxTypeCode());
                setField(FieldEnum.REGION_CODE.getName() + sectionId + index, regionRecord.getRegionCode());

                setSectionRecordAmounts(sectionId, index, regionRecord);

                index++;
            }

            setSectionTotals(sectionId, regionRecordsList);
        }
    }

    private void setSectionTotals(String sectionId, List<? extends Record> recordList) throws NumberFormatException, ResourceException {

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

    private void setMultiField(String fieldName, Double sourceRecord) throws ResourceException {
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
            int localTaxRecordCount = this.form.getLocalTaxSection().getLocalTaxRecordList().size();
            int regionRecordsCount = this.form.getRegionSection().getRegionRecordList().size();
            int treasutyRecordsCount = this.form.getTreasurySection().getTaxList().size();
            int inailRecordsCount = this.form.getSocialSecuritySection().getInailRecords().size();
            int socSecurityRecordsCount = this.form.getSocialSecuritySection().getSocialSecurityRecordList().size();

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

            if (localTaxRecordCount > UNIV_RECORDS_NUMBER) {
                int pagesCount = ((localTaxRecordCount + UNIV_RECORDS_NUMBER - 1) / UNIV_RECORDS_NUMBER) - 1;
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
                setTaxPayer();
                setTreasurySection("1", copyIndex);
                setInpsSection("2", copyIndex);
                setRegionSection("3", copyIndex);
                setLocalTaxSection("4", copyIndex);
                setInail("5", copyIndex);
                setSocialSecurity("6", copyIndex);
                // setPaymentDetails();
                setField(FieldEnum.IBAN_CODE.getName(), this.form.getIbanCode());
                setField(FieldEnum.TOTAL_AMOUNT.getName(), helper.getMoney(totalBalance));
            }

            mergeCopies();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            getDoc().save(byteArrayOutputStream);
            logger.info("standard pdf created");

            return byteArrayOutputStream.toByteArray();
        } catch (ResourceException | IOException e) {
            logger.info(e.getMessage());
            return ByteArrayBuilder.NO_BYTES;
        }
    }

}
