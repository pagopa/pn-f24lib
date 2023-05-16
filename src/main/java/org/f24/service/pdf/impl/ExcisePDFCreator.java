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

import static org.f24.service.pdf.util.FieldEnum.*;

public class ExcisePDFCreator extends FormPDFCreator implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24Accise2013.pdf";
    private static final int TAX_RECORDS_NUMBER = 6;
    private static final int UNIV_RECORDS_NUMBER = 4;
    private static final int EXCISE_TAX_RECORDS_NUMBER = 7;

    private CreatorHelper helper = new CreatorHelper();
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

        if (!treasurySection.getTaxList().isEmpty()) {
            List<Tax> taxList = helper.paginateList(copyIndex, TAX_RECORDS_NUMBER, treasurySection.getTaxList());

            if (taxList.size() != 0) {
                for (int index = 1; index <= taxList.size(); index++) {
                    Tax taxRecord = taxList.get(index - 1);
                    setField(TAX_TYPE_CODE.getName() + sectionId + index, taxRecord.getTaxTypeCode());
                    setField(INSTALLMENT.getName() + sectionId + index, taxRecord.getInstallment());
                    setField(YEAR.getName() + sectionId + index, taxRecord.getYear());
                    setSectionRecordAmount(sectionId, index, taxRecord);
                }
                setField(OFFICE_CODE.getName() + sectionId, treasurySection.getOfficeCode());
                setField(DOCUMENT_CODE.getName() + sectionId, treasurySection.getDocumentCode());

                totalBalance += setSectionTotal(sectionId, taxList, totalBalance);
            }
        }
    }

    private void setInpsSection(String sectionId, int copyIndex) throws ResourceException {
        InpsSection inpsSection = this.form.getInpsSection();

        if (!inpsSection.getInpsRecordList().isEmpty()) {
            List<InpsRecord> inpsRecordList = helper.paginateList(copyIndex, UNIV_RECORDS_NUMBER, inpsSection.getInpsRecordList());

            if (inpsRecordList.size() != 0) {
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

    private void setRegionSection(String sectionId, int copyIndex) throws ResourceException {
        RegionSection regionSection = this.form.getRegionSection();

        if (!regionSection.getRegionRecordList().isEmpty()) {
            List<RegionRecord> regionRecordsList = helper.paginateList(copyIndex, UNIV_RECORDS_NUMBER, regionSection.getRegionRecordList());

            if (regionRecordsList.size() != 0) {
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

    private void setLocalTaxSection(String sectionId, int copyIndex) throws ResourceException {
        LocalTaxSection localTaxSection = this.form.getLocalTaxSection();

        if (!localTaxSection.getLocalTaxRecordList().isEmpty()) {
            List<LocalTaxRecord> localTaxRecordList = helper.paginateList(copyIndex, UNIV_RECORDS_NUMBER, localTaxSection.getLocalTaxRecordList());

            if (!localTaxRecordList.isEmpty()) {
                for (int index = 1; index <= localTaxRecordList.size(); index++) {
                    LocalTaxRecord taxRecord = localTaxRecordList.get(index - 1);
                    setField(YEAR.getName() + sectionId + index, taxRecord.getYear());
                    setField(INSTALLMENT.getName() + sectionId + index, taxRecord.getInstallment());
                    setField(TAX_TYPE_CODE.getName() + sectionId + index, taxRecord.getTaxTypeCode());
                    setField(MUNICIPALITY_CODE.getName() + sectionId + index, taxRecord.getMunicipalityCode());

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

    private void setExciseSection(String sectionId, int copyIndex) throws ResourceException {
        ExciseSection exciseSection = this.form.getExciseSection();
        if (!exciseSection.getExciseTaxList().isEmpty()) {
            List<ExciseTax> exciseTaxList = helper.paginateList(copyIndex, EXCISE_TAX_RECORDS_NUMBER, exciseSection.getExciseTaxList());

            if (exciseTaxList.size() != 0) {
                for (int index = 1; index <= exciseTaxList.size(); index++) {
                    ExciseTax exciseTax = exciseTaxList.get(index - 1);
                    setField(MUNICIPALITY.getName() + sectionId + index, exciseTax.getMunicipality());
                    setField(EXCISE_PROVINCE.getName() + sectionId + index, exciseTax.getProvince());
                    setField(TAX_TYPE_CODE.getName() + sectionId + index, exciseTax.getTaxTypeCode());
                    setField(ID_CODE.getName() + sectionId + index, exciseTax.getIdCode());
                    setField(INSTALLMENT.getName() + sectionId + index, exciseTax.getInstallment());
                    setField(MONTH.getName() + sectionId + index, exciseTax.getMonth());
                    setField(YEAR.getName() + sectionId + index, exciseTax.getYear());

                    setSectionRecordAmount(sectionId, index, exciseTax);
                }
                setField(OFFICE_CODE.getName() + sectionId, exciseSection.getOfficeCode());
                setField(DOCUMENT_CODE.getName() + sectionId, exciseSection.getDocumentCode());

                totalBalance += setSectionTotal(sectionId, exciseTaxList, totalBalance);
            }
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

            totalPages = getTotalPages(treasutyRecordsCount,TAX_RECORDS_NUMBER, totalPages);
            totalPages = getTotalPages(inpsRecordsCount, UNIV_RECORDS_NUMBER, totalPages);
            totalPages = getTotalPages(regionRecordsCount, UNIV_RECORDS_NUMBER,totalPages);
            totalPages = getTotalPages(localTaxRecordCount, UNIV_RECORDS_NUMBER,totalPages);
            totalPages = getTotalPages(localTaxRecordCount, EXCISE_TAX_RECORDS_NUMBER,totalPages);

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
                //setPaymentDetails();
                //setField(IBAN_CODE.getName(), this.form.getIbanCode());
                setField(TOTAL_AMOUNT.getName(), helper.getMoney(totalBalance));
                totalBalance = 0;
            }

            mergeCopies();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            getDoc().save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

        } catch (ResourceException | IOException e) {
            return ByteArrayBuilder.NO_BYTES;
        }
    }

}
