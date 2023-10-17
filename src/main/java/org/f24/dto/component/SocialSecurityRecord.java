package org.f24.dto.component;

public class SocialSecurityRecord extends Record {

    private String municipalityCode;

    private String officeCode;

    private String contributionReason;

    private String positionCode;

    private Period period;

    /**
     * Constructs Social Security Record for Other social security and insurance institutions (sezione altri enri previdenziali e assicuretivi)
     *
     * @param municipalityCode   municipality code (codice ente)
     * @param officeCode         location code (codice sede)
     * @param contributionReason contribution reason (causale contributo)
     * @param positionCode       position code (codice posizione)
     * @param period             Period component (periodo di riferimento)
     * @param debitAmount        debit amounts paid (importi a debito versati)
     * @param creditAmount       credit amounts offset (importi a credito compensati)
     */
    public SocialSecurityRecord(String municipalityCode, String officeCode, String contributionReason, String positionCode, Period period, String debitAmount, String creditAmount) {
        super(debitAmount, creditAmount, "");
        this.municipalityCode = municipalityCode;
        this.officeCode = officeCode;
        this.contributionReason = contributionReason;
        this.positionCode = positionCode;
        this.period = period;
    }

    public SocialSecurityRecord() {
    }

    public String getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(String municipalityCode) {
        this.municipalityCode = municipalityCode;
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

}
