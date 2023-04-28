package org.f24.dto.component;

public class PersonalData {

    private String surname;

    private String name;

    private String birthdate;

    private String sex;

    private String birthMunicipality;

    private String province;

    public PersonalData() {}

    /**
     * Constructs personal data section of contributor (dati anagrafici)
     *
     * @param surname             surname/name or corporate name (cognome/denominazione o ragione sociale )
     * @param name                name (nome)
     * @param birthdate         date of birth (data di nascita)
     * @param sex                 sex (sesso)
     * @param birthMunicipality municipality (or foreign state) of birth (comune (o Stato estero) di nascita)
     * @param province            province (prov.)
     */
    public PersonalData(String surname, String name, String birthdate, String sex, String birthMunicipality, String province) {
        this.surname = surname;
        this.name = name;
        this.birthdate = birthdate;
        this.sex = sex;
        this.birthMunicipality = birthMunicipality;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthMunicipality() {
        return birthMunicipality;
    }

    public void setBirthMunicipality(String birthMunicipality) {
        this.birthMunicipality = birthMunicipality;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

}
