package org.f24.service.pdf.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
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

  @Before
  public void setup() throws IOException {
    String jsonFile = "src/test/resources/input/f24form.json";
    String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
    F24Form form = new ObjectMapper().readValue(jsonString, F24Form.class);

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
    int documentCopies =  f24FormCreator.getCopies().size();
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
}
