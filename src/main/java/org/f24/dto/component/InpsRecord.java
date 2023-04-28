package org.f24.dto.component;

public class InpsRecord {

    private String officeCode;

    private String contributionReason;

    private String inpsCode;

    private Period period;

    private String debitAmount;

    private String creditAmount;

    public InpsRecord() {
    }

    /**
     * Constructs INPS record for INPS Section (Sezione INPS)
     *
     * @param officeCode       location code (codice sede)
     * @param contributionReason contribution reason (causale contributo)
     * @param inpsCode           INPS number/INPS code/ company branch (matricola INPS/codice INPS/ filiale azienda)
     * @param period    ReportingPeriod component (periodo di riferimento)
     * @param debitAmount        debit amounts paid (importi a debito versati)
     * @param creditAmount       credit amounts offset (importi a credito compensati)
     */
    public InpsRecord(String officeCode, String contributionReason, String inpsCode, Period period, String debitAmount, String creditAmount) {
        this.officeCode = officeCode;
        this.contributionReason = contributionReason;
        this.inpsCode = inpsCode;
        this.period = period;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getContributionReason() {
        return contributionReason;
    }

    public void setContributionReason(String contributionReason) {
        this.contributionReason = contributionReason;
    }

    public String getInpsCode() {
        return inpsCode;
    }

    public void setInpsCode(String inpsCode) {
        this.inpsCode = inpsCode;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
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
