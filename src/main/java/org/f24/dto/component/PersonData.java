package org.f24.dto.component;

public class PersonData {

    private PersonalData personalData;

    private TaxAddress taxAddress;

    public PersonData() {
    }

    /**
     * Constructs personal data section of contributor (dati anagrafici PF)
     *
     * @param personalData PersonalData component (dati anagrafici)
     * @param taxAddress TaxResidence component (domicilio fiscale)
     */
    public PersonData(PersonalData personalData, TaxAddress taxAddress) {
        this.personalData = personalData;
        this.taxAddress = taxAddress;
    }

    /**
     * Constructs personal data section of contributor (dati anagrafici PF) without tax residence
     *
     * @param personalData PersonalData component (dati anagrafici)
     */
    public PersonData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public TaxAddress getTaxAddress() {
        return taxAddress;
    }

    public void setTaxAddress(TaxAddress taxAddress) {
        this.taxAddress = taxAddress;
    }

}
