package org.f24.dto.component;

public class InpsRecord {

    private String locationCode;

    private String contributionReason;

    private String inpsCode;

    private ReportingPeriod reportingPeriod;

    private String debitAmount;

    private String creditAmount;

    public InpsRecord() {
    }

    /**
     * Constructs INPS record for INPS Section (Sezione INPS)
     *
     * @param locationCode       location code (codice sede)
     * @param contributionReason contribution reason (causale contributo)
     * @param inpsCode           INPS number/INPS code/ company branch (matricola INPS/codice INPS/ filiale azienda)
     * @param reportingPeriod    ReportingPeriod component (periodo di riferimento)
     * @param debitAmount        debit amounts paid (importi a debito versati)
     * @param creditAmount       credit amounts offset (importi a credito compensati)
     */
    public InpsRecord(String locationCode, String contributionReason, String inpsCode, ReportingPeriod reportingPeriod, String debitAmount, String creditAmount) {
        this.locationCode = locationCode;
        this.contributionReason = contributionReason;
        this.inpsCode = inpsCode;
        this.reportingPeriod = reportingPeriod;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
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

    public ReportingPeriod getReportingPeriod() {
        return reportingPeriod;
    }

    public void setReportingPeriod(ReportingPeriod reportingPeriod) {
        this.reportingPeriod = reportingPeriod;
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
