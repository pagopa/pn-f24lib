package org.f24.dto.component;

public class RegionRecord extends Record {

    private String regionCode;

    private String tributeCode;

    private String installment;

    private String year;

    /**
     * Constructs region record for region section (sezione regioni)
     *
     * @param regionCode    region code (codice regione)
     * @param tributeCode   tribute code (codice tributo)
     * @param installment   installment/month ref. (rateazione/ mese rif.)
     * @param year reporting year (anno di riferimento)
     * @param debitAmount   debit amounts paid (importi a debito versati)
     * @param creditAmount  credit amounts offset (importi a credito compensati)
     */
    public RegionRecord(String regionCode, String tributeCode, String installment, String year, String debitAmount, String creditAmount) {
        super(debitAmount, creditAmount, "");
        this.regionCode = regionCode;
        this.tributeCode = tributeCode;
        this.installment = installment;
        this.year = year;
    }

    public RegionRecord() {}

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
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

}
