package org.f24.pdf;

import org.f24.dto.form.*;
import org.f24.pdf.creator.impl.ExcisePDFCreator;
import org.f24.pdf.creator.PDFCreator;
import org.f24.pdf.creator.impl.ElidPDFCreator;
import org.f24.pdf.creator.impl.ImuPDFCreator;
import org.f24.pdf.creator.impl.SimplifiedPDFCreator;

public class PDFCreatorFactory {

    /**
     * This method takes F24Form DTO and creates object of definite PDFCreator for it.
     *
     * @param form F24Form component (DTO for F24 Forms).
     * @return PDFCreator object that can generate PDF for F24Form.
     */
    public static PDFCreator createPDFCreator(F24Form form) {
        if (form instanceof F24Excise)
            return new ExcisePDFCreator((F24Excise) form);
        else if (form instanceof F24Elid)
            return new ElidPDFCreator((F24Elid) form);
        else if (form instanceof F24Imu)
            return new ImuPDFCreator((F24Imu) form);
        else if (form instanceof F24Simplified)
            return new SimplifiedPDFCreator((F24Simplified) form);
        //ToDo throw exception
        else return null;
    }

}
