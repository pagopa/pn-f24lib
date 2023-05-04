package org.f24.dto.component;

public class LocalTaxRecord extends Record {

    private String municipalityCode;

    private Boolean reconsideration;
    private Boolean propertiesChanges;
    private Boolean advancePayment;
    private Boolean fullPayment;

    private String numberOfProperties;

    private String taxTypeCode;

    private String installment;

    private String year;

    /**
     * Constructs LocalTaxPayer record for IMU Section (Old IMU) (IMU e altri tributi locali)
     *
     * @param municipalityCode  entity code/municipality code (codice ente/codice comune)
     * @param reconsideration  active reconsideration (ravvedimento)
     * @param propertiesChanges   buildings (immobiliare variati)
     * @param advancePayment    advance fullPayment (acconto)
     * @param numberOfProperties number of buildings (numero immobili)
     * @param fullPayment           fullPayment (saldo)
     * @param taxTypeCode       tribute code (codice tributo)
     * @param installment       installment/month ref. (rateazione/ mese rif.)
     * @param year              year (anno di riferimento)
     * @param debitAmount       debit amounts paid (importi a debito versati )
     * @param creditAmount      credit amounts offset (importi a credito compensati )
     */
    public LocalTaxRecord(String municipalityCode, Boolean reconsideration, Boolean propertiesChanges, Boolean advancePayment, Boolean fullPayment, String numberOfProperties, String taxTypeCode, String installment, String year, String debitAmount, String creditAmount) {
        super(debitAmount, creditAmount, "");
        this.municipalityCode = municipalityCode;
        this.reconsideration = reconsideration;
        this.propertiesChanges = propertiesChanges;
        this.advancePayment = advancePayment;
        this.fullPayment = fullPayment;
        this.numberOfProperties = numberOfProperties;
        this.taxTypeCode = taxTypeCode;
        this.installment = installment;
        this.year = year;
    }

    public LocalTaxRecord() {}

    public String getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(String municipalityCode) {
        this.municipalityCode = municipalityCode;
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

    public String getNumberOfProperties() {
        return numberOfProperties;
    }

    public void setNumberOfProperties(String numberOfProperties) {
        this.numberOfProperties = numberOfProperties;
    }

    public Boolean getFullPayment() {
        return fullPayment;
    }

    public void setFullPayment(Boolean fullPayment) {
        this.fullPayment = fullPayment;
    }

    public String getTaxTypeCode() {
        return taxTypeCode;
    }

    public void setTaxTypeCode(String taxTypeCode) {
        this.taxTypeCode = taxTypeCode;
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

}
