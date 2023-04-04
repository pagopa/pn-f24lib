package org.f24.dto.component;

import java.util.List;

public class TreasuryAndOtherSection {

    private String officeCode;
    private String deedCode;
    private List<TreasuryRecord> treasuryRecords;

    /**
     * Constructs treasury and other section
     *
     * @param officeCode      office code (codice ufficio)
     * @param deedCode        deed code (codice atto)
     * @param treasuryRecords list of TreasuryRecord components
     */
    public TreasuryAndOtherSection(String officeCode, String deedCode, List<TreasuryRecord> treasuryRecords) {
        this.officeCode = officeCode;
        this.deedCode = deedCode;
        this.treasuryRecords = treasuryRecords;
    }

}
