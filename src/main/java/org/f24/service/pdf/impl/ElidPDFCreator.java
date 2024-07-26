package org.f24.service.pdf.impl;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import org.f24.dto.component.TaxPayer;
import org.f24.dto.component.TreasuryAndOtherSection;
import org.f24.dto.component.TreasuryRecord;
import org.f24.dto.form.F24Elid;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.PDFCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.f24.service.pdf.util.FieldEnum.*;

public class ElidPDFCreator extends FormPDFCreator implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24ELID.pdf";

    private final F24Elid form;
    private final Logger logger = LoggerFactory.getLogger(ElidPDFCreator.class.getName());

    /**
     * Constructs ELID PDF Creator.
     *
     * @param form F24Elid component (DTO for ELID Form).
     */
    public ElidPDFCreator(F24Elid form) {
        super(form);
        this.form = form;
    }

    @Override
    protected void setTaxPayer() throws ResourceException {
        TaxPayer taxPayer = this.form.getTaxPayer();

        if (taxPayer != null) {
            setField(TAX_CODE.getName(), taxPayer.getTaxCode());
            setField(RELATIVE_PERSON_TAX_CODE.getName(), taxPayer.getRelativePersonTaxCode());
            setField(ID_CODE.getName(), taxPayer.getIdCode());

            setRegistryData();
            setTaxResidenceData();
        }
    }

    private void setTreasurySection(int copyIndex) throws ResourceException {
        TreasuryAndOtherSection treasuryAndOtherSection = this.form.getTreasuryAndOtherSection();

        if (treasuryAndOtherSection != null) {
            List<TreasuryRecord> treasuryTaxList = treasuryAndOtherSection.getTreasuryRecords();

            if (treasuryTaxList != null) {
                treasuryTaxList = paginateList(copyIndex, TREASURY_RECORDS_NUMBER.getRecordsNum(), treasuryTaxList);

                for (int index = 1; index <= treasuryTaxList.size(); index++) {
                    TreasuryRecord treasuryRecord = treasuryTaxList.get(index - 1);
                    setField(TYPE.getName() + index, treasuryRecord.getType());
                    setField(ID_ELEMENT.getName() + index, treasuryRecord.getIdElements());
                    setField(TAX_TYPE_CODE.getName() + index, treasuryRecord.getTaxTypeCode());
                    setField(YEAR.getName() + index, treasuryRecord.getYear());

                    setSectionRecordAmount("", index, treasuryRecord);
                }

                setField(OFFICE_CODE.getName(), treasuryAndOtherSection.getOfficeCode());
                setField(DOCUMENT_CODE.getName(), treasuryAndOtherSection.getDocumentCode());

                totalBalance += getTotalAmount(treasuryTaxList);
            }
        }
    }

    @Override
    public int getPagesAmount() throws IOException {
        loadDoc(MODEL_NAME);
        int totalPages = 0;

        if(this.form.getTreasuryAndOtherSection() != null) {
            int treasuryRecordsCount = this.form.getTreasuryAndOtherSection().getTreasuryRecords().size();
            totalPages = getTotalPages(treasuryRecordsCount, TREASURY_RECORDS_NUMBER.getRecordsNum(), totalPages);
        }
        copy(totalPages);

        return getCopies().size();
    }

    /**
     * Method which creates PDF Document for F24 ELID Form.
     *
     * @return PDFDocument object with filled fields.
     */
    @Override
    public byte[] createPDF() {
        try {
            int copiesCount = getPagesAmount();

            for (int copyIndex = 0; copyIndex < copiesCount; copyIndex++) {
                setIndex(copyIndex);
                setHeader();
                setTaxPayer();
                setTreasurySection(copyIndex);
                setField(TOTAL_AMOUNT.getName(), getMoney(totalBalance));
                totalBalance = 0;
                flat();
                updateCopy();
            }

            byte[] mergeCopies = mergeCopies();

            finalizeDoc();

            logger.info("elid PDF is created");

            return mergeCopies;
        } catch (ResourceException | IOException e) {
            return ByteArrayBuilder.NO_BYTES;
        }
    }

}
