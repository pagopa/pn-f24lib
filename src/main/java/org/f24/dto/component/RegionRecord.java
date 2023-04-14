package org.f24.dto.component;

public class RegionRecord {

    private String regionCode;

    private String tributeCode;

    private String installment;

    private String reportingYear;

    private String debitAmount;

    private String creditAmount;

    /**
     * Constructs region record for region section (sezione regioni)
     *
     * @param regionCode    region code (codice regione)
     * @param tributeCode   tribute code (codice tributo)
     * @param installment   installment/month ref. (rateazione/ mese rif.)
     * @param reportingYear reporting year (anno di riferimento)
     * @param debitAmount   debit amounts paid (importi a debito versati)
     * @param creditAmount  credit amounts offset (importi a credito compensati)
     */
    public RegionRecord(String regionCode, String tributeCode, String installment, String reportingYear, String debitAmount, String creditAmount) {
        this.regionCode = regionCode;
        this.tributeCode = tributeCode;
        this.installment = installment;
        this.reportingYear = reportingYear;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getTributeCode() {
        return tributeCode;
    }

    public void setTributeCode(String tributeCode) {
        this.tributeCode = tributeCode;
    }

    public String getInstallment() {
        return installment;
    }

    public void setInstallment(String installment) {
        this.installment = installment;
    }

    public String getReportingYear() {
        return reportingYear;
    }

    public void setReportingYear(String reportingYear) {
        this.reportingYear = reportingYear;
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
