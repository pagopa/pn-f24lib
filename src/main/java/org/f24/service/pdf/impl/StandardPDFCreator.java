package org.f24.service.pdf.impl;

import org.f24.dto.form.F24Standard;
import org.f24.service.pdf.PDFCreator;

public class StandardPDFCreator implements PDFCreator {

    private F24Standard form;

    /**
     * Constructs Standard PDF Creator.
     *
     * @param form F24Standard component (DTO for Standard Form).
     */
    public StandardPDFCreator(F24Standard form) {
        this.form = form;
    }

    /**
     * Method which creates PDF Document for F24 Standard Form.
     *
     * @return PDFDocument object with filled fields.
     */
    @Override
    public byte[]  createPDF() {
        return null;
    }

}
