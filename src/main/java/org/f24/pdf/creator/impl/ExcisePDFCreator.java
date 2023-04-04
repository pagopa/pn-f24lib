package org.f24.pdf.creator.impl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.f24.dto.form.F24Excise;
import org.f24.pdf.creator.PDFCreator;

public class ExcisePDFCreator implements PDFCreator {

    private F24Excise form;

    /**
     * Constructs Excise PDF Creator.
     *
     * @param form F24Excise component (DTO for Excise Form).
     */
    public ExcisePDFCreator(F24Excise form) {
        this.form = form;
    }

    /**
     * Method which creates PDF Document for F24 Excise Form.
     *
     * @return PDFDocument object with filled fields.
     */
    @Override
    public PDDocument createPDF() {
        return null;
    }

}
