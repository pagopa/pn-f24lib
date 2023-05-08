package org.f24.dto.component;

public class TreasuryRecord extends Record {

    private String type;

    private String idElements;

    private String tributeCode;

    private String year;

    /**
     * Constructs Treasury record for Treasury and other section (Sezione erario ed altro)
     *
     * @param type        type (tipo)
     * @param idElements  identification elements (elementi identificativi)
     * @param tributeCode tribute code (codice)
     * @param year        reporting year (anno di riferimento)
     * @param debitAmount debit amounts paid (debit amounts paid)
     */
    public TreasuryRecord(String type, String idElements, String tributeCode, String year, String debitAmount) {
        super(debitAmount, "", "");
        this.type = type;
        this.idElements = idElements;
        this.tributeCode = tributeCode;
        this.year = year;
    }

    public TreasuryRecord() {
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

}
