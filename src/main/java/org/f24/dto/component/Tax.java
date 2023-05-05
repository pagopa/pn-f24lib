package org.f24.dto.component;

public class Tax extends Record {

    private String taxTypeCode;

    private String installment;

    private String year;

    /**
     * Constructs  taxes : direct taxes - VAT IMPOSTE (imposte dirette - IVA),
     * withhholding taxes (ritenute alla fonte) ant other taxes (altri tributi ed interessi)
     *
     * @param taxTypeCode   tribute code (codice tributo)
     * @param installment   installment/region/ prov./month ref (rateazione/regione/ prov./mese rif.)
     * @param year reporting year (anno di riferimento)
     * @param debitAmount   debit amounts paid (importi a debito versati)
     * @param creditAmount  credit amounts offset (importi a credito)
     */
    public Tax(String taxTypeCode, String installment, String year, String debitAmount, String creditAmount) {
        super(debitAmount, creditAmount, "");
        this.taxTypeCode = taxTypeCode;
        this.installment = installment;
        this.year = year;
    }

    public Tax() { }

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
