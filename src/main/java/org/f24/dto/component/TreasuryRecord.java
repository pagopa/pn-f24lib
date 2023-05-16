package org.f24.dto.component;

public class TreasuryRecord extends Record {

    private String type;

    private String idElements;

    private String taxTypeCode;

    private String year;

    /**
     * Constructs Treasury record for Treasury and other section (Sezione erario ed altro)
     *
     * @param type        type (tipo)
     * @param idElements  identification elements (elementi identificativi)
     * @param taxTypeCode tax type code (codice)
     * @param year        reporting year (anno di riferimento)
     * @param debitAmount debit amounts paid (debit amounts paid)
     */
    public TreasuryRecord(String type, String idElements, String taxTypeCode, String year, String debitAmount) {
        super(debitAmount, "", "");
        this.type = type;
        this.idElements = idElements;
        this.taxTypeCode = taxTypeCode;
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

    public String getTaxTypeCode() {
        return taxTypeCode;
    }

    public void setTaxTypeCode(String taxTypeCode) {
        this.taxTypeCode = taxTypeCode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
