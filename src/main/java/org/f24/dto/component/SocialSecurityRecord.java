package org.f24.dto.component;

public class SocialSecurityRecord extends Record {

    private String institutionCode;

    private String officeCode;

    private String contributionReason;

    private String positionCode;

    private Period period;

    private String debitAmount;

    private String creditAmount;

    public SocialSecurityRecord() {}

    /**
     * Constructs Social Security Record for Other social security and insurance institutions (sezione altri enri previdenziali e assicuretivi)
     *
     * @param institutionCode    institution code (codice ente)
     * @param officeCode       location code (codice sede)
     * @param contributionReason contribution reason (causale contributo)
     * @param positionCode       position code (codice posizione)
     * @param period             ReportingPeriod component (periodo di riferimento)
     * @param debitAmount        debit amounts paid (importi a debito versati)
     * @param creditAmount       credit amounts offset (importi a credito compensati)
     */
    public SocialSecurityRecord(String institutionCode, String officeCode, String contributionReason, String positionCode, Period period, String debitAmount, String creditAmount) {
        this.institutionCode = institutionCode;
        this.officeCode = officeCode;
        this.contributionReason = contributionReason;
        this.positionCode = positionCode;
        this.period = period;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
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

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
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
