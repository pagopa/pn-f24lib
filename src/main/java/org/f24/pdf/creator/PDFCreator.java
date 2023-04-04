package org.f24.pdf.creator;

import org.apache.pdfbox.pdmodel.PDDocument;

public interface PDFCreator {

    /**
     * Method which creates PDF Document for certain dto with no parameters in
     *
     * @return PDFDocument object which represents generated PDF
     */
    PDDocument createPDF();

}
