package org.f24.service.validator.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

public class TaxCodeCalculatorTest {

  @Test
  public void givenPersonData_whenCalculateTaxCode_thenReturnTaxCode() {
    Date date = new GregorianCalendar(1265, Calendar.MAY, 05).getTime();
    String taxCode = TaxCodeCalculator.calculateTaxCode("Dante", "Alighieri", "m", date, "F234");

    assertNotNull(taxCode);
    assertEquals(taxCode, "DNTLHR65E05F234N");
  }

  @Test
  public void givenPersonLastName_whenCalculateCode_thenReturnLastNameCode() {
    StringBuilder nameCode = TaxCodeCalculator.calculateLastnameCod("Dante");

    assertEquals(nameCode.toString(), "Dan");
  }

  @Test 
  public void givenFirstPersonName_whenCalculateCode_thenReturnFirstNameCode() {
    StringBuilder nameCode = TaxCodeCalculator.calculateLastnameCod("Alighieri");

    assertEquals(nameCode.toString(), "lig");
  }

  @Test
  public void givenBirthDateAndSex_whenCalculateDtCod_thenReturnDtCod() {
    Date date = new GregorianCalendar(1265, Calendar.MAY, 05).getTime();
    StringBuilder dtCode = TaxCodeCalculator.calculateDtCod(date, "m");

    assertEquals(dtCode.toString(), "65E45");
  }

  @Test
  public void givenControlChar_whenCalcualteChar_thenReturnControlChar() {
    StringBuilder taxCode = new StringBuilder("DNTLHR65E05");

    taxCode.append(TaxCodeCalculator.calculateControlChar(taxCode));

    assertEquals(taxCode.toString(), "DNTLHR65E05R");
  }

  @Test
  public void givenStringValue_whenCalculateSubstitutionChar_thenReturnSubChar() {
    String value = TaxCodeCalculator.getSubstitutionChar("Dante");

    assertEquals(value, "Dante");
  }

  @Test 
  public void givenStringAndBoolean_whenGenerateConsVow_thenReturnConsVow() {
    String value = TaxCodeCalculator.getConsVow("Dante", true);
    assertEquals(value, "Dante");

    value = TaxCodeCalculator.getConsVow("Dante", false);
    assertEquals(value, "");
  }
}
