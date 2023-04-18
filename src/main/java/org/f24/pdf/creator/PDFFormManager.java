package org.f24.pdf.creator;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

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

    private PDAcroForm getForm() throws Exception {
        PDDocumentCatalog documentCatalog = getCurrentCopy().getDocumentCatalog();
        PDAcroForm form = documentCatalog.getAcroForm();
        if(form == null) throw new Exception(); // TODO
        return form;
    }

    protected PDField getField(String name) throws Exception {
        PDField field = getForm().getField(name);
        if(field == null) throw new Exception(); // TODO
        field.setReadOnly(true);
        return field;
    }

    protected void setField(String fieldName, String fieldValue) throws Exception {
        getField(fieldName).setValue(fieldValue);
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

    protected void merge() throws IOException {
        PDFMergerUtility merger = new PDFMergerUtility();
        int numberOfCopies = this.copies.size();
        for(int copyIndex = 1; copyIndex < numberOfCopies; copyIndex++) {
            merger.appendDocument(doc, this.copies.get(copyIndex));
        }
    }

}
