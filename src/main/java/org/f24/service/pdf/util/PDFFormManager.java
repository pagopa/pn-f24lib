package org.f24.service.pdf.util;

import org.apache.pdfbox.io.IOUtils;
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
import java.util.logging.Logger;

import static org.f24.service.pdf.util.FieldEnum.*;

public class PDFFormManager {

    private String modelName;

    private Integer currentIndex;

    private PDDocument doc;
    private PDDocument sortedDoc;
    private List<PDDocument> copies;
    private Integer totalAcroFromPages;
    private final Logger logger = Logger.getLogger(PDFFormManager.class.getName());

    protected void loadDoc(String modelName) throws IOException {
        this.modelName = modelName;
        this.doc = PDDocument.load(getClass().getClassLoader().getResourceAsStream(modelName));
        this.sortedDoc = new PDDocument();
        this.totalAcroFromPages = doc.getNumberOfPages();

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
            }
        }
    }

    protected void copy(int numberOfCopies) throws IOException {
        if(copies.size() == 1)
            while (numberOfCopies > 0) {
                copies.add(PDDocument.load(getClass().getClassLoader().getResourceAsStream(modelName)));
                numberOfCopies--;
            }
    }

    protected List<PDDocument> getCopies() {
        return this.copies;
    }

    private void flat(int copyIndex) throws ResourceException {
        setIndex(copyIndex);
        getForm().getFieldTree().forEach(pdField -> pdField.setReadOnly(true));
    }

    protected void mergeCopies() throws IOException, ResourceException {
        PDFMergerUtility merger = new PDFMergerUtility();
        int numberOfCopies = this.copies.size();
        flat(0);
        
        for (int copyIndex = 1; copyIndex < numberOfCopies; copyIndex++) {
            flat(copyIndex);
            merger.appendDocument(doc, this.copies.get(copyIndex)); 
        }

        if(totalAcroFromPages > 1) {
            sortDoc(numberOfCopies);
            doc = sortedDoc;
        }
    }

    protected void sortDoc(int numberOfCopies) {
        for (int pageIndex = 0; pageIndex < totalAcroFromPages; pageIndex++) {
            sortedDoc.addPage(doc.getPages().get(pageIndex));
            for (int copyIndex = 1; copyIndex < numberOfCopies; copyIndex++) sortedDoc.addPage(doc.getPages().get(this.totalAcroFromPages * copyIndex + pageIndex));
        }
        sortedDoc.getDocumentCatalog().setAcroForm(doc.getDocumentCatalog().getAcroForm());
    }

    public int getTotalPages(int recordAmount, int maxAmount, int totalPages) {
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
        return new String[]{Integer.toString(integerPart),
                String.format(Locale.ROOT, "%.2f", decimalPart).split("\\.")[1]};
    }

    protected Integer getTotalAmount(List<? extends Record> totalRecord) throws ResourceException {

        if (totalRecord == null)
            throw new ResourceException(ErrorEnum.RECORD_EMPTY.getMessage());

        int totalAmount = totalRecord
                .stream()
                .mapToInt(mr -> Integer.parseInt(mr.getDebitAmount() != null ? mr.getDebitAmount() : "0")
                        - Integer.parseInt(mr.getCreditAmount() != null ? mr.getCreditAmount() : "0"))
                .sum();
        if (totalAmount < 0) {
            throw new ResourceException(TOTAL_AMOUNT + ErrorEnum.NEGATIVE_NUM.getMessage());
        }
        return totalAmount;
    }

    protected Integer getDebitTotal(List<? extends Record> debitRecord) throws ResourceException {

        if (debitRecord == null)
            throw new ResourceException(ErrorEnum.RECORD_EMPTY.getMessage());

        int totalAmount = debitRecord
                .stream()
                .mapToInt(mr -> Integer.parseInt(mr.getDebitAmount() != null ? mr.getDebitAmount() : "0"))
                .sum();
        if (totalAmount < 0) {
            throw new ResourceException(TOTAL_AMOUNT + ErrorEnum.NEGATIVE_NUM.getMessage());
        }
        return totalAmount;
    }

    protected Integer getCreditTotal(List<? extends Record> creditRecord) throws ResourceException {

        if (creditRecord == null)
            throw new ResourceException(ErrorEnum.RECORD_EMPTY.getMessage());

        int totalAmount = creditRecord
                .stream()
                .mapToInt(mr -> Integer.parseInt(mr.getCreditAmount() != null ? mr.getCreditAmount() : "0"))
                .sum();
        if (totalAmount < 0) {
            throw new ResourceException(TOTAL_AMOUNT + ErrorEnum.NEGATIVE_NUM.getMessage());
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

    protected void finalizeDoc() throws IOException {
        if (this.doc != null) {
            this.doc.close();
            
            for (PDDocument c : this.copies) {
                c.close();
            }
            IOUtils.closeQuietly(getCurrentCopy());
        }
    }

}
