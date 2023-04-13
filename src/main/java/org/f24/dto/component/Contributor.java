package org.f24.dto.component;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

public class Contributor {

    @Pattern(regexp = "^[A-Z]{6}[0-9]{2}[ABCDEHLMPRST]{1}[0-9]{2}[A-Z]{1}[0-9]{3}[A-Z]{1}$", message = "Invalid tax code. (codice fiscale)")
    private String taxCode;

    private boolean ifCalendarYear;

    @Pattern(regexp = "^\\d{11}$", message = "Invalid act code. (codice atto)")
    private String actCode;

    @Pattern(regexp = "^[A-Z0-9]{3}$", message = "Invalid office code. (codice ufficio)")
    private String officeCode;

    @Valid
    private PersonalData personalData;

    @Valid
    private TaxResidence taxResidence;

    @Pattern(regexp = "^[A-Z]{6}[0-9]{2}[ABCDEHLMPRST]{1}[0-9]{2}[A-Z]{1}[0-9]{3}[A-Z]{1}$", message = "Invalid tax code of receiver.")
    private String receiverTaxCode;

    @Pattern(regexp = "^[A-Z0-9]{2}$", message = "Id code. (codice identificativo)")
    private String idCode;

    /**
     * Constructs contributor section (Contribuente) for Excise, Standard forms
     *
     * @param taxCode         tax code (codice fiscale)
     * @param ifCalendarYear  if tax year coincide with calendar year (anno d’imposta non coincidente con anno solare)
     * @param personalData    PersonalData component (dati anagrafici)
     * @param taxResidence    TaxResidence component (domicilio fiscale)
     * @param receiverTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode          ID code (codice identificativo)
     */
    public Contributor(String taxCode, boolean ifCalendarYear, PersonalData personalData, TaxResidence taxResidence, String receiverTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.ifCalendarYear = ifCalendarYear;
        this.personalData = personalData;
        this.taxResidence = taxResidence;
        this.receiverTaxCode = receiverTaxCode;
        this.idCode = idCode;
    }

    /**
     * Constructs contributor section (Contribuente) for ELID form
     *
     * @param taxCode         tax code (codice fiscale)
     * @param personalData    personal data (dati anagrafici)
     * @param taxResidence    tax residence (domicilio fiscale)
     * @param receiverTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode          ID code (codice identificativo)
     */
    public Contributor(String taxCode, PersonalData personalData, TaxResidence taxResidence, String receiverTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.personalData = personalData;
        this.taxResidence = taxResidence;
        this.receiverTaxCode = receiverTaxCode;
        this.idCode = idCode;
    }

    /**
     * Constructs contributor section (Contribuente) for Simplified form
     *
     * @param taxCode         tax code (codice fiscale)
     * @param actCode         act code (codice atto)
     * @param officeCode      office code (codice ufficio)
     * @param personalData    personal data (dati anagrafici)
     * @param receiverTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode          ID code (codice identificativo)
     */
    public Contributor(String taxCode, String actCode, String officeCode, PersonalData personalData, String receiverTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.actCode = actCode;
        this.officeCode = officeCode;
        this.personalData = personalData;
        this.receiverTaxCode = receiverTaxCode;
        this.idCode = idCode;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public boolean isIfCalendarYear() {
        return ifCalendarYear;
    }

    public void setIfCalendarYear(boolean ifCalendarYear) {
        this.ifCalendarYear = ifCalendarYear;
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public TaxResidence getTaxResidence() {
        return taxResidence;
    }

    public void setTaxResidence(TaxResidence taxResidence) {
        this.taxResidence = taxResidence;
    }

    public String getReceiverTaxCode() {
        return receiverTaxCode;
    }

    public void setReceiverTaxCode(String receiverTaxCode) {
        this.receiverTaxCode = receiverTaxCode;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

}
