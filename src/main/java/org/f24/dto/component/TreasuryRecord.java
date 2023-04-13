package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Pattern;

@Schema(description = "Treasury Record object")
public class TreasuryRecord {

    @Pattern(regexp = "^[A-Z]$", message = "Invalid type. (tipo)")
    private String type;

    @Pattern(regexp = "^[A-Z0-9]{17}$", message = "Invalid id elements. (elementi identificativi)")
    private String idElements;

    @Pattern(regexp = "^[A-Z0-9]{4}$", message = "Invalid tribute code. (codice tributo)")
    private String tributeCode;

    @Pattern(regexp = "^[1-2][0-9]{3}$", message = "Invalid reporting year. (anno di riferimento)")
    private String reportingYear;

    @Pattern(regexp = "^(0|[1-9][0-9]{0,14})(\\.\\d{2})$", message = "Invalid debit amount paid. (importo a debito)")
    private String debitAmount;

    /**
     * Constructs Treasury record for Treasury and other section (Sezione erario ed altro)
     *
     * @param type          type (tipo)
     * @param idElements    identification elements (elementi identificativi)
     * @param tributeCode   tribute code (codice)
     * @param reportingYear reporting year (anno di riferimento)
     * @param debitAmount   debit amounts paid (debit amounts paid)
     */
    public TreasuryRecord(String type, String idElements, String tributeCode, String reportingYear, String debitAmount) {
        this.type = type;
        this.idElements = idElements;
        this.tributeCode = tributeCode;
        this.reportingYear = reportingYear;
        this.debitAmount = debitAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdElements() {
        return idElements;
    }

    public void setIdElements(String idElements) {
        this.idElements = idElements;
    }

    public String getTributeCode() {
        return tributeCode;
    }

    public void setTributeCode(String tributeCode) {
        this.tributeCode = tributeCode;
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

}
