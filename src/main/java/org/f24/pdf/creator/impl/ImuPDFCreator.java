package org.f24.pdf.creator.impl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.f24.dto.form.F24Imu;
import org.f24.pdf.creator.PDFCreator;

public class ImuPDFCreator implements PDFCreator {

    private F24Imu form;

    /**
     * Constructs IMU PDF Creator.
     *
     * @param form F24Imu component (DTO for IMU Form).
     */
    public ImuPDFCreator(F24Imu form) {
        this.form = form;
    }

    /**
     * Method which creates PDF Document for F24 IMU Form.
     *
     * @return PDFDocument object with filled fields.
     */
    @Override
    public PDDocument createPDF() {
        return null;
    }

}
