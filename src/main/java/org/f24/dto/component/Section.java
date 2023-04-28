package org.f24.dto.component;

import java.util.List;

import org.f24.exception.ResourceException;
import org.f24.exception.ErrorEnum;

public class Section {
  private List<? extends Record> recordList;

  public Section() {
  }

  public List<? extends Record> getRecordList() {
    return recordList;
  }

  public void setRecordList(List<Record> recordList) {
    this.recordList = recordList;
  }

  public Double getTotalAmount(List<? extends Record> record) throws ResourceException {

    if (record == null)
      throw new ResourceException("Record can`t be empty");

    Double totalAmount = record
        .stream()
        .mapToDouble(mr -> Double.parseDouble(mr.getDebitAmount() != null ? mr.getDebitAmount() : "0")
            - Double.parseDouble(mr.getCreditAmount() != null ? mr.getCreditAmount() : "0"))
        .sum();
    if (totalAmount < 0) {
      throw new ResourceException("TotalAmount: " + ErrorEnum.NEGATIVE_NUM.getMessage());//TODO remove for gen testing
    }
    return totalAmount;
  }

  public Double getDebitTotal(List<? extends Record> record) throws ResourceException {

    if (record == null)
      throw new ResourceException("Record can`t be empty");

    Double totalAmount = record
        .stream()
        .mapToDouble(mr -> Double.parseDouble(mr.getDebitAmount() != null ? mr.getDebitAmount() : "0"))
        .sum();
    if (totalAmount < 0) {
      throw new ResourceException("TotalAmount: " + ErrorEnum.NEGATIVE_NUM.getMessage());
    }
    return totalAmount;
  }

  public Double getCreditTotal(List<? extends Record> record) throws ResourceException {

    if (record == null)
      throw new ResourceException("Record can`t be empty");

    Double totalAmount = record
        .stream()
        .mapToDouble(mr -> Double.parseDouble(mr.getCreditAmount() != null ? mr.getCreditAmount() : "0"))
        .sum();
    if (totalAmount < 0) {
      throw new ResourceException("TotalAmount: " + ErrorEnum.NEGATIVE_NUM.getMessage());
    }
    return totalAmount;
  }

}
