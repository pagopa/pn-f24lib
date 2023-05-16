package org.f24.service.pdf.impl;

import org.f24.dto.component.*;
import org.f24.dto.form.F24Elid;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.PDFCreator;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import org.f24.service.pdf.util.CreatorHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.f24.service.pdf.util.FieldEnum.*;

public class ElidPDFCreator extends FormPDFCreator implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24ELID.pdf";
    private static final int TREASURY_RECORDS_NUMBER = 28;

    private F24Elid form;
    private CreatorHelper helper = new CreatorHelper();
    private int totalBalance = 0;

    /**
     * Constructs ELID PDF Creator.
     *
     * @param form F24Elid component (DTO for ELID Form).
     */
    public ElidPDFCreator(F24Elid form) {
        super(form);
        this.form = form;
    }

    private void setTaxPayer() throws ResourceException {
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
        TreasuryAndOtherSection treasurySection = this.form.getTreasurySection();
        List<TreasuryRecord> treasuryTaxList = treasurySection.getTreasuryRecords();

        if (treasuryTaxList != null) {
            treasuryTaxList = helper.paginateList(copyIndex, TREASURY_RECORDS_NUMBER, treasuryTaxList);

            for (int index = 1; index <= treasuryTaxList.size(); index++) {
                TreasuryRecord treasuryRecord = treasuryTaxList.get(index - 1);
                setField(TYPE.getName() + index, treasuryRecord.getType());
                setField(ID_ELEMENT.getName() + index, treasuryRecord.getIdElements());
                setField(TAX_TYPE_CODE.getName() + index, treasuryRecord.getTaxTypeCode());
                setField(YEAR.getName() + index, treasuryRecord.getYear());

                setSectionRecordAmount("",index,treasuryRecord);
            }

            setField(OFFICE_CODE.getName(), treasurySection.getOfficeCode());
            setField(DOCUMENT_CODE.getName(), treasurySection.getDocumentCode());

            totalBalance += helper.getTotalAmount(treasuryTaxList);
        }
    }


    /**
     * Method which creates PDF Document for F24 ELID Form.
     *
     * @return PDFDocument object with filled fields.
     */
    @Override
    public byte[] createPDF() {
        try {
            loadDoc(MODEL_NAME);
            int totalPages = 0;

            int treasutyRecordsCount = this.form.getTreasurySection().getTreasuryRecords().size();

            if (treasutyRecordsCount > TREASURY_RECORDS_NUMBER) {
                int pagesCount = ((treasutyRecordsCount + TREASURY_RECORDS_NUMBER - 1) / TREASURY_RECORDS_NUMBER) - 1;
                totalPages = Math.max(totalPages, pagesCount);
            }

            copy(totalPages);

            int copiesCount = getCopies().size();

            for (int copyIndex = 0; copyIndex < copiesCount; copyIndex++) {
                setIndex(copyIndex);
                setHeader();
                setTaxPayer();
                setTreasurySection(copyIndex);
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
