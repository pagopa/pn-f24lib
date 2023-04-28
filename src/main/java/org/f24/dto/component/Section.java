package org.f24.dto.component;

import java.util.List;

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

}
