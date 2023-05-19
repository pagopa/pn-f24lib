package org.f24.service.pdf.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.f24.dto.component.Record;
import org.f24.dto.form.F24Form;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.impl.FormPDFCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

class PDFFormManagerTest {

    private FormPDFCreator pdfCreator;
    private static final String MODEL_NAME = "templates" + "/ModF24Semplificato.pdf";
    private List<Record> recordList;

    @BeforeEach
    void setup() throws IOException {
        String jsonFile = "src/test/resources/input/f24form.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        F24Form form = new ObjectMapper().readValue(jsonString, F24Form.class);

        recordList = new ArrayList<>(Arrays.asList(
                new Record("1000", "0", "1000"),
                new Record("0", "800", "800"),
                new Record("1200", "0", "1200"),
                new Record("500", "0", "500"),
                new Record("1000", "0", "1000"),
                new Record("0", "800", "800"),
                new Record("1200", "0", "1200"),
                new Record("500", "0", "500")));

        pdfCreator = new FormPDFCreator(form);
        pdfCreator.loadDoc(MODEL_NAME);
    }

    @Test
    void givenPdfDocument_whenGetPdfAcroForm_thenReturnPdfForm() {
        pdfCreator.setIndex(0);
        PDDocumentCatalog documentCatalog = pdfCreator.getCurrentCopy().getDocumentCatalog();
        PDAcroForm form = documentCatalog.getAcroForm();

        assertNotNull(form);
    }

    @Test
    void givenStringFieldName_whenGetPDFField_thenReturnPDFField() throws ResourceException {
        pdfCreator.setIndex(0);
        PDField field = pdfCreator.getField("taxCode");

        assertNotNull(field);
    }

    @Test
    void givenStringFieldNameStringFieldValue_shouldUpdatePdfField() throws ResourceException {
        pdfCreator.setIndex(0);
        pdfCreator.setField("name", "test");

        PDField field = pdfCreator.getField("name");

        assertNotNull(field);
        assertEquals("test", field.getValueAsString());
    }

    @Test
    void givenIntNumberOfCopies_shouldAddNewCopy() throws IOException {
        pdfCreator.setIndex(1);
        int documentCopies = pdfCreator.getCopies().size();
        pdfCreator.copy(1);

        assertNotNull(pdfCreator.getCopies());
        assertEquals(documentCopies + 1, pdfCreator.getCopies().size());
    }

    @Test
    void shouldMergeDocumentCopies() throws IOException, ResourceException {
        pdfCreator.setIndex(0);
        pdfCreator.copy(1);
        pdfCreator.mergeCopies();

        assertNotNull(pdfCreator.getDoc());
    }

    @Test
    void givenIntValue_whenConvertToFloatString_thenReturnString() {
        String parsedInt = pdfCreator.getMoney(10000);

        assertNotNull(parsedInt);
        assertEquals("100  0 0", parsedInt);
    }

    @Test
    void giventDoubleValue_whenConverToFloatString_thenReturnString() {
        String[] paresedFloat = pdfCreator.splitField(1465.236);

        assertNotNull(paresedFloat);
        assertEquals("1465", paresedFloat[0]);
        assertEquals("24", paresedFloat[1]);
    }

    @Test
    void givenPDFRecordList_whenCalculateTotalAmount_thenReturnTotalAmount() throws ResourceException {
        int totalAmount = pdfCreator.getTotalAmount(recordList);

        assertEquals(3800, totalAmount);
    }

    @Test
    void givenPDFRecordList_whenCalculateTotalCredit_thenReturnTotalCredit() throws ResourceException {
        int totalCredit = pdfCreator.getCreditTotal(recordList);

        assertEquals(1600, totalCredit);
    }

    @Test
    void givenPDFRecordList_whenCalculateTotalDebit_thenReturnTotalDebit() throws ResourceException {
        int totalDebit = pdfCreator.getDebitTotal(recordList);

        assertEquals(5400, totalDebit);
    }

    @Test
    void givenPDFRecordList_whenPaginateRecordList_thenReturnPage() {
        List<Record> page = pdfCreator.paginateList(1, 4, recordList);

        assertNotNull(page);
        assertEquals(4, page.size());

        List<Record> examplePage = recordList.subList(4, 8);
        assertEquals(examplePage, page);
    }
}
