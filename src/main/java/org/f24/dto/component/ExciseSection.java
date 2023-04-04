package org.f24.dto.component;

import java.util.List;

public class ExciseSection {

    private List<ExciseTax> exciseTaxList;
    private String officeCode;
    private String deedCode;

    /**
     * Constructs Excise Section (Sezione Accise)
     *
     * @param exciseTaxList list of ExciseTax components
     * @param officeCode    office code (codice ufficio)
     * @param deedCode      deed code (codice atto)
     */
    public ExciseSection(List<ExciseTax> exciseTaxList, String officeCode, String deedCode) {
        this.exciseTaxList = exciseTaxList;
        this.officeCode = officeCode;
        this.deedCode = deedCode;
    }

}
