package org.f24.pdf.creator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.IOException;

public class PDFFormManager {

    private PDDocument doc;
    private PDAcroForm pdfForm;

    private PDAcroForm getForm() throws Exception {
        PDDocumentCatalog documentCatalog = doc.getDocumentCatalog();
        PDAcroForm form = documentCatalog.getAcroForm();
        if(form == null) throw new Exception(); // TODO
        return form;
    }

    protected void loadDoc(String modelName) throws Exception {
        this.doc = PDDocument.load(getClass().getClassLoader().getResourceAsStream(modelName));
        this.pdfForm = getForm();
    }

    protected PDDocument getDoc() {
        return this.doc;
    }

    protected PDField getField(String name) {
        PDField field = pdfForm.getField(name);
        field.setReadOnly(true);
        return field;
    }

    protected void setField(String fieldName, String fieldValue) throws IOException {
        getField(fieldName).setValue(fieldValue);
    }

}
