package org.f24.dto.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaxPayerTest {

  private PersonData personData;
  private PersonalData personalData;
  private CompanyData companyData;
  private TaxAddress taxAddress;

  @BeforeEach
  void setup() {
    personalData = new PersonalData("Surname", "Name", "20061986", "f", "birthplace", "province");
    taxAddress = new TaxAddress("municipality", "province", "address");
    companyData = new CompanyData("name", taxAddress);
    personData = new PersonData(personalData);
  }

  @Test
  void givenTaxPayerData_whenCreateNewTaxPayerWithPersonData_thenReturnTaxPayer() {

    TaxPayer taxPayer = new TaxPayer("taxCode", false, personData, "code", "idCode");
    assertNotNull(taxPayer);

    assertEquals("taxCode", taxPayer.getTaxCode());
    assertEquals(false, taxPayer.getIsNotTaxYear());
    assertEquals(personData, taxPayer.getPersonData());
    assertEquals("code", taxPayer.getRelativePersonTaxCode());
    assertEquals("idCode", taxPayer.getIdCode());

  }

  @Test
  void givenTaxPayerData_whenCreateNewTaxPayerWithCompanyData_thenReturnTaxPayer() {

    TaxPayer taxPayer = new TaxPayer("taxCode", false, personData, "code", "idCode");
    assertNotNull(taxPayer);

    assertEquals("taxCode", taxPayer.getTaxCode());
    assertEquals(false, taxPayer.getIsNotTaxYear());
    assertEquals(personData, taxPayer.getPersonData());
    assertEquals("code", taxPayer.getRelativePersonTaxCode());
    assertEquals("idCode", taxPayer.getIdCode());

  }

  @Test
  void givenTaxPayerData_whenCreateNewTaxPayerWithPersonAndCompanyData_thenReturnTaxPayer() {

    TaxPayer taxPayer = new TaxPayer("taxCode", false, personData, companyData, "code", "idCode");
    assertNotNull(taxPayer);

    assertEquals("taxCode", taxPayer.getTaxCode());
    assertEquals(false, taxPayer.getIsNotTaxYear());
    assertEquals(companyData, taxPayer.getCompanyData());
    assertEquals(personData, taxPayer.getPersonData());
    assertEquals("code", taxPayer.getRelativePersonTaxCode());
    assertEquals("idCode", taxPayer.getIdCode());
  }

  @Test
  void givenTaxPayerData_whenCreateNewSimplifiedTaxPayer_thenReturnTaxPayer() {

    TaxPayer simpleTaxPayer = new TaxPayer("taxCode", "documentCode", "officeCode", personData, "code", "idCode");
    assertNotNull(simpleTaxPayer);

    assertEquals("taxCode", simpleTaxPayer.getTaxCode());
    assertEquals("documentCode", simpleTaxPayer.getDocumentCode());
    assertEquals("officeCode", simpleTaxPayer.getOfficeCode());
    assertEquals(personData, simpleTaxPayer.getPersonData());
    assertEquals("code", simpleTaxPayer.getRelativePersonTaxCode());
    assertEquals("idCode", simpleTaxPayer.getIdCode());
  }

  @Test
  void givenTaxPayerData_whenCreateNewElidWithPersonDataTaxPayer_thenReturnTaxPayer() {

    TaxPayer taxPayer = new TaxPayer("taxCode", personData, "code", "idCode");
    assertNotNull(taxPayer);

    assertEquals("taxCode", taxPayer.getTaxCode());
    assertEquals(personData, taxPayer.getPersonData());
    assertEquals("code", taxPayer.getRelativePersonTaxCode());
    assertEquals("idCode", taxPayer.getIdCode());
  }


  @Test
  void givenTaxPayerData_whenCreateNewElidWithCompanyDataTaxPayer_thenReturnTaxPayer() {

    TaxPayer taxPayer = new TaxPayer("taxCode", companyData, "code", "idCode");
    assertNotNull(taxPayer);

    assertEquals("taxCode", taxPayer.getTaxCode());
    assertEquals(companyData, taxPayer.getCompanyData());
    assertEquals("code", taxPayer.getRelativePersonTaxCode());
    assertEquals("idCode", taxPayer.getIdCode());
  }


}
