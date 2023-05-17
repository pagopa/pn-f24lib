package org.f24.service.pdf.impl;

import org.f24.dto.component.*;
import org.f24.dto.form.F24Excise;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.PDFCreator;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static org.f24.service.pdf.util.FieldEnum.*;

public class ExcisePDFCreator extends FormPDFCreator implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24Accise2013.pdf";

    private F24Excise form;

    private Logger logger = Logger.getLogger(SimplifiedPDFCreator.class.getName());

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

    @Override
    protected void setTreasurySection(String sectionId, int copyIndex) throws ResourceException {
        TreasurySection treasurySection = this.form.getTreasurySection();

        if (!treasurySection.getTaxList().isEmpty()) {
            List<Tax> taxList = paginateList(copyIndex, TAX_RECORDS_NUMBER.getRecordsNum(), treasurySection.getTaxList());

            if (!taxList.isEmpty()) {
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

    private void setExciseSection(String sectionId, int copyIndex) throws ResourceException {
        ExciseSection exciseSection = this.form.getExciseSection();
        if (!exciseSection.getExciseTaxList().isEmpty()) {
            List<ExciseTax> exciseTaxList = paginateList(copyIndex, EXCISE_TAX_RECORDS_NUMBER.getRecordsNum(),
                    exciseSection.getExciseTaxList());

            if (!exciseTaxList.isEmpty()) {
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

            totalPages = getTotalPages(treasutyRecordsCount, TAX_RECORDS_NUMBER.getRecordsNum(), totalPages);
            totalPages = getTotalPages(inpsRecordsCount, UNIV_RECORDS_NUMBER.getRecordsNum(), totalPages);
            totalPages = getTotalPages(regionRecordsCount, UNIV_RECORDS_NUMBER.getRecordsNum(), totalPages);
            totalPages = getTotalPages(localTaxRecordCount, UNIV_RECORDS_NUMBER.getRecordsNum(), totalPages);
            totalPages = getTotalPages(localTaxRecordCount, EXCISE_TAX_RECORDS_NUMBER.getRecordsNum(), totalPages);

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
                setField(TOTAL_AMOUNT.getName(), getMoney(totalBalance));
                totalBalance = 0;
            }

            mergeCopies();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            getDoc().save(byteArrayOutputStream);

            logger.info("excise pdf is created");
            return byteArrayOutputStream.toByteArray();
        } catch (ResourceException | IOException e) {
            return ByteArrayBuilder.NO_BYTES;
        }
    }

}
