package org.f24.service.pdf.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.f24.dto.component.Record;
import org.f24.exception.ResourceException;
import org.junit.Before;
import org.junit.Test;

public class CreatorHelperTest {

  private CreatorHelper helper = new CreatorHelper();
  List<Record> recordList;

  @Before
  public void setup() throws IOException {
    recordList = new ArrayList<Record>(Arrays.asList(
        new Record("1000", "0", "1000"),
        new Record("0", "800", "800"),
        new Record("1200", "0", "1200"),
        new Record("500", "0", "500"),
        new Record("1000", "0", "1000"),
        new Record("0", "800", "800"),
        new Record("1200", "0", "1200"),
        new Record("500", "0", "500")
        ));

  }

  @Test
  public void givenIntValue_whenConvertToFloatString_thenReturnString() {
    String parsedInt = helper.getMoney(10000);

    assertNotNull(parsedInt);
    assertEquals(parsedInt, "100  0 0");
  }

  @Test
  public void giventDoubleValue_whenConverToFloatString_thenReturnString() {
    String[] paresedFloat = helper.splitField(1465.236);

    assertNotNull(paresedFloat);
    assertEquals(paresedFloat[0], "1465");
    assertEquals(paresedFloat[1], "24");
  }

  @Test
  public void givenPDFRecordList_whenCalculateTotalAmount_thenReturnTotalAmount() throws ResourceException {
    int totalAmount = helper.getTotalAmount(recordList);

    assertNotNull(totalAmount);
    assertEquals(totalAmount, 3800);
  }

  @Test
  public void givenPDFRecordList_whenCalculateTotalCredit_thenReturnTotalCredit() throws ResourceException {
    int totalCredit = helper.getCreditTotal(recordList);

    assertNotNull(totalCredit);
    assertEquals(totalCredit, 1600);
  }

  @Test
  public void givenPDFRecordList_whenCalculateTotalDebit_thenReturnTotalDebit() throws ResourceException {
    int totalDebit = helper.getDebitTotal(recordList);

    assertNotNull(totalDebit);
    assertEquals(totalDebit, 5400);
  }

  @Test
  public void givenPDFRecordList_whenPaginateRecordList_thenReturnPage() {
    List<Record> page = helper.paginateList(1, 4, recordList);

    assertNotNull(page);
    assertEquals(page.size(), 4);

    List<Record> examplePage = recordList.subList(4, 8);
    assertEquals(page, examplePage);
  }

  
}
