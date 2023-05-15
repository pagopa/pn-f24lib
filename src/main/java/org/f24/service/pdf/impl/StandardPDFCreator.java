package org.f24.service.pdf.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.f24.dto.component.*;
import org.f24.dto.component.Record;
import org.f24.dto.form.F24Standard;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.PDFCreator;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;

import static org.f24.service.pdf.util.FieldEnum.*;

public class StandardPDFCreator extends FormPDFCreator implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24IMU2013.pdf";
    private static final int TAX_RECORDS_NUMBER = 6;
    private static final int UNIV_RECORDS_NUMBER = 4;
    private static final int INAIL_RECORDS_NUMBER = 3;
    private static final int SOC_RECORDS_NUMBER = 2;

    private Logger logger = Logger.getLogger(StandardPDFCreator.class.getName());
    private F24Standard form;
    private int totalBalance = 0;

    /**
     * Constructs Standard PDF Creator.
     *
     * @param form F24Standard component (DTO for Standard Form).
     */
    public StandardPDFCreator(F24Standard form) {
        super(form);
        this.form = form;
    }

    private void setTaxPayer() throws ResourceException {
        TaxPayer taxPayer = this.form.getTaxPayer();

        if (taxPayer != null) {
            setField(TAX_CODE.getName(), taxPayer.getTaxCode());
            setField(RELATIVE_PERSON_TAX_CODE.getName(), taxPayer.getRelativePersonTaxCode());
            setField(ID_CODE.getName(), taxPayer.getIdCode());

            if (taxPayer.getIsNotTaxYear())
                setField(IS_NOT_TAX_YEAR.getName(), "X");

            setRegistryData();
            setTaxResidenceData();
        }
    }

    private void setInpsSection(String sectionId, int copyIndex) throws ResourceException {
        InpsSection inpsSection = this.form.getInpsSection();
        List<InpsRecord> inpsRecordList = inpsSection.getInpsRecordList();

        if (inpsRecordList != null) {
            inpsRecordList = paginateList(copyIndex, UNIV_RECORDS_NUMBER, inpsRecordList);

            int index = 1;
            for (InpsRecord inpsRecord : inpsRecordList) {
                setField(OFFICE_CODE.getName() + sectionId + index, inpsRecord.getOfficeCode());
                setField(CONTRIBUTION_REASON.getName() + sectionId + index, inpsRecord.getContributionReason());
                setField(INPS_CODE.getName() + sectionId + index, inpsRecord.getInpsCode());

                setMultiDate(START_DATE.getName(), sectionId, index, inpsRecord.getPeriod().getStartDate());
                setMultiDate(END_DATE.getName(), sectionId, index, inpsRecord.getPeriod().getEndDate());

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
            localTaxRecordList = paginateList(copyIndex, UNIV_RECORDS_NUMBER, localTaxRecordList);

            int index = 1;
            for (LocalTaxRecord localTaxRecord : localTaxRecordList) {
                setField(YEAR.getName() + sectionId + index, localTaxRecord.getYear());
                setField(INSTALLMENT.getName() + sectionId + index, localTaxRecord.getInstallment());
                setField(TAX_TYPE_CODE.getName() + sectionId + index, localTaxRecord.getTaxTypeCode());
                setField(MUNICIPALITY_CODE.getName() + sectionId + index, localTaxRecord.getMunicipalityCode());

                if (localTaxRecord.getReconsideration() != null) {
                    setField(RECONSIDERATION.getName() + index, "X");
                }
                if (localTaxRecord.getPropertiesChanges() != null) {
                    setField(PROPERTIES_CHANGED.getName() + index, "X");
                }
                if (localTaxRecord.getAdvancePayment() != null) {
                    setField(ADVANCE_PAYMENT.getName() + index, "X");
                }
                if (localTaxRecord.getFullPayment() != null) {
                    setField(FULL_PAYMENT.getName() + index, "X");
                }
                if (localTaxRecord.getNumberOfProperties() != null) {
                    setField(NUMBER_OF_PROPERTIES.getName() + index, localTaxRecord.getNumberOfProperties());
                }

                setSectionRecordAmounts(sectionId, index, localTaxRecord);

                index++;
            }

            setSectionTotals(sectionId, localTaxRecordList);

            Double parsedDeduction = Double.parseDouble(localTaxSection.getDeduction());
            setMultiField(DEDUCTION.getName(), parsedDeduction);
        }
    }

    private void setTreasurySection(String sectionId, int copyIndex) throws ResourceException {
        TreasurySection treasurySection = this.form.getTreasurySection();
        List<Tax> taxList = treasurySection.getTaxList();

        if (taxList != null) {
            taxList = paginateList(copyIndex, TAX_RECORDS_NUMBER, taxList);

            int index = 1;
            for (Tax taxRecord : taxList) {
                setField(TAX_TYPE_CODE.getName() + sectionId + index, taxRecord.getTaxTypeCode());
                setField(INSTALLMENT.getName() + sectionId + index, taxRecord.getInstallment());
                setField(YEAR.getName() + sectionId + index, taxRecord.getYear());

                setSectionRecordAmounts(sectionId, index, taxRecord);

                index++;
            }

            setField(OFFICE_CODE.getName(), treasurySection.getOfficeCode());
            setField(DOCUMENT_CODE.getName(), treasurySection.getDocumentCode());

            setSectionTotals(sectionId, taxList);
        }
    }

    private void setSocialSecurity(String sectionId, int copyIndex) throws ResourceException {
        SocialSecuritySection socSecurity = this.form.getSocialSecuritySection();
        List<SocialSecurityRecord> socSecurityList = socSecurity.getSocialSecurityRecordList();

        if (socSecurityList != null) {
            socSecurityList = paginateList(copyIndex, SOC_RECORDS_NUMBER, socSecurityList);

            int index = 1;
            for (SocialSecurityRecord socSecRecord : socSecurityList) {
                setField(INSTITUTION_CODE.getName() + sectionId, socSecRecord.getInstitutionCode());
                setField(OFFICE_CODE.getName() + sectionId + index, socSecRecord.getOfficeCode());
                setField(CONTRIBUTION_REASON.getName() + sectionId + index, socSecRecord.getContributionReason());
                setField(POSITION_CODE.getName() + sectionId + index, socSecRecord.getPositionCode());

                setMultiDate(START_DATE.getName(), sectionId, index, socSecRecord.getPeriod().getStartDate());
                setMultiDate(END_DATE.getName(), sectionId, index, socSecRecord.getPeriod().getEndDate());

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
            inailRecordList = paginateList(copyIndex, INAIL_RECORDS_NUMBER, inailRecordList);

            int index = 1;
            for (InailRecord inailRecord : inailRecordList) {
                setField(OFFICE_CODE.getName() + sectionId + index, inailRecord.getOfficeCode());
                setField(COMPANY_CODE.getName() + sectionId + index, inailRecord.getCompanyCode());
                setField(CONTROL_CODE.getName() + sectionId + index, inailRecord.getControlCode());
                setField(REFERENCE_NUMBER.getName() + sectionId + index, inailRecord.getReferenceNumber());
                setField(REASON.getName() + sectionId + index, inailRecord.getReason());

                setSectionRecordAmounts(sectionId, index, inailRecord);

                index++;
            }

            setSectionTotals(sectionId, inailRecordList);
        }
    }

    private void setRegionSection(String sectionId, int copyIndex) throws ResourceException {
        RegionSection regionSection = this.form.getRegionSection();
        List<RegionRecord> regionRecordsList = regionSection.getRegionRecordList();

        if (regionRecordsList != null) {
            regionRecordsList = paginateList(copyIndex, UNIV_RECORDS_NUMBER, regionRecordsList);

            int index = 1;
            for (RegionRecord regionRecord : regionRecordsList) {
                setField(YEAR.getName() + sectionId + index, regionRecord.getYear());
                setField(INSTALLMENT.getName() + sectionId + index, regionRecord.getInstallment());
                setField(TAX_TYPE_CODE.getName() + sectionId + index, regionRecord.getTaxTypeCode());
                setField(REGION_CODE.getName() + sectionId + index, regionRecord.getRegionCode());

                setSectionRecordAmounts(sectionId, index, regionRecord);

                index++;
            }

            setSectionTotals(sectionId, regionRecordsList);
        }
    }

    private void setSectionTotals(String sectionId, List<? extends Record> recordList) throws NumberFormatException, ResourceException {

        if (getTotalAmount(recordList) != null) {
            Integer total = getTotalAmount(recordList);
            totalBalance += total;
            String parsedTotal = getMoney(total);
            setField(TOTAL_AMOUNT.getName() + sectionId, parsedTotal);
        }

        if (getCreditTotal(recordList) != null) {
            Integer creditTotal = getCreditTotal(recordList);
            String parsedTotal = getMoney(creditTotal);
            setField(TOTAL_CREDIT.getName() + sectionId, parsedTotal);
        }

        if (getDebitTotal(recordList) != null) {
            Integer debitTotal = getDebitTotal(recordList);
            String parsedTotal = getMoney(debitTotal);
            setField(TOTAL_DEBIT.getName() + sectionId, parsedTotal);
        }
    }

    private void setSectionRecordAmounts(String sectionId, int index, Record sourceRecord)
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
                totalPages = Math.max(totalPages, pagesCount);
            }

            if (inpsRecordsCount > UNIV_RECORDS_NUMBER) {
                int pagesCount = ((inpsRecordsCount + UNIV_RECORDS_NUMBER - 1) / UNIV_RECORDS_NUMBER) - 1;
                totalPages = Math.max(totalPages, pagesCount);
            }

            if (regionRecordsCount > UNIV_RECORDS_NUMBER) {
                int pagesCount = ((regionRecordsCount + UNIV_RECORDS_NUMBER - 1) / UNIV_RECORDS_NUMBER) - 1;
                totalPages = Math.max(totalPages, pagesCount);
            }

            if (localTaxRecordCount > UNIV_RECORDS_NUMBER) {
                int pagesCount = ((localTaxRecordCount + UNIV_RECORDS_NUMBER - 1) / UNIV_RECORDS_NUMBER) - 1;
                totalPages = Math.max(totalPages, pagesCount);
            }

            if (inailRecordsCount > INAIL_RECORDS_NUMBER) {
                int pagesCount = ((inailRecordsCount + INAIL_RECORDS_NUMBER - 1) / INAIL_RECORDS_NUMBER) - 1;
                totalPages = Math.max(totalPages, pagesCount);
            }

            if (socSecurityRecordsCount > SOC_RECORDS_NUMBER) {
                int pagesCount = ((socSecurityRecordsCount + SOC_RECORDS_NUMBER - 1) / SOC_RECORDS_NUMBER) - 1;
                totalPages = Math.max(totalPages, pagesCount);
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
                setField(IBAN_CODE.getName(), this.form.getIbanCode());
                setField(TOTAL_AMOUNT.getName(), getMoney(totalBalance));
                totalBalance = 0;
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
