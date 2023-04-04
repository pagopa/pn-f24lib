package org.f24.dto.component;

public class ExciseTax {

    private String institution;
    //ToDo check translation of prov.
    private String province;
    private String idCode;
    private String tributeCode;
    private String installment;
    private String month;
    private String reportingYear;
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
     * @param reportingYear reporting year (anno di riferimento)
     * @param debitAmount   debit amounts paid (debit amounts paid)
     */
    public ExciseTax(String institution, String province, String idCode, String tributeCode, String installment, String month, String reportingYear, String debitAmount) {
        this.institution = institution;
        this.province = province;
        this.idCode = idCode;
        this.tributeCode = tributeCode;
        this.installment = installment;
        this.month = month;
        this.reportingYear = reportingYear;
        this.debitAmount = debitAmount;
    }

}
