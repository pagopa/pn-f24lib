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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTributeCode() {
        return tributeCode;
    }

    public void setTributeCode(String tributeCode) {
        this.tributeCode = tributeCode;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getRavv() {
        return ravv;
    }

    public void setRavv(String ravv) {
        this.ravv = ravv;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getNumberOfBuildings() {
        return numberOfBuildings;
    }

    public void setNumberOfBuildings(String numberOfBuildings) {
        this.numberOfBuildings = numberOfBuildings;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDeduction() {
        return deduction;
    }

    public void setDeduction(String deduction) {
        this.deduction = deduction;
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

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }

}
