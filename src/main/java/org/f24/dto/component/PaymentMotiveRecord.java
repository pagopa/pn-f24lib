package org.f24.dto.component;

public class PaymentMotiveRecord {

    private String section;
    private String tributeCode;
    private String institutionCode;

    //ToDo translate next 2 fields in English
    private String ravv;
    private String acc;

    private String balance;
    private String building;
    private String numberOfBuildings;
    private String month;
    private String deduction;
    private String reportingYear;
    private String debitAmount;
    private String creditAmount;

    /**
     * Constructs record for Motive for Payment Section (Motivo del Pergamento)
     *
     * @param section           section (sezione)
     * @param tributeCode       tribute code (cod. tributo)
     * @param institutionCode   institution code (codice ente )
     * @param ravv              (ravv.)
     * @param building          building (immob. variati)
     * @param acc               ( acc.)
     * @param balance           balance (saldo)
     * @param numberOfBuildings number of buildings (num. immob.)
     * @param month             month (rateazione/mese)
     * @param deduction         deduction (detrazione)
     * @param reportingYear     reporting year (anno di riferimento)
     * @param debitAmount       debit amounts paid (importi a debito versati)
     * @param creditAmount      credit amounts offset (importi a credito compensati)
     */
    public PaymentMotiveRecord(String section, String tributeCode, String institutionCode, String ravv, String building, String acc, String balance, String numberOfBuildings, String month, String deduction, String reportingYear, String debitAmount, String creditAmount) {
        this.section = section;
        this.tributeCode = tributeCode;
        this.institutionCode = institutionCode;
        this.ravv = ravv;
        this.building = building;
        this.acc = acc;
        this.balance = balance;
        this.numberOfBuildings = numberOfBuildings;
        this.month = month;
        this.deduction = deduction;
        this.reportingYear = reportingYear;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

}
