package org.f24.service.pdf;

import org.f24.dto.form.*;
import org.f24.exception.ErrorEnum;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.impl.ExcisePDFCreator;
import org.f24.service.pdf.impl.ElidPDFCreator;
import org.f24.service.pdf.impl.StandardPDFCreator;
import org.f24.service.pdf.impl.SimplifiedPDFCreator;

public class PDFCreatorFactory {

    private PDFCreatorFactory() {}

    /**
     * This method takes F24Form DTO and creates object of definite PDFCreator for it.
     *
     * @param form F24Form component (DTO for F24 Forms).
     * @return PDFCreator object that can generate PDF for F24Form.
     * @throws ResourceException
     */
    public static PDFCreator createPDFCreator(F24Form form) throws ResourceException {
        if (form instanceof F24Excise f24Excise)
            return new ExcisePDFCreator(f24Excise);
        else if (form instanceof F24Elid f24Elid)
            return new ElidPDFCreator(f24Elid);
        else if (form instanceof F24Standard f24Standard)
            return new StandardPDFCreator(f24Standard);
        else if (form instanceof F24Simplified f24simplified)
            return new SimplifiedPDFCreator(f24simplified);
       
        else throw new ResourceException(ErrorEnum.GENERATOR_OBSOLETE.getMessage());
    }

}
