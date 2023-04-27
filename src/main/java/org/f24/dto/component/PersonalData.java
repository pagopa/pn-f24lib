package org.f24.dto.component;

public class PersonalData {

    private String surname;

    private String name;

    private String dateOfBirth;

    private String sex;

    private String municipalityOfBirth;

    private String province;

    public PersonalData() {}

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
