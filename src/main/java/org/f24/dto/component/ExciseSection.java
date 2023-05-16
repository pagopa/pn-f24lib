package org.f24.dto.component;

import java.util.List;

public class ExciseSection {

    private List<ExciseTax> exciseTaxList;

    private String officeCode;

    private String documentCode;

    /**
     * Constructs Excise Section (Sezione Accise)
     *
     * @param exciseTaxList list of ExciseTax components
     * @param officeCode    office code (codice ufficio)
     * @param documentCode  document code (codice atto)
     */
    public ExciseSection(List<ExciseTax> exciseTaxList, String officeCode, String documentCode) {
        this.exciseTaxList = exciseTaxList;
        this.officeCode = officeCode;
        this.documentCode = documentCode;
    }

    public ExciseSection() {
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

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }
}
