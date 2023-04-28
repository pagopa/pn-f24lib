package org.f24.dto.component;

public class ExciseTax {

    private String institution;

    private String province;

    private String idCode;

    private String tributeCode;

    private String installment;

    private String month;

    private String year;

    private String debitAmount;

    /**
     * Constructs excise tax for excise section (sezione Accise)
     *
     * @param institution   institution (ente)
     * @param province      province (prov.)
     * @param idCode        ID Code (codice identificativo )
     * @param tributeCode   tribute code (codice tributo)
     * @param installment   installment (rateazione)
     * @param month         month (mese rif.)
     * @param year reporting year (anno di riferimento)
     * @param debitAmount   debit amounts paid (debit amounts paid)
     */
    public ExciseTax(String institution, String province, String idCode, String tributeCode, String installment, String month, String year, String debitAmount) {
        this.institution = institution;
        this.province = province;
        this.idCode = idCode;
        this.tributeCode = tributeCode;
        this.installment = installment;
        this.month = month;
        this.year = year;
        this.debitAmount = debitAmount;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getTributeCode() {
        return tributeCode;
    }

    public void setTributeCode(String tributeCode) {
        this.tributeCode = tributeCode;
    }

    public String getInstallment() {
        return installment;
    }

    public void setInstallment(String installment) {
        this.installment = installment;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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
