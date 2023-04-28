package org.f24.dto.component;

import java.util.List;

import org.f24.exception.ResourceException;

public class TreasurySection extends Section {

    private List<Tax> taxList;

    private String officeCode;

    private String actCode;

    public TreasurySection() {}

    /**
     * Constructs Treasury and other section (Sezione erario ed altro)
     *
     * @param taxList    list of Tax components
     * @param officeCode office code (codice ufficio)
     * @param actCode    act code (codice atto)
     */
    public TreasurySection(List<Tax> taxList, String officeCode, String actCode) {
        this.taxList = taxList;
        this.officeCode = officeCode;
        this.actCode = actCode;
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

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    @Override
    public Double getTotalAmount(List<? extends Record> record) throws ResourceException {
        return super.getTotalAmount(record);
    }

}
