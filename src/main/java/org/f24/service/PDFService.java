package org.f24.service;

import org.f24.dto.form.F24Form;
import org.f24.service.pdf.PDFCreator;
import org.f24.service.pdf.PDFCreatorFactory;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class PDFService {

    /**
     * This method takes F24Form DTO and creates PDF document for it.
     *
     * @param form F24Form component (DTO for F24 Forms).
     * @return generated PDF Document.
     */
    public byte[] generatePDF(F24Form form) {
        PDFCreator pdfCreator = PDFCreatorFactory.createPDFCreator(form);
        return pdfCreator.createPDF();
    }

}
