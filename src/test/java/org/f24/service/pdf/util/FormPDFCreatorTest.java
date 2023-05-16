package org.f24.service.pdf.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.f24.dto.component.Header;
import org.f24.dto.component.PersonData;
import org.f24.dto.component.PersonalData;
import org.f24.dto.form.F24Form;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.impl.FormPDFCreator;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.f24.service.pdf.util.FieldEnum.*;

public class FormPDFCreatorTest {

  private FormPDFCreator f24FormCreator;
  private F24Form form;

  @Before
  public void setup() throws IOException {
    String jsonFile = "src/test/resources/input/f24form.json";
    String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
    form = new ObjectMapper().readValue(jsonString, F24Form.class);

    f24FormCreator = new FormPDFCreator(form);
    f24FormCreator.loadDoc("templates" + "/ModF24IMU2013.pdf");
  }

  @Test
  public void shouldFillHeaderSection() throws ResourceException {
    f24FormCreator.setIndex(0);
    Header header = form.getHeader();

    assertNotNull(header);

    f24FormCreator.setField(DELEGATION.getName(), "delegation");
    f24FormCreator.setField(AGENCY.getName(), "agency");
    f24FormCreator.setField(AGENCY_PROVINCE.getName(), "MI");

    assertEquals(f24FormCreator.getField(DELEGATION.getName()).getValueAsString(), "delegation");
    assertEquals(f24FormCreator.getField(AGENCY.getName()).getValueAsString(), "agency");
    assertEquals(f24FormCreator.getField(AGENCY_PROVINCE.getName()).getValueAsString(), "MI");
  }

  @Test
  public void shouldFillPersonData() throws ResourceException {
    f24FormCreator.setIndex(0);
    PersonData personData = form.getTaxPayer().getPersonData();
    PersonalData personalData = personData.getPersonalData();

    assertNotNull(personData);
    assertNotNull(personalData);

    f24FormCreator.setField(CORPORATE_NAME.getName(), personalData.getSurname());
    f24FormCreator.setField(NAME.getName(), personalData.getName());
    f24FormCreator.setField(BIRTH_DATE.getName(), personalData.getBirthDate().replace("-", ""));
    f24FormCreator.setField(SEX.getName(), personalData.getSex());
    f24FormCreator.setField(BIRTH_PLACE.getName(), personalData.getBirthPlace());
    f24FormCreator.setField(BIRTH_PROVINCE.getName(), personalData.getBirthProvince());

    assertEquals(f24FormCreator.getField(CORPORATE_NAME.getName()).getValueAsString(), personalData.getSurname());
    assertEquals(f24FormCreator.getField(NAME.getName()).getValueAsString(), personalData.getName());
    assertEquals(f24FormCreator.getField(BIRTH_DATE.getName()).getValueAsString(),
        personalData.getBirthDate().replace("-", ""));
    assertEquals(f24FormCreator.getField(SEX.getName()).getValueAsString(), personalData.getSex());
    assertEquals(f24FormCreator.getField(BIRTH_PLACE.getName()).getValueAsString(), personalData.getBirthPlace());
    assertEquals(f24FormCreator.getField(BIRTH_PROVINCE.getName()).getValueAsString(), personalData.getBirthProvince());
  }

  @Test
  public void shouldFillCompanyData() throws ResourceException {
    f24FormCreator.setIndex(0);

    f24FormCreator.setField(CORPORATE_NAME.getName(), "name");

    assertEquals(f24FormCreator.getField(CORPORATE_NAME.getName()).getValueAsString(), "name");
  }

  @Test
  public void shouldFillResidenceData() throws ResourceException {
    f24FormCreator.setIndex(0);

    f24FormCreator.setField(ADDRESS.getName(), "address");
    f24FormCreator.setField(MUNICIPALITY.getName(),"province");
    f24FormCreator.setField(TAX_PROVINCE.getName(), "taxprovince");

    assertEquals(f24FormCreator.getField(ADDRESS.getName()).getValueAsString(), "address");
    assertEquals(f24FormCreator.getField(MUNICIPALITY.getName()).getValueAsString(), "province");
    assertEquals(f24FormCreator.getField(TAX_PROVINCE.getName()).getValueAsString(), "taxprovince");
  }

  @Test
  public void shouldFillPaymentDetails() throws ResourceException {
    f24FormCreator.setIndex(0);

    f24FormCreator.setField(DATE_OF_PAYMENT.getName(), "20042068");
    f24FormCreator.setField(COMPANY.getName(), "company");
    f24FormCreator.setField(CAB_CODE.getName(), "code");
    f24FormCreator.setField(CHECK_NUMBER.getName(), "number");
    f24FormCreator.setField(ABI_CODE.getName(), "code");
    f24FormCreator.setField(BANK.getName(), "X");
    f24FormCreator.setField(CIRCULAR.getName(), "");

    assertEquals(f24FormCreator.getField(DATE_OF_PAYMENT.getName()).getValueAsString(), "20042068");
    assertEquals(f24FormCreator.getField(COMPANY.getName()).getValueAsString(), "company");
    assertEquals(f24FormCreator.getField(CAB_CODE.getName()).getValueAsString(), "code");
    assertEquals(f24FormCreator.getField(CHECK_NUMBER.getName()).getValueAsString(), "number");
    assertEquals(f24FormCreator.getField(ABI_CODE.getName()).getValueAsString(), "code");
    assertEquals(f24FormCreator.getField(BANK.getName()).getValueAsString(), "X");
    assertEquals(f24FormCreator.getField(CIRCULAR.getName()).getValueAsString(), "");
  }

}
