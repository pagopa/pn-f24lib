package org.f24.pdf.creator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

import java.io.IOException;

public interface PDFCreator {

    String MODEL_FOLDER_NAME = "templates";

    default PDAcroForm getFormFromPDF(PDDocument doc) throws Exception {
        PDDocumentCatalog documentCatalog = doc.getDocumentCatalog();
        PDAcroForm form = documentCatalog.getAcroForm();
        if(form == null) throw new Exception(); // TODO
        return form;
    }

    /**
     * Method which creates PDF Document for certain dto with no parameters in
     *
     * @return PDFDocument object which represents generated PDF
     */
    PDDocument createPDF();

}
