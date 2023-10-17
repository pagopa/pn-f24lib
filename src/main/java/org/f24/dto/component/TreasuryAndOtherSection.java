package org.f24.dto.component;

import java.util.List;

public class TreasuryAndOtherSection {

    private String officeCode;

    private String documentCode;

    private List<TreasuryRecord> treasuryRecords;

    public TreasuryAndOtherSection() {}

    /**
     * Constructs treasury and other section
     *
     * @param officeCode      office code (codice ufficio)
     * @param documentCode    document code (codice atto)
     * @param treasuryRecords list of TreasuryRecord components
     */
    public TreasuryAndOtherSection(String officeCode, String documentCode, List<TreasuryRecord> treasuryRecords) {
        this.officeCode = officeCode;
        this.documentCode = documentCode;
        this.treasuryRecords = treasuryRecords;
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

    public List<TreasuryRecord> getTreasuryRecords() {
        return treasuryRecords;
    }

    public void setTreasuryRecords(List<TreasuryRecord> treasuryRecords) {
        this.treasuryRecords = treasuryRecords;
    }

}
