package org.f24.dto.component;

public class InpsRecord {

    private String headquartersCode;
    private String contributionReason;
    private String inpsCode;
    private ReportingPeriod reportingPeriod;
    private String debitAmount;
    private String creditAmount;

    /**
     * Constructs INPS record for INPS Section (Sezione INPS)
     *
     * @param headquartersCode   headquarters code (codice sede)
     * @param contributionReason contribution reason (causale contributo)
     * @param inpsCode           INPS number/INPS code/ company branch (matricola INPS/codice INPS/ filiale azienda)
     * @param reportingPeriod    ReportingPeriod component (periodo di riferimento)
     * @param debitAmount        debit amounts paid (importi a debito versati)
     * @param creditAmount       credit amounts offset (importi a credito compensati)
     */
    public InpsRecord(String headquartersCode, String contributionReason, String inpsCode, ReportingPeriod reportingPeriod, String debitAmount, String creditAmount) {
        this.headquartersCode = headquartersCode;
        this.contributionReason = contributionReason;
        this.inpsCode = inpsCode;
        this.reportingPeriod = reportingPeriod;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

}
