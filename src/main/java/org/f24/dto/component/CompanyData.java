package org.f24.dto.component;

public class CompanyData {

    private String name;

    private TaxResidence taxResidence;

    public CompanyData() {}

    /**
     * Constructs personal data section of contributor (dati anagrafici PF)
     *
     * @param name         name. (denominazione)
     * @param taxResidence TaxResidence component (domicilio fiscale)
     */
    public CompanyData(String name, TaxResidence taxResidence) {
        this.name = name;
        this.taxResidence = taxResidence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaxResidence getTaxResidence() {
        return taxResidence;
    }

    public void setTaxResidence(TaxResidence taxResidence) {
        this.taxResidence = taxResidence;
    }

}
