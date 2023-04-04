package org.f24.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.f24.dto.form.F24Form;
import org.f24.pdf.creator.PDFCreator;

public class F24PDFGenerator {

    /**
     * This method takes F24Form DTO and creates PDF document for it.
     *
     * @param form F24Form component (DTO for F24 Forms).
     * @return generated PDF Document.
     */
    public PDDocument generatePDF(F24Form form) {
        PDFCreator pdfCreator = PDFCreatorFactory.createPDFCreator(form);
        return pdfCreator.createPDF();
    }

}
