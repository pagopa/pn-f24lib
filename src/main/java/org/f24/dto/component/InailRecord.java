package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(description = "INAIL Record object")
public class InailRecord {

    @Pattern(regexp = "^[0-9]{5}$", message = "Invalid location code. (codice sede)")
    private String locationCode;

    @NotBlank
    @Pattern(regexp = "^[0-9]{8}$", message = "Invalid company code. (codice ditta)")
    private String companyCode;

    @NotBlank
    @Pattern(regexp = "^[0-9]{2}$", message = "Invalid bank account code. (codice controllo (c.c.) del Codice Ditta)")
    private String bankAccount;

    @NotBlank
    @Pattern(regexp = "^[0-9]{6}$", message = "Invalid reference number. (numero di riferimento)")
    private String referenceNumber;

    @Pattern(regexp = "^[A-Z0-9]$", message = "Invalid reason. (causale contributo)")
    private String reason;

    @Pattern(regexp = "^(0|[1-9][0-9]{0,14})(\\.\\d{2})$", message = "Invalid debit amount paid. (importo a debito)")
    private String debitAmount;

    @Pattern(regexp = "^(0|[1-9][0-9]{0,14})(\\.\\d{2})$", message = "Invalid credit amount offset. (importo a credito)")
    private String creditAmount;

    /**
     * Constructs INAIL Record for Other social security and insurance institutions (sezione altri enri previdenziali e assicuretivi)
     *
     * @param locationCode    location code (codice sede)
     * @param companyCode     company code (codice ditta)
     * @param bankAccount     bank account
     * @param referenceNumber reference number (numero di riferimento)
     * @param reason          reason (causale)
     * @param debitAmount     debit amounts paid (importi a debito versati)
     * @param creditAmount    credit amounts offset (importi a credito compensati)
     */
    public InailRecord(String locationCode, String companyCode, String bankAccount, String referenceNumber, String reason, String debitAmount, String creditAmount) {
        this.locationCode = locationCode;
        this.companyCode = companyCode;
        this.bankAccount = bankAccount;
        this.referenceNumber = referenceNumber;
        this.reason = reason;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
