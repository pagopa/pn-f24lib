package org.f24.dto.component;

public class RegionRecord extends Record {

    private String regionCode;

    private String taxTypeCode;

    private String installment;

    private String year;

    /**
     * Constructs region record for region section (sezione regioni)
     *
     * @param regionCode   region code (codice regione)
     * @param taxTypeCode  tribute code (codice tributo)
     * @param installment  installment/month ref. (rateazione/ mese rif.)
     * @param year         reporting year (anno di riferimento)
     * @param debitAmount  debit amounts paid (importi a debito versati)
     * @param creditAmount credit amounts offset (importi a credito compensati)
     */
    public RegionRecord(String regionCode, String taxTypeCode, String installment, String year, String debitAmount, String creditAmount) {
        super(debitAmount, creditAmount, "");
        this.regionCode = regionCode;
        this.taxTypeCode = taxTypeCode;
        this.installment = installment;
        this.year = year;
    }

    public RegionRecord() {
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
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
