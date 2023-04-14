package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Pattern;

@Schema(description = "Personal Data (Dati Anagrafici) object")
public class PersonalData {

    @Pattern(regexp = "^[A-Z]{1,24}$", message = "Invalid surname. (cognome)")
    private String surname;

    @Pattern(regexp = "^[A-Z]{1,20}$", message = "Invalid name. (nome)")
    private String name;

    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "Invalid date of birth. (data di nascita)")
    private String dateOfBirth;

    @Pattern(regexp = "^[FM]$",message = "Invalid sex. (sesso)")
    private String sex;

    @Pattern(regexp = "^[A-Z]{1,40}$", message = "Invalid municipality of birth. (comune o stato estero di nascita)")
    private String municipalityOfBirth;

    @Pattern(regexp = "^[A-Z]{2}$", message = "Invalid province. (provincia)")
    private String province;

    /**
     * Constructs personal data section of contributor (dati anagrafici)
     *
     * @param surname             surname/name or corporate name (cognome/denominazione o ragione sociale )
     * @param name                name (nome)
     * @param dateOfBirth         date of birth (data di nascita)
     * @param sex                 sex (sesso)
     * @param municipalityOfBirth municipality (or foreign state) of birth (comune (o Stato estero) di nascita)
     * @param province            province (prov.)
     */
    public PersonalData(String surname, String name, String dateOfBirth, String sex, String municipalityOfBirth, String province) {
        this.surname = surname;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.municipalityOfBirth = municipalityOfBirth;
        this.province = province;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMunicipalityOfBirth() {
        return municipalityOfBirth;
    }

    public void setMunicipalityOfBirth(String municipalityOfBirth) {
        this.municipalityOfBirth = municipalityOfBirth;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

}
