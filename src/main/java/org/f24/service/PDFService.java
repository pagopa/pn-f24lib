package org.f24.service;

import org.f24.dto.form.F24Form;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.PDFCreator;
import org.f24.service.pdf.PDFCreatorFactory;

public class PDFService {

    /**
     * This method takes F24Form DTO and creates PDF document for it.
     *
     * @param form F24Form component (DTO for F24 Forms).
     * @return generated PDF Document.
     * @throws ResourceException
     */
    public byte[] generatePDF(F24Form form) throws ResourceException {
        PDFCreator pdfCreator = PDFCreatorFactory.createPDFCreator(form);
        return pdfCreator.createPDF();
    }

}
