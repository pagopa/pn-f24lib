package org.f24.dto.component;

import java.util.List;

public class TreasurySection extends Section {

    private List<Tax> taxList;

    private String officeCode;

    private String documentCode;

    public TreasurySection() {}

    /**
     * Constructs Treasury and other section (Sezione erario ed altro)
     *
     * @param taxList    list of Tax components
     * @param officeCode office code (codice ufficio)
     * @param documentCode    act code (codice atto)
     */
    public TreasurySection(List<Tax> taxList, String officeCode, String documentCode) {
        this.taxList = taxList;
        this.officeCode = officeCode;
        this.documentCode = documentCode;
    }

    public List<Tax> getTaxList() {
        return taxList;
    }

    public void setTaxList(List<Tax> taxList) {
        this.taxList = taxList;
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
