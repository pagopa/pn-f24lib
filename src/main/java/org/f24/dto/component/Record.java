package org.f24.dto.component;

public class Record {

  private String debitAmount;

  private String creditAmount;

  private String deduction;

  public Record() {}

  /**
   * Constructs region record for region section (sezione regioni)
   *
   * @param debitAmount  debit amounts paid (importi a debito versati)
   * @param creditAmount credit amounts offset (importi a credito compensati)
   * @param deduction deduction amounts offset (importi a deduction compensati)
   */
  public Record(String debitAmount, String creditAmount, String deduction) {
    this.debitAmount = debitAmount;
    this.creditAmount = creditAmount;
    this.deduction = deduction;
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

  public String getDeduction() {
    return deduction;
  }

  public void setDeduction(String deduction) {
    this.deduction = deduction;
  }

}
