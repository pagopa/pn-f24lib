package org.f24.dto.component;

public class ExciseTax extends Record {

    private String municipality;

    private String province;

    private String idCode;

    private String taxTypeCode;

    private String installment;

    private String month;

    private String year;

    /**
     * Constructs excise tax for excise section (sezione Accise)
     *
     * @param municipality municipality (ente)
     * @param province     province (prov.)
     * @param idCode       ID Code (codice identificativo )
     * @param taxTypeCode  tax type code (codice tributo)
     * @param installment  installment (rateazione)
     * @param month        month (mese rif.)
     * @param year         reporting year (anno di riferimento)
     * @param debitAmount  debit amounts paid (debit amounts paid)
     */
    public ExciseTax(String municipality, String province, String idCode, String taxTypeCode, String installment, String month, String year, String debitAmount) {
        super(debitAmount, "", "");
        this.municipality = municipality;
        this.province = province;
        this.idCode = idCode;
        this.taxTypeCode = taxTypeCode;
        this.installment = installment;
        this.month = month;
        this.year = year;
    }

    public ExciseTax() {
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
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

    public String getTaxTypeCode() {
        return taxTypeCode;
    }

    public void setTaxTypeCode(String taxTypeCode) {
        this.taxTypeCode = taxTypeCode;
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

}
