package org.f24.dto.component;

public class TreasuryRecord {

    private String type;

    private String idElements;

    private String tributeCode;

    private String year;

    private String debitAmount;

    /**
     * Constructs Treasury record for Treasury and other section (Sezione erario ed altro)
     *
     * @param type          type (tipo)
     * @param idElements    identification elements (elementi identificativi)
     * @param tributeCode   tribute code (codice)
     * @param year reporting year (anno di riferimento)
     * @param debitAmount   debit amounts paid (debit amounts paid)
     */
    public TreasuryRecord(String type, String idElements, String tributeCode, String year, String debitAmount) {
        this.type = type;
        this.idElements = idElements;
        this.tributeCode = tributeCode;
        this.year = year;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(String debitAmount) {
        this.debitAmount = debitAmount;
    }

}
