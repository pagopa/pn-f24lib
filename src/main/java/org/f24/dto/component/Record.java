package org.f24.dto.component;

public class Record {

  private String debitAmount;

  private String creditAmount;

  private String deductionAmount;

  /**
   * Constructs region record for region section (sezione regioni)
   *
   * @param debitAmount  debit amounts paid (importi a debito versati)
   * @param creditAmount credit amounts offset (importi a credito compensati)
   * @param deductionAmount deduction amounts offset (importi a deduction compensati)
   */

  public Record() {}

  public Record(String debitAmount, String creditAmount, String deductionAmount) {
    this.debitAmount = debitAmount;
    this.creditAmount = creditAmount;
    this.deductionAmount = deductionAmount;
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

  public String getDeuctionAmount() {
    return deductionAmount;
  }

  public void setCreditAmount(String creditAmount) {
    this.creditAmount = creditAmount;
  }
}
