package org.f24.dto.component;

public class Record {

  private String debitAmount;

  private String creditAmount;

  /**
   * Constructs region record for region section (sezione regioni)
   *
   * @param debitAmount  debit amounts paid (importi a debito versati)
   * @param creditAmount credit amounts offset (importi a credito compensati)
   */

  public Record() {

  }

  public Record(String debitAmount, String creditAmount) {
    this.debitAmount = debitAmount;
    this.creditAmount = creditAmount;
  }

  public String getDebitAmount() {
    return debitAmount;
  }

  public void setDebitAmount(String debitAmount) {
    this.debitAmount = debitAmount;
  }

  public String getCreditAmount() {
    return creditAmount;
  }

  public void setCreditAmount(String creditAmount) {
    this.creditAmount = creditAmount;
  }
}
