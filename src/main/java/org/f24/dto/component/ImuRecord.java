package org.f24.dto.component;

public class ImuRecord {

    private String operationId;
    private String municipalityCode;

    //ToDo translate next 2 fields
    private String ravv;
    private String acc;

    private String building;
    private String numberOfBuildings;
    private String balance;
    private String tributeCode;
    private String installment;
    private String reportingYear;
    private String debitAmount;
    private String creditAmount;

    /**
     * Constructs IMU record for IMU Section (IMU e altri tributi locali)
     *
     * @param operationId       operation identifier (identicativo operazione)
     * @param municipalityCode  entity code/municipality code (codice ente/codice comune)
     * @param ravv
     * @param building          buildings (immobiliare variati)
     * @param acc
     * @param numberOfBuildings number of buildings (numero immobili)
     * @param balance           balance (saldo)
     * @param tributeCode       tribute code (codice tributo)
     * @param installment       installment/month ref. (rateazione/ mese rif.)
     * @param reportingYear     reporting year (anno di riferimento)
     * @param debitAmount       debit amounts paid (importi a debito versati )
     * @param creditAmount      credit amounts offset (importi a credito compensati )
     */
    public ImuRecord(String operationId, String municipalityCode, String ravv, String building, String acc, String numberOfBuildings, String balance, String tributeCode, String installment, String reportingYear, String debitAmount, String creditAmount) {
        this.operationId = operationId;
        this.municipalityCode = municipalityCode;
        this.ravv = ravv;
        this.building = building;
        this.acc = acc;
        this.numberOfBuildings = numberOfBuildings;
        this.balance = balance;
        this.tributeCode = tributeCode;
        this.installment = installment;
        this.reportingYear = reportingYear;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

}
