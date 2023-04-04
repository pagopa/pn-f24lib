package org.f24.dto.component;

public class TreasuryRecord {

    private String type;
    private String idElements;
    private String code;
    private String yearOfElements;
    private String debitAmount;

    /**
     * Constructs Treasury record for Treasury and other section (Sezione erario ed altro)
     *
     * @param type           type (tipo)
     * @param idElements     identification elements (elementi identificativi)
     * @param code           code (codice)
     * @param yearOfElements year of elements (anno di elementi)
     * @param debitAmount    debit amounts paid (debit amounts paid)
     */
    public TreasuryRecord(String type, String idElements, String code, String yearOfElements, String debitAmount) {
        this.type = type;
        this.idElements = idElements;
        this.code = code;
        this.yearOfElements = yearOfElements;
        this.debitAmount = debitAmount;
    }

}
