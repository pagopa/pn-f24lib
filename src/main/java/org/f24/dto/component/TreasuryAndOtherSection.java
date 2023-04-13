package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(description = "Treasury ans Other Section (Sezione Erario e Altro) object")
public class TreasuryAndOtherSection {

    @Pattern(regexp = "^[A-Z0-9]{3}$", message = "Invalid office code. (codice ufficio)")
    private String officeCode;

    @Pattern(regexp = "^\\d{11}$", message = "Invalid act code. (codice atto)")
    private String actCode;

    @Valid
    @Size(max = 28, message = "Maximum amount of records in Treasury and Other Section (Sezione Erario e Altro) is 28.")
    private List<TreasuryRecord> treasuryRecords;

    /**
     * Constructs treasury and other section
     *
     * @param officeCode      office code (codice ufficio)
     * @param actCode         act code (codice atto)
     * @param treasuryRecords list of TreasuryRecord components
     */
    public TreasuryAndOtherSection(String officeCode, String actCode, List<TreasuryRecord> treasuryRecords) {
        this.officeCode = officeCode;
        this.actCode = actCode;
        this.treasuryRecords = treasuryRecords;
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

    public List<TreasuryRecord> getTreasuryRecords() {
        return treasuryRecords;
    }

    public void setTreasuryRecords(List<TreasuryRecord> treasuryRecords) {
        this.treasuryRecords = treasuryRecords;
    }

}
