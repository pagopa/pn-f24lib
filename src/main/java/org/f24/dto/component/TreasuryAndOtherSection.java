package org.f24.dto.component;

import java.util.List;

public class TreasuryAndOtherSection {

    private String officeCode;

    private String actCode;

    private List<TreasuryRecord> treasuryRecords;

    public TreasuryAndOtherSection() {}

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
