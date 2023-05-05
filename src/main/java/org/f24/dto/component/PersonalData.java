package org.f24.dto.component;

public class PersonalData {

    private String surname;

    private String name;

    private String birthDate;

    private String sex;

    private String birthPlace;

    private String birthProvince;

    public PersonalData() {}

    /**
     * Constructs personal data section of contributor (dati anagrafici)
     *
     * @param surname             surname/name or corporate name (cognome/denominazione o ragione sociale )
     * @param name                name (nome)
     * @param birthDate         date of birth (data di nascita)
     * @param sex                 sex (sesso)
     * @param birthPlace municipality (or foreign state) of birth (comune (o Stato estero) di nascita)
     * @param birthProvince            birthProvince (prov.)
     */
    public PersonalData(String surname, String name, String birthDate, String sex, String birthPlace, String birthProvince) {
        this.surname = surname;
        this.name = name;
        this.birthDate = birthDate;
        this.sex = sex;
        this.birthPlace = birthPlace;
        this.birthProvince = birthProvince;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBirthProvince() {
        return birthProvince;
    }

    public void setBirthProvince(String birthProvince) {
        this.birthProvince = birthProvince;
    }

}
