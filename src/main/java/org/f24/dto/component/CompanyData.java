package org.f24.dto.component;

public class CompanyData {

    private String name;

    private TaxAddress taxAddress;

    public CompanyData() {
    }

    /**
     * Constructs personal data section of contributor (dati anagrafici PF)
     *
     * @param name       name. (denominazione)
     * @param taxAddress TaxAddress component (domicilio fiscale)
     */
    public CompanyData(String name, TaxAddress taxAddress) {
        this.name = name;
        this.taxAddress = taxAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaxAddress getTaxAddress() {
        return taxAddress;
    }

    public void setTaxAddress(TaxAddress taxAddress) {
        this.taxAddress = taxAddress;
    }

}
