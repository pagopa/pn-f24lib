package org.f24.pdf.creator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.IOException;

public interface PDFCreator {

    String MODEL_FOLDER_NAME = "templates";

    /**
     * Method which creates PDF Document for certain dto with no parameters in
     *
     * @return PDFDocument object which represents generated PDF
     */
    byte[] createPDF();

}
