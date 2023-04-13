package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(description = "Treasury Section (Sezione Erario) object")
public class TreasurySection {

    @Valid
    @Size(max = 6, message = "Maximum amount of records in treasury section (sezione erario) is 6.")
    private List<Tax> taxList;

    @NotBlank
    @Pattern(regexp = "^[A-Z0-9]{3}$", message = "Invalid office code. (codice ufficio)")
    private String officeCode;

    @NotBlank
    @Pattern(regexp = "^\\d{11}$", message = "Invalid act code. (codice atto)")
    private String actCode;

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

}
