package org.f24;

import org.f24.dto.component.Contributor;
import org.f24.dto.component.Header;
import org.f24.dto.form.F24Simplified;
import org.f24.pdf.F24PDFGenerator;

public class F24Generator {

    public static void main(String[] args) {
        F24PDFGenerator f24PDFGenerator = new F24PDFGenerator();

        Header header = new Header("MARIO ROSSI", "TEST", "AQ");
        Contributor contributor = new Contributor("RSSMRA80A01A515B", false, null, null, null, null);

        F24Simplified form = new F24Simplified(header, contributor, null, null, null);
        f24PDFGenerator.generatePDF(form);
    }

}
