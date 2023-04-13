package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Pattern;

@Schema(description = "Excise Tax object")
public class ExciseTax {

    @Pattern(regexp = "^[A-Z]{2}$", message = "Invalid institution. (ente)")
    private String institution;

    @Pattern(regexp = "^[A-Z]{2}$", message = "Invalid province. (provincia)")
    private String province;

    @Pattern(regexp = "^[A-Z0-9]{17}$", message = "Invalid id elements. (elementi identificativi)")
    private String idCode;

    @Pattern(regexp = "^[0-9A-Z]{4}$", message = "Invalid tribute code. (codice tributo)")
    private String tributeCode;

    @Pattern(regexp = "^[A-Z0-9]{0,4}$", message = "Invalid installment. (rateazione)")
    private String installment;

    @Pattern(regexp = "^[0-1][0-9]$", message = "Invalid month. (mese)")
    private String month;

    @Pattern(regexp = "^[1-2][0-9]{3}$", message = "Invalid reporting year. (anno di riferimento)")
    private String reportingYear;

    @Pattern(regexp = "^(0|[1-9][0-9]{0,14})(\\.\\d{2})$", message = "Invalid debit amount paid. (importo a debito)")
    private String debitAmount;

    /**
     * Constructs excise tax for excise section (sezione Accise)
     *
     * @param institution   institution (ente)
     * @param province      province (prov.)
     * @param idCode        ID Code (codice identificativo )
     * @param tributeCode   tribute code (codice tributo)
     * @param installment   installment (rateazione)
     * @param month         month (mese rif.)
     * @param reportingYear reporting year (anno di riferimento)
     * @param debitAmount   debit amounts paid (debit amounts paid)
     */
    public ExciseTax(String institution, String province, String idCode, String tributeCode, String installment, String month, String reportingYear, String debitAmount) {
        this.institution = institution;
        this.province = province;
        this.idCode = idCode;
        this.tributeCode = tributeCode;
        this.installment = installment;
        this.month = month;
        this.reportingYear = reportingYear;
        this.debitAmount = debitAmount;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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
