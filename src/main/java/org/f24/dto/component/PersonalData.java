package org.f24.dto.component;

import java.util.Date;

public class PersonalData {

    private String corporateName;
    private String name;
    private Date dateOfBirth;
    private int sex;
    private String municipalityOfBirth;
    private String province;

    //ToDo check province field translation

    /**
     * Constructs personal data section of contributor (dati anagrafici)
     *
     * @param corporateName       surname/name or corporate name (cognome/denominazione o ragione sociale )
     * @param name                name (nome)
     * @param dateOfBirth         date of birth (data di nascita)
     * @param sex                 sex (sesso)
     * @param municipalityOfBirth municipality (or foreign state) of birth (comune (o Stato estero) di nascita)
     * @param province            province (prov.)
     */

    public PersonalData(String corporateName, String name, Date dateOfBirth, int sex, String municipalityOfBirth, String province) {
        this.corporateName = corporateName;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.municipalityOfBirth = municipalityOfBirth;
        this.province = province;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
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
