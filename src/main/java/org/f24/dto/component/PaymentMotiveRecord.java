package org.f24.dto.component;

public class PaymentMotiveRecord extends Record {

    private String section;

    private String tributeCode;

    private String institutionCode;

    private Boolean activeRepentance;
    private Boolean variedBuildings;
    private Boolean advancePayment;
    private Boolean balance;

    private String numberOfBuildings;

    private String month;

    private String deduction;

    private String reportingYear;

    private String debitAmount;

    private String creditAmount;

    public PaymentMotiveRecord() {
    }

    /**
     * Constructs record for Motive for Payment Section (Motivo del Pergamento)
     *
     * @param section           section (sezione)
     * @param tributeCode       tribute code (cod. tributo)
     * @param institutionCode   institution code (codice ente )
     * @param activeRepentance  active repentance (ravvedimento)
     * @param variedBuildings   buildings (immobiliare variati)
     * @param advancePayment    advance payment (acconto)
     * @param numberOfBuildings number of buildings (numero immobili)
     * @param balance           balance (saldo)
     * @param month             month (rateazione/mese)
     * @param deduction         deduction (detrazione)
     * @param reportingYear     reporting year (anno di riferimento)
     * @param debitAmount       debit amounts paid (importi a debito versati)
     * @param creditAmount      credit amounts offset (importi a credito compensati)
     */
    public PaymentMotiveRecord(String section, String tributeCode, String institutionCode, Boolean activeRepentance, Boolean variedBuildings, Boolean advancePayment, Boolean balance, String numberOfBuildings, String month, String deduction, String reportingYear, String debitAmount, String creditAmount) {
        this.section = section;
        this.tributeCode = tributeCode;
        this.institutionCode = institutionCode;
        this.activeRepentance = activeRepentance;
        this.variedBuildings = variedBuildings;
        this.advancePayment = advancePayment;
        this.balance = balance;
        this.numberOfBuildings = numberOfBuildings;
        this.month = month;
        this.deduction = deduction;
        this.reportingYear = reportingYear;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTributeCode() {
        return tributeCode;
    }

    public void setTributeCode(String tributeCode) {
        this.tributeCode = tributeCode;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
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

    public Boolean getBalance() {
        return balance;
    }

    public void setBalance(Boolean balance) {
        this.balance = balance;
    }

    public String getNumberOfBuildings() {
        return numberOfBuildings;
    }

    public void setNumberOfBuildings(String numberOfBuildings) {
        this.numberOfBuildings = numberOfBuildings;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDeduction() {
        return deduction;
    }

    public void setDeduction(String deduction) {
        this.deduction = deduction;
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
