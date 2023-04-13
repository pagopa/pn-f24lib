package org.f24.service.pdf;

public interface PDFCreator {

    /**
     * Method which creates PDF Document for certain dto with no parameters in
     *
     * @return PDFDocument object which represents generated PDF
     */
    byte[] createPDF();

}
