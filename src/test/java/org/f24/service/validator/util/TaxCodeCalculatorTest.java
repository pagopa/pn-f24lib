package org.f24.service.validator.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

class TaxCodeCalculatorTest {

  @Test
  void givenPersonData_whenCalculateTaxCode_thenReturnTaxCode() {
    Date date = new GregorianCalendar(1265, Calendar.MAY, 05).getTime();
    String taxCode = TaxCodeCalculator.calculateTaxCode("Dante", "Alighieri", "m", date, "F234");

    assertNotNull(taxCode);
    assertEquals("DNTLHR65E05F234N", taxCode);
  }

  @Test
  void givenPersonLastName_whenCalculateCode_thenReturnLastNameCode() {
    StringBuilder nameCode = TaxCodeCalculator.calculateLastnameCod("Dante");

    assertEquals("Dan", nameCode.toString());
  }

  @Test
  void givenFirstPersonName_whenCalculateCode_thenReturnFirstNameCode() {
    StringBuilder nameCode = TaxCodeCalculator.calculateLastnameCod("Alighieri");

    assertEquals("lig", nameCode.toString());
  }

  @Test
  void givenBirthDateAndSex_whenCalculateDtCod_thenReturnDtCod() {
    Date date = new GregorianCalendar(1265, Calendar.MAY, 05).getTime();
    StringBuilder dtCode = TaxCodeCalculator.calculateDtCod(date, "m");

    assertEquals("65E45", dtCode.toString());
  }

  @Test
  void givenControlChar_whenCalcualteChar_thenReturnControlChar() {
    StringBuilder taxCode = new StringBuilder("DNTLHR65E05");

    taxCode.append(TaxCodeCalculator.calculateControlChar(taxCode));

    assertEquals("DNTLHR65E05R", taxCode.toString());
  }

  @Test
  void givenStringValue_whenCalculateSubstitutionChar_thenReturnSubChar() {
    String charValue = TaxCodeCalculator.getSubstitutionChar("Dante");

    assertEquals("Dante", charValue);
  }

  @Test
  void givenStringAndBoolean_whenGenerateConsVow_thenReturnConsVow() {
    String consValue = TaxCodeCalculator.getConsVow("Dante", true);
    assertEquals("Dante", consValue);

    consValue = TaxCodeCalculator.getConsVow("Dante", false);
    assertEquals("", consValue);
  }
}
