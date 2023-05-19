package org.f24.service.pdf.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.f24.dto.component.CompanyData;
import org.f24.dto.component.PaymentDetails;
import org.f24.dto.component.Header;
import org.f24.dto.component.InailRecord;
import org.f24.dto.component.InpsSection;
import org.f24.dto.component.Record;
import org.f24.dto.component.PersonData;
import org.f24.dto.component.PersonalData;
import org.f24.dto.form.F24Form;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.impl.FormPDFCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.f24.service.pdf.util.FieldEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FormPDFCreatorTest {

  private FormPDFCreator f24FormCreator;
  private F24Form form;
  List<InailRecord> sectionRecords;

  @BeforeEach
  void setup() throws IOException {
    String jsonFile = "src/test/resources/input/f24form.json";
    String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
    form = new ObjectMapper().readValue(jsonString, F24Form.class);

    sectionRecords = new ArrayList<InailRecord>(Arrays.asList(
        new InailRecord("", "", "", "", "", "2000", "500"),
        new InailRecord("", "", "", "", "", "500", "1000"),
        new InailRecord("", "", "", "", "", "1000", "500"),
        new InailRecord("", "", "", "", "", "500", "1000"),
        new InailRecord("", "", "", "", "", "1000", "500"),
        new InailRecord("", "", "", "", "", "500", "1000")));

    f24FormCreator = new FormPDFCreator(form);
    f24FormCreator.loadDoc("templates" + "/ModF24IMU2013.pdf");
  }

  @Test
  void shouldFillHeaderSection() throws ResourceException {
    f24FormCreator.setIndex(0);
    Header header = form.getHeader();

    assertNotNull(header);

    f24FormCreator.setField(DELEGATION.getName(), "delegation");
    f24FormCreator.setField(AGENCY.getName(), "agency");
    f24FormCreator.setField(AGENCY_PROVINCE.getName(), "MI");

    assertEquals("delegation", f24FormCreator.getField(DELEGATION.getName()).getValueAsString());
    assertEquals("agency", f24FormCreator.getField(AGENCY.getName()).getValueAsString());
    assertEquals("MI", f24FormCreator.getField(AGENCY_PROVINCE.getName()).getValueAsString());
  }

  @Test
  void shouldFillPersonData() throws ResourceException {
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

    assertEquals(personalData.getSurname(), f24FormCreator.getField(CORPORATE_NAME.getName()).getValueAsString());
    assertEquals(personalData.getName(), f24FormCreator.getField(NAME.getName()).getValueAsString());
    assertEquals(personalData.getBirthDate().replace("-", ""),
        f24FormCreator.getField(BIRTH_DATE.getName()).getValueAsString());
    assertEquals(personalData.getSex(), f24FormCreator.getField(SEX.getName()).getValueAsString());
    assertEquals(personalData.getBirthPlace(), f24FormCreator.getField(BIRTH_PLACE.getName()).getValueAsString());
    assertEquals(personalData.getBirthProvince(), f24FormCreator.getField(BIRTH_PROVINCE.getName()).getValueAsString());
  }

  @Test
  void shouldSetCompanyData() throws ResourceException {
    f24FormCreator.setIndex(0);
    CompanyData companyData = form.getTaxPayer().getCompanyData();

    if (companyData == null) {
      companyData = new CompanyData("NAME", null);
    }

    f24FormCreator.setField(CORPORATE_NAME.getName(), companyData.getName());
    assertEquals(companyData.getName(), f24FormCreator.getField(CORPORATE_NAME.getName()).getValueAsString());
  }

  @Test
  void shouldFillCompanyData() throws ResourceException {
    f24FormCreator.setIndex(0);

    f24FormCreator.setField(CORPORATE_NAME.getName(), "name");

    assertEquals("name", f24FormCreator.getField(CORPORATE_NAME.getName()).getValueAsString());
  }

  @Test
  void shouldFillResidenceData() throws ResourceException {
    f24FormCreator.setIndex(0);

    f24FormCreator.setField(ADDRESS.getName(), "ADDRESS");
    f24FormCreator.setField(MUNICIPALITY.getName(), "PR");
    f24FormCreator.setField(TAX_PROVINCE.getName(), "TAXPR");

    assertEquals("ADDRESS", f24FormCreator.getField(ADDRESS.getName()).getValueAsString());
    assertEquals("PR", f24FormCreator.getField(MUNICIPALITY.getName()).getValueAsString());
    assertEquals("TAXPR", f24FormCreator.getField(TAX_PROVINCE.getName()).getValueAsString());
  }

  @Test
  void givenStringSectionIdListRecordsList_whenCalculateSectionTotal_theReturnIntSectionTotal()
      throws NumberFormatException, ResourceException {
    f24FormCreator.setIndex(0);
    assertEquals(1000, f24FormCreator.setSectionTotal("1", sectionRecords));
  }

  @Test
  void shouldFillPaymentDetails() throws ResourceException {
    f24FormCreator.setIndex(0);
    PaymentDetails paymentDetails = new PaymentDetails("20042068", "company", "code", "number", true, "code", "");

    assertNotNull(paymentDetails);

    f24FormCreator.setField(DATE_OF_PAYMENT.getName(), paymentDetails.getPaymentDate());
    f24FormCreator.setField(COMPANY.getName(), paymentDetails.getCompany());
    f24FormCreator.setField(CAB_CODE.getName(), paymentDetails.getCabCode());
    f24FormCreator.setField(CHECK_NUMBER.getName(), paymentDetails.getCheckNumber());
    f24FormCreator.setField(ABI_CODE.getName(), paymentDetails.getAbiCode());
    f24FormCreator.setField(BANK.getName(), "X");
    f24FormCreator.setField(CIRCULAR.getName(), paymentDetails.getIbanCode());

    assertEquals("20042068", f24FormCreator.getField(DATE_OF_PAYMENT.getName()).getValueAsString());
    assertEquals("company", f24FormCreator.getField(COMPANY.getName()).getValueAsString());
    assertEquals("code", f24FormCreator.getField(CAB_CODE.getName()).getValueAsString());
    assertEquals("number", f24FormCreator.getField(CHECK_NUMBER.getName()).getValueAsString());
    assertEquals("code", f24FormCreator.getField(ABI_CODE.getName()).getValueAsString());
    assertEquals("X", f24FormCreator.getField(BANK.getName()).getValueAsString());
    assertEquals("", f24FormCreator.getField(CIRCULAR.getName()).getValueAsString());
  }

}
