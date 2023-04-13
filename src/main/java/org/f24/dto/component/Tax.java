package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Pattern;

@Schema(description = "Tax object")
public class Tax {

    @Pattern(regexp = "^[A-Z0-9]{4}$", message = "Invalid tribute code. (codice tributo)")
    private String tributeCode;

    @Pattern(regexp = "^[A-Z0-9]{0,4}$", message = "Invalid installment. (rateazione/regione/provincia)")
    private String installment;

    @Pattern(regexp = "^[1-2][0-9]{3}$", message = "Invalid reporting year. (anno di riferimento)")
    private String reportingYear;

    @Pattern(regexp = "^(0|[1-9][0-9]{0,14})(\\.\\d{2})$", message = "Invalid debit amount paid. (importo a debito)")
    private String debitAmount;

    @Pattern(regexp = "^(0|[1-9][0-9]{0,14})(\\.\\d{2})$", message = "Invalid credit amount offset. (importo a credito)")
    private String creditAmount;

    /**
     * Constructs  taxes : direct taxes - VAT IMPOSTE (imposte dirette - IVA),
     * withhholding taxes (ritenute alla fonte) ant other taxes (altri tributi ed interessi)
     *
     * @param tributeCode   tribute code (codice tributo)
     * @param installment   installment/region/ prov./month ref (rateazione/regione/ prov./mese rif.)
     * @param reportingYear reporting year (anno di riferimento)
     * @param debitAmount   debit amounts paid (importi a debito versati)
     * @param creditAmount  credit amounts offset (importi a credito)
     */
    public Tax(String tributeCode, String installment, String reportingYear, String debitAmount, String creditAmount) {
        this.tributeCode = tributeCode;
        this.installment = installment;
        this.reportingYear = reportingYear;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
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
