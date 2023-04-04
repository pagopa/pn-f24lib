package org.f24.dto.component;

public class RegionRecord {

    private String regionCode;
    private String tributeCode;
    private String installment;
    private String reportingYear;
    private String debitAmount;
    private String creditAmount;

    /**
     * Constructs region record for region section (sezione regioni)
     *
     * @param regionCode    region code (codice regione)
     * @param tributeCode   tribute code (codice tributo)
     * @param installment   installment/month ref. (rateazione/ mese rif.)
     * @param reportingYear reporting year (anno di riferimento)
     * @param debitAmount   debit amounts paid (importi a debito versati)
     * @param creditAmount  credit amounts offset (importi a credito compensati)
     */
    public RegionRecord(String regionCode, String tributeCode, String installment, String reportingYear, String debitAmount, String creditAmount) {
        this.regionCode = regionCode;
        this.tributeCode = tributeCode;
        this.installment = installment;
        this.reportingYear = reportingYear;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

}
