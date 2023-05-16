package org.f24.service.pdf.util;

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
import java.util.logging.Level;
import java.util.logging.Logger;

public class PDFFormManager {

    private String modelName;

    private Integer currentIndex;

    private PDDocument doc;
    private List<PDDocument> copies;
    private Logger logger = Logger.getLogger(PDFFormManager.class.getName());

    protected void loadDoc(String modelName) throws IOException {
        this.modelName = modelName;
        this.doc = PDDocument.load(getClass().getClassLoader().getResourceAsStream(modelName));
        this.copies = new ArrayList<>();
        this.copies.add(doc);

        logger.setLevel(Level.WARNING);
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
        if (form == null)
            throw new ResourceException(ErrorEnum.ACROFORM_EMPTY.getMessage());
        return form;
    }

    protected PDField getField(String name) throws ResourceException {
        PDField field = getForm().getField(name);
        if (field == null)
            throw new ResourceException(ErrorEnum.FIELD_OBSOLETE.getMessage() + name);
        field.setReadOnly(true);
        return field;
    }

    protected void setField(String fieldName, String fieldValue) throws ResourceException {
        if (fieldValue != null) {
            PDField field = getField(fieldName);
            if (field instanceof PDTextField pdfTextfield) {
                (pdfTextfield).setActions(null);
            }
            try {
                field.setValue(fieldValue);
            } catch (IOException e) {
                logger.info(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    protected void copy(int numberOfCopies) throws IOException {
        while (numberOfCopies > 0) {
            copies.add(PDDocument.load(getClass().getClassLoader().getResourceAsStream(modelName)));
            numberOfCopies--;
        }
    }

    protected List<PDDocument> getCopies() {
        return this.copies;
    }

    private void flat(int copyIndex) throws IOException, ResourceException {
        setIndex(copyIndex);
        getForm().flatten();
    }

    protected void mergeCopies() throws IOException, ResourceException {
        PDFMergerUtility merger = new PDFMergerUtility();
        int numberOfCopies = this.copies.size();
        flat(0);
        for (int copyIndex = 1; copyIndex < numberOfCopies; copyIndex++) {
            flat(copyIndex);
            merger.appendDocument(doc, this.copies.get(copyIndex));
        }
    }
    protected int getTotalPages(int recordAmount, int maxAmount, int totalPages ){
        if (recordAmount > maxAmount) {
            int pagesCount = ((recordAmount + maxAmount - 1) / maxAmount) - 1;
            totalPages = Math.max(totalPages, pagesCount);
        }
        return totalPages;
    }

}
