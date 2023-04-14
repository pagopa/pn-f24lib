package org.f24.dto.component;

import java.util.List;

public class ExciseSection {

    private List<ExciseTax> exciseTaxList;

    private String officeCode;

    private String actCode;

    /**
     * Constructs Excise Section (Sezione Accise)
     *
     * @param exciseTaxList list of ExciseTax components
     * @param officeCode    office code (codice ufficio)
     * @param actCode       act code (codice atto)
     */
    public ExciseSection(List<ExciseTax> exciseTaxList, String officeCode, String actCode) {
        this.exciseTaxList = exciseTaxList;
        this.officeCode = officeCode;
        this.actCode = actCode;
    }

    public List<ExciseTax> getExciseTaxList() {
        return exciseTaxList;
    }

    public void setExciseTaxList(List<ExciseTax> exciseTaxList) {
        this.exciseTaxList = exciseTaxList;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

}
