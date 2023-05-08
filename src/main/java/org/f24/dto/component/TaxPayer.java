package org.f24.dto.component;

public class TaxPayer {

    private String taxCode;

    private boolean isNotTaxYear;

    private PersonData personData;

    private CompanyData companyData;

    private String relativePersonTaxCode;

    private String idCode;

    private String documentCode;

    private String officeCode;

    public TaxPayer() {
    }

    /**
     * Constructs taxPayer section (Contribuente) for Excise, Standard forms with Person Data
     *
     * @param taxCode               tax code (codice fiscale)
     * @param isNotTaxYear          if tax year coincide with calendar year (anno d’imposta non coincidente con anno solare)
     * @param personData            PersonData component (dati anagrafici PF)
     * @param relativePersonTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode                ID code (codice identificativo)
     */
    public TaxPayer(String taxCode, boolean isNotTaxYear, PersonData personData, String relativePersonTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.isNotTaxYear = isNotTaxYear;
        this.personData = personData;
        this.relativePersonTaxCode = relativePersonTaxCode;
        this.idCode = idCode;
    }

    /**
     * Constructs taxPayer section (Contribuente) for Excise, Standard forms with Company Data
     *
     * @param taxCode               tax code (codice fiscale)
     * @param isNotTaxYear          if tax year coincide with calendar year (anno d’imposta non coincidente con anno solare)
     * @param companyData           CompanyData component (dati anagrafici PNF)
     * @param relativePersonTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode                ID code (codice identificativo)
     */
    public TaxPayer(String taxCode, boolean isNotTaxYear, CompanyData companyData, String relativePersonTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.isNotTaxYear = isNotTaxYear;
        this.companyData = companyData;
        this.relativePersonTaxCode = relativePersonTaxCode;
        this.idCode = idCode;
    }

    /**
     * Constructs taxPayerStandart section for Standard forms with Company Data adn PersonData
     *
     * @param taxCode               tax code (codice fiscale)
     * @param isNotTaxYear          if tax year coincide with calendar year (anno d’imposta non coincidente con anno solare)
     * @param companyData           CompanyData component (dati anagrafici PNF)
     * @param relativePersonTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode                ID code (codice identificativo)
     */
    public TaxPayer(String taxCode, boolean isNotTaxYear, PersonData personData, CompanyData companyData, String relativePersonTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.isNotTaxYear = isNotTaxYear;
        this.personData = personData;
        this.companyData = companyData;
        this.relativePersonTaxCode = relativePersonTaxCode;
        this.idCode = idCode;
    }

    /**
     * Constructs taxPayer section (Contribuente) for Simplified (Semplificato) Form
     *
     * @param taxCode               tax code (codice fiscale)
     * @param documentCode          act code
     * @param officeCode            office code
     * @param personData            PersonData component (dati anagrafici PF)
     * @param relativePersonTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode                ID code (codice identificativo)
     */
    public TaxPayer(String taxCode, String documentCode, String officeCode, PersonData personData, String relativePersonTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.personData = personData;
        this.relativePersonTaxCode = relativePersonTaxCode;
        this.idCode = idCode;
        this.documentCode = documentCode;
        this.officeCode = officeCode;
    }

    /**
     * Constructs taxPayer section (Contribuente) for Elid Form with Person data
     *
     * @param taxCode               tax code (codice fiscale)
     * @param personData            PersonData component (dati anagrafici PF)
     * @param relativePersonTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode                ID code (codice identificativo)
     */
    public TaxPayer(String taxCode, PersonData personData, String relativePersonTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.personData = personData;
        this.relativePersonTaxCode = relativePersonTaxCode;
        this.idCode = idCode;
    }

    /**
     * Constructs taxPayer section (Contribuente) for Elid Form with Company data
     *
     * @param taxCode               tax code (codice fiscale)
     * @param companyData           CompanyData component
     * @param relativePersonTaxCode tax code of the co-obligor, heir, parent, guardian or receiver (codice fiscal del coobbligato, erede, genitore, tutore o curatore fallimentare)
     * @param idCode                ID code (codice identificativo)
     */
    public TaxPayer(String taxCode, CompanyData companyData, String relativePersonTaxCode, String idCode) {
        this.taxCode = taxCode;
        this.companyData = companyData;
        this.relativePersonTaxCode = relativePersonTaxCode;
        this.idCode = idCode;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public boolean getIsNotTaxYear() {
        return isNotTaxYear;
    }

    public void setIsNotTaxYear(boolean isNotTaxYear) {
        this.isNotTaxYear = isNotTaxYear;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
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

    public String getRelativePersonTaxCode() {
        return relativePersonTaxCode;
    }

    public void setRelativePersonTaxCode(String relativePersonTaxCode) {
        this.relativePersonTaxCode = relativePersonTaxCode;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

}
