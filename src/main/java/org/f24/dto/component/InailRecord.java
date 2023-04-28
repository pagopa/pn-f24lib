package org.f24.dto.component;

public class InailRecord {

    private String officeCode;

    private String companyCode;

    private String controlCode;

    private String referenceNumber;

    private String reason;

    private String debitAmount;

    private String creditAmount;

    /**
     * Constructs INAIL Record for Other social security and insurance institutions (sezione altri enri previdenziali e assicuretivi)
     *
     * @param officeCode    location code (codice sede)
     * @param companyCode     company code (codice ditta)
     * @param controlCode     bank account
     * @param referenceNumber reference number (numero di riferimento)
     * @param reason          reason (causale)
     * @param debitAmount     debit amounts paid (importi a debito versati)
     * @param creditAmount    credit amounts offset (importi a credito compensati)
     */
    public InailRecord(String officeCode, String companyCode, String controlCode, String referenceNumber, String reason, String debitAmount, String creditAmount) {
        this.officeCode = officeCode;
        this.companyCode = companyCode;
        this.controlCode = controlCode;
        this.referenceNumber = referenceNumber;
        this.reason = reason;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
