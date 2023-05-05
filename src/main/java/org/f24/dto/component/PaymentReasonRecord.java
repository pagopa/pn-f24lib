package org.f24.dto.component;

public class PaymentReasonRecord extends Record {

    private String section;

    private String taxTypeCode;

    private String institutionCode;

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
     * @param taxTypeCode        tribute code (cod. tributo)
     * @param institutionCode    institution code (codice ente )
     * @param reconsideration    active reconsideration (ravvedimento)
     * @param propertiesChanges  buildings (immobiliare variati)
     * @param advancePayment     advance payment (acconto)
     * @param numberOfProperties number of buildings (numero immobili)
     * @param fullPayment        fullPayment (saldo)
     * @param month              month (rateazione/mese)
     * @param deduction          deduction (detrazione)
     * @param year               reporting year (anno di riferimento)
     * @param debitAmount        debit amounts paid (importi a debito versati)
     * @param creditAmount       credit amounts offset (importi a credito compensati)
     */
    public PaymentReasonRecord(String section, String taxTypeCode, String institutionCode, Boolean reconsideration, Boolean propertiesChanges, Boolean advancePayment, Boolean fullPayment, String numberOfProperties, String month, String deduction, String year, String debitAmount, String creditAmount) {
        super(debitAmount, creditAmount, deduction);
        this.section = section;
        this.taxTypeCode = taxTypeCode;
        this.institutionCode = institutionCode;
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

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

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
