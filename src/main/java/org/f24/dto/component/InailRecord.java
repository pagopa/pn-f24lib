package org.f24.dto.component;

public class InailRecord {

    private String locationCode;
    private String companyCode;

    //ToDo translate cc
    private String cc;

    private String referenceNumber;
    private String reason;
    private String debitAmount;
    private String creditAmount;

    /**
     * Constructs INAIL Record for Other social security and insurance institutions (sezione altri enri previdenziali e assicuretivi)
     *
     * @param locationCode    location code (codice sede)
     * @param companyCode     company code (codice ditta)
     * @param cc
     * @param referenceNumber reference number (numero di riferimento)
     * @param reason          reason (causale)
     * @param debitAmount     debit amounts paid (importi a debito versati)
     * @param creditAmount    credit amounts offset (importi a credito compensati)
     */
    public InailRecord(String locationCode, String companyCode, String cc, String referenceNumber, String reason, String debitAmount, String creditAmount) {
        this.locationCode = locationCode;
        this.companyCode = companyCode;
        this.cc = cc;
        this.referenceNumber = referenceNumber;
        this.reason = reason;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

}
