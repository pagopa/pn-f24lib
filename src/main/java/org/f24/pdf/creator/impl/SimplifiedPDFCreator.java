package org.f24.pdf.creator.impl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.f24.dto.form.F24Simplified;
import org.f24.pdf.creator.PDFCreator;

public class SimplifiedPDFCreator implements PDFCreator {

    private F24Simplified form;

    /**
     * Constructs Simplified PDF Creator.
     *
     * @param form F24Simplifiedcomponent (DTO for Simplified Form).
     */
    public SimplifiedPDFCreator(F24Simplified form) {
        this.form = form;
    }

    /**
     * Method which creates PDF Document for F24 Simplified Form.
     *
     * @return PDFDocument object with filled fields.
     */
    @Override
    public PDDocument createPDF() {
        return null;
    }

}
