package org.f24.dto.component;

public class Contributor {

    private String taxCode;
    private boolean ifCalendarYear;
    private String deedCode;
    private String officeCode;
    private PersonalData personalData;
    private TaxResidence taxResidence;
    private String receiverTaxCode;
    private String idCode;

    /**
     * Constructs contributor section (Contribuente) for Excise, IMU forms
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
     * @param deedCode        deed code (codice atto)
     * @param officeCode      office code (codice ufficio)
     * @param personalData    personal data (dati anagrafici)
     * @param receiverTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode          ID code (codice identificativo)
     */
    public Contributor(String taxCode, String deedCode, String officeCode, PersonalData personalData, String receiverTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.deedCode = deedCode;
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

    public String getDeedCode() {
        return deedCode;
    }

    public void setDeedCode(String deedCode) {
        this.deedCode = deedCode;
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
