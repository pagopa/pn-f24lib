package org.f24.service.pdf;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.f24.exception.ErrorEnum;
import org.f24.exception.ResourceException;
import org.f24.dto.component.Record;

public class CreatorHelper {

  public String getMoney(int input) {
    double doubleInput = input / 100.0;
    int integerPart = (int) doubleInput;
    double decimalPart = doubleInput - integerPart;
    String decimalPartString = String.format(Locale.ROOT, "%.2f", decimalPart).split("\\.")[1];
    decimalPartString = decimalPartString.replace("", " ").trim();
    return integerPart + "  " + decimalPartString;
  }

  public Integer getTotalAmount(List<? extends Record> totalRecord) throws ResourceException {

    if (totalRecord == null)
      throw new ResourceException(ErrorEnum.RECORD_EMPTY.getMessage());

    Integer totalAmount = totalRecord
        .stream()
        .mapToInt(mr -> Integer.parseInt(mr.getDebitAmount() != null ? mr.getDebitAmount() : "0")
            - Integer.parseInt(mr.getCreditAmount() != null ? mr.getCreditAmount() : "0"))
        .sum();
    if (totalAmount < 0) {
      // throw new ResourceException("TotalAmount: " + ErrorEnum.NEGATIVE_NUM.getMessage());// TODO comment for gen testing
      totalAmount *= -1;// TODO uncomment for gen testing
    }
    return totalAmount;
  }

  public Integer getDebitTotal(List<? extends Record> debitRecord) throws ResourceException {

    if (debitRecord == null)
      throw new ResourceException(ErrorEnum.RECORD_EMPTY.getMessage());

    Integer totalAmount = debitRecord
        .stream()
        .mapToInt(mr -> Integer.parseInt(mr.getDebitAmount() != null ? mr.getDebitAmount() : "0"))
        .sum();
    if (totalAmount < 0) {
      throw new ResourceException("TotalAmount: " + ErrorEnum.NEGATIVE_NUM.getMessage());
    }
    return totalAmount;
  }

  public Integer getCreditTotal(List<? extends Record> creditRecord) throws ResourceException {

    if (creditRecord == null)
      throw new ResourceException(ErrorEnum.RECORD_EMPTY.getMessage());

    Integer totalAmount = creditRecord
        .stream()
        .mapToInt(mr -> Integer.parseInt(mr.getCreditAmount() != null ? mr.getCreditAmount() : "0"))
        .sum();
    if (totalAmount < 0) {
      throw new ResourceException("TotalAmount: " + ErrorEnum.NEGATIVE_NUM.getMessage());
    }
    return totalAmount;
  }

  public <T> List<T> paginateList(int copyIndex, int maxRecordsNumber,
      List<T> targetList) {
    int startIndex = copyIndex * maxRecordsNumber;
    int endIndex = Math.min(startIndex + maxRecordsNumber, targetList.size());
    if (startIndex > endIndex) {
      return Collections.emptyList();
    }
    return targetList.subList(startIndex, endIndex);
  }

}
