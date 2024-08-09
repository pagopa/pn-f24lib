package org.f24.service.pdf.util;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.*;
import org.f24.dto.component.Record;
import org.f24.exception.ErrorEnum;
import org.f24.exception.ResourceException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import static org.f24.service.pdf.util.FieldEnum.TOTAL_AMOUNT;

public class PDFFormManager {

    private String modelName;
    private Integer currentIndex;
    private PdfStamper pdfStamper;
    private List<PdfReader> copies;
    private ByteArrayOutputStream copyByteArrayOutputStream;
    private final Logger logger = Logger.getLogger(PDFFormManager.class.getName());

    protected void loadDoc(String modelName) throws IOException {
        this.modelName = modelName;
        PdfReader firstCopy = new PdfReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(modelName)));
        this.copies = new ArrayList<>();
        this.copies.add(firstCopy);
        this.copyByteArrayOutputStream = new ByteArrayOutputStream();
    }

    protected void setIndex(Integer currentIndex) {
        this.currentIndex = currentIndex;
    }

    protected PdfReader getCurrentCopy() {
        return this.copies.get(this.currentIndex);
    }

    protected AcroFields getForm() throws ResourceException {
        try {
            if (pdfStamper == null) {
                pdfStamper = new PdfStamper(getCurrentCopy(), copyByteArrayOutputStream);
                pdfStamper.setFormFlattening(false);
            }
            AcroFields form = pdfStamper.getAcroFields();
            if (form == null) {
                throw new ResourceException(ErrorEnum.ACROFORM_EMPTY.getMessage());
            }
            return form;
        } catch (IOException e) {
            throw new ResourceException("Error accessing AcroForm");
        }
    }

    public void closeStamper() throws IOException {
        if (pdfStamper != null) {
            pdfStamper.close();
            pdfStamper = null;
        }
    }

    public void updateCopy() throws IOException {
        closeStamper();
        copies.set(currentIndex, new PdfReader(copyByteArrayOutputStream.toByteArray()));
        copyByteArrayOutputStream.reset();
    }

    protected String getField(String name) throws ResourceException {
        AcroFields form = getForm();
        if (!form.getAllFields().containsKey(name)) {
            throw new ResourceException(ErrorEnum.FIELD_OBSOLETE.getMessage() + name);
        }
        return form.getField(name);
    }

    protected void setField(String fieldName, String fieldValue) throws ResourceException {
        if (fieldValue != null) {
            try {
                AcroFields form = getForm();
                if (!form.getAllFields().containsKey(fieldName)) {
                    throw new ResourceException(ErrorEnum.FIELD_OBSOLETE.getMessage() + fieldName);
                }
                AcroFields.Item item = form.getFieldItem(fieldName);
                PdfDictionary mergedDict = item.getMerged(0);
                mergedDict.put(PdfName.MAXLEN, new PdfNumber(fieldValue.length()));

                form.setField(fieldName, fieldValue);
            } catch (IOException | DocumentException e) {
                logger.info(e.getMessage());
                throw new ResourceException("Error setting field: " + fieldName);
            }
        }
    }

    protected void copy(int numberOfCopies) throws IOException {
        if (copies.size() == 1)
            while (numberOfCopies > 0) {
                copies.add(new PdfReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(modelName))));
                numberOfCopies--;
            }
    }

    protected List<PdfReader> getCopies() {
        return this.copies;
    }

    public void flat() throws ResourceException {
        try {
            AcroFields form = getForm();
            for (String fieldName : form.getAllFields().keySet()) {
                form.setFieldProperty(fieldName, "setfflags", PdfFormField.FF_READ_ONLY, null);
            }
        } catch (DocumentException e) {
            throw new ResourceException("Error setting fields to read-only");
        }
    }

    protected byte[] mergeCopies() throws IOException, ResourceException {
        ByteArrayOutputStream mergedOutputStream = new ByteArrayOutputStream();
        Document document = new Document();

        try (document; PdfCopy pdfCopy = new PdfCopy(document, mergedOutputStream)) {
            document.open();

            PdfReader firstCopy = copies.get(0);
            pdfCopy.copyAcroForm(firstCopy);

            // Retrieve the pages for each copy and iterate over them to maintain the pagination sequence
            int totalPages = firstCopy.getNumberOfPages();
            for (int i = 1; i <= totalPages; i++) {
                for (PdfReader reader : copies) {
                    pdfCopy.addPage(pdfCopy.getImportedPage(reader, i));
                }
            }
        } catch (Exception e) {
            throw new ResourceException("Error merging copies");
        }

        return mergedOutputStream.toByteArray();
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
        if (this.pdfStamper != null) {
            this.pdfStamper.close();
        }

        for (PdfReader reader : this.copies) {
            reader.close();
        }
    }
}
