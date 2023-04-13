package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(description = "Excise Section (Accise) object")
public class ExciseSection {

    @Valid
    @Size(max = 4, message = "Maximum amount of records in Excise section (Accise) is 7.")
    private List<ExciseTax> exciseTaxList;

    @Pattern(regexp = "^[A-Z0-9]{3}$", message = "Invalid office code. (codice ufficio)")
    private String officeCode;

    @Pattern(regexp = "^\\d{11}$", message = "Invalid act code. (codice atto)")
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
