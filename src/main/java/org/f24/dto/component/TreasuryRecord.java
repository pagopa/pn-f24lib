package org.f24.dto.component;

public class TreasuryRecord extends Record {

    private String type;

    private String idElements;

    private String tributeCode;

    private String reportingYear;

    private String debitAmount;

    public TreasuryRecord() {}

    /**
     * Constructs Treasury record for Treasury and other section (Sezione erario ed altro)
     *
     * @param type          type (tipo)
     * @param idElements    identification elements (elementi identificativi)
     * @param tributeCode   tribute code (codice)
     * @param reportingYear reporting year (anno di riferimento)
     * @param debitAmount   debit amounts paid (debit amounts paid)
     */
    public TreasuryRecord(String type, String idElements, String tributeCode, String reportingYear, String debitAmount) {
        this.type = type;
        this.idElements = idElements;
        this.tributeCode = tributeCode;
        this.reportingYear = reportingYear;
        this.debitAmount = debitAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdElements() {
        return idElements;
    }

    public void setIdElements(String idElements) {
        this.idElements = idElements;
    }

    public String getTributeCode() {
        return tributeCode;
    }

    public void setTributeCode(String tributeCode) {
        this.tributeCode = tributeCode;
    }

    public String getReportingYear() {
        return reportingYear;
    }

    public void setReportingYear(String reportingYear) {
        this.reportingYear = reportingYear;
    }

    public String getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(String debitAmount) {
        this.debitAmount = debitAmount;
    }

}
