package org.f24.pdf.creator.impl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.f24.dto.form.F24Elid;
import org.f24.pdf.creator.PDFCreator;

public class ElidPDFCreator implements PDFCreator {

    private F24Elid form;

    /**
     * Constructs ELID PDF Creator.
     *
     * @param form F24Elid component (DTO for ELID Form).
     */
    public ElidPDFCreator(F24Elid form) {
        this.form = form;
    }

    /**
     * Method which creates PDF Document for F24 ELID Form.
     *
     * @return PDFDocument object with filled fields.
     */
    @Override
    public PDDocument createPDF() {
        return null;
    }

}
