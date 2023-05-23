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

    private Logger logger = Logger.getLogger(ExcisePDFCreator.class.getName());

    /**
     * Constructs Excise PDF Creator.
     *
     * @param form F24Excise component (DTO for Excise Form).
     */
    public ExcisePDFCreator(F24Excise form) {
        super(form);
        this.form = form;
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

                totalBalance += setSectionTotal(sectionId, exciseTaxList);
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
