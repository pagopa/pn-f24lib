package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(description = "INPS Record object")
public class InpsRecord {

    @NotBlank
    @Pattern(regexp = "^[0-9]{3,4}$", message = "Invalid location code. (codice sede)")
    private String locationCode;

    @NotBlank
    @Pattern(regexp = "^[A-Z-]{3,4}$", message = "Invalid contribution reason. (causale contributo)")
    private String contributionReason;

    @Pattern(regexp = "^[A-Za-z0-9\\s]{0,17}$",message = "Invalid INPS code. (matricola INPS)")
    private String inpsCode;

    @Valid
    private ReportingPeriod reportingPeriod;

    @Pattern(regexp = "^(0|[1-9][0-9]{0,14})(\\.\\d{2})$", message = "Invalid debit amount paid. (importo a debito)")
    private String debitAmount;

    @Pattern(regexp = "^(0|[1-9][0-9]{0,14})(\\.\\d{2})$", message = "Invalid credit amount offset. (importo a credito)")
    private String creditAmount;

    public InpsRecord() {
    }

    /**
     * Constructs INPS record for INPS Section (Sezione INPS)
     *
     * @param locationCode       location code (codice sede)
     * @param contributionReason contribution reason (causale contributo)
     * @param inpsCode           INPS number/INPS code/ company branch (matricola INPS/codice INPS/ filiale azienda)
     * @param reportingPeriod    ReportingPeriod component (periodo di riferimento)
     * @param debitAmount        debit amounts paid (importi a debito versati)
     * @param creditAmount       credit amounts offset (importi a credito compensati)
     */
    public InpsRecord(String locationCode, String contributionReason, String inpsCode, ReportingPeriod reportingPeriod, String debitAmount, String creditAmount) {
        this.locationCode = locationCode;
        this.contributionReason = contributionReason;
        this.inpsCode = inpsCode;
        this.reportingPeriod = reportingPeriod;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getContributionReason() {
        return contributionReason;
    }

    public void setContributionReason(String contributionReason) {
        this.contributionReason = contributionReason;
    }

    public String getInpsCode() {
        return inpsCode;
    }

    public void setInpsCode(String inpsCode) {
        this.inpsCode = inpsCode;
    }

    public ReportingPeriod getReportingPeriod() {
        return reportingPeriod;
    }

    public void setReportingPeriod(ReportingPeriod reportingPeriod) {
        this.reportingPeriod = reportingPeriod;
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
