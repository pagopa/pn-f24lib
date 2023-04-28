package org.f24.dto.component;

public class ImuRecord {

    private String municipalityCode;

    private Boolean repentance;
    private Boolean changedBuildings;
    private Boolean advancePayment;
    private Boolean payment;

    private String numberOfBuildings;

    private String tributeCode;

    private String installment;

    private String year;

    private String debitAmount;

    private String creditAmount;

    /**
     * Constructs IMU record for IMU Section (IMU e altri tributi locali)
     *
     * @param municipalityCode  entity code/municipality code (codice ente/codice comune)
     * @param repentance  active repentance (ravvedimento)
     * @param changedBuildings   buildings (immobiliare variati)
     * @param advancePayment    advance payment (acconto)
     * @param numberOfBuildings number of buildings (numero immobili)
     * @param payment           payment (saldo)
     * @param tributeCode       tribute code (codice tributo)
     * @param installment       installment/month ref. (rateazione/ mese rif.)
     * @param year     reporting year (anno di riferimento)
     * @param debitAmount       debit amounts paid (importi a debito versati )
     * @param creditAmount      credit amounts offset (importi a credito compensati )
     */
    public ImuRecord(String municipalityCode, Boolean repentance, Boolean changedBuildings, Boolean advancePayment, Boolean payment, String numberOfBuildings, String tributeCode, String installment, String year, String debitAmount, String creditAmount) {
        this.municipalityCode = municipalityCode;
        this.repentance = repentance;
        this.changedBuildings = changedBuildings;
        this.advancePayment = advancePayment;
        this.payment = payment;
        this.numberOfBuildings = numberOfBuildings;
        this.tributeCode = tributeCode;
        this.installment = installment;
        this.year = year;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

    public String getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(String municipalityCode) {
        this.municipalityCode = municipalityCode;
    }

    public Boolean getRepentance() {
        return repentance;
    }

    public void setRepentance(Boolean repentance) {
        this.repentance = repentance;
    }

    public Boolean getChangedBuildings() {
        return changedBuildings;
    }

    public void setChangedBuildings(Boolean changedBuildings) {
        this.changedBuildings = changedBuildings;
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

    public Boolean getPayment() {
        return payment;
    }

    public void setPayment(Boolean payment) {
        this.payment = payment;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
