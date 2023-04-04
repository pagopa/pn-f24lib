package org.f24.dto.component;

public class SocialSecurityRecord {

    private String institutionCode;
    private String locationCode;
    private String contributionReason;
    private String positionCode;
    private ReportingPeriod period;
    private String debitAmount;
    private String creditAmount;

    /**
     * Constructs Social Security Record for Other social security and insurance institutions (sezione altri enri previdenziali e assicuretivi)
     *
     * @param institutionCode    institution code (codice ente)
     * @param locationCode       location code (codice sede)
     * @param contributionReason contribution reason (causale contributo)
     * @param positionCode       position code (codice posizione)
     * @param period             ReportingPeriod component (periodo di riferimento)
     * @param debitAmount        debit amounts paid (importi a debito versati)
     * @param creditAmount       credit amounts offset (importi a credito compensati)
     */
    public SocialSecurityRecord(String institutionCode, String locationCode, String contributionReason, String positionCode, ReportingPeriod period, String debitAmount, String creditAmount) {
        this.institutionCode = institutionCode;
        this.locationCode = locationCode;
        this.contributionReason = contributionReason;
        this.positionCode = positionCode;
        this.period = period;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

}
