package org.f24.dto.component;

public class TaxPayer {

    private String taxCode;

    private boolean isNotCalendarYear;

    private PersonData personData;

    private CompanyData companyData;

    private String otherTaxCode;

    private String idCode;

    private String actCode;

    private String officeCode;

    public TaxPayer() {
    }

    /**
     * Constructs contributor section (Contribuente) for Excise, Standard forms with Person Data
     *
     * @param taxCode         tax code (codice fiscale)
     * @param isNotCalendarYear  if tax year coincide with calendar year (anno d’imposta non coincidente con anno solare)
     * @param personData      PersonData component (dati anagrafici PF)
     * @param otherTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode          ID code (codice identificativo)
     */
    public TaxPayer(String taxCode, boolean isNotCalendarYear, PersonData personData, String otherTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.isNotCalendarYear = isNotCalendarYear;
        this.personData = personData;
        this.otherTaxCode = otherTaxCode;
        this.idCode = idCode;
    }

    /**
     * Constructs contributor section (Contribuente) for Excise, Standard forms with Company Data
     *
     * @param taxCode         tax code (codice fiscale)
     * @param isNotCalendarYear  if tax year coincide with calendar year (anno d’imposta non coincidente con anno solare)
     * @param companyData     CompanyData component (dati anagrafici PNF)
     * @param otherTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode          ID code (codice identificativo)
     */
    public TaxPayer(String taxCode, boolean isNotCalendarYear, CompanyData companyData, String otherTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.isNotCalendarYear = isNotCalendarYear;
        this.companyData = companyData;
        this.otherTaxCode = otherTaxCode;
        this.idCode = idCode;
    }

    /**
     * Constructs contributor section (Contribuente) for Simplified (Semplificato) Form
     *
     * @param taxCode         tax code (codice fiscale)
     * @param actCode         act code
     * @param officeCode      office code
     * @param personData      PersonData component (dati anagrafici PF)
     * @param otherTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode          ID code (codice identificativo)
     */
    public TaxPayer(String taxCode, String actCode, String officeCode, PersonData personData, String otherTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.actCode = actCode;
        this.officeCode = officeCode;
        this.personData = personData;
        this.otherTaxCode = otherTaxCode;
        this.idCode = idCode;
    }

    /**
     * Constructs contributor section (Contribuente) for Elid Form with Person data
     *
     * @param taxCode         tax code (codice fiscale)
     * @param personData      PersonData component (dati anagrafici PF)
     * @param otherTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode          ID code (codice identificativo)
     */
    public TaxPayer(String taxCode, PersonData personData, String otherTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.personData = personData;
        this.otherTaxCode = otherTaxCode;
        this.idCode = idCode;
    }

    /**
     * Constructs contributor section (Contribuente) for Elid Form with Company data
     *
     * @param taxCode         tax code (codice fiscale)
     * @param companyData     CompanyData component
     * @param otherTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode          ID code (codice identificativo)
     */
    public TaxPayer(String taxCode, CompanyData companyData, String otherTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.companyData = companyData;
        this.otherTaxCode = otherTaxCode;
        this.idCode = idCode;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public boolean isNotCalendarYear() {
        return isNotCalendarYear;
    }

    public void setNotCalendarYear(boolean notCalendarYear) {
        this.isNotCalendarYear = notCalendarYear;
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

    public String getOtherTaxCode() {
        return otherTaxCode;
    }

    public void setOtherTaxCode(String otherTaxCode) {
        this.otherTaxCode = otherTaxCode;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

}
