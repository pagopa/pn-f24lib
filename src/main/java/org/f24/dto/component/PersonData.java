package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;

@Schema(description = "Person Data (Dati Anagrafici PF) object")
public class PersonData {

    @Valid
    private PersonalData personalData;

    @Valid
    private TaxResidence taxResidence;

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
