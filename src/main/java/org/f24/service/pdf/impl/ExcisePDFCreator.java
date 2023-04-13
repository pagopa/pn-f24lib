package org.f24.service.pdf.impl;

import org.f24.dto.form.F24Excise;
import org.f24.service.pdf.PDFCreator;

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
    public byte[]  createPDF() {
        return null;
    }

}
