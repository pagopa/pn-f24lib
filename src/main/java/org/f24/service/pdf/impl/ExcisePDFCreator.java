package org.f24.service.pdf.impl;

import org.f24.dto.component.*;
import org.f24.dto.form.F24Excise;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.PDFCreator;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import org.f24.service.pdf.util.CreatorHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static org.f24.service.pdf.util.FieldEnum.*;

public class ExcisePDFCreator extends FormPDFCreator implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24Accise2013.pdf";
    private static final int TAX_RECORDS_NUMBER = 6;
    private static final int UNIV_RECORDS_NUMBER = 4;
    private static final int EXCISE_TAX_RECORDS_NUMBER = 7;

    private CreatorHelper helper = new CreatorHelper();
    private Logger logger = Logger.getLogger(StandardPDFCreator.class.getName());
    private F24Excise form;
    private int totalBalance = 0;


    /**
     * Constructs Excise PDF Creator.
     *
     * @param form F24Excise component (DTO for Excise Form).
     */
    public ExcisePDFCreator(F24Excise form) {
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

    private void setTreasurySection(String sectionId, int copyIndex) throws ResourceException {
        TreasurySection treasurySection = this.form.getTreasurySection();
        List<Tax> taxList = treasurySection.getTaxList();

        if (taxList != null) {
            taxList = helper.paginateList(copyIndex, TAX_RECORDS_NUMBER, taxList);

            int index = 1;
            for (Tax taxRecord : taxList) {
                setField(TAX_TYPE_CODE.getName() + sectionId + index, taxRecord.getTaxTypeCode());
                setField(INSTALLMENT.getName() + sectionId + index, taxRecord.getInstallment());
                setField(YEAR.getName() + sectionId + index, taxRecord.getYear());

                setSectionRecordAmount(sectionId, index, taxRecord);

                index++;
            }

            setField(OFFICE_CODE.getName() + sectionId, treasurySection.getOfficeCode());
            setField(DOCUMENT_CODE.getName() + sectionId, treasurySection.getDocumentCode());

            totalBalance += setSectionTotal(sectionId, taxList, totalBalance);
        }
    }

    private void setInpsSection(String sectionId, int copyIndex) throws ResourceException {
        InpsSection inpsSection = this.form.getInpsSection();
        List<InpsRecord> inpsRecordList = inpsSection.getInpsRecordList();

        if (inpsRecordList != null) {
            inpsRecordList = helper.paginateList(copyIndex, UNIV_RECORDS_NUMBER, inpsRecordList);

            int index = 1;
            for (InpsRecord inpsRecord : inpsRecordList) {
                setField(OFFICE_CODE.getName() + sectionId + index, inpsRecord.getOfficeCode());
                setField(CONTRIBUTION_REASON.getName() + sectionId + index, inpsRecord.getContributionReason());
                setField(INPS_CODE.getName() + sectionId + index, inpsRecord.getInpsCode());

                setMultiDate(START_DATE.getName(), sectionId, index, inpsRecord.getPeriod().getStartDate());
                setMultiDate(END_DATE.getName(), sectionId, index, inpsRecord.getPeriod().getEndDate());

                setSectionRecordAmount(sectionId, index, inpsRecord);

                index++;
            }
            totalBalance += setSectionTotal(sectionId, inpsRecordList, totalBalance);
        }
    }

    private void setRegionSection(String sectionId, int copyIndex) throws ResourceException {
        RegionSection regionSection = this.form.getRegionSection();
        List<RegionRecord> regionRecordsList = regionSection.getRegionRecordList();

        if (regionRecordsList != null) {
            regionRecordsList = helper.paginateList(copyIndex, UNIV_RECORDS_NUMBER, regionRecordsList);

            int index = 1;
            for (RegionRecord regionRecord : regionRecordsList) {
                setField(YEAR.getName() + sectionId + index, regionRecord.getYear());
                setField(INSTALLMENT.getName() + sectionId + index, regionRecord.getInstallment());
                setField(TAX_TYPE_CODE.getName() + sectionId + index, regionRecord.getTaxTypeCode());
                setField(REGION_CODE.getName() + sectionId + index, regionRecord.getRegionCode());

                setSectionRecordAmount(sectionId, index, regionRecord);

                index++;
            }

            totalBalance += setSectionTotal(sectionId, regionRecordsList, totalBalance);
        }
    }

    private void setLocalTaxSection(String sectionId, int copyIndex) throws ResourceException {
        LocalTaxSection localTaxSection = this.form.getLocalTaxSection();
        List<LocalTaxRecord> localTaxRecordList = localTaxSection.getLocalTaxRecordList();

        if (!localTaxRecordList.isEmpty()) {
            localTaxRecordList = helper.paginateList(copyIndex, UNIV_RECORDS_NUMBER, localTaxRecordList);

            int index = 1;
            for (LocalTaxRecord localTaxRecord : localTaxRecordList) {
                setField(YEAR.getName() + sectionId + index, localTaxRecord.getYear());
                setField(INSTALLMENT.getName() + sectionId + index, localTaxRecord.getInstallment());
                setField(TAX_TYPE_CODE.getName() + sectionId + index, localTaxRecord.getTaxTypeCode());
                setField(MUNICIPALITY_CODE.getName() + sectionId + index, localTaxRecord.getMunicipalityCode());

                if (localTaxRecord.getReconsideration() != null && localTaxRecord.getReconsideration()) {
                    setField(RECONSIDERATION.getName() + index, "X");
                }
                if (localTaxRecord.getPropertiesChanges() != null && localTaxRecord.getPropertiesChanges()) {
                    setField(PROPERTIES_CHANGED.getName() + index, "X");
                }
                if (localTaxRecord.getAdvancePayment() != null && localTaxRecord.getAdvancePayment()) {
                    setField(ADVANCE_PAYMENT.getName() + index, "X");
                }
                if (localTaxRecord.getFullPayment() != null && localTaxRecord.getFullPayment()) {
                    setField(FULL_PAYMENT.getName() + index, "X");
                }
                if (localTaxRecord.getNumberOfProperties() != null) {
                    setField(NUMBER_OF_PROPERTIES.getName() + index, localTaxRecord.getNumberOfProperties());
                }

                setSectionRecordAmount(sectionId, index, localTaxRecord);

                index++;
            }
            if (!localTaxSection.getOperationId().isEmpty()) {
                setField(OPERATION_ID.getName(), localTaxSection.getOperationId());
            }

            totalBalance += setSectionTotal(sectionId, localTaxRecordList, totalBalance);

            Double parsedDeduction = Double.parseDouble(localTaxSection.getDeduction());
            setMultiField(DEDUCTION.getName(), parsedDeduction);
        }
    }

    private void setExciseSection(String sectionId, int copyIndex) throws ResourceException {
        ExciseSection exciseSection = this.form.getExciseSection();
        List<ExciseTax> exciseTaxList = exciseSection.getExciseTaxList();

        if (exciseTaxList != null) {
            exciseTaxList = helper.paginateList(copyIndex, EXCISE_TAX_RECORDS_NUMBER, exciseTaxList);

            int index = 1;
            for (ExciseTax taxRecord : exciseTaxList) {
                setField(MUNICIPALITY.getName() + sectionId + index, taxRecord.getMunicipality());
                setField(EXCISE_PROVINCE.getName() + sectionId + index, taxRecord.getProvince());
                setField(TAX_TYPE_CODE.getName() + sectionId + index, taxRecord.getTaxTypeCode());
                setField(ID_CODE.getName() + sectionId + index, taxRecord.getIdCode());
                setField(INSTALLMENT.getName() + sectionId + index, taxRecord.getInstallment());
                setField(MONTH.getName() + sectionId + index, taxRecord.getMonth());

                setField(YEAR.getName() + sectionId + index, taxRecord.getYear());

                setSectionRecordAmount(sectionId, index, taxRecord);

                index++;
            }

            setField(OFFICE_CODE.getName() + sectionId, exciseSection.getOfficeCode());
            setField(DOCUMENT_CODE.getName() + sectionId, exciseSection.getDocumentCode());

            totalBalance += setSectionTotal(sectionId, exciseTaxList, totalBalance);
        }
    }

    /**
     * Method which creates PDF Document for F24 Excise Form.
     *
     * @return PDFDocument object with filled fields.
     */
    @Override
    public byte[] createPDF() {
        try {
            loadDoc(MODEL_NAME);
            int totalPages = 0;


            int treasutyRecordsCount = this.form.getTreasurySection().getTaxList().size();
            int inpsRecordsCount = this.form.getInpsSection().getInpsRecordList().size();
            int regionRecordsCount = this.form.getRegionSection().getRegionRecordList().size();
            int localTaxRecordCount = this.form.getLocalTaxSection().getLocalTaxRecordList().size();

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
                setExciseSection("5", copyIndex);
                // setPaymentDetails();
                //setField(IBAN_CODE.getName(), this.form.getIbanCode());
                setField(TOTAL_AMOUNT.getName(), helper.getMoney(totalBalance));
                totalBalance = 0;
            }

            mergeCopies();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            getDoc().save(byteArrayOutputStream);
            logger.info("excise pdf created");

            return byteArrayOutputStream.toByteArray();
        } catch (ResourceException | IOException e) {
            logger.info(e.getMessage());
            return ByteArrayBuilder.NO_BYTES;
        }
    }

}
