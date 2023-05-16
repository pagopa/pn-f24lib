package org.f24.service.pdf.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PDFFormManagerTest {

  private FormPDFCreator f24FormCreator;
  private static final String MODEL_NAME = "templates" + "/ModF24Semplificato.pdf";
  private List<Record> recordList;

  @Before
  public void setup() throws IOException {
    String jsonFile = "src/test/resources/input/f24form.json";
    String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
    F24Form form = new ObjectMapper().readValue(jsonString, F24Form.class);

    recordList = new ArrayList<Record>(Arrays.asList(
        new Record("1000", "0", "1000"),
        new Record("0", "800", "800"),
        new Record("1200", "0", "1200"),
        new Record("500", "0", "500"),
        new Record("1000", "0", "1000"),
        new Record("0", "800", "800"),
        new Record("1200", "0", "1200"),
        new Record("500", "0", "500")));

    f24FormCreator = new FormPDFCreator(form);
    f24FormCreator.loadDoc(MODEL_NAME);
  }

  @Test
  public void givenPDFDocument_whentGetPDFAcroForm_thenReturnPDFForm() throws ResourceException {
    f24FormCreator.setIndex(0);
    PDDocumentCatalog documentCatalog = f24FormCreator.getCurrentCopy().getDocumentCatalog();
    PDAcroForm form = documentCatalog.getAcroForm();

    assertNotNull(form);
  }

  @Test
  public void givenStringFieldName_whenGetPDFField_thenReturnPDFField() throws ResourceException {
    f24FormCreator.setIndex(0);
    PDField field = f24FormCreator.getField("taxCode");
    assertNotNull(field);
  }

  @Test
  public void givenStringFieldNameStringFieldValue_shouldUpdatePdfField() throws ResourceException {
    f24FormCreator.setIndex(0);
    f24FormCreator.setField("name", "test");

    PDField field = f24FormCreator.getField("name");

    assertNotNull(field);
    assertEquals(field.getValueAsString(), "test");
  }

  @Test
  public void givenIntNumberOfCopies_shouldAddNewCopy() throws IOException {
    f24FormCreator.setIndex(1);
    int documentCopies = f24FormCreator.getCopies().size();
    f24FormCreator.copy(1);

    assertNotNull(f24FormCreator.getCopies());
    assertEquals(f24FormCreator.getCopies().size(), documentCopies + 1);
  }

  @Test
  public void shouldMergeDocumentCopies() throws IOException, ResourceException {
    f24FormCreator.setIndex(0);
    f24FormCreator.copy(1);
    f24FormCreator.mergeCopies();

    assertNotNull(f24FormCreator.getDoc());
  }

  @Test
  public void givenIntValue_whenConvertToFloatString_thenReturnString() {
    String parsedInt = f24FormCreator.getMoney(10000);

    assertNotNull(parsedInt);
    assertEquals(parsedInt, "100  0 0");
  }

  @Test
  public void giventDoubleValue_whenConverToFloatString_thenReturnString() {
    String[] paresedFloat = f24FormCreator.splitField(1465.236);

    assertNotNull(paresedFloat);
    assertEquals(paresedFloat[0], "1465");
    assertEquals(paresedFloat[1], "24");
  }

  @Test
  public void givenPDFRecordList_whenCalculateTotalAmount_thenReturnTotalAmount() throws ResourceException {
    int totalAmount = f24FormCreator.getTotalAmount(recordList);

    assertNotNull(totalAmount);
    assertEquals(totalAmount, 3800);
  }

  @Test
  public void givenPDFRecordList_whenCalculateTotalCredit_thenReturnTotalCredit() throws ResourceException {
    int totalCredit = f24FormCreator.getCreditTotal(recordList);

    assertNotNull(totalCredit);
    assertEquals(totalCredit, 1600);
  }

  @Test
  public void givenPDFRecordList_whenCalculateTotalDebit_thenReturnTotalDebit() throws ResourceException {
    int totalDebit = f24FormCreator.getDebitTotal(recordList);

    assertNotNull(totalDebit);
    assertEquals(totalDebit, 5400);
  }

  @Test
  public void givenPDFRecordList_whenPaginateRecordList_thenReturnPage() {
    List<Record> page = f24FormCreator.paginateList(1, 4, recordList);

    assertNotNull(page);
    assertEquals(page.size(), 4);

    List<Record> examplePage = recordList.subList(4, 8);
    assertEquals(page, examplePage);
  }
}
