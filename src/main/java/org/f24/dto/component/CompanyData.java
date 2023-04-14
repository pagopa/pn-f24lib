package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Schema(description = "Company Data (Dati Anagrafici PNF) object")
public class CompanyData {

    @Pattern(regexp = "^[A-Z]{1,60}$", message = "Invalid name. (denominazione)")
    private String name;

    @Valid
    private TaxResidence taxResidence;

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
