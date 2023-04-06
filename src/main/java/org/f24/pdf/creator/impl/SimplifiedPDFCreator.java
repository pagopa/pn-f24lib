package org.f24.pdf.creator.impl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.f24.dto.form.F24Simplified;
import org.f24.pdf.creator.PDFCreator;

import java.io.IOException;

public class SimplifiedPDFCreator implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24SemplificatoForm.pdf";

    private F24Simplified form;

    /**
     * Constructs Simplified PDF Creator.
     *
     * @param form F24Simplifiedcomponent (DTO for Simplified Form).
     */
    public SimplifiedPDFCreator(F24Simplified form) {
        this.form = form;
    }

    private PDField getField(String name, PDAcroForm form) {
        PDField field = form.getField(name);
        field.setReadOnly(true);
        return field;
    }

    private void setHeader(PDAcroForm form) throws IOException {
        getField("attorney", form).setValue(this.form.getHeader().getAttorneyTo());
        getField("agency", form).setValue(this.form.getHeader().getAgency());
        getField("province", form).setValue(this.form.getHeader().getProvince());
    }

    private void setFiscalCode(PDAcroForm form) throws IOException {
        String[] splittedFiscalCode = this.form.getContributor().getTaxCode().split("");
        for(int index = 0; index < splittedFiscalCode.length; index++) {
            getField("fc"+index, form).setValue(splittedFiscalCode[index]);
        }
    }

    /**
     * Method which creates PDF Document for F24 Simplified Form.
     *
     * @return PDFDocument object with filled fields.
     */
    @Override
    public PDDocument createPDF() {
        try {
            PDDocument doc = PDDocument.load(getClass().getClassLoader().getResourceAsStream(MODEL_NAME));
            PDAcroForm form = getFormFromPDF(doc);

            setHeader(form);
            setFiscalCode(form);

            // TODO remove
            doc.save("C:\\Users\\GianlucaScatena\\Desktop\\PROGETTI\\F24\\output.pdf");

            return doc;
        } catch (Exception e) {
            // TODO
            return null;
        }
    }

}
