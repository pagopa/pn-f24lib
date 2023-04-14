package org.f24.dto.component;

public class ImuRecord {

    private String municipalityCode;

    private Boolean activeRepentance;
    private Boolean variedBuildings;
    private Boolean advancePayment;
    private Boolean balance;

    private String numberOfBuildings;

    private String tributeCode;

    private String installment;

    private String reportingYear;

    private String debitAmount;

    private String creditAmount;

    /**
     * Constructs IMU record for IMU Section (IMU e altri tributi locali)
     *
     * @param municipalityCode  entity code/municipality code (codice ente/codice comune)
     * @param activeRepentance  active repentance (ravvedimento)
     * @param variedBuildings   buildings (immobiliare variati)
     * @param advancePayment    advance payment (acconto)
     * @param numberOfBuildings number of buildings (numero immobili)
     * @param balance           balance (saldo)
     * @param tributeCode       tribute code (codice tributo)
     * @param installment       installment/month ref. (rateazione/ mese rif.)
     * @param reportingYear     reporting year (anno di riferimento)
     * @param debitAmount       debit amounts paid (importi a debito versati )
     * @param creditAmount      credit amounts offset (importi a credito compensati )
     */
    public ImuRecord(String municipalityCode, Boolean activeRepentance, Boolean variedBuildings, Boolean advancePayment, Boolean balance, String numberOfBuildings, String tributeCode, String installment, String reportingYear, String debitAmount, String creditAmount) {
        this.municipalityCode = municipalityCode;
        this.activeRepentance = activeRepentance;
        this.variedBuildings = variedBuildings;
        this.advancePayment = advancePayment;
        this.balance = balance;
        this.numberOfBuildings = numberOfBuildings;
        this.tributeCode = tributeCode;
        this.installment = installment;
        this.reportingYear = reportingYear;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

    public String getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(String municipalityCode) {
        this.municipalityCode = municipalityCode;
    }

    public Boolean getActiveRepentance() {
        return activeRepentance;
    }

    public void setActiveRepentance(Boolean activeRepentance) {
        this.activeRepentance = activeRepentance;
    }

    public Boolean getVariedBuildings() {
        return variedBuildings;
    }

    public void setVariedBuildings(Boolean variedBuildings) {
        this.variedBuildings = variedBuildings;
    }

    public Boolean getAdvancePayment() {
        return advancePayment;
    }

    public void setAdvancePayment(Boolean advancePayment) {
        this.advancePayment = advancePayment;
    }

    public String getNumberOfBuildings() {
        return numberOfBuildings;
    }

    public void setNumberOfBuildings(String numberOfBuildings) {
        this.numberOfBuildings = numberOfBuildings;
    }

    public Boolean getBalance() {
        return balance;
    }

    public void setBalance(Boolean balance) {
        this.balance = balance;
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
