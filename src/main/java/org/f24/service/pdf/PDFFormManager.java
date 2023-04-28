package org.f24.service.pdf;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.f24.exception.ErrorEnum;
import org.f24.exception.ResourceException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFFormManager {

    private String modelName;

    private Integer currentIndex;

    private PDDocument doc;
    private List<PDDocument> copies;

    protected void loadDoc(String modelName) throws Exception {
        this.modelName = modelName;
        this.doc = PDDocument.load(getClass().getClassLoader().getResourceAsStream(modelName));
        this.copies = new ArrayList<>();
        this.copies.add(doc);
    }

    protected void setIndex(Integer currentIndex) {
        this.currentIndex = currentIndex;
    }

    protected PDDocument getDoc() {
        return this.doc;
    }

    protected PDDocument getCurrentCopy() {
        return this.copies.get(this.currentIndex);
    }

    protected PDAcroForm getForm() throws ResourceException {
        PDDocumentCatalog documentCatalog = getCurrentCopy().getDocumentCatalog();
        PDAcroForm form = documentCatalog.getAcroForm();
        if(form == null) throw new ResourceException(ErrorEnum.ACROFORM_EMPTY.getMessage()); 
        return form;
    }

    protected PDField getField(String name) throws ResourceException {
        PDField field = getForm().getField(name);
        if(field == null) throw new ResourceException(ErrorEnum.FIELD_OBSOLETE.getMessage() + name);
        field.setReadOnly(true);
        return field;
    }

    protected void setField(String fieldName, String fieldValue) throws ResourceException {
        if(fieldValue != null) {
            PDField field = getField(fieldName);
            if (field instanceof PDTextField textField) {
                textField.setActions(null);
            }
            try {
                field.setValue(fieldValue);
            } catch (IOException e) {
                e.printStackTrace(); //TODO input/output exception
            }
        }
    }

    protected void copy(int numberOfCopies) throws IOException {
        while(numberOfCopies > 0) {
            copies.add(PDDocument.load(getClass().getClassLoader().getResourceAsStream(modelName)));
            numberOfCopies--;
        }
    }

    protected List<PDDocument> getCopies() {
        return this.copies;
    }

    private void flat(int copyIndex) throws Exception {
        setIndex(copyIndex);
        getForm().flatten();
    }

    protected void mergeCopies() throws Exception {
        PDFMergerUtility merger = new PDFMergerUtility();
        int numberOfCopies = this.copies.size();
        flat(0);
        for(int copyIndex = 1; copyIndex < numberOfCopies; copyIndex++) {
            flat(copyIndex);
            merger.appendDocument(doc, this.copies.get(copyIndex));
        }
    }

}
