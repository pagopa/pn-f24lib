package org.f24.dto.component;

public class Contributor {

    private String taxCode;

    private boolean ifCalendarYear;

    private PersonData personData;

    private CompanyData companyData;

    private String receiverTaxCode;

    private String idCode;

    private String actCode;

    private String officeCode;

    public Contributor() {}

    /**
     * Constructs contributor section (Contribuente) for Excise, Standard forms
     *
     * @param taxCode         tax code (codice fiscale)
     * @param ifCalendarYear  if tax year coincide with calendar year (anno d’imposta non coincidente con anno solare)
     * @param personData      PersonData component (dati anagrafici PF)
     * @param receiverTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode          ID code (codice identificativo)
     */
    public Contributor(String taxCode, boolean ifCalendarYear, String actCode, String officeCode, PersonData personData, String receiverTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.ifCalendarYear = ifCalendarYear;
        this.actCode = actCode;
        this.officeCode = officeCode;
        this.personData = personData;
        this.receiverTaxCode = receiverTaxCode;
        this.idCode = idCode;
    }

    /**
     * Constructs contributor section (Contribuente) for Excise, Standard forms
     *
     * @param taxCode         tax code (codice fiscale)
     * @param ifCalendarYear  if tax year coincide with calendar year (anno d’imposta non coincidente con anno solare)
     * @param companyData     CompanyData component (dati anagrafici PNF)
     * @param receiverTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode          ID code (codice identificativo)
     */
    public Contributor(String taxCode, boolean ifCalendarYear, String actCode, String officeCode, CompanyData companyData, String receiverTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.ifCalendarYear = ifCalendarYear;
        this.actCode = actCode;
        this.officeCode = officeCode;
        this.companyData = companyData;
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

    public PersonData getPersonData() {
        return personData;
    }

    public void setPersonData(PersonData personData) {
        this.personData = personData;
    }

    public CompanyData getCompanyData() {
        return companyData;
    }

    public void setCompanyData(CompanyData companyData) {
        this.companyData = companyData;
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
