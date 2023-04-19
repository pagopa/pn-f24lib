package org.f24.dto.component;

public class PersonData {

    private PersonalData personalData;

    private TaxResidence taxResidence;

    public PersonData() {}

    /**
     * Constructs personal data section of contributor (dati anagrafici PF)
     *
     * @param personalData PersonalData component (dati anagrafici)
     * @param taxResidence TaxResidence component (domicilio fiscale)
     */
    public PersonData(PersonalData personalData, TaxResidence taxResidence) {
        this.personalData = personalData;
        this.taxResidence = taxResidence;
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

}
