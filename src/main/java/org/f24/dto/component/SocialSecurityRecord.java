package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(description = "Social Security Record object")
public class SocialSecurityRecord {

    @Pattern(regexp = "^[0-9]{4}$", message = "Invalid institution code. (codice ente)")
    private String institutionCode;

    @NotBlank
    @Pattern(regexp = "^[0-9A-Z]{5}$", message = "Invalid location code. (codice sede)")
    private String locationCode;

    @NotBlank
    @Pattern(regexp = "^[0-9A-Z]{3,4}$", message = "Invalid contribution reason. (causale contributo)")
    private String contributionReason;

    @NotBlank
    @Pattern(regexp = "^[0-9]{9}$", message = "Invalid position code. (codice posizione)")
    private String positionCode;

    @Valid
    private ReportingPeriod period;

    @Pattern(regexp = "^(0|[1-9][0-9]{0,14})(\\.\\d{2})$", message = "Invalid debit amount paid. (importo a debito)")
    private String debitAmount;

    @Pattern(regexp = "^(0|[1-9][0-9]{0,14})(\\.\\d{2})$", message = "Invalid credit amount offset. (importo a credito)")
    private String creditAmount;

    /**
     * Constructs Social Security Record for Other social security and insurance institutions (sezione altri enri previdenziali e assicuretivi)
     *
     * @param institutionCode    institution code (codice ente)
     * @param locationCode       location code (codice sede)
     * @param contributionReason contribution reason (causale contributo)
     * @param positionCode       position code (codice posizione)
     * @param period             ReportingPeriod component (periodo di riferimento)
     * @param debitAmount        debit amounts paid (importi a debito versati)
     * @param creditAmount       credit amounts offset (importi a credito compensati)
     */
    public SocialSecurityRecord(String institutionCode, String locationCode, String contributionReason, String positionCode, ReportingPeriod period, String debitAmount, String creditAmount) {
        this.institutionCode = institutionCode;
        this.locationCode = locationCode;
        this.contributionReason = contributionReason;
        this.positionCode = positionCode;
        this.period = period;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
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

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public ReportingPeriod getPeriod() {
        return period;
    }

    public void setPeriod(ReportingPeriod period) {
        this.period = period;
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
