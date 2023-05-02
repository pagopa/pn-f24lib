package org.f24.dto.component;

public class PaymentReasonRecord extends Record {

    private String section;

    private String tributeCode;

    private String institutionCode;

    private Boolean repentance;
    private Boolean changedBuildings;
    private Boolean advancePayment;
    private Boolean payment;

    private String numberOfBuildings;

    private String month;

    private String year;

    /**
     * Constructs record for Motive for Payment Section (Motivo del Pergamento)
     *
     * @param section           section (sezione)
     * @param tributeCode       tribute code (cod. tributo)
     * @param institutionCode   institution code (codice ente )
     * @param repentance  active repentance (ravvedimento)
     * @param changedBuildings   buildings (immobiliare variati)
     * @param advancePayment    advance payment (acconto)
     * @param numberOfBuildings number of buildings (numero immobili)
     * @param payment           payment (saldo)
     * @param month             month (rateazione/mese)
     * @param deduction         deduction (detrazione)
     * @param year     reporting year (anno di riferimento)
     * @param debitAmount       debit amounts paid (importi a debito versati)
     * @param creditAmount      credit amounts offset (importi a credito compensati)
     */
    public PaymentReasonRecord(String section, String tributeCode, String institutionCode, Boolean repentance, Boolean changedBuildings, Boolean advancePayment, Boolean payment, String numberOfBuildings, String month, String deduction, String year, String debitAmount, String creditAmount) {
        super(debitAmount, creditAmount, deduction);
        this.section = section;
        this.tributeCode = tributeCode;
        this.institutionCode = institutionCode;
        this.repentance = repentance;
        this.changedBuildings = changedBuildings;
        this.advancePayment = advancePayment;
        this.payment = payment;
        this.numberOfBuildings = numberOfBuildings;
        this.month = month;
        this.year = year;
    }

    public PaymentReasonRecord() {}

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

    public Boolean getPayment() {
        return payment;
    }

    public void setPayment(Boolean payment) {
        this.payment = payment;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
