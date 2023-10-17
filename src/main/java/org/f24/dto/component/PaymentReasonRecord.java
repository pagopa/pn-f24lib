package org.f24.dto.component;

public class PaymentReasonRecord extends Record {

    private String section;

    private String taxTypeCode;

    private String municipalityCode;

    private Boolean reconsideration;
    private Boolean propertiesChanges;
    private Boolean advancePayment;
    private Boolean fullPayment;

    private String numberOfProperties;

    private String month;

    private String year;

    /**
     * Constructs record for Motive for Payment Section (Motivo del Pergamento)
     *
     * @param section            section (sezione)
     * @param taxTypeCode        tax type code (cod. tributo)
     * @param municipalityCode   municipality code (codice ente )
     * @param reconsideration    reconsideration (ravvedimento)
     * @param propertiesChanges  properties changes (immobiliare variati)
     * @param advancePayment     advance payment (acconto)
     * @param fullPayment        fullPayment (saldo)
     * @param numberOfProperties number of buildings (numero immobili)
     * @param month              month (rateazione/mese)
     * @param deduction          deduction (detrazione)
     * @param year               reporting year (anno di riferimento)
     * @param debitAmount        debit amounts paid (importi a debito versati)
     * @param creditAmount       credit amounts offset (importi a credito compensati)
     */
    public PaymentReasonRecord(String section, String taxTypeCode, String municipalityCode, Boolean reconsideration, Boolean propertiesChanges, Boolean advancePayment, Boolean fullPayment, String numberOfProperties, String month, String deduction, String year, String debitAmount, String creditAmount) {
        super(debitAmount, creditAmount, deduction);
        this.section = section;
        this.taxTypeCode = taxTypeCode;
        this.municipalityCode = municipalityCode;
        this.reconsideration = reconsideration;
        this.propertiesChanges = propertiesChanges;
        this.advancePayment = advancePayment;
        this.fullPayment = fullPayment;
        this.numberOfProperties = numberOfProperties;
        this.month = month;
        this.year = year;
    }

    public PaymentReasonRecord() {
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTaxTypeCode() {
        return taxTypeCode;
    }

    public void setTaxTypeCode(String taxTypeCode) {
        this.taxTypeCode = taxTypeCode;
    }

    public String getMunicipalityCode() { return municipalityCode; }

    public void setMunicipalityCode(String municipalityCode) { this.municipalityCode = municipalityCode; }

    public Boolean getReconsideration() {
        return reconsideration;
    }

    public void setReconsideration(Boolean reconsideration) {
        this.reconsideration = reconsideration;
    }

    public Boolean getPropertiesChanges() {
        return propertiesChanges;
    }

    public void setPropertiesChanges(Boolean propertiesChanges) {
        this.propertiesChanges = propertiesChanges;
    }

    public Boolean getAdvancePayment() {
        return advancePayment;
    }

    public void setAdvancePayment(Boolean advancePayment) {
        this.advancePayment = advancePayment;
    }

    public Boolean getFullPayment() {
        return fullPayment;
    }

    public void setFullPayment(Boolean fullPayment) {
        this.fullPayment = fullPayment;
    }

    public String getNumberOfProperties() {
        return numberOfProperties;
    }

    public void setNumberOfProperties(String numberOfProperties) {
        this.numberOfProperties = numberOfProperties;
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
