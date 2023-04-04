package org.f24.dto.component;

import java.util.List;

public class TreasurySection {

    private List<Tax> taxList;
    private String officeCode;
    private String deedCode;

    /**
     * Constructs Treasury and other section (Sezione erario ed altro)
     *
     * @param taxList    list of Tax components
     * @param officeCode office code (codice ufficio)
     * @param deedCode   deed code (codice atto)
     */
    public TreasurySection(List<Tax> taxList, String officeCode, String deedCode) {
        this.taxList = taxList;
        this.officeCode = officeCode;
        this.deedCode = deedCode;
    }

}
