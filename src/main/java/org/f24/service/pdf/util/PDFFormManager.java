package org.f24.service.pdf.util;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.f24.exception.ErrorEnum;
import org.f24.exception.ResourceException;
import org.f24.dto.component.Record;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
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

    protected String getMoney(int input) {
        double doubleInput = input / 100.0;
        int integerPart = (int) doubleInput;
        double decimalPart = doubleInput - integerPart;
        String decimalPartString = String.format(Locale.ROOT, "%.2f", decimalPart).split("\\.")[1];
        decimalPartString = decimalPartString.replace("", " ").trim();
        return integerPart + "  " + decimalPartString;
    }

    protected String[] splitField(double input) {
        input = Math.round(input * 100.0) / 100.0;
        int integerPart = (int) input;
        double decimalPart = input - integerPart;
        return new String[] { Integer.toString(integerPart),
                String.format(Locale.ROOT, "%.2f", decimalPart).split("\\.")[1] };
    }

    protected Integer getTotalAmount(List<? extends Record> totalRecord) throws ResourceException {

        if (totalRecord == null)
            throw new ResourceException(ErrorEnum.RECORD_EMPTY.getMessage());

        Integer totalAmount = totalRecord
                .stream()
                .mapToInt(mr -> Integer.parseInt(mr.getDebitAmount() != null ? mr.getDebitAmount() : "0")
                        - Integer.parseInt(mr.getCreditAmount() != null ? mr.getCreditAmount() : "0"))
                .sum();
        if (totalAmount < 0) {
            throw new ResourceException("TotalAmount: " + ErrorEnum.NEGATIVE_NUM.getMessage());// TODO comment for gen testing
            // totalAmount *= -1;// TODO uncomment for gen testing
        }
        return totalAmount;
    }

    protected Integer getDebitTotal(List<? extends Record> debitRecord) throws ResourceException {

        if (debitRecord == null)
            throw new ResourceException(ErrorEnum.RECORD_EMPTY.getMessage());

        Integer totalAmount = debitRecord
                .stream()
                .mapToInt(mr -> Integer.parseInt(mr.getDebitAmount() != null ? mr.getDebitAmount() : "0"))
                .sum();
        if (totalAmount < 0) {
            throw new ResourceException("TotalAmount: " + ErrorEnum.NEGATIVE_NUM.getMessage());
        }
        return totalAmount;
    }

    protected Integer getCreditTotal(List<? extends Record> creditRecord) throws ResourceException {

        if (creditRecord == null)
            throw new ResourceException(ErrorEnum.RECORD_EMPTY.getMessage());

        Integer totalAmount = creditRecord
                .stream()
                .mapToInt(mr -> Integer.parseInt(mr.getCreditAmount() != null ? mr.getCreditAmount() : "0"))
                .sum();
        if (totalAmount < 0) {
            throw new ResourceException("TotalAmount: " + ErrorEnum.NEGATIVE_NUM.getMessage());
        }
        return totalAmount;
    }

    protected <T> List<T> paginateList(int copyIndex, int maxRecordsNumber,
            List<T> targetList) {
        int startIndex = copyIndex * maxRecordsNumber;
        int endIndex = Math.min(startIndex + maxRecordsNumber, targetList.size());
        if (startIndex > endIndex) {
            return Collections.emptyList();
        }
        return targetList.subList(startIndex, endIndex);
    }

}
